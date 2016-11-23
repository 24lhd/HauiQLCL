package com.lhd.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.ken.hauiclass.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Duong on 11/21/2016.
 */

public class GioLyThuyetFragment extends Fragment {
    private WebView textView;
    private TextView tietView;
    private TextView timeView;
    private int phutConLai;

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_gio_ly_thuyet,container,false);
        textView= (WebView) view.findViewById(R.id.text_more);
        String str;
        str="<!DOCTYPEhtml><html>" +
                "<head>" +
                "<meta charset=\"utf-8\"/>" +
                "<style type=\"text/css\" media=\"screen\">" +
                "*{" +
                "margin:0;padding: 0;" +
                "}" +
                "table{" +
                "width:100%;border:2px solid white;text-align:center;border-collapse:collapse;background-color:#E8F5E9;" +
                "}" +
                "th{" +
                "padding:10px;background-color:#42A5F5;color:white;padding:10px;" +
                "}" +
                "td{" +
                "color:#42A5F5;padding:5px;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<table border=\"1px\"><tr><th>Tiết</th><th>Giờ Học</th></tr><tr><td>1</td><td>7h00 - 7h45</td></tr><tr><td>2</td><td>7h50 - 8h35</td></tr><tr><td>3</td><td>8h40 - 9h25</td></tr><tr><td>4</td><td>9h35 - 10h20</td></tr><tr><td>5</td><td>10h25-11h10</td></tr><tr><td>6</td><td>11h15 - 12h00</td></tr><tr><td>7</td><td>12h30 - 13h15</td></tr><tr><td>8</td><td>13h20 - 14h05</td></tr><tr><td>9</td><td>14h10 - 14h45</td></tr><tr><td>10</td><td>15h05 - 15h50</td></tr><tr><td>11</td><td>15h55 - 16h40</td></tr><tr><td>12</td><td>16h45 - 17h30</td></tr><tr><td>13</td><td>18h00 - 18h45</td></tr><tr><td>14</td><td>18h45 - 19h30</td></tr><tr><td>15</td><td>19h45 - 20h30</td></tr><tr><td>16</td><td>20h30 - 21h15</td></tr>" +
                "</table>" +
                "</body>" +
                "</html>";
        textView.loadDataWithBaseURL(null,str, "text/html", "utf-8",null);
        initView(view);
        return view;
    }
    static class TietHoc{
        int tiet;
        int gioBatDau;
        int phutBatDau;
        int gioKetThuc;
        int phutKetThuc;

        @Override
        public String toString() {
            return tiet +" "+gioBatDau +
                    "h" + phutBatDau +
                    "         " + gioKetThuc +
                    "h" + phutKetThuc;
        }

        public int getTiet() {
            return tiet;
        }
        public int getGioBatDau() {
            return gioBatDau;
        }
        public int getPhutBatDau() {
            return phutBatDau;
        }
        public int getGioKetThuc() {
            return gioKetThuc;
        }


        public int getPhutKetThuc() {
            return phutKetThuc;
        }


        public TietHoc(int tiet, int gioBatDau, int phutBatDau, int gioKetThuc, int phutKetThuc) {
            this.tiet = tiet;
            this.gioBatDau = gioBatDau;
            this.phutBatDau = phutBatDau;
            this.gioKetThuc = gioKetThuc;
            this.phutKetThuc = phutKetThuc;
        }
    }
    public static  final  TietHoc [] tietHocs={
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
    private void initView(View view) {
        tietView= (TextView) view.findViewById(R.id.tv_tiet_hientai);
        timeView= (TextView) view.findViewById(R.id.tv_time_conlai);
        checkTime();

    }

    private void runTask() {
        new CountDownTimer(phutConLai*60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeView.setText("Còn lại "+String.format("%d phút %d giây",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
//                checkTime();
            }
        }.start();
    }


    private void checkTime() {
        Date today=new Date(System.currentTimeMillis());
        SimpleDateFormat timeFormat= new SimpleDateFormat("HH:mm:ss");
        String s=timeFormat.format(today.getTime());
        String time=s.split(" ")[0];
        int gio=Integer.parseInt(time.split(":")[0]);
        int phut=Integer.parseInt(time.split(":")[1]);
        String tiet = "Tự học";
        phutConLai=0;
        for (TietHoc tietHoc:tietHocs) {
            if (tietHoc.getGioBatDau()<=gio&&gio<=tietHoc.getGioKetThuc()){
                if (tietHoc.getGioBatDau()==tietHoc.getGioKetThuc()){
                    if (phut<=tietHoc.getPhutKetThuc()&&phut>=tietHoc.getPhutBatDau()){
                        tiet="Tiết "+tietHoc.getTiet();
                        phutConLai=tietHoc.getPhutKetThuc()-phut;
//                        if (phutConLai<0){
//                            phutConLai=60-(-1*phutConLai);
//                        }
                    }else {
                        tiet="Đang giải lao ";
                        phutConLai=0;
//                        phutConLai=tietHocs[tietHoc.getTiet()].getPhutBatDau()-phut;
//                        if (phutConLai>10){
//                            phutConLai=60-phutConLai;
//                        }else
//                        if (phutConLai<0){
//                            phutConLai=60-(-1*phutConLai);
//                        }
                    }
                }else {
                    if (phut>=tietHoc.getPhutBatDau()&&phut>=tietHoc.getPhutKetThuc()
                            ||phut<=tietHoc.getPhutBatDau()&&phut<=tietHoc.getPhutKetThuc()){
                        tiet="Tiết "+tietHoc.getTiet();
                        phutConLai=60-phut+tietHoc.getPhutKetThuc();
//                        if (phutConLai<0){
//                            phutConLai=60-(-1*phutConLai);
//                        }
                    }else {
                        tiet="Đang giải lao ";
                        phutConLai=0;
//                        phutConLai=tietHocs[tietHoc.getTiet()].getPhutBatDau()-phut;
//                        if (phutConLai>10){
//                            phutConLai=60-phutConLai;
//                        }else
//                        if (phutConLai<0){
//                            phutConLai=60-(-1*phutConLai);
//                        }

                    }
                }
            }

        }
        tietView.setText(tiet);
        if (tiet.equals("Tự học")){
//            int gioConlai=0;
//            if (gio<=7){
//                gioConlai=gio-7;
//            }else{
//                gioConlai=24-(gio-7);
//            }
//            if (gioConlai<0) gioConlai=gioConlai*(-1);
//            phutConLai=gioConlai*60+phut;
            phutConLai=0;
        }
        runTask();
    }
}
