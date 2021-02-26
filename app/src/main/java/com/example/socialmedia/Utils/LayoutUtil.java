package com.example.socialmedia.Utils;

import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;

public final class LayoutUtil {

    public static void hideActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public static void setTitleActionBar(ActionBar actionBar, String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public static void hideItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    public static void showItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setVisible(true);
        }
    }

    public static void disableItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setEnabled(false);
        }
    }

    public static void enableItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setEnabled(true);
        }
    }
}
