package com.two.assignment.itsmap.weather.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.util.List;

public class WeatherDatabase extends SQLiteOpenHelper implements IWeatherDatabase {

    private static final int VERSION = 1;
    private static final String NAME = "WeatherDb";

    public WeatherDatabase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS weather");
        this.onCreate(db);
    }

    @Override
    public WeatherInfo getCurrentWeather() {
        return null;
    }

    @Override
    public List<WeatherInfo> getPastWeather() {
        return null;
    }
}
