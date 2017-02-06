package com.lhd.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.fragment.KetQuaThiFragment;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.ItemDiemThiTheoMon;
import com.lhd.object.ItemNotiDTTC;
import com.lhd.object.KetQuaHocTap;
import com.lhd.object.LichThi;
import com.lhd.task.ParserKetQuaHocTap;
import com.lhd.task.ParserKetQuaThiTheoMon;
import com.lhd.task.ParserLichThiTheoMon;
import com.lhd.task.ParserNotiDTTC;

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
    private NotificationCompat.Builder nBuilder;
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
                        sqLiteManager.deleteDMon(id);
                        for (ItemBangKetQuaHocTap diemHocTapTheoMon:itemBangKetQuaHocTapsNew){
                            sqLiteManager.insertDMon(diemHocTapTheoMon,ketQuaHocTap.getSinhVien().getMaSV());
                        }
                        showNoti("Thông báo từ Gà Công Nghiệp ","cập nhật thêm "+(itemBangKetQuaHocTapsNew.size()-itemBangKetQuaHocTapsOld.size())+" học phần",0);
                    }
                        for (int i = 0; i <itemBangKetQuaHocTapsNew.size() ; i++) {
                            for (int j = 0; j <itemBangKetQuaHocTapsOld.size() ; j++) {
                                ItemBangKetQuaHocTap itemBangKetQuaHocTapOld=itemBangKetQuaHocTapsOld.get(j);
                                ItemBangKetQuaHocTap itemBangKetQuaHocTapNew=itemBangKetQuaHocTapsNew.get(i);
                                if (itemBangKetQuaHocTapNew.getMaMon().equals(itemBangKetQuaHocTapOld.getMaMon())
                                        &&!itemBangKetQuaHocTapNew.getdTB().equals(itemBangKetQuaHocTapOld.getdTB())){
                                        sqLiteManager.updateDMon(itemBangKetQuaHocTapNew,id,itemBangKetQuaHocTapOld.getMaMon());
                                        showNoti("Thông báo từ Gà Công Nghiệp","có điểm học phần "+itemBangKetQuaHocTapNew.getTenMon(),0);
                                    break;
                                }
                            }
                        }
                    ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handlerNotiQLCL);
                    parserNotiDTTC.execute();
                }
                if (msg.arg1==2){ // ket qua thi
                    ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMonNews = (ArrayList<ItemDiemThiTheoMon>) msg.obj;
                    ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMonOlds =sqLiteManager.getAllDThiMon(id);
                    if (itemDiemThiTheoMonNews.size()> itemDiemThiTheoMonOlds.size()){
                        sqLiteManager.deleteDThiMon(id);
                        for (ItemDiemThiTheoMon itemDiemThiTheoMonNew : itemDiemThiTheoMonNews) {
//                            boolean flag=true;
//                            for (int i = 0; i < itemDiemThiTheoMonOlds.size() ; i++) {
//                                if (itemDiemThiTheoMonNew.getLinkDiemThiTheoLop().equals(itemDiemThiTheoMonOlds.get(i)
//                                        .getLinkDiemThiTheoLop())){
//                                    flag=false;
//                                    break;
//                                }
//                            }
//                            if (flag){
                                sqLiteManager.insertDThiMon(itemDiemThiTheoMonNew,id);
//                            }
                        }
                        showNoti("Thông báo từ Gà Công Nghiệp ","cập nhật kết quả thi "+(itemDiemThiTheoMonNews.size()- itemDiemThiTheoMonOlds.size())+" học phần",1);
                    }
                        for (int i = 0; i< itemDiemThiTheoMonNews.size(); i++){
                            for (int j = 0; j < itemDiemThiTheoMonOlds.size() ; j++) {
                                String dcOld= itemDiemThiTheoMonOlds.get(j).getdCuoiCung();
                                String dcNew= itemDiemThiTheoMonNews.get(i).getdCuoiCung();
                                if (!dcOld.equals(dcNew)&&!itemDiemThiTheoMonNews.get(i).getdLan1().isEmpty()&& itemDiemThiTheoMonNews.get(i).getLinkDiemThiTheoLop().equals(itemDiemThiTheoMonOlds.get(j).getLinkDiemThiTheoLop())){
                                    for (ItemDiemThiTheoMon itemDiemThiTheoMon : itemDiemThiTheoMonNews) {
                                        sqLiteManager.updateDThiMon(itemDiemThiTheoMon,id);
                                    }
                                    String str=ranDom(KetQuaThiFragment.charPoint(itemDiemThiTheoMonNews.get(i)),
                                            itemDiemThiTheoMonNews.get(i).getTenMon());
                                    showNoti("Thông báo từ Gà Công Nghiệp ",str,1);
                                    break;
                                }
                            }

                        }

                    ParserLichThiTheoMon parserLichThiTheoMon=new ParserLichThiTheoMon(handler);
                    parserLichThiTheoMon.execute(id);
                }
                if (msg.arg1==5){ // lich thi theo mon
                    ArrayList<LichThi> lichThiNews= (ArrayList<LichThi>) msg.obj;
                    ArrayList<LichThi> lichThiOlds=sqLiteManager.getAllLThi(id);
                    if (lichThiNews.size()>lichThiOlds.size()){
                        sqLiteManager.deleteDLThi(id);
                        for (LichThi lichThi:lichThiNews) {
//                            boolean flag=true;
//                            for (int i = 0; i <lichThiOlds.size() ; i++) {
//                                if (lichThi.getSbd().equals(lichThiOlds.get(i).getSbd())){
//                                    flag=false;
//                                    break;
//                                }
//                            }
//                            if (flag){
                                sqLiteManager.insertlthi(lichThi,id);
//                            }
                        }
                            showNoti("Thông báo từ Gà Công Nghiệp ","có lịch thi mới "+(lichThiNews.size()-lichThiOlds.size())+" học phần",2);
                    }
                }
            }catch (NullPointerException e){
                stopSelf();
            }
        }
    };
    private String ranDom(String charPoint, String tenMon) {
        switch(charPoint){
            case "A":
                return "Chúc mừng bạn đã được A"
                        +" môn "+ tenMon+"\n\n-----\nSự thật bao giờ cũng đơn giản - Ngạn ngữ Hy Lạp";
            case "B+":
                return  "Có điểm thi môn "+tenMon+"\n\n-----\nSay là cái điên tự nguyện - " +
                        "Ngạn ngữ Nga";
            case "B":

                return  "Có điểm thi môn "+tenMon+"\n\n-----\nNếu tự tin ở bản thân, bạn sẽ truyền niềm tin đến người khác - " +
                        "Ngạn ngữ Đức";
            case "C+":
                return  "Có điểm thi môn "+tenMon+"\n\n-----\nTa không được chọn nơi mình sinh ra. Nhưng ta được chọn cách mình sẽ sống " +
                        " - Khuyết danh";
            case "C":
                return  "Có điểm thi môn "+tenMon+"\n\n-----\nKhông có hoàn cảnh nào tuyệt vọng, chỉ có người tuyệt vọng vì hoàn cảnh " +
                        "- Khuyết danh";
            case "D+":
                return  "Có điểm thi môn "+tenMon+"\n\n-----\nĐừng ngại thay đổi."
                        +"Hãy sống là chính mình, bình thường nhưng không tầm thường - " +
                        "Khuyết danh";
            case "D":
                return  "Có điểm thi môn "+tenMon+"\n\n-----\nĐừng ngại thay đổi." +
                        " Bạn có thể mất một cái gì đó tốt nhưng bạn có thể đạt được một cái gì đó còn tốt hơn - Khuyết danh";
            case "F":
                return  "Có điểm thi môn "+tenMon+"\n\n-----\nCuộc sống vốn không công bằng. Hãy tập quen dần với điều đó - Bill Gates";
        }
        return  "Đã có điểm thi môn "+tenMon;
    }


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
        nBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.icon_app)
                .setVibrate(l)
                .setSound(alarmSound).setStyle(new NotificationCompat.BigTextStyle().bigText(content)).addAction (android.R.drawable.btn_star,"Xem", resultPendingIntent)
                .setContentTitle(title).setDefaults(Notification.DEFAULT_ALL)
                .setContentText(content)
                .setContentIntent(resultPendingIntent)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        int mNotificationId = random.nextInt(1000);
            mNotifyMgr.notify(mNotificationId, nBuilder.build());
    }
    private Handler handlerNotiQLCL=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ArrayList<ItemNotiDTTC> itemNotiDTTCsC;
            ArrayList<ItemNotiDTTC> itemNotiDTTCsM;
            try{
                itemNotiDTTCsM = (ArrayList<ItemNotiDTTC>) msg.obj;
                itemNotiDTTCsC=sqLiteManager.getNotiDTTC();
                if (itemNotiDTTCsM.size()>itemNotiDTTCsC.size()){
                    showNoti("Thông báo từ Gà Công Nghiệp ","đã cập nhật "+(itemNotiDTTCsM.size()-itemNotiDTTCsC.size())+" thông báo mới",5);
                    sqLiteManager.deleteItemNotiDTTC();
                    for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCsM) {
                        sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                    }
                }else if(itemNotiDTTCsM.size()!=0){
                    for (int i = 0; i < itemNotiDTTCsM.size(); i++) {
                        boolean flag=true;
                        for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCsC) {
                            if (itemNotiDTTCsM.get(i).getTitle().equals(itemNotiDTTC.getTitle())){
                                flag=false;
                            }
                        }
                        if (flag) showNoti("Thông báo từ Gà Công Nghiệp",itemNotiDTTCsM.get(i).getTitle(),4);
                    }
                    sqLiteManager.deleteItemNotiDTTC();
                    for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCsM) {
                        sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                    }
                }
                ParserKetQuaThiTheoMon ketQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
                ketQuaThiTheoMon.execute(id);
            }catch (Exception e){
            }
        }
    };
    BroadcastReceiver myBroadcastOnScrern = new BroadcastReceiver() {
        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
//            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//               if (MainActivity.wifiIsEnable(MyService.this)){
//                         startParser();
//               }
//            }else
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//                Log.e("faker", " ACTION_SCREEN_OFF");
                startParser();
            }
        }
    };

    private void startParser() {
        if (!MainActivity.isOnline(MyService.this))
            return;
        ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(handler);
        ketQuaHocTapTheoMon.execute(id);





    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log=new com.lhd.log.Log(this);
         id=log.getID();
        sqLiteManager =new SQLiteManager(this);
//        Log.e("faker", " onStartCommand");
        registerReceiver(myBroadcastOnScrern, new IntentFilter(Intent.ACTION_SCREEN_OFF));
//        registerReceiver(new MyReserver(), new IntentFilter(Intent.ACTION_SCREEN_ON));
        if (MainActivity.wifiIsEnable(this))
            startParser();
//        Toast.makeText(this,"Gà Công Nghiệp đang kiểm tra xem có gì hót ^.^",Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("faker", " onDestroy");
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
    }
}
