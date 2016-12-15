package com.lhd.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.item.DiemThiTheoMon;
import com.lhd.item.ItemNotiDTTC;
import com.lhd.item.SinhVien;
import com.lhd.task.ParserKetQuaThiTheoMon;
import com.lhd.task.ParserLinkFileNoti;
import com.lhd.task.ParserNotiDTTC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by D on 12/15/2016.
 */

public class ThongBaoDtttcFragment extends Fragment {
    private TextView tVnull;
    private ProgressBar progressBar;
    private SQLiteManager sqLiteManager;
    private PullRefreshLayout pullRefreshLayout;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private ArrayList<ItemNotiDTTC> itemNotiDTTCs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_fragment_in_main,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mainActivity = (MainActivity) getActivity();
        sqLiteManager=new SQLiteManager(mainActivity);
        pullRefreshLayout= (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        progressBar= (ProgressBar) view.findViewById(R.id.pg_loading);
        tVnull= (TextView) view.findViewById(R.id.text_null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        refesh();
        checkDatabase();
        pullRefreshLayout.setRefreshing(false);
    }
    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }catch (Exception e) {
            return false;
        }
    }
    private void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline()){
                    sqLiteManager.deleteItemNotiDTTC();
                    startParser();
                }else{
                    pullRefreshLayout.setRefreshing(false);
                }

            }
        });
    }
    private void showRecircleView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tVnull.setVisibility(View.GONE);
    }
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tVnull.setVisibility(View.GONE);
    }
    private void checkDatabase() {
        showProgress();
        itemNotiDTTCs=sqLiteManager.getNotiDTTC();
        if (!itemNotiDTTCs.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else{
            loadData();
        }


    }
    private void startParser() {
        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handler);
        parserNotiDTTC.execute();

    }
    private void showTextNull() {
        progressBar.setVisibility(View.GONE);
        tVnull.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    private void cantLoadData() {
        showTextNull();
        tVnull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()){
                    showProgress();
                    startParser();
                }else {
                    final Snackbar snackbar=Snackbar.make(recyclerView, "Vui lòng bật kết nối internet!",Snackbar.LENGTH_SHORT);

                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.setAction("Bật wifi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                            showProgress();
                            snackbar.dismiss();
                            startParser();
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }
    private void loadData() {
        if (isOnline()){
            showProgress();
            startParser();
        }else{
            cantLoadData();
        }
    }
    private void setRecyclerView() {
        AdapterNoti adapterNoti=new AdapterNoti(itemNotiDTTCs);
        recyclerView.removeAllViews();
        recyclerView.setAdapter(adapterNoti);


    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
                setRecyclerView();
                showRecircleView();
                for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs) {
                    sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
    class ItemNoti extends  RecyclerView.ViewHolder { // tao mot đói tượng
        TextView text;
        TextView stt;
        public ItemNoti(View itemView) {
            super(itemView);

            this.text = (TextView) itemView.findViewById(R.id.tv_noti);
            this.stt = (TextView) itemView.findViewById(R.id.stt_noti);
        }
    }
    class AdapterNoti extends RecyclerView.Adapter<ItemNoti> implements RecyclerView.OnClickListener {
        private ArrayList<ItemNotiDTTC> data;
        public AdapterNoti( ArrayList<ItemNotiDTTC> data) {
            this.data = data;
        }
        @Override
        public ItemNoti onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.card_noti_qlcl, parent, false);
            view.setOnClickListener(this);
            ItemNoti holder = new ItemNoti(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ItemNoti holder, int position) {
            holder.text.setText(data.get(position).getTitle());
            holder.stt.setText(""+position);

        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {

            int itemPosition = recyclerView.getChildLayoutPosition(view);
            ParserLinkFileNoti parserNotiDTTC=new ParserLinkFileNoti(getActivity());
            parserNotiDTTC.execute(data.get(itemPosition).getLink());

        }
    }
}
