package com.lhd.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.item.DiemThiTheoMon;
import com.lhd.item.ItemBangKetQuaHocTap;
import com.lhd.item.KetQuaHocTap;
import com.lhd.item.LichThi;
import com.lhd.task.ParserKetQuaHocTap;
import com.lhd.task.ParserKetQuaThiTheoMon;
import com.lhd.task.ParserLichThiTheoMon;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Faker on 9/5/2016.
 */

public class MyService extends Service{
    public static final String TAB_POSITON = "tab_positon";
    public static final String KEY_TAB = "key tab";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private SQLiteManager sqLiteManager;
    private com.lhd.log.Log log;
    private String id;
    private Notification.Builder nBuilder;
    private  NotificationManager mNotifyMgr;
    private  PendingIntent resultPendingIntent;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                if(msg.arg1==0){// ket qua hoc tap
                    KetQuaHocTap ketQuaHocTap= (KetQuaHocTap) msg.obj;
                    ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTapsNew=ketQuaHocTap.getBangKetQuaHocTaps();
                    ArrayList<ItemBangKetQuaHocTap> itemBangKetQuaHocTapsOld=sqLiteManager.getBangKetQuaHocTap(id);
                    if (itemBangKetQuaHocTapsNew.size()>itemBangKetQuaHocTapsOld.size()){
                        for (int i=itemBangKetQuaHocTapsOld.size();i<itemBangKetQuaHocTapsNew.size();i++){
                            sqLiteManager.insertDMon(itemBangKetQuaHocTapsNew.get(i),id);
                        }
                        showNoti("Cập nhật học phần",(itemBangKetQuaHocTapsNew.size()-itemBangKetQuaHocTapsOld.size())+" học phần",0);
                    }
                        for (int i = 0; i <itemBangKetQuaHocTapsNew.size() ; i++) {
                            for (int j = 0; j <itemBangKetQuaHocTapsOld.size() ; j++) {
                                ItemBangKetQuaHocTap itemBangKetQuaHocTapOld=itemBangKetQuaHocTapsOld.get(j);
                                ItemBangKetQuaHocTap itemBangKetQuaHocTapNew=itemBangKetQuaHocTapsNew.get(i);
                                if (itemBangKetQuaHocTapNew.getMaMon().equals(itemBangKetQuaHocTapOld.getMaMon())&&!itemBangKetQuaHocTapNew.getdTB().equals(itemBangKetQuaHocTapOld.getdTB())){
                                        sqLiteManager.updateDMon(itemBangKetQuaHocTapNew,id,itemBangKetQuaHocTapOld.getMaMon());
                                        showNoti("Cập nhật điểm học phần",itemBangKetQuaHocTapNew.getTenMon(),0);
                                    break;
                                }
                            }
                        }
                }
                if (msg.arg1==2){ // ket qua thi
                    ArrayList<DiemThiTheoMon> diemThiTheoMonNews= (ArrayList<DiemThiTheoMon>) msg.obj;
                    ArrayList<DiemThiTheoMon> diemThiTheoMonOlds=sqLiteManager.getAllDThiMon(id);
                    if (diemThiTheoMonNews.size()>diemThiTheoMonOlds.size()){
                        for (DiemThiTheoMon diemThiTheoMonNew:diemThiTheoMonNews) {
                            boolean flag=true;
                            for (int i = 0; i <diemThiTheoMonOlds.size() ; i++) {
                                if (diemThiTheoMonNew.getLinkDiemThiTheoLop().equals(diemThiTheoMonOlds.get(i).getLinkDiemThiTheoLop())){
                                    flag=false;
                                    break;
                                }
                            }
                            if (flag){
                                sqLiteManager.insertDThiMon(diemThiTheoMonNew,id);
                            }
                        }
                        showNoti("Cập nhật kết quả thi",(diemThiTheoMonNews.size()-diemThiTheoMonOlds.size())+" học phần",1);
                    }
                        for (int i=0;i<diemThiTheoMonNews.size();i++){
                            for (int j = 0; j <diemThiTheoMonOlds.size() ; j++) {
                                String dcOld=diemThiTheoMonOlds.get(j).getdCuoiCung();
                                String dcNew=diemThiTheoMonNews.get(i).getdCuoiCung();
                                if (!dcOld.equals(dcNew)&&!diemThiTheoMonNews.get(i).getdLan1().isEmpty()&&diemThiTheoMonNews.get(i).getLinkDiemThiTheoLop().equals(diemThiTheoMonOlds.get(j).getLinkDiemThiTheoLop())){
                                    for (DiemThiTheoMon diemThiTheoMon:diemThiTheoMonNews) {
                                        sqLiteManager.updateDThiMon(diemThiTheoMon,id);
                                    }
                                    showNoti("Thông báo kết quả thi",diemThiTheoMonNews.get(i).getTenMon(),1);
                                    break;
                                }
                            }

                        }
                }
                if (msg.arg1==5){ // lich thi theo mon
                    ArrayList<LichThi> lichThiNews= (ArrayList<LichThi>) msg.obj;
                    ArrayList<LichThi> lichThiOlds=sqLiteManager.getAllLThi(id);
                    if (lichThiNews.size()>lichThiOlds.size()){
                        for (LichThi lichThi:lichThiNews) {
                            boolean flag=true;
                            for (int i = 0; i <lichThiOlds.size() ; i++) {
                                if (lichThi.getSbd().equals(lichThiOlds.get(i).getSbd())){
                                    flag=false;
                                    break;
                                }
                            }
                            if (flag){
                                sqLiteManager.insertlthi(lichThi,id);
                            }
                        }
                            showNoti("Có lịch thi mới",(lichThiNews.size()-lichThiOlds.size())+" học phần",2);
                    }
                }
            }catch (NullPointerException e){
                stopSelf();
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNoti(String title, String content, int index) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long [] l={200,200,200,200};
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt(TAB_POSITON,index);
        resultIntent.putExtra(KEY_TAB,bundle);
        Random random=new Random();
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),  random.nextInt(1000), resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nBuilder = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.icon_app)
                .setVibrate(l)
                .setSound(alarmSound)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);
//                .setFullScreenIntent(resultPendingIntent,false);
        int mNotificationId = random.nextInt(1000);
            mNotifyMgr.notify(mNotificationId, nBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log=new com.lhd.log.Log(this);
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
}
