package app.cddic.com.smarter.service.protocol;

import app.cddic.com.smarter.service.PacketMsg;
import app.cddic.com.smarter.service.SmartService;

/**
 * Created by yfs on 4/21 0021.
 */

public class TestProtocol extends SmartProtocol {
    public TestProtocol(SmartService Srv) {
        setSrv(Srv);
    }

    public boolean sendProc(PacketMsg packetMsg){
        return true;
    }
    public PacketMsg recvProc(PacketMsg packetMsg){
        return null;
    }
    public void timeoutProc(PacketMsg packetMsg){

    }

}
