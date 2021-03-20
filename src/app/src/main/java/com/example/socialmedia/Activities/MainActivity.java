package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.socialmedia.AppConfig;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        redirectToActivity();
    }

    private void redirectToActivity() {
        Intent intent;

        if (AppConfig.isLogged(context)) {
            intent = new Intent(context, PostActivity.class);
        } else {
            intent = new Intent(context, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
