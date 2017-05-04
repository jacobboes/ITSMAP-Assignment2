package com.two.assignment.itsmap.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.two.assignment.itsmap.weather.model.CityWeather;

import java.util.List;

public class WeatherService extends Service implements IWeatherService {
    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public CityWeather getCurrentWeather() {
        return null;
    }

    @Override
    public List<CityWeather> getPastWeather() {
        return null;
    }
}
