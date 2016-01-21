package com.raremile.suraj.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raremile.suraj.R;
import com.raremile.suraj.weatherofcity.CityWeather;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by suraj on 1/21/16.
 */
public class ConnectionGetterAsyncTask extends AsyncTask{
    String urlString;
    String response;
    HttpURLConnection urlConnection;
    View rootView;
    Context appContext;

    public ConnectionGetterAsyncTask(String inputURLString,View rootView, Context appContext){
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
        CityWeather cwObj = gson.fromJson(weatherNowForCity, CityWeather.class);
        setCurrentDayWeather(cwObj);

    }

    private void setCurrentDayWeather(CityWeather cwObj){

        LayoutInflater someInfater = LayoutInflater.from(appContext);

        RelativeLayout cityWeatherLayout = (RelativeLayout)someInfater.inflate(R.layout.activity_city_weather,null);

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
        currentDayLayout.addView(currentDayWeatherView);

    }
    private void setTextView(TextView targetTextView, String viewText){
        targetTextView.setText(viewText);
    }

    private String getDateFromEpoch(Long epochDate){
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(epochDate*1000));
        return date;
    }

    private String convertTemp(Double temp,String degrees){
        if(degrees.compareTo("F")==0){
            temp = (temp * 9 / 5) - 459.67;
        }
        else{
            temp = temp - 273.0;
        }
        return String.valueOf(temp.intValue());
    }

    public String getResponse(){
        return response;
    }
}