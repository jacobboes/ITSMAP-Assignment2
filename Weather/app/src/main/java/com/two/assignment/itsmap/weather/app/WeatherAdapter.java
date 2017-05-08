package com.two.assignment.itsmap.weather.app;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.two.assignment.itsmap.weather.R;
import com.two.assignment.itsmap.weather.model.WeatherInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends ArrayAdapter<WeatherInfo> {
    public WeatherAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public WeatherAdapter(@NonNull Context context, @NonNull List<WeatherInfo> items) {
        super(context, R.layout.weather_adapter, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.weather_adapter, null);
        }

        WeatherInfo item = getItem(position);
        if (item != null) {
            ImageView image = (ImageView) view.findViewById(R.id.adapterImage);
            TextView description = (TextView) view.findViewById(R.id.adapterDescription);
            TextView temp = (TextView) view.findViewById(R.id.adapterTemp);
            TextView date = (TextView) view.findViewById(R.id.adapterDate);
            TextView time = (TextView) view.findViewById(R.id.adapterTime);

            description.setText(item.description);
            temp.setText(item.temp + getContext().getString(R.string.celsius));

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date.setText(dateFormat.format(item.date));
            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            time.setText(timeFormat.format(item.date));
        }

        return view;
    }
}
