package com.lhd.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Bundle;

import com.ken.hauiclass.R;
import com.lhd.database.SQLiteManager;

/**
 * Created by Faker on 9/24/2016.
 */

public class Test extends Activity {
    private  NotificationManager mNotifyMgr;
    private  PendingIntent resultPendingIntent;
    private Notification.Builder nBuilder;
    private SQLiteManager sqLiteManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

    }


}
