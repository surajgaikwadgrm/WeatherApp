package com.raremile.suraj.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raremile.suraj.R;

import org.w3c.dom.Text;

public class CityWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        displayWeatherForCity(getCityName());
        setListenerForRefresh();

    }
    private String getCityName(){
        return "Bangalore";
    }

    private void displayWeatherForCity(String cityName){

        TextView cityNameView = (TextView) findViewById(R.id.tVCityName);
        cityNameView.setText(cityName);

        CurrentWeatherAsyncTask cGAT = new CurrentWeatherAsyncTask(AllConstants.baseApiCallURLCurrent+ cityName +"&appid="+AllConstants.apiKey,findViewById(android.R.id.content),getApplicationContext());
        cGAT.execute();

        WeatherForecastAsynctask wFAT = new WeatherForecastAsynctask(AllConstants.baseApiCallURLForecast+ cityName + "&appid=" + AllConstants.apiKey,findViewById(android.R.id.content),getApplicationContext());
        wFAT.execute();
    }
    private void setListenerForRefresh(){

        Button refreshButton = (Button) findViewById(R.id.buttonRefreshWeather);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWeatherForCity(getCityName());
                Toast.makeText(getApplicationContext(),"Refreshed",Toast.LENGTH_SHORT);
            }
        });
    }

}
