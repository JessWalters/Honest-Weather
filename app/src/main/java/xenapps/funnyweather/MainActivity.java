package xenapps.funnyweather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements LocationListener{

    @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @InjectView(R.id.summary) TextView mSummary;
    @InjectView(R.id.iconView) ImageView mIconView;
    @InjectView(R.id.refreshView) ImageView mRefresh;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.settingsButton) ImageView mSettingsButton;
    public LocationManager mLocationManager;
    Icons icons = new Icons();

    private CurrentWeather mCurrentWeather;
    private LocationUpdate mLocationUpdate = new LocationUpdate();

    public static final String API_KEY = "8a1da7d911b938678792817d6f6a7ed2";
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mProgressBar.setVisibility(View.VISIBLE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        try {
            UpdateDialog.appLaunched(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                mRefresh.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                //toggleRefresh();
            }
        });

        //Todo: make more summaries
        //Todo: Revise and set up XML files for the layout

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSettings();
            }
        });

    }

    private void launchSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void getLocation() {
        //mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            mProgressBar.setVisibility(View.INVISIBLE);
            Log.v(TAG, "Lat: " + location.getLatitude());
            Log.v(TAG, "Long: " + location.getLongitude());

            getWeather(latitude, longitude);


        }
        else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            mLocationManager.removeUpdates(this);
            getWeather(latitude, longitude);
        }
        else{
            Log.v(TAG, "Location is null");
        }
    }

    public void getWeather(double latitude, double longitude) {
        String weatherURL = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=imperial" + "&APPID=" + API_KEY;


        if (isNetworkAvailable()) {
            //toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(weatherURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertError();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String jsonData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //toggleRefresh();
                        }
                    });
                    try {
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertError();
                        }
                    } catch (JSONException e) {
                        Log.v(TAG, "Error caught:", e);
                    }
                }
            });

        } else {
            Toast.makeText(this, "The network is unavailable. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void updateDisplay() {
        String[] summaryArray = null;
        String fucking = "fucking";
        String fuck = "fuck";
        mTemperatureLabel.setText("" + mCurrentWeather.getTemperature());
        mIconView.setImageResource(icons.getIcon(mCurrentWeather.getIconId(), mCurrentWeather.getSunrise(), mCurrentWeather.getSunset()));
        summaryArray = Summary.getType(mCurrentWeather.getIconId(), mCurrentWeather.getTemperature());
        mCurrentWeather.setSummary(Summary.getSummary(summaryArray));

        if(mCurrentWeather.getSummary().length() < 30) {
            mSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }
        else {
            mSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        }

        //Find the words fuck and fucking and change color
        if (mCurrentWeather.getSummary().contains(fucking)) {
            int index = mCurrentWeather.getSummary().indexOf(fucking);
            Spannable summary = new SpannableString(mCurrentWeather.getSummary());
            summary.setSpan(new ForegroundColorSpan(Color.LTGRAY), index, index + 7, 0);
            mSummary.setText(summary, TextView.BufferType.SPANNABLE);
        }
        else {
            mSummary.setText(mCurrentWeather.getSummary());
        }

        //Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        //mIconView.setImageDrawable(drawable);
        //toggleRefresh();
        mRefresh.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /*private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefresh.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefresh.setVisibility(View.VISIBLE);
        }
    } */

    private void alertError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject main = forecast.getJSONObject("main");
        JSONArray weather = forecast.getJSONArray("weather");
        JSONObject weatherObject = weather.getJSONObject(0);
        JSONObject sys = forecast.getJSONObject("sys");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(main.getDouble("humidity"));
        currentWeather.setTemperature(main.getDouble("temp"));
        currentWeather.setIcon(weatherObject.getInt("id"));
        Log.v(TAG, "code is:" + weatherObject.getInt("id"));
        currentWeather.setmCondition(weatherObject.getString("main"));
        currentWeather.setLocation(forecast.getString("name"));
        currentWeather.setSunrise(sys.getLong("sunrise"));
        currentWeather.setSunset(sys.getLong("sunset"));
        return currentWeather;
    }

    // Required functions
    public void onProviderDisabled(String arg0) {}
    public void onProviderEnabled(String arg0) {}
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
}





