package vn.edu.usth.vn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ForecastFragment firstFragment = new ForecastFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_weather, firstFragment, null)
                .commit();
    }

    public WeatherActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("WeatherActivity", "Starting");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("WeatherActivity", "Stopping");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("WeatherActivity", "Destroying");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("WeatherActivity", "Pausing");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("WeatherActivity", "Resuming");
    }
}