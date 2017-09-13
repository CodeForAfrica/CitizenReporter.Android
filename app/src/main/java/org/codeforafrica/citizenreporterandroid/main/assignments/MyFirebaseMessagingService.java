package org.codeforafrica.citizenreporterandroid.main.assignments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.codeforafrica.citizenreporterandroid.R;

/**
 * Created by Jeffkungu on 08/09/2017.
 */

/**
 * This class enables the user to receive notification and data from FCM server.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        if (remoteMessage.getData().size() > 0) {
//            try {
//
//                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
//                Log.e("Tag", remoteMessage.getData().toString());
//
//
//                sendNotification(remoteMessage.getData().toString());
//
//
//            } catch (Exception e) {
//
//            }
//
//        }

        Intent intent = new Intent(this, AssignmentDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("New Assignments");
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        }

//    private void sendNotification(String message) {
//
//        Intent intent = new Intent(this, AssignmentDetailActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        long time = System.currentTimeMillis();
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Notification.Builder notification = new Notification.Builder(this); 
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            Notification.Action action = new Notification.Action.Builder(Icon.createWithResource(this, R.mipmap.ic_archive), "View", pendingIntent).build();
//            notification.setAutoCancel(true);
//            notification.setSmallIcon(R.mipmap.ic_launcher);
//            notification.setSound(defaultSoundUri);
//            notification.setContentTitle("New Assignments");
//            notification.setContentText(message);
//            notification.setWhen(time);
//            notification.setColor(Color.TRANSPARENT);
//            notification.setContentIntent(pendingIntent);
//            notification.addAction(action);
//        } else {
//            notification.setAutoCancel(true);
//            notification.setSmallIcon(R.mipmap.ic_launcher);
//            notification.setSound(defaultSoundUri);
//            notification.setContentTitle("New Assignments");
//            notification.setContentText(message);
//            notification.setWhen(time);
//            notification.setColor(Color.TRANSPARENT);
//            notification.setContentIntent(pendingIntent);
//            notification.addAction(R.mipmap.ic_archive, "View", pendingIntent);
//        }
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notification.build());
//    }
    }
