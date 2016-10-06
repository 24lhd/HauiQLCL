package com.ken.hauiclass.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.ken.hauiclass.R;

/**
 * Created by Faker on 9/24/2016.
 */

public class Test extends Activity {
    private  NotificationManager mNotifyMgr;
    private  PendingIntent resultPendingIntent;
    private Notification.Builder nBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long [] l={200,200,200,200};
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nBuilder = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_class_2)
                .setVibrate(l)
                .setFullScreenIntent(resultPendingIntent,true)
                .setContentTitle("Cập nhật mới học phần")
                .setContentText(" học phần đã được cập nhật")
                .setSound(alarmSound)
                .setAutoCancel(true).setContentIntent(resultPendingIntent);
        int mNotificationId = 001;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotifyMgr.notify(mNotificationId, nBuilder.build());
        }
    }
}
