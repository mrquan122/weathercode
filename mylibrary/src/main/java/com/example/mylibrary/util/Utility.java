package com.example.mylibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/3/15.
 */
public class Utility {


    public static boolean handleWeatherResponse(Context context,InputStream response)
            throws XmlPullParserException, IOException {

        XmlPullParser parser = null;
        String cityName = null;
        String weatherCode =null;
        String temp1=null;
        String temp2=null;
        String weatherDesp = null;
        String publishTime = null;

        parser = XmlPullParserFactory.newInstance().newPullParser();

        parser.setInput(response, "UTF-8");
        int eventType = 0;
        eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("updatetime")) {
                      publishTime=parser.getText();
                    }
                    if (parser.getName().equals("city")) {
                      cityName=parser.getText();
                    }
                    if (parser.getName().equals("forecase")&&parser.getName().equals("weather")) {

                          if(parser.getName().equals("high")){
                              temp1=parser.getText();
                          }
                          if(parser.getName().equals("low")){
                              temp2=parser.getText();
                          }
                          if(parser.getName().equals("day")&&parser.getName().equals("type")){
                              weatherDesp=parser.getText();
                          }

                      }

                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("weather")&&parser.getName().equals("forecast")) {

                            saveWeatherInfo(context, cityName, temp1,temp2 ,weatherDesp , publishTime);
                    }
                    break;
            }

            eventType = parser.next();

        }
        return true;
    }


    /**
     *将服务器返回的的所有天气信息存储到 SharedPreference文件中。
     *
     */
     public static void saveWeatherInfo(Context context, String cityName,  String temp1,
                                        String temp2, String weatherDesp,String publishTime){
         SimpleDateFormat sdf =new SimpleDateFormat("yyy年M月D日", Locale.CANADA);
         SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
               editor.putBoolean("city_selected",true);
               editor.putString("city_selected",cityName);
               editor.putString("temp1",temp1);
               editor.putString("temp2",temp2);
               editor.putString("weather_desp",weatherDesp);
          //     editor.putString("weather_code",cityCode);
               editor.putString("publish_time",publishTime);
               editor.putString("current_data",sdf.format(new Date()));
               editor.commit();

     }
}

