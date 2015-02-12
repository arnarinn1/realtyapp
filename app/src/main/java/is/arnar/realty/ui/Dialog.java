package is.arnar.realty.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Arnar on 10.2.2015.
 */
public class Dialog
{
    public static final int THEME_HOLO_DARK = 3;

    public static void ShowDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, THEME_HOLO_DARK);
        builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.show();
        alert.show();
    }
}
