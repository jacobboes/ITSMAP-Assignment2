package com.two.assignment.itsmap.weather.service;

import com.two.assignment.itsmap.weather.model.CityWeather;

import java.util.List;

public interface IWeatherService {
    CityWeather getCurrentWeather();

    List<CityWeather> getPastWeather();
}
