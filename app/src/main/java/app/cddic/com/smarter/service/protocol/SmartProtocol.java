package app.cddic.com.smarter.service.protocol;

//import org.json.JSONObject;

import app.cddic.com.smarter.service.PacketMsg;
import app.cddic.com.smarter.service.SmartService;

/**
 * Created by yfs on 4/12 0012.
 */

public abstract class SmartProtocol {
    protected SmartService mSrv;

    public void setSrv(SmartService Srv) {
        this.mSrv = Srv;
    }

    public abstract boolean sendProc(PacketMsg packetMsg);
    public abstract PacketMsg recvProc(PacketMsg packetMsg);
    public abstract void timeoutProc(PacketMsg packetMsg);

}
