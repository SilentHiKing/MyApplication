package com.h.myapplication.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseInterface extends SQLiteOpenHelper {

    public static final String DB_NAME = "pptv.db";
    public static final String TABLE_NAME = "nihao";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            /**
             * data字段
             */
            + " (id INTEGER PRIMARY KEY, "
            + "msgType" + " TEXT, "
            + "appName" + " TEXT, "
            + "startTime" + " TEXT, "
            + "openTime" + " TEXT, "
            + "endTime" + " TEXT, "
            + "title" + " TEXT, "
            + "appVersions" + " TEXT, "
            + "channels" + " TEXT, "
            + "params" + " TEXT, "
            /**
             * message字段
             */
            + "ShowTime" + " INTEGER, "
            + "DisplayType" + " INTEGER, "
            + "MessageClass" + " INTEGER, "
            + "DefaultFocusFlag" + " INTEGER, "
            + "ActionPara" + " TEXT, "
            + "ActivityId" + " TEXT, "
            + "WebURL" + " TEXT, "
            + "ExpiryTime" + " INTEGER, "
            + "ExtincTime" + " INTEGER, "
            + "ApplicationPackage" + " TEXT, "
            + "TextDisplayType" + " INTEGER, "
            + "ApplicationActivityClass" + " TEXT, "
            + "LocalSaveFlag" + " INTEGER, "
            + "IconImg" + " TEXT, "
            + "Opertion" + " INTEGER, "
            + "MessageTitle" + " TEXT, "
            + "IconType" + " INTEGER, "
            + "ActionURI" + " TEXT, "
            + "DisplayTime" + " INTEGER, "
            + "PopUpType" + " INTEGER, "
            + "MessageType" + " INTEGER, "

            + "MessageBody" + " TEXT, "
            + "ClickSupport" + " INTEGER, "
            + "MessageId" + " INTEGER, "

            + "ActionParasList" + " INTEGER)"
            ;
    private static final String TAG = DatabaseInterface.class.getSimpleName();
    private static SQLiteDatabase mDatabase;
    private Context mContext;
    private static DatabaseInterface mInstance;

    private DatabaseInterface(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public static DatabaseInterface getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseInterface(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(){

        try {
            ContentValues v=new ContentValues();
            v.put("appName","nihaoma");
            getWritableDatabase().insert(TABLE_NAME,null,v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void query(){
        Cursor cursor =null;
        try {
            cursor = getWritableDatabase().query(TABLE_NAME,null,null,null,null,null,null,null);
            if (cursor != null) {
                //打印获取到的内容
                while (cursor.moveToNext()) {
                    Log.i(this.TAG, ""+cursor.getInt(cursor.getColumnIndex("id")));
                    Log.i(this.TAG, cursor.getString(cursor.getColumnIndex("appName")));
                }
            } else {
                Log.i(this.TAG, "l am null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
    }
}