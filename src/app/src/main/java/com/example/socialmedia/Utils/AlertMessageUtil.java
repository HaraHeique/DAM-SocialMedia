package com.example.socialmedia.Utils;

import android.content.Context;
import android.widget.Toast;

public final class AlertMessageUtil {

    public static void defaultAlert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
