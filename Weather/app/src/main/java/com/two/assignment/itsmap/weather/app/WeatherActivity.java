package com.two.assignment.itsmap.weather.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.two.assignment.itsmap.weather.R;
import com.two.assignment.itsmap.weather.service.WeatherService;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        startService(new Intent(WeatherActivity.this, WeatherService.class));
    }
}
