package app.cddic.com.smarter.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Message;

import app.cddic.com.smarter.R;
import app.cddic.com.smarter.activity.base.TestActivity;
import app.cddic.com.smarter.entity.*;
import app.cddic.com.smarter.global.SmartApplication;
import app.cddic.com.smarter.service.protocol.Protocol;
import app.cddic.com.smarter.utils.StaticClass;

/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.service
 * 文件名：  SmartService
 * 创建者：  zhl
 * 创建时间： 2017/3/6 11:24
 * 描述：服务类
 */

public class SmartService extends Service implements CallBackFromNet {

    boolean isActTop = false;   //当前app有界面在最前面
    int mBind = 0;              //统计绑定activity计数

    SmartApplication mAPP;      //记录全局变量的信息

    RetMsg mRetMsg;        //用于记录返回信息
    CommInfo commInfo;  //通信信息;         //通信过程信息
//    Queue<PacketMsg> rcvPktQueue;   //接收处理报文队列
//    Queue<PacketMsg> sndPktQueue;   //发送处理报文
    Queue<FileInfo> fileInfoQueue;  //正在下载文件队列

    Protocol mProto;            //协议处理对象
    NetHandler mNetHandler;     //网络通信对象
    Thread mThread;             //接收处理线程

    private SmartBinder mBinder = new SmartBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("smartService","onBind方法执行了");
        mBind++;
        return mBinder;
    }

    public class SmartBinder extends Binder{  //返回当前的service实例
         public SmartService getService(){
             Log.d("smartService","返回service实例");
            return SmartService.this;
         }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBind--;
        return super.onUnbind(intent);
    }

    public void setActTop(boolean actTop) {
        isActTop = actTop;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAPP = (SmartApplication) getApplication();
        mAPP.setSrv(this);

        commInfo = new CommInfo();
        mProto = new Protocol(this);
        mRetMsg = new RetMsg();

//        rcvPktQueue =new LinkedList<PacketMsg>();
//        sndPktQueue =new LinkedList<PacketMsg>();
        fileInfoQueue =new LinkedList<FileInfo>();

        Log.i("smartService","开始启动接收线程");
        startThread();

        Log.i("smartService","开始启动定时器");
        timer.schedule(task, 5000, 5000); // 30s后执行task,经过30s再次执行，真实运行时需要修改
    }

    public RetMsg getRetMsg() {
        return mRetMsg;
    }

    public CommInfo getCommInfo() {
        return commInfo;
    }

    public SmartApplication getmAPP() {
        return mAPP;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAPP.setSrv(null);
    }

    void startThread( ) {
        if (mThread != null && mThread.isAlive()) {
            Log.i("Service", "start: thread is alive");
        } else {
            mNetHandler = new NetHandler(commInfo.bindPort);
            mNetHandler.setCallBack(this);

            Thread mThread = new Thread(mNetHandler);
            mThread.start();
        }
    }

      public void stopThread() {
            if (mThread != null && mThread.isAlive()) {
                mThread.interrupt();
                mThread = null;
           }
       }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("smartService","onStartCommand运行了");
        return super.onStartCommand(intent, flags, startId);
    }

    //发送通知到通知栏，然后启动主页界面接收通知。
    public void sendMsgToNoticeBar(int MsgType) {
        String tipMsg;

        if(MsgType < 10) {//编号小于10的才需要发消息到通知栏
            switch (MsgType){
                case 3:
                    tipMsg = "发现附近前端设备,请点击查看";
                    break;
                case 4:
                    tipMsg = "已经下载新版本，请点击安装";
                    break;
                case 7:
                    tipMsg = "发现新版本，请点击下载";
                    break;
                default:
                    tipMsg = "有新消息请点击查看";
                    break;
            }
        }else
            return;

        Intent intent=new Intent(this, TestActivity.class); //此句今后得修改到合适页面
        intent.putExtra("MsgType",MsgType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.notification)
                    .setWhen(System.currentTimeMillis())
                    .setContentText(tipMsg)
                    .setTicker("消息")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notification.build());

    }

    //发送消息类型广播到所有activity
    public void sendMsgToActivity( int MsgType) {
        //这里要发送一个广播通知
        Intent receiverIntent = new Intent();
        receiverIntent.setAction(StaticClass.ACTION);
        receiverIntent.putExtra("MsgType",MsgType);
        sendBroadcast(receiverIntent);
    }

    //发送消息类型给界面时，调用该方法
    public void pushMsg(int MsgType) {
        //没有界面运行，发送通知
        if (mBind == 0) {
            sendMsgToNoticeBar(MsgType);
        } else {
            if (isActTop) {
                //在前台运行，发送到activity
                sendMsgToActivity(MsgType);
            }
            else {
                //有activity在后台但是不是在最前面，要发送
                sendMsgToNoticeBar(MsgType);
                sendMsgToActivity(MsgType);
            }
        }
    }

    //get message from service
    public RetMsg pickMsg(int type){
        if (mRetMsg.getType() == type)
            return mRetMsg;
        return null;
    }

    void fileProc(FileInfo fileInfo){
        PacketMsg pkt = new PacketMsg(null);
        FileMsg msg = new FileMsg();

        msg.setType(StaticClass.MSG_FILE);
        msg.setFile(fileInfo.url);      //app代码为1
        msg.setSize(fileInfo.size);     //当前版本
        msg.setFrom(fileInfo.sized+1);
        msg.setIndex(1);                //支持带内二进制模式

        if (fileInfo.fromPlat) {
            pkt.dsort = 3;
            msg.setUser(mAPP.getUserName());
        }
        else {
            pkt.dsort = 2;
            msg.setUser(mAPP.getUserDev());
        }
        pkt.cmd = 7;
        pkt.type = 1;
        pkt.msg = msg;

        if( mProto.sendProc(pkt)) {
            SendPkt(pkt,true);
        }
    }

    void reSendProc(long curTime){
        PacketMsg pkt;
        int num= 0;
        //遍历重发队列，对于超时的报文进行删除或者重发处理
        Map<Integer, PacketMsg> map = commInfo.sendPkt;
        Iterator<Map.Entry<Integer, PacketMsg>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer, PacketMsg> entry=it.next();
            pkt = entry.getValue();
            num++;
            if(curTime - pkt.time > commInfo.resendValue){
                if(pkt.count-- > 0){
                    SendPkt(pkt,false);
                }else {
                    mProto.timeoutProc(pkt); //协议相关的超时处理
                    pushMsg(pkt.msg.getType());
                    it.remove();
                }
            }
        }
        if(num > 0){
            Log.i("定时处理:", "重发报文数"+num);
        }
    }

    void timerProc(long curTime){
        PacketMsg pkt;
        Log.i("定时处理:", "平台状态"+mAPP.getLoginState());
        if(mAPP.getLoginState() == 3){ //如果已经平台在线
            if(commInfo.isKeepPlat){    //需要保活处理
                if(curTime - commInfo.rcvTime >= commInfo.keepOutValue){ //保活已经超时，掉线通知
                    mAPP.setLoginState((byte)0);    //离线状态
                    mAPP.setOfflineMode(true);      //自动离线

                    mRetMsg.setState((byte)-1);     //表示超时没有应答
                    mRetMsg.setType(StaticClass.MSG_EXCEP);
                    mRetMsg.setSort((byte)3);
                    pushMsg(StaticClass.MSG_EXCEP);
                    Log.i("定时处理:", "平台保活超时");
                }else if(curTime - commInfo.rcvTime >= commInfo.keepValue){ //需要保活
                    pkt = new PacketMsg(null);
                    pkt.cmd = 1;
                    pkt.type = 6;
                    pkt.dsort = 3;
                    Log.i("定时处理:", "平台保活一次");
                    if( mProto.sendProc(pkt)) {
                        SendPkt(pkt,false);
                    }
                }
            }
            //自动更新检查
            if (commInfo.isUpdate && (curTime- commInfo.lastUpdateTime >= commInfo.updateValue)){

                commInfo.lastUpdateTime = curTime;
                pkt = new PacketMsg(null);
                UpdateMsg msg = new UpdateMsg();

                msg.setType(StaticClass.MSG_UPDATE);
                msg.setSerial((byte)1);     //app代码为1
                msg.setVersion("010101");   //当前版本
                pkt.cmd = 11;
                pkt.type = 1;
                pkt.msg = msg;
                pkt.dsort = 3;

                if( mProto.sendProc(pkt)) {
                    SendPkt(pkt,false);
                }
            }

            if(commInfo.isFile){
                FileInfo fileInfo;
                boolean tag = false;
                Log.i("定时处理:", "文件传输检查");
                while ((fileInfo = fileInfoQueue.poll()) != null){ //依次从队列取出报文进行发送处理
                    //如果是新加入的，或者已经停止传输的，重新启动传输处理
                    if(fileInfo.fromPlat && (curTime-fileInfo.lastTime >=  commInfo.keepValue)) {
                        fileProc(fileInfo);
                    }
                    tag = true;
                }
                if (!tag){
                    commInfo.isFile = false; //如果没有等到传输的文件队列，则置下次不检查
                }else{
                    Log.i("定时处理:", "没有平台文件传输了");
                }
            }
        }else if(mAPP.getLoginState() == 0) {//平台处于离线状态

            if(commInfo.isLoginPlat
                    && (curTime-commInfo.lastLoginTime >= commInfo.loginValue)
                    && mAPP.isLoginWay() && mAPP.isOfflineMode()){ //要是允许自动登录平台
                Log.i("定时处理:", "进行自动平台登录");
                commInfo.lastLoginTime = curTime;
                ConnectMsg msg = new ConnectMsg();
                pkt = new PacketMsg(null);

                msg.setUsername(mAPP.getUserName());
                msg.setPassword(mAPP.getPassWord());
                msg.setType(StaticClass.MSG_LOGIN);
                pkt.dsort = 3;
                pkt.cmd = 1;
                pkt.type = 1;
                pkt.msg = msg;

                if (mProto.sendProc(pkt)) {
                    SendPkt(pkt,false);
                }
            }
        }

        if(mAPP.getLoginDevState()==3) {//如果已经设备在线
            if(commInfo.isKeepDev) {    //需要保活处理
                if (curTime - commInfo.rcvDevTime >= commInfo.keepOutValue) { //保活已经超时，掉线通知
                    mAPP.setLoginDevState((byte) 0);
                    mRetMsg.setState((byte) -1);  //表示超时没有应答
                    mRetMsg.setType(StaticClass.MSG_EXCEP);
                    mRetMsg.setSort((byte)2);
                    pushMsg(StaticClass.MSG_EXCEP);
                    Log.i("定时处理:", "设备保活超时");
                } else if (curTime - commInfo.rcvDevTime >= commInfo.keepValue) { //需要保活
                    Log.i("定时处理:", "设备保活一次");
                    pkt = new PacketMsg(null);
                    pkt.cmd = 1;
                    pkt.type = 6;
                    pkt.dsort = 2;
                    if (mProto.sendProc(pkt)) {
                        SendPkt(pkt,false);
                    }
                }
            }
            if(commInfo.isDevFile) {
                FileInfo fileInfo;
                boolean tag = false;
                while ((fileInfo = fileInfoQueue.poll()) != null) { //依次从队列取出报文进行发送处理
                    //如果是新加入的，或者已经停止传输的，重新启动传输处理
                    if ((!fileInfo.fromPlat) && (curTime - fileInfo.lastTime >= commInfo.keepValue)) {
                        fileProc(fileInfo);
                    }
                    tag = true;
                }
                if (!tag) {
                    commInfo.isDevFile = false; //如果没有等到传输的文件队列，则置下次不检查
                }else{
                    Log.i("定时处理:", "没有设备文件传输");
                }
            }
        }
    }

    //定时器定义及处理
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //handler.sendEmptyMessage(1);
            Log.i("定时处理:", "开始检查");
            long curTime = System.currentTimeMillis();
            reSendProc(curTime); //重发队列处理,检查是否有超时
            timerProc(curTime);//检查comminfo，是否需要执行定时处理事务
        }
    };

    @Override
    public void NetNotify(PacketMsg pkt) {
        Log.d("smartService","处理接收的报文");
        PrintData("recv msg:",pkt.message);

        if(mProto.proRecvProc(pkt))
        {
            PacketMsg packetMsg = mProto.recvProc(pkt);
            if( packetMsg != null) { //有报文要发送
                SendPkt(packetMsg, true);
            }
           // rcvPktQueue.offer(pkt);
           // handler.sendEmptyMessage(3);
        }
    }

    //execute command coming from activity
    public boolean execMsg(MsgObject msgObject){
        if(mAPP.getNetState() == 0 && msgObject.getType() != StaticClass.MSG_EXIT) {
            return false;
        } //没有网络不执行

        int type = msgObject.getType();
        PacketMsg pkt = new PacketMsg(null);

        switch (type){
            case StaticClass.MSG_TEST: {//测试协议
                ConnectMsg msg = (ConnectMsg) msgObject;
                if (msg.getPort() != 0) { //不是发给服务器的
                    pkt.dsort = 2; //发给设备
                }else{
                    pkt.dsort = 3; //发给平台
                }
                pkt.cmd = 0;
                pkt.type = 0;
                pkt.msg = msgObject;
            }
            break;
            case StaticClass.MSG_LOGIN: {
                ConnectMsg msg = (ConnectMsg) msgObject;
                if (mAPP.getLoginState() != 0 && msg.getPort() == 0) {
                    return false;
                }
                if (mAPP.getLoginDevState() != 0 && msg.getPort() != 0) {
                    return false;
                }

                if (msg.getPort() != 0) { //不是发给服务器的
                    pkt.dsort = 2; //发给设备
                }else{
                    pkt.dsort = 3; //发给平台
                }
                pkt.cmd = 1;
                pkt.type = 1;
                pkt.msg = msgObject;
                // sndPktQueue.offer(pkt); //放在发送队列中
            }
            break;
            case StaticClass.MSG_LANFIND:
                pkt.dsort = 2;
                pkt.cmd = 6;
                pkt.type = 1;
                pkt.msg = msgObject;
                // sndPktQueue.offer(pkt); //放在发送队列中
                break;
            case StaticClass.MSG_EXIT: //退出程序处理
                stopThread();
                timer.cancel();
                return true;
            default:
                return false;
        }
        //handler.sendEmptyMessage(2);
        if( mProto.sendProc(pkt)) {
            SendPkt(pkt, true);
        }
        return true;
    }

    public void PrintData(String tip,byte[] b)
    {
        String prints="";

        for (int i = 0; i < b.length; i++)
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex ='0' + hex;
            }
            prints = prints+hex+" ";
        }
        Log.i(tip, prints);
    }

    private boolean SendPkt(PacketMsg pkt, boolean reSend){
        boolean ret = false;

        mProto.proSendProc(pkt);
        if (mNetHandler.sendPacket(pkt)) {
            PrintData("send msg:", pkt.message);

            if (pkt.count > 0 && reSend) { //表示需要重发,记录到重发集合

                pkt.time = System.currentTimeMillis();
                commInfo.sendPkt.put(pkt.seq, pkt);
            }
            ret = true;
        }
        return ret;
    }
}