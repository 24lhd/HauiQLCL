package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.item.LichThiLop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Faker on 8/29/2016.
 */

public class ParserLichThiTheoLop extends AsyncTask<String,Void,ArrayList<LichThiLop>> {
    private Handler handler;
        public ParserLichThiTheoLop(Handler handler ) {
            this.handler = handler;
        }
    @Override
    protected ArrayList<LichThiLop> doInBackground(String... strings) {
        ArrayList<LichThiLop> arrLichThiLops=new ArrayList<>();
        try {
            // mã lớp
            Document doc= Jsoup.connect("http://qlcl.edu.vn"+strings[0]).get();
            Elements parents=doc.select("div#_ctl8_viewResult");
            Elements tbodys=parents.get(0).select("tbody");
            Elements tbody=tbodys.get(0).select("tr");
            for (int i=0;i<tbody.size();i++){
                Elements tds=tbody.get(i).select("td");
                LichThiLop lichThiLop=new LichThiLop(tds.get(1).text(),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(4).text(),
                        tds.get(5).text(),
                        tds.get(6).text(),
                        tds.get(7).text());
                arrLichThiLops.add(lichThiLop);
            }
        } catch (Exception e) {
            return null;
        }
        return arrLichThiLops;
    }
    @Override
    protected void onPostExecute(ArrayList<LichThiLop> diemLops) {
        try {
            Message message=new Message();
            message.arg1=4;
            message.obj=diemLops;
            handler.sendMessage(message);
        }catch (NullPointerException e){
            Log.e("faker","tong");
        }

    }
}