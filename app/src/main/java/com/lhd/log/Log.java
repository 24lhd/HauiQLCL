package com.lhd.log;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Faker on 8/28/2016.
 */

public class Log {
    public static final String LOG_NAME="log";
    public static final String LOG_ID="id";
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    public Log(Context context) {
        preferences= context.getSharedPreferences(LOG_NAME, MODE_PRIVATE);
        editor=preferences.edit();
    }
    public void putID(String id) {
        editor.putString(LOG_ID,id);
        editor.commit();
    }
    public String getID() {
        String s= preferences.getString(LOG_ID,"");
        return s;
    }
    public void remove() {
        editor.clear();
        editor.commit();
    }
}
