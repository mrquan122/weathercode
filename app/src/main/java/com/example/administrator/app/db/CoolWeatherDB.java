package com.example.administrator.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.app.model.Allcity;
import com.example.administrator.app.model.City;
import com.example.administrator.app.model.County;
import com.example.administrator.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/9.
 */
public class CoolWeatherDB {
    /**
     * 数据库名
     */

    public static final  String DB_NAME ="cool_weather";
    /**
     * 数据库版本
     */
    public  static final  int  VERSION = 1;
    private  static  CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

/**
 * 将构造方法私有化
 */

    private  CoolWeatherDB ( Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper( context,DB_NAME ,null, VERSION);
        db= dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB 实例
     */
    public synchronized  static  CoolWeatherDB getInstance (Context context){
        if(coolWeatherDB ==null){
            coolWeatherDB =new CoolWeatherDB(context);
        }
        return  coolWeatherDB;
    }

    public  void  saveAll_city (Allcity allcity){
        if (allcity!=null){
            ContentValues values =new ContentValues();
            values.put("city_name",allcity.getCityName());
            values.put("city_code",allcity.getCityCode());
            db.insert("All_city",null,values);

            Log.i("All_city",allcity.getCityName());
            Log.i("All_city",allcity.getCityCode());

        }

    }

    /**
     * 从数据库读取全国所有的省份信息
     */

    public String loadAllcity(String cityName){
        String cityCode = null;
        String city=null;
        Cursor cursor =db.query("All_city",null,"city_name=?",new String[]{cityName},null,null,null);

        if (cursor.moveToFirst()){
            do {

              cityCode= cursor.getString(cursor.getColumnIndex("city_code"));
              city=cursor.getString(cursor.getColumnIndex("city_name"));



            } while (cursor.moveToNext());
           Log.i("citytransform",cityCode);
        }
        Log.i("城市转换代码",cityCode);

      return cityCode;


    }
    /**
     * 将Province 实例储存到数据库
     */
    public  void  saveProvince (Province province){
        if (province!=null){
            ContentValues values =new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
            Log.i("province",province.getProvinceName());

        }

    }

    /**
     * 从数据库读取全国所有的省份信息
     */

    public List<Province> loadProvinces(){
        List<Province>list =new ArrayList<Province>(39);
        Cursor cursor =db.query("Province",null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor
                        .getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor
                        .getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor
                        .getColumnIndex("province_code")));
                list.add(province);


            } while (cursor.moveToNext());

        }

        return  list ;
    }


    /**
     * 将City 实例储存到数据库
     */
    public  void  saveCity (City city){
        if (city!=null){
            ContentValues values =new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
          //  Log.i("province",city.getCityCode()+city.getCityName());

        }

    }
    /**
     * 从数据库读取全国所有的省份信息
     * @param provinceId
     */

    public List<City> loadCities(String provinceId){
        List<City>list =new ArrayList<City>(15);
        Cursor cursor =db.query("City",null,"province_id= ?",new String[]{provinceId},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor
                        .getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor
                        .getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor
                        .getColumnIndex("city_code")));
                list.add(city);
                Log.i("province",city.getCityName());
            }while (cursor.moveToNext());

        }
        return  list ;
    }


    /**
     * 将County 实例储存到数据库
     */
    public  void  saveCounty (County county){
        if (county!=null){
            ContentValues values =new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_Id",county.getCityId());
            db.insert("Province",null,values);

        }

    }
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor
                        .getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor
                        .getColumnIndex("county_code")));
                list.add(county);


            } while (cursor.moveToFirst());

        }

        return list;
    }

 }
