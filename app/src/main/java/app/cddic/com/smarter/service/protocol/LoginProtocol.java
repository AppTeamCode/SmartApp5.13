package app.cddic.com.smarter.service.protocol;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import app.cddic.com.smarter.entity.ConnectMsg;
import app.cddic.com.smarter.entity.RetMsg;
import app.cddic.com.smarter.service.CommInfo;
import app.cddic.com.smarter.service.PacketMsg;
import app.cddic.com.smarter.service.SmartService;
import app.cddic.com.smarter.utils.StaticClass;

/**
 * Created by yfs on 4/13 0013.
 */

public class LoginProtocol extends SmartProtocol {
    ConnectMsg loginMsg;

    public LoginProtocol(SmartService Srv) {
        setSrv(Srv);
    }

    public boolean sendProc(PacketMsg pkt) {
        boolean ret = true;

        JSONObject jsonProt = new JSONObject();//创建json格式的数据
        JSONObject jsonProtData = new JSONObject();

        loginMsg = (ConnectMsg)pkt.msg;
        pkt.count = 3; //要求重发三次
        try {
            if (pkt.type == 1) {
                if (pkt.dsort == 2)
                    mSrv.getmAPP().setLoginDevState((byte) 1);
                else
                    mSrv.getmAPP().setLoginState((byte) 1);
                jsonProtData.put("SNUM", loginMsg.getSnum());
                jsonProtData.put("SVER", loginMsg.getSver());
                jsonProtData.put("USER", loginMsg.getUsername());
            } else if (pkt.type == 3) {
                if (pkt.dsort == 2)
                    mSrv.getmAPP().setLoginDevState((byte) 2);
                else
                    mSrv.getmAPP().setLoginState((byte) 2);
                jsonProtData.put("CODE", loginMsg.getCode());
                jsonProtData.put("USER", loginMsg.getUsername());
            } else if (pkt.type == 5) {//登出报文
                jsonProtData.put("USER", loginMsg.getUsername());
            } else if (pkt.type > 6){
                ret = false;
            }
            if(ret) {
                jsonProt.put("DATA", jsonProtData);//再将这个json格式的放到最终的json对象中。
                pkt.data = jsonProt.toString();
                Log.i("login Send:", jsonProt.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }

    String getCode(String b1, String b2){ //bi和b2进行异或运算，结果进行base64编码后返回。
        return null;
    }

    public PacketMsg recvProc(PacketMsg pkt){
        int ret;
        String info;
        CommInfo commInfo = mSrv.getCommInfo();
        RetMsg retMsg = mSrv.getRetMsg();

        Log.i("LOGIN Parse:",pkt.data);

        if(pkt.type > 4) //表示保活报文
        {
            return null;
        }
        //解析数据区选项
        try {
            JSONObject jsonMsg = new JSONObject(pkt.data);//创建json格式的数据
            ret = jsonMsg.getInt("RET");
            info = jsonMsg.getString("INFO");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        retMsg.setType(StaticClass.MSG_LOGIN);
        retMsg.setSort(pkt.sort);
        retMsg.setCmd(pkt.cmd);
        retMsg.setPktType(pkt.type);
        retMsg.setFromIP(pkt.address);
        retMsg.setPort(pkt.port);
        retMsg.setState((byte)ret);
        retMsg.setError((byte)0);

        //对结果进行分析和处理
        if(ret == 0) { //表示正确返回了
            if(commInfo.sid != 0) {
                commInfo.sid = Integer.getInteger(info); //登记其id
            }

            if(pkt.sort == 2) { //来自设备的报文
                mSrv.getmAPP().setLoginDevState((byte) 3);
                commInfo.devAddr = pkt.address;
                commInfo.devPort = pkt.port;
            }
            else {
                mSrv.getmAPP().setLoginState((byte) 3);
            }
            mSrv.pushMsg(StaticClass.MSG_LOGIN);

            /*构造一个数据查询报文进行数据查询
            PacketMsg chkPkt = new PacketMsg(null);
            chkPkt.cmd = 4;
            chkPkt.type =1;
            chkPkt.dsort = pkt.sort;
            return chkPkt;*/
            return null;
        }else if(ret == 1) { //表示需要认证
            mSrv.pushMsg(StaticClass.MSG_LOGIN);

            if( pkt.type == 2 ){//构造认证报文返回
                loginMsg.setCode(getCode(info,loginMsg.getPassword()));
                PacketMsg authPkt = new PacketMsg(null);
                authPkt.cmd = 1;
                authPkt.type = 3;
                authPkt.msg = loginMsg;
                authPkt.dsort = pkt.sort;
                return authPkt;
            }
        }else if(ret == -1){  //如果出错,设置错误号
            retMsg.setError(Integer.getInteger(info).byteValue());
            mSrv.pushMsg(StaticClass.MSG_LOGIN);
            //设置出错处理，返回空
        }else{
            Log.i("RecvProc:","收到错误的RET值");
        }
         return null;
    }

    //超时处理
    public void timeoutProc(PacketMsg pkt){
        if(pkt.dsort == 3)
            mSrv.getmAPP().setLoginState((byte)0);
        else
            mSrv.getmAPP().setLoginDevState((byte)0);
    }
}
