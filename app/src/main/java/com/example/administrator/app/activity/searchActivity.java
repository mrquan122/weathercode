package com.example.administrator.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.app.R;

/**
 * Created by Administrator on 2016/7/14.
 */
public class searchActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final EditText editText =(EditText)findViewById(R.id.et);
        Button button =(Button)findViewById(R.id.bt);
        setContentView(R.layout.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName= editText.getText().toString();
                Intent intent= new Intent(searchActivity.this,WeatherActivity.class);
                intent.putExtra("city_name",cityName);
                intent.putExtra("tag","searchActivity");
                startActivity(intent);
            }
        });



    }
}
