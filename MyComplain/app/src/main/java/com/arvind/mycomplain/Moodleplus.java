package com.arvind.mycomplain;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by arvind on 21/2/16.
 */

public class Moodleplus extends Application {
    private static final String SET_COOKIE_KEY = "Authorization";
    private static final String COOKIE_KEY = "Authorization ";
//    private static final String SESSION_COOKIE = "session_id";

    private static Moodleplus _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public static Moodleplus get() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _requestQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }


    /**
     * Checks the response headers for session cookie and saves it
     * if it finds it.
     * @param headers Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            Log.d(Moodleplus.class.getSimpleName(), cookie+"next");
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                cookie = splitCookie[0];
                //cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(COOKIE_KEY, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final Map<String, String> addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(COOKIE_KEY, "");
        Log.d(Moodleplus.class.getSimpleName(), sessionId+"chek");

        if (sessionId.length() > 0) {
            Log.d(Moodleplus.class.getSimpleName(), sessionId);
            StringBuilder builder = new StringBuilder();
            builder.append(sessionId);
            if (headers.containsKey(SET_COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(SET_COOKIE_KEY));
            }
            headers.put(SET_COOKIE_KEY, builder.toString());
            Log.d(Moodleplus.class.getSimpleName(), headers.toString());

        }
        return headers;
    }

}
