package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.item.DiemThiTheoMon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Faker on 8/29/2016.
 * danh sách kết quả thi các môn theo mã sinh viên
 */

public class ParserKetQuaThiTheoMon extends AsyncTask<String,Void,ArrayList<DiemThiTheoMon>> {
    private Handler handler;

        public ParserKetQuaThiTheoMon(Handler handler) {
            this.handler = handler;
        }

    @Override
    protected ArrayList<DiemThiTheoMon> doInBackground(String... strings) {
        ArrayList<DiemThiTheoMon> arrDiemThiTheoMons=new ArrayList<>();
        try {
            Document doc= Jsoup.connect("http://qlcl.edu.vn/searchstexre/ket-qua-thi.htm?code="+strings[0]).get();
            Elements parents=doc.select("div#_ctl8_viewResult");
            Elements tbodys=parents.get(0).select("tbody");
            Elements tbody=tbodys.get(1).select("tr");
            Elements tbody0=tbodys.get(0).select("tr");
            for (int i=0;i<=tbody.size()-2;i++){
                Elements tds=tbody.get(i).select("td");
                DiemThiTheoMon diemThiTheoMon=new DiemThiTheoMon(
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
                arrDiemThiTheoMons.add(diemThiTheoMon);

            }
//

        } catch (Exception e) {
            return null;
        }
        return arrDiemThiTheoMons;
    }
    @Override
    protected void onPostExecute(ArrayList<DiemThiTheoMon> diemThiTheoMons) {
        try {
            Message message=new Message();
            message.arg1=2;
            message.obj=diemThiTheoMons;
            handler.sendMessage(message);
        }catch (NullPointerException e){
        }

    }
}
