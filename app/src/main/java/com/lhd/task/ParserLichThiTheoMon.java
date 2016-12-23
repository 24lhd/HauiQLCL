package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lhd.object.LichThi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Faker on 8/29/2016.
 */

public class ParserLichThiTheoMon extends AsyncTask<String,Void,ArrayList<LichThi>> {
    private Handler handler;
        public ParserLichThiTheoMon(Handler handler ) {
            this.handler = handler;
        }

    @Override
    protected ArrayList<LichThi> doInBackground(String... strings) {
        ArrayList<LichThi> arrayLichThi=new ArrayList<>();
        try {
            Document doc= Jsoup.connect("http://qlcl.edu.vn/examplanuser/ke-hoach-thi.htm?code="+strings[0]).get();
            Elements parents=doc.select("div#_ctl8_viewResult"); // chả vể node chỉ có 1 parrent
            Elements tbodys=parents.get(0).select("tbody");  // chọn parrent 1 và chọn các thẻ con là <tbody> nó trả về size=2 vì có 2 thẻ <tbody>
            Elements trs=tbodys.get(1).select("tr"); // chọn thẻ <tbody> thứ 2 chọn các thể con là thẻ <tr> trả về list thẻ <tr>
            for (int i=0;i<=trs.size()-2;i++){
                Elements tds=trs.get(i).select("td");
                LichThi lichThi=new LichThi(tds.get(1).text(),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(4).text(),
                        tds.get(5).text(),
                        tds.get(6).text(),
                        tds.get(7).text(),
                        tds.get(8).text(),
                        tds.get(9).text());
                arrayLichThi.add(lichThi);
            }
        } catch (Exception e) {
            return null;
        }
        return arrayLichThi;
    }

    @Override
    protected void onPostExecute(ArrayList<LichThi> lichThis) {
        try {
            Message message=new Message();
            message.arg1=5;
            message.obj=lichThis;
            handler.sendMessage(message);
        }catch (NullPointerException e){
        }

    }
}
