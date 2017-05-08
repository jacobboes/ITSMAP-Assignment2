package com.two.assignment.itsmap.weather.service;

import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.util.List;

public interface IWeatherDatabase{
    WeatherInfo getCurrentWeather();

    List<WeatherInfo> getPastWeather();
}
