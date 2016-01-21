package com.raremile.suraj.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raremile.suraj.R;
import com.raremile.suraj.weatherofcity.CityWeather;

public class CityWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        ConnectionGetterAsyncTask cGAT = new ConnectionGetterAsyncTask(AllConstants.baseApiCallURL+ "Bangalore" +"&appid="+AllConstants.apiKey,findViewById(android.R.id.content),getApplicationContext());
        cGAT.execute();
    }


}
