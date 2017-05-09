package app.cddic.com.smarter.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import app.cddic.com.smarter.db.DBHelper;
import app.cddic.com.smarter.service.SmartService;
import app.cddic.com.smarter.utils.StaticClass;

/**
 * Created by zhl on 2017/3/5.
 * application基类取代系统默认application
 */

public class SmartApplication extends Application {
    DBHelper mHelper; //数据库操作对象
    boolean isActTop = false; //判断activity是否在前台
    int mCount; //本程序启动次数
    String mUserDev; //登录设备用户名
    String mPassDev; //登录设备密码
    String mDevSerial; //登录设备的序列号

    String mUserName; //登录平台用户名
    String mPassWord; //登录平台密码

    boolean isLoginWay; //登录方式，0人工登录，1自动登录
    boolean isOfflineMode; //最近离线方式 1自动 0人为

    byte bLoginDevState; //登录设备状态:0离线，1验证用户，2验证密码，3在线
    byte bLoginState; //登录平台状态，0离线，1验证用户，2验证密码，3在线
    byte bNetState; //当前网络状态，0无网络，1wifi 2流量 3蓝牙
    SmartService mSrv;  //service指针

    public static Context getSmartApplication() {
        return new SmartApplication();
    }

    public DBHelper getmHelper(){
        return mHelper;
    }

    public void setActTop(boolean actTop) {
        isActTop = actTop;
    }

    public Boolean getActTop(){
        return isActTop;
    }

    public boolean isOfflineMode() {
        return isOfflineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        isOfflineMode = offlineMode;
    }

    public String getUserDev() {
        return mUserDev;
    }

    public void setUserDev(String mUserDev) {
        this.mUserDev = mUserDev;
    }

    public String getPassDev() {
        return mPassDev;
    }

    public void setPassDev(String mPassDev) {
        this.mPassDev = mPassDev;
    }

    public byte getLoginState() {
        return bLoginState;
    }

    public void setLoginState(byte loginState) {
        bLoginState = loginState;
    }

    public boolean isLoginWay() {
        return isLoginWay;
    }

    public void setLoginWay(boolean loginWay) {
        isLoginWay = loginWay;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public void setPassWord(String mPassWord) {
        this.mPassWord = mPassWord;
    }

    public byte getLoginDevState() {
        return bLoginDevState;
    }

    public void setLoginDevState(byte loginDevState) {
        bLoginDevState = loginDevState;
    }

    public String getDevSerial() {
        return mDevSerial;
    }

    public void setDevSerial(String mDevSerial) {
        this.mDevSerial = mDevSerial;
    }

    public byte getNetState() {
        return bNetState;
    }

    public void setNetState(byte bNetState) {
        this.bNetState = bNetState;
    }

    public int getCount() {
        return mCount;
    }

    public SmartService getSrv() {
        return mSrv;
    }

    public void setSrv(SmartService mSrv) {
        this.mSrv = mSrv;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //数据库初始化操作
//        SQLiteDatabase.openOrCreateDatabase(StaticClass.PATH,null);
//        mHelper = new DBHelper(this);

        //进行数据初始化操作
        //获取配置信息
        SharedPreferences preferences = getSharedPreferences("smart",MODE_PRIVATE);
        mCount = preferences.getInt("count",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count",++mCount);
        editor.apply();

        setLoginWay(preferences.getBoolean("loginway",false));

        setPassWord(null);
        setUserName(null);

        setDevSerial(null);
        setNetState((byte)1);
        setLoginState((byte)0);
        setSrv(null);
    }
}
