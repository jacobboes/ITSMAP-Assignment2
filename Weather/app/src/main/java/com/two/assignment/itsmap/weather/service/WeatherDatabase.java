package com.two.assignment.itsmap.weather.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDatabase extends SQLiteOpenHelper {

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

    }
}