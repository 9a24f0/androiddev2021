package vn.edu.usth.vn;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WeatherActivity extends AppCompatActivity {

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PagerAdapter adapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.pager_header);
        tabLayout.setupWithViewPager(viewPager);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.weatherforecast);
    }

    public WeatherActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("WeatherActivity", "Starting");
        mediaPlayer.start();
        copyFileToExternalStorage(R.raw.weatherforecast, "coypfile.mp3");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("WeatherActivity", "Stopping");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        Log.i("WeatherActivity", "Destroying");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        Log.i("WeatherActivity", "Pausing");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        Log.i("WeatherActivity", "Resuming");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Intent pref = new Intent(this, PrefActivity.class);
                startActivity(pref);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void copyFileToExternalStorage(int resourceId, String resourceName) {
        try {
            File file = new File(getExternalFilesDir(null), resourceName);
            InputStream in = getApplicationContext().getResources().openRawResource(resourceId);
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024 * 2];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            }
            finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "hello we are at catch :(", Toast.LENGTH_LONG);
            toast.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}