package com.example.socialmedia.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
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

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        AppConfig.setAppToken(context, s);
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
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String notificationTitle = "Social Media";
        String notificationText = String.format("%s comentou o seu post.", userName);

        Notification notification = buildNotificationPostComment(pendingIntent, notificationTitle, notificationText);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(AppConfig.nextNotificationId(context), notification);
    }

    private Intent createIntentPostComment(String id) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("postId", id);

        return intent;
    }

    private Notification buildNotificationPostComment(PendingIntent pendingIntent, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat
            .Builder(context, getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_round_message_24)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

        return builder.build();
    }
}