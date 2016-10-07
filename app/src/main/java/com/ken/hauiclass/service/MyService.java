package com.ken.hauiclass.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ken.hauiclass.R;
import com.ken.hauiclass.activity.MainActivity;
import com.ken.hauiclass.database.SQLiteManager;
import com.ken.hauiclass.item.ItemBangKetQuaHocTap;
import com.ken.hauiclass.item.KetQuaHocTap;
import com.ken.hauiclass.task.ParserKetQuaHocTap;
import com.ken.hauiclass.task.ParserKetQuaThiTheoMon;
import com.ken.hauiclass.task.ParserLichThiTheoMon;

import java.util.ArrayList;

/**
 * Created by Faker on 9/5/2016.
 */

public class MyService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private SQLiteManager sqLiteManager;
    private com.ken.hauiclass.log.Log log;
    private String id;
    private Notification.Builder nBuilder;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "Đang cập nhật lớp", Toast.LENGTH_SHORT).show();
            try{
                if(msg.arg1==0){// ket qua hoc tap
                    KetQuaHocTap ketQuaHocTap= (KetQuaHocTap) msg.obj;
                    ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTapsNew=ketQuaHocTap.getBangKetQuaHocTaps();
                    ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTapsOld=sqLiteManager.getBangKetQuaHocTap(id);
                    if (itemBangKetQuaHocTapsNew.size()>itemBangKetQuaHocTapsOld.size()){
                        for (int i=itemBangKetQuaHocTapsOld.size();i<itemBangKetQuaHocTapsNew.size();i++){
                            sqLiteManager.insertDMon(itemBangKetQuaHocTapsNew.get(i),id);
                        }
                        long [] l={200,200,200,200};
                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nBuilder = new Notification.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_class_2)
                                .setVibrate(l)
//                                .setFullScreenIntent(resultPendingIntent,false)
                                .setContentTitle("Cập nhật mới học phần")
                                .setContentText((itemBangKetQuaHocTapsNew.size()-itemBangKetQuaHocTapsOld.size())+" học phần đã được cập nhật")
                                .setAutoCancel(true);
                        int mNotificationId = 001;
                        nBuilder.setContentIntent(resultPendingIntent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mNotifyMgr.notify(mNotificationId, nBuilder.build());
                        }
                        Log.e("faker","Đã cập nhập thêm lớp mới");
                    }else{
                        for (int i = itemBangKetQuaHocTapsNew.size()*2/3; i <ketQuaHocTap.getBangKetQuaHocTaps().size() ; i++) {
                            if (!itemBangKetQuaHocTapsNew.get(i).getdTB().equals(itemBangKetQuaHocTapsOld.get(i).getdTB())) {
//                                nBuilder= (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
//                                        .setSmallIcon(R.drawable.ic_class_2)
//                                        .setTicker("Đã cập nhập thêm lớp mới")
//                                        .setContentText("lớp mới")
//                                ;
//                                nBuilder.build();
                            }
                        }
                    }
//                    ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(0,handler);
//                    ketQuaHocTapTheoMon.execute(id);
                }
                if (msg.arg1==1){ // ket qua thi

//                    ParserKetQuaThiTheoMon ketQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
//                    ketQuaThiTheoMon.execute(id);
                }
                if (msg.arg1==5){ // lich thi theo mon
//
//                    ParserLichThiTheoMon parserLichThiTheoMon=new ParserLichThiTheoMon(handler);
//                    parserLichThiTheoMon.execute(id);
                }
            }catch (NullPointerException e){
//                    ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(0,handler);
//                    ketQuaHocTapTheoMon.execute(log.getID());
//                    ParserKetQuaThiTheoMon ketQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
//                    ketQuaThiTheoMon.execute(log.getID());
//                    ParserLichThiTheoMon parserLichThiTheoMon=new ParserLichThiTheoMon(handler);
//                    parserLichThiTheoMon.execute(log.getID());
            }
        }
    };
    private  NotificationManager mNotifyMgr;
    private  PendingIntent resultPendingIntent;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "server", Toast.LENGTH_SHORT).show();
        log=new com.ken.hauiclass.log.Log(this);
         id=log.getID();
        sqLiteManager =new SQLiteManager(this);
        ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(0,handler);
        ketQuaHocTapTheoMon.execute(id);
        ParserKetQuaThiTheoMon ketQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
        ketQuaThiTheoMon.execute(id);
        ParserLichThiTheoMon parserLichThiTheoMon=new ParserLichThiTheoMon(handler);
        parserLichThiTheoMon.execute(id);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
         mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return START_STICKY;
    }
    public boolean isDouble(String d) {
        double value;
        try{
            value = Double.parseDouble(d);
            return true;
        } catch (Exception e){
            android.util.Log.e("faker","khong phai double");
            return false;
        }
    }
}
