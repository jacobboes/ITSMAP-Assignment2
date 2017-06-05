package com.two.assignment.itsmap.weather.service;

import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.util.List;

public interface IWeatherDatabase{
    boolean Insert(WeatherInfo data);

    WeatherInfo getCurrentWeather();

    List<WeatherInfo> getPastWeather();

}
