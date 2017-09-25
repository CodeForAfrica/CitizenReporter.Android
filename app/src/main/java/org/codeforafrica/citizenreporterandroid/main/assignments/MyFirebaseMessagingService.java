package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Ahereza on 9/18/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    Intent intent = new Intent(this, AssignmentDetailActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

    NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
    style.setBigContentTitle("New Assignments");
    style.bigText(remoteMessage.getNotification().getBody());

    NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable
        .ic_ic_chat_50, "MORE", null).build();

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
    notificationBuilder.setContentTitle("New Assignments");
    notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
    notificationBuilder.setAutoCancel(true);
    notificationBuilder.setSmallIcon(R.mipmap.ic_creporter_launcher);
    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.splash_mdpi));
    notificationBuilder.setPriority(Notification.PRIORITY_MAX);
    notificationBuilder.setStyle(style);
    notificationBuilder.addAction(action);
    notificationBuilder.setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0, notificationBuilder.build());

  }
}

