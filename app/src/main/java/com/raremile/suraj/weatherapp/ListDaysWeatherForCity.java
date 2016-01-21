package com.raremile.suraj.weatherapp;

import javax.sql.StatementEvent;

/**
 * Created by suraj on 1/21/16.
 */
public class ListDaysWeatherForCity {
    String date;
    String humidity;
    String minTemp;
    String maxTemp;
    String currentTemp;
    public ListDaysWeatherForCity(){

    }

    public void setDate(String date){
        this.date = date;
    }

    public void setHumidity(String humidity){
        this.humidity = humidity;
    }

    public void setMinTemp(String minTemp){
        this.minTemp = minTemp;
    }

    public void setMaxTemp(String maxTemp){
        this.maxTemp = maxTemp;
    }

    public void setCurrentTemp(String currentTemp){
        this.currentTemp = currentTemp;
    }

    public String getDate(){
        return date;
    }

    public String getHumidity(){
        return humidity;
    }

    public String getMinTemp(){
        return minTemp;
    }

    public String getMaxTemp(){
        return maxTemp;
    }

    public String getCurrentTemp(){
        return currentTemp;
    }
}
