package app.cddic.com.smarter.service.protocol;

import app.cddic.com.smarter.service.PacketMsg;
import app.cddic.com.smarter.service.SmartService;

/**
 * Created by yfs on 4/24 0024.
 */

public class RelaterProtocol extends SmartProtocol {
    public RelaterProtocol(SmartService Srv) {
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
