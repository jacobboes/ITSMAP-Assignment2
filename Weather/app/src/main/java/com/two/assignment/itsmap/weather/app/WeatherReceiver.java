package com.two.assignment.itsmap.weather.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.two.assignment.itsmap.weather.util.WeatherUtil;

public class WeatherReceiver extends BroadcastReceiver {

    private WeatherReceiverAction action;

    public WeatherReceiver() {

    }

    public WeatherReceiver(WeatherReceiverAction action) {
        this.action = action;
    }

    public void setAction(WeatherReceiverAction action) {
        this.action = action;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WeatherUtil.BROADCAST_WEATHER)) {
            action.doAction();
        }
    }
}
