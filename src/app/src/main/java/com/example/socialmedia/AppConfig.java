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

        if (!value) { mEditor.clear(); }
    }

    public static void setCurrentUser(Context context, CurrentUser currentUser) {
        SharedPreferences mPrefs = context.getSharedPreferences("configs", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();

        mEditor.putString("login", currentUser.login);
        mEditor.putString("authToken", currentUser.authToken);
        mEditor.putString("appToken", currentUser.appToken);
        mEditor.apply();
    }
}
