package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.lhd.item.TietHoc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Duong on 11/24/2016.
 */

public  class Task extends AsyncTask<Void,String,String> {
    private  TextView tietView;
    private  TextView timeView;
    private  Handler hander;
    int phutConLai;

    public Task(Handler handler) {
        this.hander=handler;
    }


    @Override
    protected String doInBackground(Void... params) {
            Date today = new Date(System.currentTimeMillis());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String s = timeFormat.format(today.getTime());
            String time = s.split(" ")[0];
            int gio = Integer.parseInt(time.split(":")[0]);
            int phut = Integer.parseInt(time.split(":")[1]);
            int giay = Integer.parseInt(time.split(":")[2]);
            String tiet = "Tự học";
            int phutHienTai = (gio*60)+phut;
            int giayConLai = 59-giay;

            for (int i=0;i<tietHocs.length;i++) {
                if (phutHienTai>=tietHocs[i].getPhutBatDau()&&phutHienTai<=tietHocs[i].getPhutKetThuc()){
                    tiet = "Tiết " + tietHocs[i].getTiet();
                    phutConLai = tietHocs[i].getPhutKetThuc() - phutHienTai;
                    break;
                }else if (phutHienTai>tietHocs[i].getPhutKetThuc()&&tietHocs[i].getTiet()<16&&phutHienTai<tietHocs[i+1].getPhutBatDau()){
                        tiet = "Giải lao tiết " + (tietHocs[i].getTiet());
                        phutConLai = tietHocs[i+1].getPhutBatDau()-phutHienTai;
                    break;
                }else if (phutHienTai>1275&&phutHienTai>420||phutHienTai<1275&&phutHienTai<420){
                    if (phutHienTai<1440&&phutHienTai>1275){
                        tiet = "Tự học ";
                        phutConLai=420+(1440-phutHienTai);
                    }else{
                        phutConLai=420-phutHienTai;
                    }
                    break;
                }
            }

        Log.e("faker",""+phutConLai);
            if (phutConLai>60){
                return  tiet + "-" + "Còn lại "+(phutConLai/60)+":"+ phutConLai%60 + ":" + giayConLai+" giờ";
            }
            return  tiet + "-" + "Còn lại " + phutConLai + ":" + giayConLai +" phút";
    }

    @Override
    protected void onPostExecute(String s) {
        Message message=new Message();
        message.obj=s;
        hander.sendMessage(message);
    }
    TietHoc[] tietHocs={
            new TietHoc(1,420,465),// 420  465
            new TietHoc(2,470,515),// 470  515
            new TietHoc(3,520,565),// 520  565
            new TietHoc(4,520,620),// 575  620
            new TietHoc(5,675,670),// 675  670
            new TietHoc(6,675,720),// 675  720
            new TietHoc(7,750,795),// 750  795
            new TietHoc(8,800,845),// 800  845
            new TietHoc(9,850,895),// 850  895
            new TietHoc(10,905,950),// 905  950
            new TietHoc(11,955,1000),// 955  1000
            new TietHoc(12,1005,1050),// 1005  1050
            new TietHoc(13,1080,1125),// 1080  1125
            new TietHoc(14,1125,1170),// 1125  1170
            new TietHoc(15,1185,1230),// 1185  1230
            new TietHoc(16,1230,1275)// 1230  1275
    };
}

