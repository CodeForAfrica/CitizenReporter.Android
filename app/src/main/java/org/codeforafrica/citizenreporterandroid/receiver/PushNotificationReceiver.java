package org.codeforafrica.citizenreporterandroid.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.parse.ParsePushBroadcastReceiver;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentDetailActivity;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by edwinkato on 10/6/17.
 */

public class PushNotificationReceiver extends ParsePushBroadcastReceiver {

    public static final String PARSE_DATA_KEY = "com.parse.Data";
    public static final String TAG = "PushNotification";

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        JSONObject data = getDataFromIntent(intent);
        generate_notification(context, data);
    }

    private JSONObject getDataFromIntent(Intent intent) {
        JSONObject data = null;
        try {
            data = new JSONObject(intent.getExtras().getString(PARSE_DATA_KEY));
        } catch (JSONException e) {
            // Json was not readable...
        }
        return data;
    }

    private void generate_notification(Context context, JSONObject json){
        String description = "";
        String assignmentId = "";
        String title = "";
        try {
            description = json.getString("description");
            assignmentId = json.getString("assignmentId");
            title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, AssignmentDetailActivity.class);
        intent.putExtra("assignment_id", assignmentId);
        intent.putExtra("is_new_assignment", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_creporter_launcher)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }
}
