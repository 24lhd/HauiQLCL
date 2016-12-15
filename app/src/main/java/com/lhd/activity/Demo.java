package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ken.hauiclass.R;
import com.lhd.database.SQLiteManager;
import com.lhd.item.ItemNotiDTTC;
import com.lhd.task.ParserNotiDTTC;

import java.util.ArrayList;

/**
 * Created by D on 12/15/2016.
 */

public class Demo extends Activity {
    private SQLiteManager sqLiteManager;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        sqLiteManager=new SQLiteManager(this);
        recyclerView= (RecyclerView) findViewById(R.id.litst_demo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        class ItemNoti extends  RecyclerView.ViewHolder { // tao mot đói tượng
            TextView text;
            public ItemNoti(View itemView) {
                super(itemView);
                this.text = (TextView) itemView.findViewById(R.id.tv_noti);
            }
        }
        class AdapterNoti extends RecyclerView.Adapter<ItemNoti> implements RecyclerView.OnClickListener {
            private ArrayList<ItemNotiDTTC> data;
            public AdapterNoti( ArrayList<ItemNotiDTTC> data) {
                this.data = data;
            }
            @Override
            public ItemNoti onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_noti_qlcl, parent, false);
                view.setOnClickListener(this);
                ItemNoti holder = new ItemNoti(view);
                return holder;
            }
            @Override
            public void onBindViewHolder(ItemNoti holder, int position) {
                holder.text.setText(data.get(position).getTitle());

            }
            @Override
            public int getItemCount() {
                return data.size();
            }
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(itemPosition).getLink()));
                startActivity(browserIntent);
            }
        }


        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                ArrayList<ItemNotiDTTC> itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
                AdapterNoti adapterNoti=new AdapterNoti(itemNotiDTTCs);
                recyclerView.setAdapter(adapterNoti);
            }
        };
        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handler);
        parserNotiDTTC.execute();

    }
}
