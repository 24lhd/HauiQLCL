package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lhd.item.ItemDiemThiTheoMon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Faker on 8/29/2016.
 * danh sách kết quả thi các môn theo mã sinh viên
 */

public class ParserKetQuaThiTheoMon extends AsyncTask<String,Void,ArrayList<ItemDiemThiTheoMon>> {
    private Handler handler;

        public ParserKetQuaThiTheoMon(Handler handler) {
            this.handler = handler;
        }

    @Override
    protected ArrayList<ItemDiemThiTheoMon> doInBackground(String... strings) {
        ArrayList<ItemDiemThiTheoMon> arrItemDiemThiTheoMons =new ArrayList<>();
        try {
            Document doc= Jsoup.connect("http://qlcl.edu.vn/searchstexre/ket-qua-thi.htm?code="+strings[0]).get();
            Elements parents=doc.select("div#_ctl8_viewResult");
            Elements tbodys=parents.get(0).select("tbody");
            Elements tbody=tbodys.get(1).select("tr");
            Elements tbody0=tbodys.get(0).select("tr");
            for (int i=0;i<=tbody.size()-2;i++){
                Elements tds=tbody.get(i).select("td");
                ItemDiemThiTheoMon itemDiemThiTheoMon =new ItemDiemThiTheoMon(
                        tds.get(1).select("a").first().attr("href"),
                        tds.get(1).text(),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(4).text(),
                        tds.get(5).text(),
                        tds.get(8).text(),
                        tds.get(9).text(),
                        tds.get(10).text(),
                        tds.get(11).text());
                arrItemDiemThiTheoMons.add(itemDiemThiTheoMon);

            }
//

        } catch (Exception e) {
            return null;
        }
        return arrItemDiemThiTheoMons;
    }
    @Override
    protected void onPostExecute(ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMons) {
        try {
            Message message=new Message();
            message.arg1=2;
            message.obj= itemDiemThiTheoMons;
            handler.sendMessage(message);
        }catch (NullPointerException e){
        }

    }
}
