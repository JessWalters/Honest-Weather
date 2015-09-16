package xenapps.funnyweather;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Created by sable_000 on 4/3/2015.
 */
public class WeatherWidget extends AppWidgetProvider {

    private static LocationUpdate mLocationUpdate = new LocationUpdate();


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i=0; i<appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        //Enter any thing to happen on creating widget_info
    }

    @Override
    public void onDisabled(Context context) {
        //Enter any thing to happen when widget_info is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        //Construct remoteview object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weatherwidget);
        SharedPreferences sp = context.getSharedPreferences("widget", Activity.MODE_PRIVATE);
        mLocationUpdate.getLocation(context);

        String summary  = sp.getString("summary", "Something is wrong");
        int icon = sp.getInt("iconId", R.drawable.sunny);

        views.setImageViewResource(R.id.iconView, icon);
        views.setTextViewText(R.id.summaryView, summary);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //TODO: update display is a problem so maybe call it from on create in the main activity
    }

}
