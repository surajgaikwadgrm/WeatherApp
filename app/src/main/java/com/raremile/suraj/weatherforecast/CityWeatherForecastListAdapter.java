package com.raremile.suraj.weatherforecast;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import com.raremile.suraj.R;

/**
 * Created by suraj on 1/22/16.
 */
public class CityWeatherForecastListAdapter extends ArrayAdapter<com.raremile.suraj.weatherforecast.List> {

    public CityWeatherForecastListAdapter(Context appContext, @LayoutRes int resource){
        super(appContext,resource);
    }

    public CityWeatherForecastListAdapter(Context appContext, int resource, List<com.raremile.suraj.weatherforecast.List> someList){
        super(appContext,resource,someList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.single_day_weather_details,null);
        }

        com.raremile.suraj.weatherforecast.List listItem = getItem(position);

        if(listItem != null){
            TextView dateView = (TextView)v.findViewById(R.id.tVDateValue);
            TextView tempView = (TextView)v.findViewById(R.id.tVTemperatureLargeValue);
            TextView humidityView = (TextView)v.findViewById(R.id.tVHumidityValue);
            TextView minTempView = (TextView)v.findViewById(R.id.tVMinTempValue);
            TextView maxTempView = (TextView)v.findViewById(R.id.tVMaxTempValue);


            if(dateView!=null){
                dateView.setText(getDateFromEpoch(listItem.getDt()));
            }
            if(tempView!=null){
                tempView.setText(convertTemp(listItem.getMain().getTemp(), "C"));
            }
            if(humidityView!=null){
                humidityView.setText(listItem.getMain().getHumidity().toString());
            }
            if(minTempView!=null){
                minTempView.setText(convertTemp(listItem.getMain().getTempMin(),"C"));
            }
            if(maxTempView!=null){
                maxTempView.setText(convertTemp(listItem.getMain().getTempMax(),"C"));
            }

        }

        return v;
    }
    private String getDateFromEpoch(Long epochDate){
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(epochDate * 1000));
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
}
