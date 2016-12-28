package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lhd.object.Haui;


import java.text.SimpleDateFormat;
import java.util.Date;

import static com.lhd.object.Haui.tietHocs;


/**
 * Created by Duong on 11/24/2016.
 */

public  class TimeTask extends AsyncTask<Void,String,String> {
    private  Handler hander;
    private Haui haui;
    int phutConLai;
    public TimeTask(Handler handler) {
        this.hander=handler;
        haui=new Haui();

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
        for (int i = 0; i< tietHocs.length; i++) {
                if (phutHienTai>=tietHocs[i].getPhutBatDau()&&phutHienTai<=haui.tietHocs[i].getPhutKetThuc()){
                    tiet = "Tiết " + tietHocs[i].getTiet();
                    phutConLai = tietHocs[i].getPhutKetThuc() - phutHienTai;
                    break;
                }else if (phutHienTai>tietHocs[i].getPhutKetThuc()&&haui.tietHocs[i].getTiet()<16&&phutHienTai<tietHocs[i+1].getPhutBatDau()){
                        tiet = "Giải lao tiết " + (haui.tietHocs[i].getTiet());
                        phutConLai = haui.tietHocs[i+1].getPhutBatDau()-phutHienTai;
                    break;
                }else if (phutHienTai>1275&&phutHienTai>420||phutHienTai<1275&&phutHienTai<420){
                    if (phutHienTai<1440&&phutHienTai>1275){
                        tiet = "Tự học ";
                        phutConLai=420+(1440-phutHienTai);
                    }else phutConLai=420-phutHienTai;
                    break;
                }
            }
            if (phutConLai>60) return  tiet + "-" + "Còn "+(phutConLai/60)+":"+ phutConLai%60 + ":" + giayConLai +" giờ";
            return  tiet + "-" + "Còn " + (phutConLai-1) + ":" + giayConLai +" phút";
    }
    @Override
    protected void onPostExecute(String s) {
        Message message=new Message();
        message.obj=s;
        hander.sendMessage(message);
    }
}

