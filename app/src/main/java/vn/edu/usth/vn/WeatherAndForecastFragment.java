package vn.edu.usth.vn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WeatherAndForecastFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        WeatherFragment weatherFragment = new WeatherFragment();
        ForecastFragment forecastFragment = new ForecastFragment();

        ft.add(R.id.page_fragment, weatherFragment, null);
        ft.add(R.id.page_fragment, forecastFragment, null);
        ft.commit();
    }
}