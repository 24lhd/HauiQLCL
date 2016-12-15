package com.lhd.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.item.LichThi;
import com.lhd.item.SinhVien;
import com.lhd.task.ParserLichThiTheoMon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Faker on 8/25/2016.
 */
public class LichThiFragment extends Fragment {
    private TextView tVnull;
    private ProgressBar progressBar;
    private SQLiteManager sqLiteManager;
    private  ArrayList<LichThi> lichThis;
    private SinhVien sv;
    private String maSV;
    private PullRefreshLayout pullRefreshLayout;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_fragment_in_main,container,false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        mainActivity = (MainActivity) getActivity();
        sqLiteManager=new SQLiteManager(getContext());
        maSV=getArguments().getString(MainActivity.MA_SV);
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
                    sqLiteManager.deleteDLThi(maSV);

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
        lichThis=sqLiteManager.getAllLThi(maSV);
        if (!lichThis.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else{
            loadData();
        }


    }
    private void startParser() {

        ParserLichThiTheoMon parserKetQuaHocTap=new ParserLichThiTheoMon(handler);
        parserKetQuaHocTap.execute(maSV);


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
        Collections.reverse(lichThis);
        AdapterLichThi  adapterLichThi=new AdapterLichThi(lichThis);
        recyclerView.setAdapter(adapterLichThi);
    }
    class ItemLichThi extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tenMon;
        TextView sbd;
        TextView thuThi;
        TextView phong;
        TextView ngayThi;
        TextView caThi;
        TextView lanThi;
        TextView stt;
        public ItemLichThi(View itemView) {
            super(itemView);
            this.tenMon = (TextView) itemView.findViewById(R.id.id_item_lich_thi_tenlop);
            this.sbd = (TextView) itemView.findViewById(R.id.id_item_lich_thi_sbd);
            this.thuThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_thu);
            this.phong = (TextView) itemView.findViewById(R.id.id_item_lich_thi_phong);
            this.ngayThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_ngay);
            this.caThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_gio);
            this.lanThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lan);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_lich_thi_stt);

        }
    }
    private class AdapterLichThi extends RecyclerView.Adapter<ItemLichThi> implements RecyclerView.OnClickListener {
        private  ArrayList<LichThi> data;
        public AdapterLichThi( ArrayList<LichThi> data) {
            this.data = data;
        }
        @Override
        public ItemLichThi onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_lich_thi, parent, false);
            view.setOnClickListener(this);
            ItemLichThi holder = new ItemLichThi(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ItemLichThi holder, int position) {
            LichThi lichThi=data.get(position);
            holder.tenMon.setText(lichThi.getMon()+"");
            holder.sbd.setText(lichThi.getSbd()+"");
            holder.thuThi.setText(lichThi.getThu()+"");
            holder.phong.setText(lichThi.getPhong()+"");
            holder.ngayThi.setText(lichThi.getNgay()+"");
            holder.caThi.setText(lichThi.getGio()+"");
            holder.lanThi.setText(lichThi.getLanthi()+"");
            holder.stt.setText((position+1)+"");
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(data.get(itemPosition).getMon());
            Date today=new Date(System.currentTimeMillis());
            SimpleDateFormat timeFormat= new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
            String s=timeFormat.format(today.getTime());
            s=s.split(" ")[1];
            String ngay1=s.split("/")[0];
            String thang1=s.split("/")[1];
            String nam1=s.split("/")[2];

            String ngay2=data.get(itemPosition).getNgay().split("/")[0];
            String thang2=data.get(itemPosition).getNgay().split("/")[1];
            String nam2=data.get(itemPosition).getNgay().split("/")[2];
            String toi;
            if (thang1.equals(thang2)&&nam1.equals(nam2)){
                if ((Double.parseDouble(ngay2)-Double.parseDouble(ngay1))<0)
                    toi="Đã thi";
                else
                toi="Còn lại "+(int)(Double.parseDouble(ngay2)-Double.parseDouble(ngay1))+ " ngày để ôn :)";
            }else if (Double.parseDouble(thang1)<Double.parseDouble(thang2) &&nam1.equals(nam2)){
                toi="Chuẩn bị thi :(";
            }else
                toi="Đã thi !!!";

            WebView webView=new WebView(getActivity());
            String str="<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset=\"utf-8\">" +
                    "<style type=\"text/css\"media=\"screen\">" +
                    "h1{" +
                    "color: #FF4081;" +
                    "background-color: #F5F5F5;" +
                    "text-align: center;" +
                    "}" +
                    "p{" +
                    "text-align: center;" +
                    "font-family: Sans-serif;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<h1>"+data.get(itemPosition).getSbd()+"</h1>" +
                    "<p>tại <strong>"+data.get(itemPosition).getPhong()+"</strong><br> <strong>"+data.get(itemPosition).getThu()+"</strong> lúc <strong>"+data.get(itemPosition).getGio()+"</strong> ngày <strong>"+data.get(itemPosition).getNgay()+"</strong> <br><strong>"+toi+"</strong></p></body></html>";
            webView.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
            builder.setView(webView);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.arg1){
                    case 5:
                        lichThis= (ArrayList<LichThi>) msg.obj;
                        if (!lichThis.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            if (sqLiteManager.getAllLThi(maSV).size()<lichThis.size()){
                                for (LichThi lichThiLop:lichThis){
                                    sqLiteManager.insertlthi(lichThiLop,maSV);
                                }
                            }
                            pullRefreshLayout.setRefreshing(false);
                            showRecircleView();
                            setRecyclerView();
                        }else{
                            showTextNull();
                            tVnull.setText("Không có lịch thi theo lớp...");
                        }
                        break;
                    case 6:
                        sv= (SinhVien) msg.obj;
                        if (sv!=null){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            sqLiteManager.insertSV(sv);
                        }
                        break;
                }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };

}
