package org.codeforafrica.citizenreporterandroid.receiver;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by edwinkato on 10/9/17.
 */

public class Notifications {

    private static final String NOTIFICATIONTAG = "NOTIFICATION";

    private ParseObject notificaton;
    private int count = 0;
    private JSONArray titlesArray = new JSONArray();
    private int Unique_Integer_Number;


    private static Notifications instance = null;

    //a private constructor so no instances can be made outside this class
    private Notifications(String title) {
        notificaton = new ParseObject("Notification");
        notificaton.put("count", 0);
        notificaton.put("identificationNumber", 0);
        titlesArray.put(title);
        notificaton.put("titlesArray", titlesArray);
        notificaton.pinInBackground();
        Unique_Integer_Number = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    }

    public static synchronized Notifications getInstance(String title) {
        if(instance == null)
            instance = new Notifications(title);

        return instance;
    }

    public int getNotificationCount(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.fromLocalDatastore();
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            count = object.getInt("count");
        } catch (ParseException exception) {
            Log.d(NOTIFICATIONTAG, exception.getMessage());
        }

        return count;
    }

    public JSONArray getTitlesArray(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.fromLocalDatastore();
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            titlesArray = object.getJSONArray("titlesArray");
        } catch (ParseException exception) {
            Log.d(NOTIFICATIONTAG, exception.getMessage());
        }

        return titlesArray;
    }

    public void addNotification(String title){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.fromLocalDatastore();
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            object.increment("count");
            titlesArray = object.getJSONArray("titlesArray");
            titlesArray.put(title);
            object.put("titlesArray", titlesArray);
            count = object.getInt("count");
        } catch (ParseException exception) {
            Log.d(NOTIFICATIONTAG, exception.getMessage());
        }
    }

    public void setIdentificationNumber(int number){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.fromLocalDatastore();
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            object.put("identificationNumber", number);
        } catch (ParseException exception) {
            Log.d(NOTIFICATIONTAG, exception.getMessage());
        }
    }

    public int getIdentificationNumber(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.fromLocalDatastore();
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            Unique_Integer_Number = object.getInt("identificationNumber");
        } catch (ParseException exception) {
            Log.d(NOTIFICATIONTAG, exception.getMessage());
        }
        return Unique_Integer_Number;
    }

    public void clearNotifications(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.fromLocalDatastore();
        query.setLimit(1);
        try {
            ParseObject object = query.getFirst();
            count = 0;
            object.put("count", count);
            object.put("titlesArray", new JSONArray());
        } catch (ParseException exception) {
            Log.d(NOTIFICATIONTAG, exception.getMessage());
        }
    }

}
