package com.example.administrator.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.app.R;

/**
 * Created by Administrator on 2016/7/14.
 */
public class searchActivity extends Activity implements View.OnClickListener{

    private EditText editText;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

         editText =(EditText)findViewById(R.id.et);
         button =(Button)findViewById(R.id.button);
         button.setOnClickListener(this);


        }

    @Override
    public void onClick(View v) {

        String cityName= editText.getText().toString();
        if(!TextUtils.isEmpty(cityName)){
            Intent intent= new Intent(searchActivity.this,WeatherActivity.class);
            intent.putExtra("city_name",cityName);
            intent.putExtra("tag","searchActivity");
            startActivity(intent);

    }else {
            Toast.makeText(this,"城市为空",Toast.LENGTH_SHORT).show();
        }




    }
}
