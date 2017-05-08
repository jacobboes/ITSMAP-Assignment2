package com.two.assignment.itsmap.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.two.assignment.itsmap.weather.model.CityWeather;
import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.util.List;

public class WeatherService extends Service {
    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
