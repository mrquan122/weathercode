package com.example.administrator.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/3/4.
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
    /**
     * Province表建表语句
     */
   public static final  String CREATE_PROVINCE ="create table Province ("
    + "id integer primary key autoincrement,"
    + "province_name text,"
    + "province_code text)";

    /**
     *  City表建表语句
     */
    public static final  String CREATE_CITY="create table City ("
            + "id integer primary key autoincrement,"
            + "city_name text,"
            + "city_code text,"
            +"province_id integer)";
    /**
     * County表建表语句
     */
    public static final  String CREATE_COUTY ="create table County ("
            + "id integer primary key autoincrement,"
            + "county_name text,"
            + "county_code text,"
            + "city_id integer)";
    public static final  String CREATE_ALLCITY ="create table All_city ("
            + "id integer primary key autoincrement,"
            + "city_name text,"
            + "city_code text)";
public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
  super(context,name ,factory,version);
}
@Override
public  void onCreate(SQLiteDatabase db){
    Log.i("province","onCreate open");
    db.execSQL(CREATE_PROVINCE);   //创建province表
    db.execSQL(CREATE_CITY);    //创建city表
    db.execSQL(CREATE_COUTY);
    db.execSQL(CREATE_ALLCITY);
}
 @Override
public  void onUpgrade (SQLiteDatabase db,int oldVersion ,int newVersion){
 }
}
