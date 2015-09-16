package xenapps.funnyweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by sable_000 on 2/20/2015.
 */
public class AlertDialogFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Oh shit!")
                .setMessage("There was an error. Please try again.")
                .setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

}
