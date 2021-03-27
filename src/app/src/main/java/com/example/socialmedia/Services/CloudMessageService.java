package com.example.socialmedia.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.socialmedia.Activities.CommentActivity;
import com.example.socialmedia.AppConfig;
import com.example.socialmedia.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class CloudMessageService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        AppConfig.setAppToken(this, s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        String postId = data.get("idpost");
        String userName = data.get("nome");

        notifyPostComment(postId, userName);
    }

    private void notifyPostComment(String postId, String userName) {
        Intent intent = createIntentPostComment(postId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String notificationMessage = String.format("%s comentou o seu post.", userName);

        Notification notification = buildNotificationPostComment(pendingIntent, notificationMessage);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(AppConfig.nextNotificationId(this), notification);
    }

    private Intent createIntentPostComment(String id) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("postId", id);

        return intent;
    }

    private Notification buildNotificationPostComment(PendingIntent pendingIntent, String message) {
        NotificationCompat.Builder builder = new NotificationCompat
            .Builder(this, getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_round_message_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

        return builder.build();
    }
}