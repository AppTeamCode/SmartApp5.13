package app.cddic.com.smarter.entity;

/**
 * SmartApp
 * app.cddic.com.smarter.entity
 * Created by Pantiy on 2017/5/4.
 * Copyright © 2017 All rights Reserved by Pantiy
 */

public class ChatMSG {

    private boolean mIsFromMe;
    private String mMessage;

    public static ChatMSG get() {
        return new ChatMSG();
    }

    private ChatMSG() {

    }

    public boolean isFromMe() {
        return mIsFromMe;
    }

    public void setFromMe(boolean isFromMe) {
        mIsFromMe = isFromMe;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
