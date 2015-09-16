package xenapps.funnyweather;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
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

/**
 * Created by sable_000 on 4/9/2015.
 */
public class LocationUpdate implements LocationListener {

    //MainActivity mainActivity = new MainActivity();
    public static final String API_KEY = "8a1da7d911b938678792817d6f6a7ed2";
    public CurrentWeather mCurrentWeather;
    public Icons mIcons = new Icons();
    LocationManager mLocationManager;
    Context context;

    public void getLocation(Context mContext) {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        context = mContext;

        if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            //Log.v(TAG, "Lat: " + location.getLatitude());
            //Log.v(TAG, "Long: " + location.getLongitude());

            getWeather(latitude, longitude, mContext);


        } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
            mLocationManager.removeUpdates(this);
            getWeather(latitude, longitude, context);

            //TODO: Make this work for widget_info and the min activity
            //TODO: Look into doing a sp boolean or int to distinguish between widget_info update and the main display update
        } else {
            //Log.v(TAG, "Location is null");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getWeather(double latitude, double longitude, final Context mContext) {
        String weatherURL = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=imperial" + "&APPID=" + API_KEY;


        if (isNetworkAvailable(mContext)) {
            //toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(weatherURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {


                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String jsonData = response.body().string();

                    try {
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            storeStrings(mContext);
                        } else {

                        }
                    } catch (JSONException e) {
                        //Log.v(TAG, "Error caught:", e);
                    }
                }
            });

        } else {
            //Toast.makeText(this, "The network is unavailable. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void storeStrings(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences("widget", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String[] summaryArray = Summary.getType(mCurrentWeather.getIconId(), mCurrentWeather.getTemperature());
        mCurrentWeather.setSummary(Summary.getSummary(summaryArray));
        int iconId = mIcons.getIcon(mCurrentWeather.getIconId(), mCurrentWeather.getSunrise(), mCurrentWeather.getSunset());

        editor.putString("summary", mCurrentWeather.getSummary());
        editor.putInt("iconId", iconId);
        editor.apply();
    }

    private boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        currentWeather.setmCondition(weatherObject.getString("main"));
        currentWeather.setLocation(forecast.getString("name"));
        currentWeather.setSunrise(sys.getLong("sunrise"));
        currentWeather.setSunset(sys.getLong("sunset"));
        return currentWeather;
    }

}
