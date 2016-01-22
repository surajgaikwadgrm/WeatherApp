package com.raremile.suraj.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raremile.suraj.R;
import com.raremile.suraj.weatherforecast.CityWeatherForecast;
import com.raremile.suraj.weatherforecast.CityWeatherForecastListAdapter;
import com.raremile.suraj.weatherforecast.List;
import com.raremile.suraj.weatherofcity.CurrentCityWeather;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by suraj on 1/22/16.
 */
public class WeatherForecastAsynctask extends AsyncTask{
    String urlString;
    String response;
    HttpURLConnection urlConnection;
    View rootView;
    Context appContext;

    public WeatherForecastAsynctask(String inputURLString, View rootView, Context appContext){
        urlString = inputURLString;
        this.rootView = rootView;
        this.appContext = appContext;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlString);
            this.urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            response = result.toString();

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        String weatherForecastForCity = response;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        CityWeatherForecast cwfObj = gson.fromJson(weatherForecastForCity, CityWeatherForecast.class);
        setForecastWeather(cwfObj);
    }


    private void setForecastWeather(CityWeatherForecast cwObj){
        java.util.List<com.raremile.suraj.weatherforecast.List> listOfDays = cwObj.getList();
        ListView weatherForecatsListView = (ListView) rootView.findViewById(R.id.weatherForecastsList);

        CityWeatherForecastListAdapter someAdapter = new CityWeatherForecastListAdapter(appContext,R.layout.weather_forecast,listOfDays);
        weatherForecatsListView.setAdapter(someAdapter);
        //LayoutInflater someInfater = LayoutInflater.from(appContext);

        //RelativeLayout cityWeatherLayout = (RelativeLayout)someInfater.inflate(R.layout.activity_city_weather,null);

        //View currentDayWeatherView = someInfater.inflate(R.layout.single_day_weather_details, null);



        //LinearLayout currentDayLayout = (LinearLayout)rootView.findViewById(R.id.currentDayLayout);
        //currentDayLayout.addView(currentDayWeatherView);

    }

}

