package app.cddic.com.smarter.service;

import java.io.File;

/**
 * Created by yfs on 4/6 0006.
 */

//文件下载的有关信息
public class FileInfo {
    String url;     //文件获取路径
    byte[] MD5;     //文件校验
    long openTime;  //打开时间
    int size;       //文件总大小
    boolean fromPlat; //文件来源
    byte type;       //0,app; 1,插件；2，图片；3，视频；4，日志；5，配置；6，其他

    File file;      //文件打开对象（指针）
    String path;     //文件本地存储路径和文件名
    long lastTime;  //最近发送报文时间
    int sized;      //文件已读大小（为0表示还未读、被下载-1、已下载>0）

}
