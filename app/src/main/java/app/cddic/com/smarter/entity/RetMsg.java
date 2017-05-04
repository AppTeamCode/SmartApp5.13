package app.cddic.com.smarter.entity;

/**
 * Created by yfs on 4/14 0014.
 */

public class RetMsg extends MsgObject {
    private byte state;
    private String fromIP;
    private int fromPort;
    private byte cmd;
    private byte pktType;
    private byte sort;
    private byte error;

    public byte getError() {
        return error;
    }

    public void setError(byte error) {
        this.error = error;
    }

    public byte getSort() {
        return sort;
    }

    public void setSort(byte sort) {
        this.sort = sort;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }


    public byte getPktType() {
        return pktType;
    }

    public void setPktType(byte type) {
        pktType = type;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getFromIP() {
        return fromIP;
    }

    public void setFromIP(String fromIP) {
        this.fromIP = fromIP;
    }

    public int getPort() {
        return fromPort;
    }

    public void setPort(int port) {
        this.fromPort = fromPort;
    }
}
