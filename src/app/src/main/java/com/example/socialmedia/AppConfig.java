package com.example.socialmedia;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.socialmedia.Models.CurrentUser;

public final class AppConfig {

    public static final String BASE_URL = "https://socialifes.herokuapp.com/";

    public static boolean isLogged(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);

        return mPrefs.getBoolean("isLogged", false);
    }

    public static CurrentUser getCurrentUser(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);

        CurrentUser currentUser = new CurrentUser(
            mPrefs.getString("login", ""),
            mPrefs.getString("name", ""),
            mPrefs.getString("bornDate", ""),
            mPrefs.getString("city", ""),
            mPrefs.getString("password", ""),
            mPrefs.getString("pathImgProfile", "")
        );
        currentUser.isLogged = isLogged(context);

        return currentUser;
    }

    public static void setLogin(Context context, boolean value) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("isLogged", value).apply();
    }

    public static void setCurrentUser(Context context, CurrentUser user) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();

        mEditor.putString("password", user.password);
        mEditor.putString("name", user.name);
        mEditor.putString("login", user.login);
        mEditor.putString("city", user.city);
        mEditor.putString("bornDate", user.bornDate);
        mEditor.putString("pathImgProfile", user.pathImgProfile);
        mEditor.apply();
    }
}
