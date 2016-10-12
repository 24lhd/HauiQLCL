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
import android.util.Log;

import com.ken.hauiclass.R;
import com.ken.hauiclass.database.SQLiteManager;
import com.ken.hauiclass.fragment.KetQuaHocTapFragment;
import com.ken.hauiclass.item.DiemThiTheoMon;
import com.ken.hauiclass.item.ItemBangDiemThanhPhan;
import com.ken.hauiclass.item.ItemBangKetQuaHocTap;
import com.ken.hauiclass.item.KetQuaHocTap;

import java.util.ArrayList;

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
//        long [] l={200,200,200,200};
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
//        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        nBuilder = new Notification.Builder(getApplicationContext())
//                .setSmallIcon(R.drawable.ic_class_2)
//                .setVibrate(l)
//                .setFullScreenIntent(resultPendingIntent,true)
//                .setContentTitle("Cập nhật mới học phần")
//                .setContentText(" học phần đã được cập nhật")
//                .setSound(alarmSound)
//                .setAutoCancel(true).setContentIntent(resultPendingIntent);
//        int mNotificationId = 001;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mNotifyMgr.notify(mNotificationId, nBuilder.build());
//        }
        String id="0941260041";
        sqLiteManager=new SQLiteManager(this);
        ArrayList<ItemBangKetQuaHocTap> bangDiemThanhPhen=
        sqLiteManager.getBangKetQuaHocTap(id);
        ArrayList<DiemThiTheoMon> diemThiTheoMons=
        sqLiteManager.getAllDThiMon(id);
//        String [] stringsDiemThiTheoMon=new String[diemThiTheoMons.size()];
//        stringsDiemThiTheoMon=diemThiTheoMons.toArray(stringsDiemThiTheoMon);
        for (DiemThiTheoMon diemThiTheoMon:diemThiTheoMons) {
            String dc=diemThiTheoMon.getdCuoiCung().split(" ")[0];
            dc=dc.trim();
            if (!dc.isEmpty()&& KetQuaHocTapFragment.isDouble(dc)){
                Log.e("faker",dc);
            }
        }
//        for (int i = 0; i < stringsDiemThiTheoMon.length; i++) {
//            Log.e("faker","------------n1-------"+stringsDiemThiTheoMon[i].toString());
//        }
        
        ArrayList<String> mons=new ArrayList<>();
        for (ItemBangKetQuaHocTap itemBangDiemThanhPhan:bangDiemThanhPhen) {
            if (itemBangDiemThanhPhan.getMaMon().trim().length()>=15){
                mons.add(itemBangDiemThanhPhan.getTenMon());
            }
        }
        String [] strings= new String[mons.size()];
        strings=mons.toArray(strings);
        int n=strings.length;
        for (int i = 0; i < (n-1); i++) {
            for (int j = i+1; j <n ; j++) {
                if (strings[i].equals(strings[j])){
                    for (int k = j; k <n-1 ; k++) {
                        strings[k]=strings[k].replace(strings[k],strings[k+1]);
                    }
                    n--;
                }
            }
        }
//        Log.e("faker","------------n1-------"+n);
        ArrayList<Double> diems=new ArrayList<>();
        for (int i = 0; i <n; i++) {
//           strings[i]
        }

    }
}
