package com.lhd.log;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by D on 15/01/2017.
 */

public class AppLog {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    public void openLog(Context context, String logName) {
        preferences= context.getSharedPreferences(logName, MODE_PRIVATE);
        editor=preferences.edit();
    }
    public void putValueByName(Context context, String logName, String name, String value) {
        openLog(context,logName);
        editor.putString(name,value);
        editor.commit();
    }
    public String getValueByName(Context context, String logName, String name) {
        openLog(context,logName);
        String values= preferences.getString(name,"");
        return values;
    }
    public void removeAll() {
        editor.clear();
        editor.commit();
    }
}
