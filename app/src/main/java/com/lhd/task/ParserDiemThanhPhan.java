package com.lhd.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.activity.MainActivity;
import com.lhd.item.DiemThanhPhan;
import com.lhd.item.ItemBangDiemThanhPhan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by root on 9/14/16.
 */

public class ParserDiemThanhPhan extends AsyncTask<String,Void,DiemThanhPhan> {
    private Handler handler1;
    public ParserDiemThanhPhan(Handler handler ) {
        this.handler1 = handler;
    }
    @Override
    protected DiemThanhPhan doInBackground(String... strings) {
        DiemThanhPhan diemThanhPhan=null;
        ArrayList<ItemBangDiemThanhPhan> arrDiemHocTapTheoLops =new ArrayList<>();
        try {
            Document doc= Jsoup.connect("http://qlcl.edu.vn"+strings[0]+"?start=0&recpage=150").get();
            Elements parents=doc.select("div.kPanel");
            Elements parentsTitle=doc.select("div.k-toolbar").select("tbody").select("tr").select("td");
            String maLopDL=parentsTitle.get(9).select("strong").get(0).text();
            String tenLopUuTien=parentsTitle.get(11).select("strong").get(0).text();
            String soTC=parentsTitle.get(7).select("strong").get(0).text().split(" ")[0];
            Elements tbodys=parents.get(0).select("tbody");
            Elements trs=tbodys.get(0).select("tr");
            for (int i=0;i<trs.size();i++){
                Elements tds=trs.get(i).select("td");
                ItemBangDiemThanhPhan diemHocTapTheoLop =new ItemBangDiemThanhPhan(
                        tds.get(1).text(),
                        tds.get(2).select("a").first().attr("href"),
                        tds.get(2).text(),
                        tds.get(3).text(),
                        tds.get(4).text(),
                        tds.get(5).text(),
                        tds.get(6).text(),
                        tds.get(10).text(),
                        tds.get(12).text(),
                        tds.get(13).text());
                arrDiemHocTapTheoLops.add(diemHocTapTheoLop);
            }
            diemThanhPhan=new DiemThanhPhan(maLopDL,tenLopUuTien,soTC,arrDiemHocTapTheoLops);
        } catch (Exception e) {
            Log.e("faker","null");
            return null;
        }
        return diemThanhPhan;
    }
    private  int i=0;
    @Override
    protected void onPostExecute( DiemThanhPhan diemThanhPhan) {

        try {
            Message message=new Message();
            message.arg1=1;
            message.obj=diemThanhPhan;
            handler1.sendMessage(message);
//            Handler handler=new Handler(){
//                @Override
//                public void handleMessage(Message msg) {
//                    if (msg.arg1==0){
//                        diemThanhPhan.getDiemHocTapTheoLops().get(i).setTenLop((String) msg.obj);
//                        if (i<diemThanhPhan.getDiemHocTapTheoLops().size()-1){
//                            i=i+1;
//                        }else{
//                            Message message=new Message();
//                            message.arg1=1;
//                            message.obj=diemThanhPhan;
//                            handler1.sendMessage(message);
//                        }
//                    }
//                }
//            };
//            for (DiemHocTapTheoLop diemHocTapTheoLop:diemThanhPhan.getDiemHocTapTheoLops()) {
//                GetInfomation getInfomation=new GetInfomation(handler);
//                getInfomation.execute(diemHocTapTheoLop.getMsv());
//            }
        }catch (NullPointerException e){

        }
    }
}
