package com.two.assignment.itsmap.weather.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//https://developer.android.com/training/basics/data-storage/databases.html
public class WeatherDatabase extends SQLiteOpenHelper implements IWeatherDatabase {

    private static final int VERSION = 1;
    private static final String NAME = "WeatherDb";
    public static final String TABLE_NAME = "weather";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_DESCRIPTION = "description";
    private static final String COLUMN_NAME_TEMP = "temp";
    private static final String COLUMN_NAME_TIME = "time";
    private static final String COLUMN_NAME_ICON = "icon";

    public WeatherDatabase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE "+TABLE_NAME+" ( " +
                COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_DESCRIPTION + " TEXT, "+
                COLUMN_NAME_TEMP + " DOUBLE, "+
                COLUMN_NAME_TIME + " LONG, "+
                COLUMN_NAME_ICON + " TEXT )";

        db.execSQL(CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    @Override
    public boolean Insert(WeatherInfo data) {
        if(data == null)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_DESCRIPTION, data.description);
        values.put(COLUMN_NAME_TEMP, data.temp);
        values.put(COLUMN_NAME_TIME, data.date.getTime());
        values.put(COLUMN_NAME_ICON, data.icon);


        db.insert(TABLE_NAME, null, values);
        //TODO
        //CleanUp(db);
        return true;
    }

    @Override
    public WeatherInfo getCurrentWeather() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToLast();
            WeatherInfo returnVal = CursorToObj(cursor);
            cursor.close();
            db.close();
            return returnVal;
        }

        return new WeatherInfo();
    }

    @Override
    public List<WeatherInfo> getPastWeather() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME_ID + " DESC", null);

        List<WeatherInfo> returnVal = new ArrayList<>();
        while(cursor.moveToNext()) {
            returnVal.add(CursorToObj(cursor));
        }
        cursor.close();
        db.close();
        return returnVal;
    }

    private WeatherInfo CursorToObj(Cursor cursor){
        WeatherInfo returnVal = new WeatherInfo();
        returnVal.id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID));
        returnVal.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_DESCRIPTION));
        returnVal.temp = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_TEMP));
        returnVal.date = new Date();
        returnVal.date.setTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_TIME)));
        returnVal.icon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ICON));
        return returnVal;
    }

    private void CleanUp(SQLiteDatabase db){
        //http://stackoverflow.com/questions/11771580/deleting-android-sqllite-rows-older-than-x-days
        db.rawQuery("DELETE FORM "+ TABLE_NAME+ " WHERE " + COLUMN_NAME_TIME + " <= date('now','-1 day')", null);
        db.close();
    }
}
