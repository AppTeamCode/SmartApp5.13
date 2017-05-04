package app.cddic.com.smarter.activity.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import java.io.IOException;
import java.util.List;
import app.cddic.com.smarter.entity.MsgObject;
import app.cddic.com.smarter.entity.RegisterMSG;
import app.cddic.com.smarter.global.SmartApplication;
import app.cddic.com.smarter.service.SmartService;
import app.cddic.com.smarter.utils.StaticClass;

/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.ui
 * 文件名：  BaseActivity
 * 创建者：  zhl
 * 创建时间： 2017/3/5 10:37
 * 描述：activity基类
 * 1.统一的属性
 * 2.统一的接口
 * 3.统一的方法
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected SmartService mService;//service实例变量
    protected SmartApplication mAPP;//全局单例
    protected FragmentManager mFragmentManager;
    protected SQLiteDatabase db;
    protected List<MsgObject> mList;
    private String table;


    @Override
    protected void onPause() {
        super.onPause();
        mAPP.setActTop(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAPP.setActTop(true);
    }

    public abstract void onHandleMsg(int MsgType);  //希望派生类实现的函数

    //获取service实例
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((SmartService.SmartBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mAPP = (SmartApplication) this.getApplication();
        mFragmentManager = getSupportFragmentManager();

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //动态注册receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(StaticClass.ACTION);
        registerReceiver(smartReceiver,filter);

        Intent intent = new Intent(this,SmartService.class);
        if(!isServiceRunning(this)){        //判断service是否在后台运行
            startService(intent);
        }
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
    }

    public BroadcastReceiver smartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int MsgType = intent.getIntExtra("MsgType",0);
            onHandleMsg(MsgType);
        }
    };

    //判断MyService是否在运行
    public  boolean isServiceRunning(Context mContext) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);

        if (!(serviceList.size()>0)) {
            return false;
        }

        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(StaticClass.SERVICE_NAME) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smartReceiver);
        //解绑服务，但是服务仍然运行
        unbindService(mServiceConnection);
    }

    //the following is interface used by derived activity class
    //save data to sqlite
    public boolean setData(MsgObject msgObject,int operation) throws IOException {
       int type = msgObject.getType();
        //对消息类型进行判断
        switch (type){
            case 11:
                break;
            case 12:
                break;
            //注册
            case 13:
                table  = "Account";
                    RegisterMSG registerMSG = (RegisterMSG) msgObject;
                    db = mAPP.getmHelper().getWritableDatabase();
                    mAPP.getmHelper().addObject(db,table,registerMSG);
                break;
            default:
                break;
        }
        return true;
    }

    // get data from sqlite
    public List getData(int mType){
        switch (mType){
            //登录信息
            case 11:
                table = "Account";
                db = mAPP.getmHelper().getReadableDatabase();
                mList = mAPP.getmHelper().selectdata(db,table);
                break;
            case 12:
                break;
        }
        return mList;
    }

    //get key value
    public String getKValue(String Key){
        return null;
    }
    //save key value
    public boolean setKValue(String key, String Value){
        return true;
    }
    //get file path&name
    public String getFile(int FType){
        return null;
    }
    //get file path
    public String getPath(int FType){
        return null;
    }
    //record file path&name
    public boolean setFile(int FType, String file){
        return true;
    }

    //send command to Service
    public boolean setMsg(MsgObject msgObject){
        return mService.execMsg(msgObject);
    }
    //get message from service
    public MsgObject getMsg(int MType){
        return mService.pickMsg(MType);
    }

}
