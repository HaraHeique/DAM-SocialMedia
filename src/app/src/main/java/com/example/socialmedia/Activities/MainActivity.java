package com.example.socialmedia.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();
        redirectToActivity();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.channel_id);
            String name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
