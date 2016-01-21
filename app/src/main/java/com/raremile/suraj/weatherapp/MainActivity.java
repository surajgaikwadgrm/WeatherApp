package com.raremile.suraj.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raremile.suraj.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showListOfCities();
    }

    private void showListOfCities(){
        // TODO: Add list of cities
    }
}
