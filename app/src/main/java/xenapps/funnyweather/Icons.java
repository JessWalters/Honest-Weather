package xenapps.funnyweather;

import android.util.Log;

/**
 * Created by sable_000 on 2/23/2015.
 */
public class Icons {
    public static String TAG = Icons.class.getSimpleName();
    long time = System.currentTimeMillis() / 1000;

    private int sunny = R.drawable.sunny;
    private int clearNight = R.drawable.clearnight;
    private int cloudy = R.drawable.cloudy;
    private int foggy = R.drawable.foggy;
    private int freezingRain = R.drawable.freezingrain;
    private int hail = R.drawable.hail;
    private int heavyRain = R.drawable.heavyrain;
    private int heavySnow = R.drawable.heavysnow;
    private int lightDrizzle = R.drawable.lightdrizzle;
    private int lightSnow = R.drawable.lightsnow;
    private int mediumSnow = R.drawable.mediumsnow;
    private int mixed = R.drawable.mixed;
    private int nightShowers = R.drawable.nightshowers;
    private int nightSnow = R.drawable.nightsnow;
    private int nightTStorm = R.drawable.nighttstorm;
    private int pCloudyDay = R.drawable.pcloudyday;
    private int pCloudyNight = R.drawable.pcloudynight;
    private int tStorm = R.drawable.thunderstorm;
    private int rainy = R.drawable.rainy;
    private int sleet = R.drawable.sleet;
    private int windy = R.drawable.windy;
    private int blizzard = R.drawable.windysnow;

    public int getIcon(int iconId, long sunrise, long sunset) {

        if (iconId == 800 || iconId == 801 || iconId == 951 || iconId == 904) {
            if (time > sunrise && time < sunset) {
                iconId = sunny;
            }
            else {
                iconId = clearNight;
            }
        }

        else if (iconId == 804) {
            iconId = cloudy;
        }

        else if (iconId == 701 || iconId == 711 || iconId == 721 || iconId == 731 || iconId == 741 || iconId == 751 || iconId == 761 || iconId == 762 || iconId == 771 || iconId == 781) {
            iconId = foggy;
        }

        else if (iconId == 511) {
            iconId = freezingRain;
        }

        else if (iconId == 906) {
            iconId = hail;
        }

        else if (iconId == 502 || iconId == 503 || iconId == 504 || iconId == 522) {
            if (time > sunrise && time < sunset) {
                iconId = heavyRain;
            }
            else {
                iconId = nightShowers;
            }
        }

        else if (iconId == 602 || iconId == 622) {
            iconId = heavySnow;
        }

        else if (iconId == 300 || iconId == 301 || iconId == 302 || iconId == 310 || iconId == 311 || iconId == 312 || iconId == 313 || iconId == 314 || iconId == 321) {
            if (time < sunset && time > sunrise) {
                iconId = lightDrizzle;
            }
            else {
                iconId = nightShowers;
            }
        }

        else if (iconId == 600 || iconId == 620) {
            if (time < sunset && time > sunrise) {
                iconId = lightSnow;
            }
            else {
                iconId = nightSnow;
            }
        }

        else if (iconId == 601 || iconId == 621) {
            if (time < sunset && time > sunrise) {
            iconId = mediumSnow;
        }
            else {
                iconId = nightSnow;
            }
        }

        else if (iconId == 615 || iconId == 616) {
            iconId = mixed;
        }

        else if (iconId == 200 || iconId == 201 || iconId == 902 || iconId == 901 || iconId == 202 || iconId == 210 || iconId == 211 || iconId == 212 || iconId == 221 || iconId == 230 || iconId == 231 ||iconId == 232) {
            if (time > sunrise && time < sunset) {
                iconId = tStorm;
            }
            else {
                iconId = nightTStorm;
            }
        }

        else if (iconId == 802 || iconId == 803) {
            if (time < sunset && time > sunrise) {
                iconId = pCloudyDay;
            }

            else {
                iconId = pCloudyNight;
            }
        }

        else if (iconId == 500 || iconId == 501 || iconId == 520 || iconId == 521 || iconId == 531) {
            if (time < sunset && time > sunrise) {
                iconId = rainy;
            }
            else {
                iconId = nightShowers;
            }
        }

        else if (iconId == 611 || iconId == 612) {
            iconId = sleet;
        }

        else if (iconId == 903) {
            iconId = blizzard;
        }

        else if (iconId == 900 || iconId == 905 || iconId == 952 || iconId == 953 || iconId == 954 || iconId == 955 || iconId == 956 || iconId == 957 || iconId == 958 || iconId == 959 || iconId == 960 || iconId == 961 || iconId == 962) {
            iconId = windy;
        }


        return iconId;
    }

}
