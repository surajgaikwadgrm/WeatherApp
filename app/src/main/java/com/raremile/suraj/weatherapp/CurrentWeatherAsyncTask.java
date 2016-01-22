package com.raremile.suraj.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raremile.suraj.R;
import com.raremile.suraj.weatherofcity.CurrentCityWeather;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by suraj on 1/21/16.
 */
public class CurrentWeatherAsyncTask extends AsyncTask{
    String urlString;
    String response;
    HttpURLConnection urlConnection;
    View rootView;
    Context appContext;

    public CurrentWeatherAsyncTask(String inputURLString, View rootView, Context appContext){
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
        String weatherNowForCity = response;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        CurrentCityWeather cwObj = gson.fromJson(weatherNowForCity, CurrentCityWeather.class);
        setCurrentDayWeather(cwObj);
    }


    private void setCurrentDayWeather(CurrentCityWeather cwObj){

        LayoutInflater someInfater = LayoutInflater.from(appContext);

        View currentDayWeatherView = someInfater.inflate(R.layout.single_day_weather_details, null);

        TextView dateView = (TextView)currentDayWeatherView.findViewById(R.id.tVDateValue);
        setTextView(dateView, getDateFromEpoch(cwObj.getDt()));

        TextView tempView = (TextView)currentDayWeatherView.findViewById(R.id.tVTemperatureLargeValue);
        setTextView(tempView, convertTemp(cwObj.getMain().getTemp(), "C"));

        TextView humidityView = (TextView)currentDayWeatherView.findViewById(R.id.tVHumidityValue);
        setTextView(humidityView, cwObj.getMain().getHumidity().toString());

        TextView minTempView = (TextView)currentDayWeatherView.findViewById(R.id.tVMinTempValue);
        setTextView(minTempView, convertTemp(cwObj.getMain().getTempMax(),"C"));

        TextView maxTempView = (TextView)currentDayWeatherView.findViewById(R.id.tVMaxTempValue);
        setTextView(maxTempView, convertTemp(cwObj.getMain().getTempMax(),"C"));

        //cityWeatherLayout.addView(currentDayWeatherView);

        LinearLayout currentDayLayout = (LinearLayout)rootView.findViewById(R.id.currentDayLayout);
        currentDayLayout.removeAllViews();
        currentDayLayout.addView(currentDayWeatherView);

    }
    private void setTextView(TextView targetTextView, String viewText){
        targetTextView.setText(viewText);
    }

    private String getDateFromEpoch(Long epochDate){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+5:30"));
        String date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new java.util.Date(epochDate * 1000));
        System.out.println(date);
        return date;
    }

    private String convertTemp(Double temp,String degrees){
        if(degrees.compareTo("F")==0){
            temp = (temp * 9 / 5) - 459.67;
        }
        else{
            temp = temp - 273.0;
        }
        return String.valueOf(temp.intValue()) + "°" + degrees;
    }

    public String getResponse(){
        return response;
    }
}