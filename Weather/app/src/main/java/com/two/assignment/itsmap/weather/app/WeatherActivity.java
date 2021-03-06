package com.two.assignment.itsmap.weather.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.two.assignment.itsmap.weather.R;
import com.two.assignment.itsmap.weather.model.WeatherInfo;
import com.two.assignment.itsmap.weather.service.WeatherService;
import com.two.assignment.itsmap.weather.util.WeatherUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private List<WeatherInfo> pastWeather;

    private WeatherAdapter pastWeatherAdapter;
    private ListView pastWeatherView;

    private ImageView weatherIcon;
    private TextView temperature;
    private TextView description;
    private FloatingActionButton refresh;

    private WeatherService service;
    private boolean isServiceBound;
    private WeatherReceiver weatherReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        startService(new Intent(this, WeatherService.class));

        initViews();
        setupWeatherReceiver();
        pastWeather = new ArrayList<>();
        pastWeatherView = (ListView) findViewById(R.id.pastWeather);
        pastWeatherAdapter = new WeatherAdapter(this, pastWeather);
        pastWeatherView.setAdapter(pastWeatherAdapter);
    }

    private void initViews() {
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        refresh = (FloatingActionButton) findViewById(R.id.refreshWeather);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshWeather();
                showRefreshToast();
            }
        });
    }

    private void showRefreshToast() {
        Toast.makeText(this, R.string.RefreshingWeather, Toast.LENGTH_SHORT).show();
    }

    private void setupWeatherReceiver() {
        weatherReceiver = new WeatherReceiver(new WeatherReceiverAction() {
            @Override
            public void doAction() {
                if (isServiceBound) {
                    refreshView();
                }
            }
        });
        registerReceiver(weatherReceiver, new IntentFilter(WeatherUtil.BROADCAST_WEATHER));
    }

    private void refreshView() {
        pastWeather.clear();
        pastWeather.addAll(service.getPastWeather());
        pastWeatherAdapter.notifyDataSetChanged();
        refreshWeather();
    }

    private void refreshWeather() {
        if (isServiceBound) {
            WeatherInfo currentWeather = service.getCurrentWeather();
            if (currentWeather.icon == null)
                return;
            description.setText(currentWeather.description);
            temperature.setText(String.format("%.2f", currentWeather.temp) + getString(R.string.celsius));
            int resId = getResources().getIdentifier("r" + currentWeather.icon, "raw", getPackageName());
            InputStream input = getResources().openRawResource(resId);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            weatherIcon.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, WeatherService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            WeatherService.WeatherBinder binder = (WeatherService.WeatherBinder) service;
            WeatherActivity.this.service = binder.getService();
            isServiceBound = true;
            refreshView();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isServiceBound = false;
        }
    };
}
