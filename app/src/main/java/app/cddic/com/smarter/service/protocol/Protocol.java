package app.cddic.com.smarter.service.protocol;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import app.cddic.com.smarter.entity.RetMsg;
import app.cddic.com.smarter.service.CommInfo;
import app.cddic.com.smarter.service.PacketMsg;
import app.cddic.com.smarter.service.SmartService;

/**
 * Created by yfs on 4/15 0015.
 */

public class Protocol {
    SmartService mSrv;
    CommInfo commInfo;
    SmartProtocol[] mProtocol = new SmartProtocol[12];

    public Protocol(SmartService Srv) {
        this.mSrv = Srv;
        commInfo = mSrv.getCommInfo();
        mProtocol[0] = new TestProtocol(mSrv);
        mProtocol[1] = new LoginProtocol(mSrv);
        mProtocol[2] = new AccountProtocol(mSrv);
        mProtocol[3] = new ControlProtocol(mSrv);
        mProtocol[4] = new QueryProtocol(mSrv);
        mProtocol[5] = new MessageProtocol(mSrv);
        mProtocol[6] = new LanfindProtocol(mSrv);
        mProtocol[7] = new MyFileProtocol(mSrv);
        mProtocol[8] = new EncodeProcotol(mSrv);
        mProtocol[9] = new RelaterProtocol(mSrv);
        mProtocol[10] = new ContactProtocol(mSrv);
        mProtocol[11] = new UpdateProtocol(mSrv);
    }

    //对外部接口
    public boolean sendProc(PacketMsg pkt){
        return mProtocol[pkt.cmd].sendProc(pkt);
    }

    public PacketMsg recvProc(PacketMsg pkt){
        return mProtocol[pkt.cmd].recvProc(pkt);
    }

    public void timeoutProc(PacketMsg pkt) {
        RetMsg retMsg = mSrv.getRetMsg();
        retMsg.setState((byte)-1);  //表示超时没有应答
        retMsg.setSort(pkt.sort);
        retMsg.setCmd(pkt.cmd);
        retMsg.setType(pkt.msg.getType());
        retMsg.setPktType(pkt.type);
        mProtocol[pkt.cmd].timeoutProc(pkt);
    }

