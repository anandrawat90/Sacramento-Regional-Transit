package com.rawat.anand.sacramentort;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Anand Rawat on 27-01-2017.
 */

public final class Utility {

    public static boolean isNotEmptyEV(String source, String text, Context context) {
        if (text != null && !text.trim().isEmpty())
            return true;
        alertWithToast(source + Constants.EMPTY_STRING_ERROR, context);
        return false;
    }

    public static void alertWithToast(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
