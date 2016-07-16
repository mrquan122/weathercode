package com.example.administrator.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.administrator.app.db.CoolWeatherDB;
import com.example.administrator.app.model.Allcity;
import com.example.administrator.app.model.City;
import com.example.administrator.app.model.Province;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Administrator on 2016/3/15.
 */
public class Utility {
    public static void handleRawResponse(InputStream response, CoolWeatherDB coolWeatherDB)
            throws XmlPullParserException, IOException{
        Allcity allcity  =new Allcity();
        XmlPullParser parser=null;
        parser=XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(response,"UTF-8");
        int eventType=0;
        eventType=parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("County")) {
                        allcity.setCityName(parser.getAttributeValue(null,"name"));
                        allcity.setCityCode(parser.getAttributeValue(null, "code"));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("County")) {
                        coolWeatherDB.saveAll_city(allcity);

                    }
                    break;
            }

            eventType = parser.next();

        }

    }

    public static boolean handleProvincesResponse(InputStream response, CoolWeatherDB coolWeatherDB)
            throws XmlPullParserException, IOException {
        List<Province> provinceList = null;
        Province province = null;
        XmlPullParser parser = null;

        parser = XmlPullParserFactory.newInstance().newPullParser();

        parser.setInput(response, "UTF-8");
        int eventType = 0;
        eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    provinceList = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("city")) {
                        province = new Province();
                        province.setProvinceName(parser.getAttributeValue(null, "quName"));
                        province.setProvinceCode(parser.getAttributeValue(null, "pyName"));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("city")) {
                        coolWeatherDB.saveProvince(province);
                        //      provinceList.add(province);

                    }
                    break;
            }

            eventType = parser.next();

        }
      return true;
    }


    public static boolean handleCityResponse(InputStream response, CoolWeatherDB coolWeatherDB)
            throws ParserConfigurationException, IOException, SAXException {
        List<City> cityList = null;
        City city = null;
         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         Document doc = builder.parse(response);
         Element root = doc.getDocumentElement();
         NodeList nodes=root.getElementsByTagName("city");
        for(int i= 0 ;i<nodes.getLength();i++){
            city = new City();
            city.setProvinceId(root.getNodeName());
            city.setCityName(nodes.item(i).getAttributes().getNamedItem("cityname").getNodeValue());
            city.setCityCode(nodes.item(i).getAttributes().getNamedItem("url").getNodeValue());
         //   Log.i("province",city.getCityName());

            coolWeatherDB.saveCity(city);
        }


      return true;
    }
/*
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

                    if (parser.getName().equals("city")) {
                        cityName=parser.getAttributeValue(null,"cityname");
                        weatherDesp=parser.getAttributeValue(null,"stateDetailed");
                        temp1=parser.getAttributeValue(null,"tem1");
                        temp2=parser.getAttributeValue(null,"tem2");
                        publishTime=parser.getAttributeValue(null,"time");
                    }

                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("city")) {

                        saveWeatherInfo(context, cityName, temp1,temp2 ,weatherDesp , publishTime);
                        Log.i("weatherinfo",cityName+temp1+"--"+temp2);
                    }
                    break;
            }

            eventType = parser.next();

        }
        return true;
    }
         */
public static boolean handleWeatherResponse(Context context,InputStream response)
        throws XmlPullParserException, IOException, JSONException {
    BufferedReader br = new BufferedReader(new InputStreamReader(response, "utf-8"));
    String line = null;
    StringBuilder builder=new StringBuilder();
    while ((line = br.readLine()) != null) {
        builder.append(line);
    }

    JSONObject jsonObject = new JSONObject(builder.toString()).getJSONObject("weatherinfo");
    String cityName = null;
    String weatherCode = null;
    String temp1 = null;
    String weatherDesp = null;
    String publishTime = null;
    String cityId=null;
    String imageCode01=null;
    String imageCode02=null;
    cityName = jsonObject.getString("city");
    temp1 = jsonObject.getString("temp1");
     weatherDesp = jsonObject.getString("weather1");
     publishTime = jsonObject.getString("date_y");
     cityId=jsonObject.getString("cityid");
    imageCode01=jsonObject.getString("img1");
    imageCode02=jsonObject.getString("img2");
    saveWeatherInfo(context, cityName, temp1,cityId ,weatherDesp , publishTime,imageCode01,imageCode02);
    Log.i("weatherinfo",cityName+temp1+"--");

    return true;
}


    /**
     *将服务器返回的的所有天气信息存储到 SharedPreference文件中。
     *
     */
    public static void saveWeatherInfo(Context context, String cityName,  String temp1,
                                       String cityId, String weatherDesp,String publishTime,String imageCode01,String imageCode02){
      //  SimpleDateFormat sdf =new SimpleDateFormat("yyy年M月D日", Locale.CANADA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("temp1",temp1);
        editor.putString("city_code",cityId);
        editor.putString("weather1",weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("img1",imageCode01);
        editor.putString("img2",imageCode02);
      //  editor.putString("current_data",sdf.format(new Date()));
        editor.commit();

    }
}

