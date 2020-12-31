package vn.edu.usth.vn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
                @SuppressLint("StaticFieldLeak") AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
                    @Override
                    protected void onPreExecute() {
                        // do some preparation here, if needed
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        // This is where the worker thread's code is executed
                        // params are passed from the execute() method call
                        try {
                            // wait for 5 seconds to simulate a long network access
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return params[0];
                    }
                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        // This method is called in the main thread, so it's possible
                        // to update UI to reflect the worker thread progress here.
                        // In a network access task, this should update a progress bar
                        // to reflect how many percent of data has been retrieved
                    }
                    @Override
                    protected void onPostExecute(String string) {
                        // This method is called in the main thread. After #doInBackground returns
                        // the String data, we simply set it to an ImageView using ImageView.setImageBitmap()
                        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                    }
                };
                task.execute("some sample json here");
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