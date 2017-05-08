package com.two.assignment.itsmap.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.two.assignment.itsmap.weather.model.CityWeather;
import com.two.assignment.itsmap.weather.model.WeatherInfo;
import com.two.assignment.itsmap.weather.util.WeatherUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherService extends Service {

    private final IBinder IWeatherBinder = new WeatherBinder();
    IWeatherDatabase weatherDatabase;

    Timer timer;
    int Interval = 30;
    int TimerInterval = 60 * 1000 * Interval;


    public WeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        weatherDatabase = new WeatherDatabase(getApplicationContext());

        new getLatestWeather().execute();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new getLatestWeather().execute();
            }
        }, TimerInterval);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return IWeatherBinder;
    }

    public class WeatherBinder extends Binder {
        public WeatherService getService() {
            return WeatherService.this;
        }
    }

    public WeatherInfo getCurrentWeather() {
        return weatherDatabase.getCurrentWeather();
    }

    public List<WeatherInfo> getPastWeather() {
        return weatherDatabase.getPastWeather();
    }

    private class getLatestWeather extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Raw JSON response as String
            String foreCastJsonStr = null;

            try {
                // Construct the URL for OpenWeatherMap query and open connection
                URL url = new URL(WeatherUtil.WEATHER_API);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read input into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Adding new lines to buffer, for easier debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                foreCastJsonStr = buffer.toString();
                return foreCastJsonStr;

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error while closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            weatherDatabase.Insert(JsonToWeatherInfo(s));

            // Broadcast intent for main activity
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(WeatherUtil.BROADCAST_WEATHER);
            sendBroadcast(broadcastIntent);

        }

        private WeatherInfo JsonToWeatherInfo(String s) {
            Gson gson = new GsonBuilder().create();
            CityWeather weatherInfo = gson.fromJson(s, CityWeather.class);
            WeatherInfo retval = new WeatherInfo();
            retval.date.setTime(weatherInfo.dt);
            retval.temp = weatherInfo.main.temp;
            retval.description = weatherInfo.weather.get(0).description;
            retval.icon = weatherInfo.weather.get(0).icon;
            return retval;
        }
    }
}
