package com.example.mylibrary;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mylibrary.util.HttpCallbacListener;
import com.example.mylibrary.util.HttpUtil;
import com.example.mylibrary.util.Utility;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import static com.example.mylibrary.R.id.refresh_weather;

/**
 * Created by Administrator on 2016/4/14.
 */
public class WeatherActivity extends Activity {
     //   implements View.OnClickListener


    private LinearLayout weatherInfoLayout;

    /**
     *用于显示城市名
     */
    private TextView cityNameText;
    /**
     *用于显示发布时间
     */
    private TextView publishText;
    /**
     *用于显示天气描述
     */
    private TextView weatherDespText;
    /**
     *用于显示气温1
     */
    private TextView temp1Text;
    /**
     *用于显示气温2
     */
    private TextView temp2Text;
    /**
     *用于显示当前日期
     */
    private TextView currentDateText;
    /**
     *用于显示切换城市按钮
     */
    private Button swithCity;
    /**
     *更新天气按钮
     */
    private Button refreshWeather;


    @Override
    protected void onCreate (Bundle saveInstanceState)  {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        //初始化各空件
        weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText = (TextView)findViewById(R.id.city_name);
        publishText = (TextView)findViewById(R.id.publish_text);
        weatherDespText = (TextView)findViewById(R.id.weather_desp);
        temp1Text= (TextView)findViewById(R.id.temp1);
        temp2Text= (TextView)findViewById(R.id.temp2);
        currentDateText = (TextView)findViewById(R.id.current_date);
        swithCity = (Button)findViewById(R.id.swith_city);
        refreshWeather= (Button) findViewById(refresh_weather);

            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            queryWeatherInfo();

            showWeather();  }


  /*      refreshWeather.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  queryWeatherInfo();

                                              }
                                          });  */



    /**
     * 查询天气代号所对应的天气
     */
    private void queryWeatherInfo(){
        String address="http://wthrcdn.etouch.cn/WeatherApi?citykey=101251101";
        queryFromServer(address);

    }
    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
   private void queryFromServer(final String address ){
       HttpUtil.sendHttpRequest(address, new HttpCallbacListener() {
           @Override
           public void onFinish(InputStream response) throws IOException, XmlPullParserException {
                   //处理服务器返回的天气信息
                   Utility.handleWeatherResponse(WeatherActivity.this,response);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           showWeather();
                       }
                   });

           }

           @Override
           public void onError(Exception e) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       publishText.setText("同步失败");
                   }
               });

           }
       });

    }
    /**
     * 从SharedPreferences 文件中读取储存的天气信息，并显示到界面上。
      */
     private void showWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));

        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        publishText.setText("今天"+"发布");
        currentDateText.setText(prefs.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);

     }



}
