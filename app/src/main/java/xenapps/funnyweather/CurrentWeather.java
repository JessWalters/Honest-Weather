package xenapps.funnyweather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sable_000 on 2/20/2015.
 */
public class CurrentWeather {

    private int mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private String mCondition;
    private String mSummary;
    private String mTimeZone;
    private String mLocation;
    private long mSunrise;
    private long mSunset;

    public String getmTimeZone() {
        return mTimeZone;
    }

    public void setmTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    public void setIcon(int iconId) {
        this.mIcon = iconId;
    }

    public int getIconId() {

        return mIcon;
    }


    public long getTime() {
        return mTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        format.setTimeZone(TimeZone.getTimeZone(getmTimeZone()));
        Date dateTime = new Date(getTime()*1000);
        String timeString = format.format(dateTime);
        return timeString;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }
    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }
    public int getTemperature() {
        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getmCondition() {
        return mCondition;
    }

    public void setmCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    public long getSunrise() {
        return mSunrise;
    }

    public void setSunrise(long sunrise) {
        this.mSunrise = sunrise;
    }

    public long getSunset() {
        return mSunset;
    }

    public void setSunset(long sunset) {
        this.mSunset = sunset;
    }

}


