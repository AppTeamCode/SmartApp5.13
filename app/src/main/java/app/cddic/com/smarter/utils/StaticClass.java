package app.cddic.com.smarter.utils;

/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.utils
 * 文件名：  StaticClass
 * 创建者：  zhl
 * 创建时间： 2017/3/5 11:23
 * 描述：静态类   数据/常量
 */

public class StaticClass {

    public static final String PATH = "SmartAPP.db";
    public static final int MSG_SPLASH = 1101;//闪屏页发送的消息
    public static final int MSG_LOGININF=1;//登录信息

    public static final String SPLASH_IS_FIRST = "isFirst";    //判断程序是否第一次进入
    public static final String SERVICE_NAME = "SmartService";    //service类名
    public static final String ACTION = "ActivityMsg.NOTIFY";    //广播action

    public static final int MSG_TEST=10;//连通测试
    public static final int MSG_LOGIN =11;//登录信息
    public static final int MSG_LOGOUT=12;//登出信息
    public static final int MSG_KEEPOUT=26;//保活异常
    public static final int MSG_REGISTER=13;//注册
    public static final int MSG_REGMOD=14;//修改注册信息
    public static final int MSG_REGOUT=15;//注销信息
    public static final int MSG_CONTROL=16;//控制信息
    public static final int MSG_ADDREL=17;//添加关联人
    public static final int MSG_DELREL=18;//删除关联人
    public static final int MSG_GETREL=19;//获取关联人
    public static final int MSG_MODREL=20;//添加关联人
    public static final int MSG_ADDCON=21;//添加联系人
    public static final int MSG_DELCON=22;//删除联系人
    public static final int MSG_GETCON=23;//获取联系人
    public static final int MSG_MODCON=24;//添加联系人
    public static final int MSG_EXCEP=25;    //异常通知信息（离线，无网络）
    public static final int MSG_NOTICE=26;    //无更新通知

    public static final int MSG_EXIT=0;    //退出请求

    public static final int MSG_QUERY=1;    //查询信息
    public static final int MSG_GET=2;      //请求信息
    public static final int MSG_LANFIND=3;  //局域网发现信息
    public static final int MSG_FILE=4;     //文件下载通知
    public static final int MSG_ACKREL=5; //关联通告确认
    public static final int MSG_ACKCON=6; //联系通告确认
    public static final int MSG_UPDATE=7; //发现新版本通知


}
