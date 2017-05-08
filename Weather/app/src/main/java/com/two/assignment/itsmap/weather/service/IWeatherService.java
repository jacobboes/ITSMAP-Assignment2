package com.two.assignment.itsmap.weather.service;

import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.util.List;

public interface IWeatherService {
    WeatherInfo getCurrentWeather();

    List<WeatherInfo> getPastWeather();
}
