package com.lhd.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lhd.item.ItemNotiDTTC;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by D on 12/2/2016.
 */

public class ParserNotiDTTC extends AsyncTask<String, Void, ArrayList<ItemNotiDTTC>> {

    private Handler handler;

    public ParserNotiDTTC(Handler handler) {
        this.handler=handler;
    }

    @Override
    protected ArrayList<ItemNotiDTTC> doInBackground(String... strings) {
        ArrayList<ItemNotiDTTC> itemNotiDTTCs = new ArrayList<>();
        String link = "https://dttc.haui.edu.vn/vn";
        try {
        Document doc = Jsoup.connect(link).get();
        Elements a = doc.select("div.boxNews").select("ul").select("li").select("a");
        for (int j = 0; j < a.size(); j++) {
            itemNotiDTTCs.add(new ItemNotiDTTC("https://dttc.haui.edu.vn"+a.get(j).attr("href"),a.get(j).html()));
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemNotiDTTCs;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemNotiDTTC> itemNotiDTTCs) {
        for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs) {
            Log.e("faker",itemNotiDTTC.toString());
        }
        Message message=new Message();
        message.obj=itemNotiDTTCs;
        handler.sendMessage(message);
    }

}
