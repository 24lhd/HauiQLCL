package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
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

    public Task(Handler handler) {
        this.hander=handler;
    }

    public Task(TextView timeView, TextView tietView) {
        this.timeView=timeView;
        this.tietView=tietView;
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
            int phutConLai = 0;
            int giayConLai = 0;
            for (TietHoc tietHoc : tietHocs) {
                if (tietHoc.getGioBatDau() <= gio && gio <= tietHoc.getGioKetThuc()) {
                    if (tietHoc.getGioBatDau() == tietHoc.getGioKetThuc()) {
                        if (phut <= tietHoc.getPhutKetThuc() && phut >= tietHoc.getPhutBatDau()) {
                            tiet = "Tiết " + tietHoc.getTiet();
                            phutConLai = tietHoc.getPhutKetThuc() - phut - 1;
                            if (phutConLai < 0) {
                                phutConLai = 60 + phutConLai;
                            }
                        } else {
                            tiet = "Đang giải lao tiết " + (tietHoc.getTiet());
                            phutConLai = tietHocs[tietHoc.getTiet()].getPhutBatDau() - phut - 1;
                            if (phutConLai < 0) {
                                phutConLai = 60 + phutConLai;
                            }
                        }
                    } else {
                        if (phut >= tietHoc.getPhutBatDau() && phut >= tietHoc.getPhutKetThuc()) {
                            tiet = "Tiết " + tietHoc.getTiet();
                            phutConLai = 60 - phut + tietHoc.getPhutKetThuc() - 1;
                            if (phutConLai < 0) {
                                phutConLai = 60 + phutConLai;
                            }
                        } else if (phut <= tietHoc.getPhutBatDau() && phut <= tietHoc.getPhutKetThuc()) {
                            tiet = "Tiết " + tietHoc.getTiet();
                            phutConLai = tietHoc.getPhutKetThuc() - phut - 1;
                            if (phutConLai < 0) {
                                phutConLai = 60 + phutConLai;
                            }
                        } else {
                            tiet = "Đang giải lao tiết " + (tietHoc.getTiet());
                            phutConLai = tietHocs[tietHoc.getTiet()].getPhutBatDau() - phut - 1;
                            if (phutConLai < 0) {
                                phutConLai = 60 + phutConLai;
                            }

                        }
                    }
                }

            }

            giayConLai = 59 - giay;
            if (tiet.equals("Tự học")) {
                int gioConlai = 0;
                if (gio <= 7) {
                    gioConlai = gio - 7;
                } else {
                    gioConlai = 24 - (gio - 7);
                }
                if (gioConlai < 0) {
                    gioConlai = gioConlai * (-1);
                }
                gioConlai = gioConlai - 1;
                phutConLai = gioConlai * 60 + (59 - phut);
            }
//            publishProgress(tiet + "-" + "Còn lại " + phutConLai + ":" + giayConLai + "s");
        return  tiet + "-" + "Còn lại " + phutConLai + ":" + giayConLai + "s";
    }
    @Override
    protected void onProgressUpdate(String... values) {
        tietView.setText(values[0].split("-")[0]);
        timeView.setText(values[0].split("-")[1]);
//        Message message=new Message();
//        message.obj=values[0];
//        hander.sendMessage(message);
    }

    @Override
    protected void onPostExecute(String s) {
        tietView.setText(s.split("-")[0]);
        timeView.setText(s.split("-")[1]);
        (new Task(hander)).execute();
    }
    TietHoc[] tietHocs={
            new TietHoc(1,7,0,7,45),
            new TietHoc(2,7,50,8,35),
            new TietHoc(3,8,40,9,25),
            new TietHoc(4,9,35,10,20),
            new TietHoc(5,10,25,11,10),
            new TietHoc(6,11,15,12,0),
            new TietHoc(7,12,30,13,15),
            new TietHoc(8,13,20,14,5),
            new TietHoc(9,14,10,14,55),
            new TietHoc(10,15,5,15,50),
            new TietHoc(11,15,55,16,40),
            new TietHoc(12,16,45,17,30),
            new TietHoc(13,18,0,18,45),
            new TietHoc(14,18,45,19,30),
            new TietHoc(15,19,45,20,30),
            new TietHoc(16,20,30,21,15)
    };
}

