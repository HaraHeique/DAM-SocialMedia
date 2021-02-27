package com.example.socialmedia.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.socialmedia.R;

public class BaseActivity extends AppCompatActivity {

    protected ActionBar actionBar;
    protected Toolbar toolbar;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        context = getApplicationContext();
    }

    protected void setToolbarConfig(int toolbarId, String title, boolean backButton) {
        toolbar = findViewById(toolbarId);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.white));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(backButton);
    }

    protected void setTitleActionBar(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void hideActionBar() {
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void hideItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    protected void showItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setVisible(true);
        }
    }

    protected void disableItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setEnabled(false);
        }
    }

    protected void enableItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setEnabled(true);
        }
    }
}
