package com.lhd.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lhd.activity.Test;
import com.lhd.item.ItemNotiDTTC;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by D on 12/15/2016.
 */

public class ParserLinkFileNoti extends AsyncTask<String, Void, String> {
    private Context context;
    public ParserLinkFileNoti( Context context) {
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];
        try {
            Document doc = Jsoup.connect(link).get();
            String a = doc.select("p.pBody").select("a").first().attr("href");
            return a;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    protected void onPostExecute(String s) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            context.startActivity(browserIntent);
        }catch (NullPointerException e){
            Toast.makeText(context,"Lỗi! xin hãy thử lại",Toast.LENGTH_SHORT).show();
        }

    }


}
