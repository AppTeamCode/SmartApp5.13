package app.cddic.com.smarter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import app.cddic.com.smarter.entity.MsgObject;

/**
 * 项目名：  SmartApp
 * 包名：    app.cddic.com.smarter.db
 * 文件名：  DBHelper
 * 创建者：
 * 创建时间： 2017/4/17 10:59
 * 描述：
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "test.db"; //数据库名
    private static int version = 1; //版本号

    //自带的构造方法
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //自己定义的构造方法,调用上面的构造方法
    public DBHelper(Context mContext){
        this(mContext,DB_NAME,null,version);
    }

    /**
     * 数据库创建时使用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //1用户账号表(Account)
        String sql1 = "create table Account"
                + "(username varchar(20) not null"
                + ",password varchar(20) not null"
                + ",uid integer"
                + ",sex char(1)"
                + ",time timestamp"
                + ",type char(1)"
                + ",email varchar(20)"
                + ",state char(1)"
                + ",did integer)";

        //1用户设备表信息记录表(Device)
        String sql2 = "create table Device"
                + "(uid integer not null"
                + ",devname varchar(20) not null"
                + ",serial varchar(30)"
                + ",code varchar(16)"
                + ",ipaddr integer"
                + ",alias varchar(20)"
                + ",user varchar(20)"
                + ",pass varchar(20)"
                + ",power char(1)"
                + ",type char(1)"
                + ",plugin varchar(20)"
                + ",did integer"
                + ",version varchar(10)"
                + ",time timestamp"
                + ",longitude float"
                + ",latitude float"
                + ",state char(1))";

        //3报警信息表(alarm)
        String sql3 = "create table Alarm"
                + "(id integer not null"
                + ",serial varchar(30) not null"
                + ",type char(1) not null"
                + ",level char(1) not null"
                + ",time timestamp not null"
                + ",url varchar(20)"
                + ",lurl varchar(20)"
                + ",state char(1))";

        //4设备附属文件表(document)
        String sql4 = "create table Document"
                + "(id integer not null"
                + ",serial varchar(30) not null"
                + ",type char(1) not null"
                + ",time timestamp not null"
                + ",url varchar(20))";

        //5设备关联人表(relater)
        String sql5 = "create table Relater"
                + "(username varchar(20) not null"
                + ",id integer not null"
                + ",power char(1) not null"
                + ",time timestamp"
                + ",type char(1)"
                + ",alias varchar(20))";

        //6我的聊天消息表(chat)
        String sql6 = "create table hat"
                + "(musername varchar(20) not null"
                + ",mid integer not null"
                + ",id integer not null"
                + ",username varchar(20) not null"
                + ",type char(1) not null"
                + ",time timestamp not null"
                + ",content varchar(100) not null"
                + ",state char(1))";

        //7我收到的通知信息表(notice)
        String sql7 = "create table otice"
                + "(username varchar(20) not null"
                + ",id integer not null"
                + ",type char(1) not null"
                + ",time timestamp not null"
                + ",message varchar(100)"
                + ",state char(1)"
                + ",serial varchar(30) not null"
                + ",power char(1)"
                + ",group varchar(10)"
                + ",title varchar(50))";

        //8我的联系人信息表(contact)
        String sql8 = "create table ontact"
                + "(username varchar(20) not null"
                + ",id integer not null"
                + ",contact varchar(20) not null"
                + ",type char(1) not null"
                + ",sex char(1) not null"
                + ",alias varchar(20)"
                + ",online char(1)"
                + ",settime date"
                + ",group varchar(10))";

        //9用户设置表(setting)未完成
        String sql9  = "create table etting"
                + "(username varchar(20) not null"
                + ",id integer not null"
                + ",sound char(1))";

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);

    }

    //封装增删查改的方法
    public void addData(SQLiteDatabase db, String table, ContentValues values){
        db.insert(table,null,values);
    }

    public boolean addObject(SQLiteDatabase db, String table, MsgObject msgObject) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
        objectOutputStream.writeObject(msgObject);
        objectOutputStream.flush();
        byte data[] = arrayOutputStream.toByteArray();
        objectOutputStream.close();
        arrayOutputStream.close();
        db.execSQL("insert into"+table+"("+table+"data) values(?)", new Object[] {data});
        db.close();
        return true;
    }

    public void deledata(SQLiteDatabase db, String table, String id, String[] delete){
        db.delete(table,id,delete);
    }

    public void updateData(SQLiteDatabase db,String table, ContentValues values, String id, String[] mReplace){
        db.update(table,values,id,mReplace);
    }
    public List selectdata(SQLiteDatabase db, String table){
        MsgObject msgObject;
        List<MsgObject> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from"+table, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex(table+"data"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    msgObject = (MsgObject) inputStream.readObject();
                    mList.add(msgObject);
                    inputStream.close();
                    arrayInputStream.close();
                    break;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return mList;
    }

    /**
     * 更新时使用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
