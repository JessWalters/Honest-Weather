package xenapps.funnyweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by sable_000 on 4/6/2015.
 */
public class UpdateDialog {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static void appLaunched(Context mContext) throws PackageManager.NameNotFoundException{
        SharedPreferences sp = mContext.getSharedPreferences("updateDialog", 0);
        SharedPreferences.Editor editor = sp.edit();
        String versionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        String lastVersionName = sp.getString("versionName", "no past version");
        editor.putString("versionName", versionName);
        editor.apply();

        if (!lastVersionName.equals(versionName)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Welcome to Honest Weather!");

            builder.setMessage("Welcome to honest weather! This app is currently in beta, so that means that the chance of bugs and crashes is pretty high. If you" +
                    "encounter any of these please let me know by shooting me an email at Xenappsdev@gmail.com! I am working hard on new features and with your help we " +
                    "can make a truly awesome app! Thank you! \n\nSome quick notes about the apps current state: \n\nVery few sayings so don't expect tons of lulz just yet. \n\n" +
                    "Widget and settings screen will be available soon!");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.show();
            dialog.show();
        }
        else {
            return;
        }
    }
}
