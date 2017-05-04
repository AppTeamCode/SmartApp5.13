package app.cddic.com.smarter.entity;

/**
 * Created by yfs on 4/21 0021.
 */

public class UpdateMsg extends MsgObject {
    byte Serial;
    String Version;

    public void setSerial(byte serial) {
        Serial = serial;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
