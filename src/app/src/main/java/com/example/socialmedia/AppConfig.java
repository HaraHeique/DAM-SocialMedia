package com.example.socialmedia;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.socialmedia.Models.CurrentUser;

public final class AppConfig {

    public static final String BASE_URL = "https://socialifes.herokuapp.com/";

    public static boolean isLogged(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);

        return mPrefs.getBoolean("isLogged", false) &&
               !mPrefs.getString("authToken", "").isEmpty();
    }

    public static int nextNotificationId(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        int id = mPrefs.getInt("lastNotificationId", 0);

        if (id + 1 == Integer.MAX_VALUE) { id = 0; }

        mPrefs.edit().putInt("lastNotificationId", id + 1).apply();

        return id;
    }

    public static String getAppToken(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);

        return mPrefs.getString("appToken", "");
    }

    public static CurrentUser getCurrentUser(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);

        CurrentUser currentUser = new CurrentUser(
            mPrefs.getString("login", ""),
            mPrefs.getString("authToken", ""),
            mPrefs.getString("appToken", "")
        );
        currentUser.isLogged = isLogged(context);

        return currentUser;
    }

    public static void setLogin(Context context, boolean value) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("isLogged", value).apply();

        // Caso seja logout exclui todas info com exceção do AppToken
        if (!value) {
            String appToken = getAppToken(context);
            mEditor.clear();
            setAppToken(context, appToken);
        }
    }

    public static void setAppToken(Context context, String appToken) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("appToken", appToken).apply();
    }

    public static void setCurrentUser(Context context, CurrentUser currentUser) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();

        mEditor.putString("login", currentUser.login);
        mEditor.putString("authToken", currentUser.authToken);
        mEditor.apply();
    }
}
