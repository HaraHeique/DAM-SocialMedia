package com.example.socialmedia.Utils;

import android.content.Context;
import android.widget.Toast;

public final class AlertMessageUtil {

    public static void defaultAlert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void errorRequestAlert(Context context) {
        Toast.makeText(context, "Houve um erro ao obter dados do servidor.", Toast.LENGTH_LONG).show();
    }
}
