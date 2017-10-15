package org.codeforafrica.citizenreporterandroid.utils;

/**
 * Created by edwinkato on 10/13/17.
 */

import com.flurry.android.FlurryAgent;

import java.util.Map;

/**
 * Helps with logging custom events and errors to Flurry
 */
public class AnalyticsHelper {

    public static final String EVENT_FACEBOOK_SIGN_UP = "FACEBOOK_SIGN_UP";
    public static final String EVENT_GOOGLE_SIGN_UP = "GOOGLE_SIGN_UP";
    public static final String EVENT_PASSWORD_SIGN_UP = "PASSWORD_SIGN_UP";
    public static final String EVENT_LOGIN = "LOGIN";
    public static final String EVENT_ASSIGNMENT_OPEN = "ASSIGNMENT_OPEN";
    public static final String EVENT_CREATE_STORY = "CREATE_STORY";
    public static final String EVENT_SAVE_STORY = "SAVE_STORY";
    public static final String EVENT_UPLOAD_STORY = "UPLOAD_STORY";
    public static final String EVENT_IMAGE_UPLOAD = "IMAGE_UPLOAD";
    public static final String EVENT_VIDEO_UPLOAD = "VIDEO_UPLOAD";
    public static final String EVENT_AUDIO_UPLOAD = "AUDIO_UPLOAD";

    public static final String PARAM_ASSIGNMENT_TITLE = "assignment_title";

    /**
     * Logs an event for analytics.
     *
     * @param eventName     name of the event
     * @param eventParams   event parameters (can be null)
     * @param timed         <code>true</code> if the event should be timed, false otherwise
     */
    public static void logEvent(String eventName, Map<String, String> eventParams, boolean timed) {
        FlurryAgent.logEvent(eventName, eventParams, timed);
    }

    /**
     * Ends a timed event that was previously started.
     *
     * @param eventName     name of the event
     * @param eventParams   event parameters (can be null)
     */
    public static void endTimedEvent(String eventName, Map<String, String> eventParams) {
        FlurryAgent.endTimedEvent(eventName, eventParams);
    }


    /**
     * Ends a timed event without event parameters.
     *
     * @param eventName     name of the event
     */
    public static void endTimedEvent(String eventName) {
        FlurryAgent.endTimedEvent(eventName);
    }

    /**
     * Logs an error.
     *
     * @param errorId           error ID
     * @param errorDescription  error description
     * @param throwable         a {@link Throwable} that describes the error
     */
    public static void logError(String errorId, String errorDescription, Throwable throwable) {
        FlurryAgent.onError(errorId, errorDescription, throwable);
    }

    /**
     * Logs location.
     *
     * @param latitude           latitude of location
     * @param longitude        longitude of location
     */
    public static void logLocation(double latitude, double longitude) {
        FlurryAgent.setLocation((float) latitude, (float) longitude);
    }

    /**
     * Logs page view counts.
     *
     */
    public static void logPageViews(){
        FlurryAgent.onPageView();
    }

}