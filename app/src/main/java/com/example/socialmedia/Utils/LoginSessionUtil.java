package com.example.socialmedia.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.socialmedia.Models.CurrentUser;

public final class LoginSessionUtil {

    public static boolean isLogged(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);

        return mPrefs.getBoolean("isLogged", false);
    }

    public static CurrentUser getCurrentInfo(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);

        return new CurrentUser(
            mPrefs.getString("login", ""),
            mPrefs.getString("name", ""),
            mPrefs.getString("password", ""),
            mPrefs.getString("pathImgProfile", "")
        );
    }

    public static void setLogin(Context context, boolean value) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("isLogged", value).apply();
    }

    public static void setCurrentUserInfo(Context context, CurrentUser user) {
        SharedPreferences mPrefs = context.getSharedPreferences("currentUserInfo", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("password", user.password);
        mEditor.putString("name", user.name);
        mEditor.putString("login", user.login);
        mEditor.putString("pathImgProfile", user.pathImgProfile);
        mEditor.apply();
    }
}
