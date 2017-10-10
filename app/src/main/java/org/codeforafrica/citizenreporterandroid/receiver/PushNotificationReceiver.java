package org.codeforafrica.citizenreporterandroid.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;

import org.codeforafrica.citizenreporterandroid.R;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.main.assignments.AssignmentDetailActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by edwinkato on 10/6/17.
 */

public class PushNotificationReceiver extends ParsePushBroadcastReceiver {

    public static final String PARSE_DATA_KEY = "com.parse.Data";
    public static final String TAG = "PushNotification";
    final static String GROUP_KEY_ASSIGNMENTS = "group_key_assignments";
    private StringBuilder stringBuilder = new StringBuilder();

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

        Notifications notifications = Notifications.getInstance(title);
        notifications.addNotification(title);

        Intent assignmentDetailIntent = new Intent(context, AssignmentDetailActivity.class);
        assignmentDetailIntent.putExtra("assignment_id", assignmentId);
        assignmentDetailIntent.putExtra("is_new_assignment", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, assignmentDetailIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_creporter_launcher);
        builder.setAutoCancel(true);

        int Unique_Integer_Number = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        int count = notifications.getNotificationCount();
        if (count > 1) {
            JSONArray titles = notifications.getTitlesArray();
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            for(int i = titles.length() ; i > -1; i--) {
                try {
                    String savedTitle = titles.getString(i);
                    if (!savedTitle.equals("")) {
                        inboxStyle.addLine(savedTitle);
                        stringBuilder.append(savedTitle);
                    }
                } catch (JSONException exception) {

                }
            }
            inboxStyle.setBigContentTitle(count + " new assignments");
            inboxStyle.setSummaryText("Citizen reporter");
            builder.setContentTitle(count + " new assignments");
            builder.setContentText("Click to view assignments");
            builder.setStyle(inboxStyle);
            builder.setGroup(GROUP_KEY_ASSIGNMENTS);
            builder.setGroupSummary(true);
            Unique_Integer_Number = notifications.getIdentificationNumber();

            Intent allAssignmentsIntent = new Intent(context, MainActivity.class);
            allAssignmentsIntent.putExtra("is_new_assignment", true);
            pendingIntent = PendingIntent.getActivity(context, 0, allAssignmentsIntent, 0);
        } else {
            builder.setContentTitle(title);
            builder.setContentText(description);
            notifications.setIdentificationNumber(Unique_Integer_Number);
        }
        builder.setContentIntent(pendingIntent);
        builder.build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Unique_Integer_Number, builder.build());
    }
}