    //接收处理过程
    public boolean proRecvProc(PacketMsg pkt){
        boolean ret=true;
        int curseq;

        pkt.time = System.currentTimeMillis();

        ByteArrayInputStream bais = new ByteArrayInputStream(pkt.message);
        DataInputStream dis = new DataInputStream(bais);
        try {
            dis.skipBytes(4); //越过znaf四个字节
            pkt.cmd = dis.readByte();
            pkt.type = dis.readByte();
            pkt.opt = dis.readByte();
            pkt.sort = dis.readByte();
            pkt.sid = dis.readInt();
            pkt.seq = dis.readInt();
            pkt.ack = dis.readInt();
        }catch(IOException e){
            e.printStackTrace();
            ret = false;
        }

        if(pkt.cmd > 11 ){
            ret = false;
        }

        if(ret == false)
            return ret;

        if(pkt.sort == 3) { //来自web平台的报文
            curseq = commInfo.rcvSeq;
        }else{
            curseq = commInfo.rcvDevSeq;
        }
        if(pkt.ack < curseq) { //重复报文，不用处理
            ret = false;
        }else {
            pkt.time = System.currentTimeMillis();
            if (pkt.cmd != 1 && pkt.type != 7) //不是保活报文应答
                commInfo.sendPkt.remove(pkt.ack);//从重发队列将相应报文移除

            if (pkt.sort != 3) { //来自非平台的设备
                commInfo.rcvDevTime = pkt.time;
                commInfo.rcvDevSeq = pkt.ack;
                if (pkt.address.equals(commInfo.srvAddr)) { //经过服务器中转而来
                    commInfo.rcvTime = pkt.time;
                }
            } else {
                commInfo.rcvTime = pkt.time;
                commInfo.rcvSeq = pkt.ack;
            }
        }

        //解析扩展首部
        if((pkt.opt&7) > 0) {
            String jsonStr = new String(pkt.message,20,pkt.length-20);
            try {
                JSONObject jsonMsg = new JSONObject(jsonStr);//创建json格式的数据
                JSONObject jsonHopt = jsonMsg.getJSONObject("HOPT");
                pkt.hopt = jsonHopt.toString();

                JSONObject jsonProt = jsonMsg.getJSONObject("DATA");
                pkt.data = jsonProt.toString();

                if ((pkt.opt & 1) > 0) {//如果如要经过服务器转发
                    pkt.did = jsonHopt.getInt("DID");
                }
                if ((pkt.opt & 4) > 0) {
                    pkt.sip = jsonHopt.getString("SIP");
                    pkt.sport = jsonHopt.getInt("SPORT");
                }
                if ((pkt.opt & 2) > 0) {
                    pkt.keySeq = jsonHopt.getInt("KEYSEQ");
                }

                Log.i("HOPT Parse:",jsonHopt.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                ret = false;
            }
        }else{
            pkt.hopt = null;
            Log.i("HOPT Parse:","No HOPT");
            if(pkt.length <= 20){
                pkt.data = null;
            }else{
                String jsonStr = new String(pkt.message,20,pkt.length-20);
                try {
                    JSONObject jsonMsg = new JSONObject(jsonStr);//创建json格式的数据
                    JSONObject jsonProt = jsonMsg.getJSONObject("DATA");
                    pkt.data = jsonProt.toString();
                }catch (JSONException e) {
                    e.printStackTrace();
                    ret = false;
                }
            }
        }
        return ret;
    }

    public void proSendProc(PacketMsg pkt){
        //完善地址和序号和id
        if(pkt.dsort == 3) { //发送给web平台
            pkt.address = commInfo.srvAddr;
            pkt.port = commInfo.srvPort;
            pkt.seq = commInfo.sendSeq++;
        }else{
            pkt.seq = commInfo.sendDevSeq++;
            pkt.address = commInfo.devAddr;
            pkt.port = commInfo.devPort;
            if(pkt.address.equals(commInfo.srvAddr)){ //如果是服务器中转
                pkt.opt += 8;
                pkt.did = commInfo.did;
            }
        }
        pkt.sid = commInfo.sid;
        pkt.sort = 0; //APP程序类型
        pkt.time = System.currentTimeMillis();

        //生成扩展扩展首部
        if((pkt.opt&7) > 0) {
            JSONObject jsonHopt = new JSONObject();//创建json格式的数据
            JSONObject jsonHoptData = new JSONObject();
            try {
                if ((pkt.opt & 1) > 0) {//如果如要经过服务器转发
                    jsonHoptData.put("DID", pkt.did);
                }
                if ((pkt.opt & 4) > 0) {
                    jsonHoptData.put("SIP", pkt.sip);
                    jsonHoptData.put("SPORT", pkt.sport);
                }
                if ((pkt.opt & 2) > 0) {
                    jsonHoptData.put("KEYSEQ", pkt.keySeq);
                }

                jsonHopt.put("HOPT", jsonHoptData);//再将这个json格式的放到最终的json对象中。
                pkt.hopt = jsonHopt.toString();
                Log.i("HOPT Create:",jsonHopt.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            pkt.hopt = null;
            Log.i("HOPT Create:","No HOPT");
        }
        //填写发送缓冲区
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeBytes("ZNAF");
            dos.writeByte(pkt.cmd);
            dos.writeByte(pkt.type);
            dos.writeByte(pkt.opt);
            dos.writeByte(pkt.sort);
            dos.writeInt(pkt.sid);
            dos.writeInt(pkt.seq);
            dos.writeInt(pkt.ack );
        }catch (IOException e){
            e.printStackTrace();
        }

        if(pkt.opt > 0){
            try {
                dos.writeBytes(pkt.hopt);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if(pkt.data != null) {
                dos.writeBytes(pkt.data);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        pkt.message = baos.toByteArray();
        pkt.length = pkt.message.length;
    }
}
