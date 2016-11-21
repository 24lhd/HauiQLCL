package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.item.ItemKetQuaThiLop;
import com.lhd.item.KetQuaThi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Faker on 8/29/2016.
 * ket qua thi theo lop
 */

public class ParserKetQuaThiTheoLop extends AsyncTask<String,Void,KetQuaThi> {
    private Handler handler;

        public ParserKetQuaThiTheoLop(Handler handler) {
            this.handler = handler;
        }

    @Override
    protected KetQuaThi doInBackground(String... strings) {
        ArrayList<ItemKetQuaThiLop> arrItemKetQuaThiLops =new ArrayList<>();
        KetQuaThi ketQuaThi = null;
        String tenLop;
        String soTC;
        try {
            Document doc= Jsoup.connect("http://qlcl.edu.vn"+strings[0]+"?start=0&recpage=150").get();
            Elements parents=doc.select("div.kPanel");
            Elements tbodys=parents.get(0).select("tbody");
            Elements trs=tbodys.get(0).select("tr");
            Elements parent0=doc.select("div.k-toolbar");
            Elements tbodys0=parent0.get(0).select("tbody");
            Elements tr0=tbodys0.get(0).select("tr");
            tenLop=tr0.get(2).select("td").get(3).select("strong").get(0).text();
            soTC=tr0.get(1).select("td").get(3).select("strong").get(0).text().split(" ")[0];
            for (int i=0;i<trs.size();i++) {
                Elements tds=trs.get(i).select("td");
                ItemKetQuaThiLop diemHocTapTheoLop =new ItemKetQuaThiLop(
                        tds.get(1).text(),
                        tds.get(2).select("a").first().attr("href"),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(4).text(),
                        tds.get(5).text());
                arrItemKetQuaThiLops.add(diemHocTapTheoLop);
            }
            ketQuaThi=new KetQuaThi(tenLop,soTC,arrItemKetQuaThiLops);
            return ketQuaThi;
        } catch (Exception e) {
            return null;
        }

    }
    @Override
    protected void onPostExecute(KetQuaThi diemHocTapTheoLops) {
        try {
            Message message=new Message();
            message.arg1=3;
            message.obj=diemHocTapTheoLops;
            handler.sendMessage(message);
        }catch (NullPointerException e){
            Log.e("duonghaui","trong");
        }

    }
}