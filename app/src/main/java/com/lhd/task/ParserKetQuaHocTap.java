package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.item.ItemBangKetQuaHocTap;
import com.lhd.item.KetQuaHocTap;
import com.lhd.item.SinhVien;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Faker on 8/29/2016.
 */

public class ParserKetQuaHocTap extends AsyncTask<String,Void,KetQuaHocTap> {
    private Handler handler;
    private int key;

    public ParserKetQuaHocTap(int key, Handler handler) {
        this.key = key;
        this.handler = handler;
    }

    @Override
    protected KetQuaHocTap doInBackground( String... strings) {
        try {
            // mã sinh viên
            KetQuaHocTap ketQuaHocTap ;
            ArrayList<ItemBangKetQuaHocTap> bangKetQuaHocTaps =new ArrayList<>();
            SinhVien sinhVien;
            Document doc= Jsoup.connect("http://qlcl.edu.vn/examre/ket-qua-hoc-tap.htm?code="+strings[0]).get();
            Elements parents=doc.select("div#_ctl8_viewResult");
            Elements tbodys=parents.get(0).select("tbody");
            Elements ttSV=tbodys.get(0).select("tr");
            String  tenSv=ttSV.get(0).select("td").get(1).select("strong").text();
            String maSV=ttSV.get(1).select("td").get(1).select("strong").text();
            String lopDL=ttSV.get(2).select("td").get(1).select("strong").text();
            sinhVien=new SinhVien(tenSv,maSV,lopDL);
            if (key==0){
                Elements tbody=tbodys.get(1).select("tr");
                for (int i=0;i<tbody.size()-1;i++){
                    Elements tds=tbody.get(i).select("td");
                    String s =tbody.get(i).select("td").get(1).select("a").first().attr("href");
                    String s2 =tbody.get(i).select("td").get(2).select("a").first().attr("href");
                    ItemBangKetQuaHocTap bangKetQuaHocTap =new ItemBangKetQuaHocTap(
                            s,s2,
                            tds.get(1).text(),
                            tds.get(2).text(),
                            tds.get(3).text(),
                            tds.get(4).text(),
                            tds.get(5).text(),
                            tds.get(9).text(),
                            tds.get(11).text(),
                            tds.get(12).text(),
                            tds.get(13).text());
                    bangKetQuaHocTaps.add(bangKetQuaHocTap);
                }
            }
            ketQuaHocTap=new KetQuaHocTap(sinhVien,bangKetQuaHocTaps);
            return ketQuaHocTap;
        } catch (Exception e) {
            return null;
        }

    }
    @Override
    protected void onPostExecute(KetQuaHocTap ketQuaHocTap) {
        try {
            if (key==0){
                Message message=new Message();
                message.arg1=0;
                message.obj=ketQuaHocTap;
                handler.sendMessage(message);
            }else{
                Message message=new Message();
                message.arg1=6;
                message.obj=ketQuaHocTap.getSinhVien();
                handler.sendMessage(message);
            }

        }catch (NullPointerException e){
        }

    }
}