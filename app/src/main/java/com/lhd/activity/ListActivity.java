package com.lhd.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.database.SQLiteManager;
import com.lhd.fragment.KetQuaHocTapFragment;
import com.lhd.item.DiemThanhPhan;
import com.lhd.item.DiemThiTheoMon;
import com.lhd.item.ItemBangDiemThanhPhan;
import com.lhd.item.ItemBangKetQuaHocTap;
import com.lhd.item.ItemKetQuaThiLop;
import com.lhd.item.KetQuaThi;
import com.lhd.task.ParserDiemThanhPhan;
import com.lhd.task.ParserKetQuaThiTheoLop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Faker on 8/30/2016.
 */
public class ListActivity extends AppCompatActivity {
    private SQLiteManager sqLiteManager;
    private RecyclerView recyclerView;
    private TextView textNull;
    private ProgressBar progressBar;
    private Intent intent;
    private PullRefreshLayout pullRefreshLayout;
    private Bundle bundle;
    private int index;
    private ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    private DiemThiTheoMon diemThiTheoMon;
    private DiemThanhPhan diemThanhPhan;
    private KetQuaThi ketQuaThi;
    private Toolbar toolbarMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        initView();
        checkDatabases();
    }
    @Override
    public void onBackPressed() {
        Intent intent=getIntent();
        setResult(Activity.RESULT_FIRST_USER,intent);
        finish();
        this.overridePendingTransition(R.anim.left_end,
                R.anim.right_end);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView( ) {
        toolbarMenu = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbarMenu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarMenu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem arg0) {
                switch (arg0.getItemId()) {
                    case R.id.cao_thap:
                        switch (index) {
                            case 0:
                                Collections.sort(diemThanhPhan.getBangDiemThanhPhan(), new Comparator<ItemBangDiemThanhPhan>() {
                                    @Override
                                    public int compare(ItemBangDiemThanhPhan o1, ItemBangDiemThanhPhan o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdTB());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdTB());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);

                                    }
                                });
                                break;
                            case 3:
                                Collections.sort(ketQuaThi.getKetQuaThiLops(), new Comparator<ItemKetQuaThiLop>() {
                                    @Override
                                    public int compare(ItemKetQuaThiLop o1, ItemKetQuaThiLop o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdL1());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdL1());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);
                                    }
                                });
                                break;
                        }
                        setRecircleView();
                        return true;
                    case R.id.thap_cao:
                        switch (index) {
                            case 0:
                                Collections.sort(diemThanhPhan.getBangDiemThanhPhan(), new Comparator<ItemBangDiemThanhPhan>() {
                                    @Override
                                    public int compare(ItemBangDiemThanhPhan o1, ItemBangDiemThanhPhan o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdTB());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdTB());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);

                                    }
                                });
                                Collections.reverse(diemThanhPhan.getBangDiemThanhPhan());
                                break;
                            case 3:
                                Collections.sort(ketQuaThi.getKetQuaThiLops(), new Comparator<ItemKetQuaThiLop>() {
                                    @Override
                                    public int compare(ItemKetQuaThiLop o1, ItemKetQuaThiLop o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdL1());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdL1());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);
                                    }
                                });
                                Collections.reverse(ketQuaThi.getKetQuaThiLops());
                                break;
                        }
                        setRecircleView();

                }
                return false;
            }
        });
        sqLiteManager=new SQLiteManager(this);
        pullRefreshLayout= (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        progressBar= (ProgressBar) findViewById(R.id.pg_loading);
        textNull= (TextView) findViewById(R.id.text_null);
        recyclerView= (RecyclerView) findViewById(R.id.recle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refesh();
            }
        });
    }

    private void showRecircleView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        textNull.setVisibility(View.GONE);
    }
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        textNull.setVisibility(View.GONE);
    }
    private void showTextNull() {
        progressBar.setVisibility(View.GONE);
        textNull.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }catch (Exception e) {
            return false;
        }
    }
    private void cantLoadData( ) {
        showTextNull();
        textNull.setOnClickListener(new View.OnClickListener() {
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
                            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
    private void startParser() {
        switch (index){
            case 0:
                ParserDiemThanhPhan parserDiemThanhPhan=new ParserDiemThanhPhan(handler);
                parserDiemThanhPhan.execute(itemBangKetQuaHocTap.getLinkDiemLop());
                break;
            case 3:
                ParserKetQuaThiTheoLop parserKetQuaHocTap=new ParserKetQuaThiTheoLop(handler);
                parserKetQuaHocTap.execute(diemThiTheoMon.getLinkDiemThiTheoLop());
                break;
        }
    }
    private void checkDatabases() {
        intent=getIntent();
        bundle=intent.getBundleExtra("action");
        index=bundle.getInt(KetQuaHocTapFragment.KEY_ACTIVITY);
        showProgress();
        try {
            switch (index){
                case 0:
                    itemBangKetQuaHocTap= (ItemBangKetQuaHocTap) intent.getSerializableExtra(KetQuaHocTapFragment.KEY_OBJECT);
                     diemThanhPhan = sqLiteManager.getAllDLop(itemBangKetQuaHocTap.getMaMon());
                    if (diemThanhPhan!=null&&!diemThanhPhan.getBangDiemThanhPhan().isEmpty()){
                        showRecircleView();
                        getSupportActionBar().setTitle("Điểm thành phần "+itemBangKetQuaHocTap.getTenMon());
                        getSupportActionBar().setSubtitle(diemThanhPhan.getTenLopUuTien()+"_"+diemThanhPhan.getSoTin()+" tín chỉ");
                      setRecircleView();
                    }else{
                        if (isOnline()){
                            showProgress();
                            startParser();
                        }else{
                            cantLoadData();
                        }
                    }
                    break;
                case 3:
                    diemThiTheoMon= (DiemThiTheoMon) intent.getSerializableExtra(KetQuaHocTapFragment.KEY_OBJECT);
                     ketQuaThi=sqLiteManager.getAllDThiLop(diemThiTheoMon.getLinkDiemThiTheoLop());
                    if (ketQuaThi!=null&&!ketQuaThi.getKetQuaThiLops().isEmpty()){
                        getSupportActionBar().setTitle("Điểm thi "+diemThiTheoMon.getTenMon());
                        getSupportActionBar().setSubtitle(ketQuaThi.getTenLopUuTien()+"_"+ketQuaThi.getSoTC()+" tín chỉ");
                        showRecircleView();
                       setRecircleView();
                    }else{
                        if (isOnline()){
                            showProgress();
                            startParser();
                        }else{
                            cantLoadData();
                        }
                    }
                    break;
            }
        }catch (NullPointerException e){}
    }
    private void refesh() {
        switch (index) {
            case 0:
                sqLiteManager.deleteDLop(itemBangKetQuaHocTap.getMaMon());
                break;
            case 3: default:
                sqLiteManager.deleteDThiLop(diemThiTheoMon.getLinkDiemThiTheoLop());
                break;
        }
        startParser();
    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            pullRefreshLayout.setRefreshing(false);
            try{
                if(msg.arg1==1){
                     diemThanhPhan= (DiemThanhPhan) msg.obj;
                    ArrayList<ItemBangDiemThanhPhan> b= diemThanhPhan.getBangDiemThanhPhan();
                    if (!b.isEmpty()){
                        for (ItemBangDiemThanhPhan diemHocTapTheoLop:b){
                           sqLiteManager.insertDLop(diemHocTapTheoLop,diemThanhPhan.getMaLopDL(),diemThanhPhan.getTenLopUuTien(),diemThanhPhan.getSoTin());
                        }
                        getSupportActionBar().setTitle("Điểm thành phần "+itemBangKetQuaHocTap.getTenMon());
                        getSupportActionBar().setSubtitle(diemThanhPhan.getTenLopUuTien()+"_"+diemThanhPhan.getSoTin()+" tín chỉ");
                        showRecircleView();
                       setRecircleView();
                    }
                }else if(msg.arg1==3){
                     ketQuaThi= (KetQuaThi) msg.obj;;
                    ArrayList<ItemKetQuaThiLop> b= ketQuaThi.getKetQuaThiLops();
                    if (!b.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                        getSupportActionBar().setTitle("Điểm thi "+diemThiTheoMon.getTenMon());
                        getSupportActionBar().setSubtitle(ketQuaThi.getTenLopUuTien()+"_"+ketQuaThi.getSoTC()+" tín chỉ");
                        for (ItemKetQuaThiLop diemHocTapTheoLop:b){
                            sqLiteManager.insertDThiLop(diemThiTheoMon.getLinkDiemThiTheoLop(),ketQuaThi.getTenLopUuTien(),ketQuaThi.getSoTC(),diemHocTapTheoLop);
                        }
                        showRecircleView();
                        setRecircleView();
                    }
                }
            }catch (NullPointerException e){
                // neu bị null nó sẽ vào đây
                startParser();
            }
        }
    };

    private void setRecircleView() {
        switch (index){
            case 0:
                AdapterListPointClass  adapter=new AdapterListPointClass(diemThanhPhan.getBangDiemThanhPhan());
                recyclerView.setAdapter(adapter);
                break;
            case 3:
                AdapterDiemThiLop  adapter2=new AdapterDiemThiLop(ketQuaThi.getKetQuaThiLops());
                recyclerView.setAdapter(adapter2);
                break;
        }
    }

    class ItemDiemThiLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tvTenSV;
        TextView tvMaSV;
        TextView tvL1;
        TextView tvL2;
        TextView tvGC;
        TextView stt;
        public ItemDiemThiLop(View itemView) {
            super(itemView);
            this.tvTenSV = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_tensv);
            this.tvMaSV = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_masv);
            this.tvL1 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_l1);
            this.tvL2 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_l2);
            this.tvGC = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_gc);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_stt);

        }
    }
    private class AdapterDiemThiLop extends RecyclerView.Adapter<ListActivity.ItemDiemThiLop>
            implements RecyclerView.OnClickListener {
        private  ArrayList<ItemKetQuaThiLop> data;
        public AdapterDiemThiLop( ArrayList<ItemKetQuaThiLop> data) {
            this.data = data;
        }
        @Override
        public ListActivity.ItemDiemThiLop onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.item_ket_qua_thi, parent, false);
            view.setOnClickListener(this);
            ListActivity.ItemDiemThiLop holder = new ListActivity.ItemDiemThiLop(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ListActivity.ItemDiemThiLop holder, int position) {
            ItemKetQuaThiLop itemKetQuaThiLop=data.get(position);
            holder.tvTenSV.setText(itemKetQuaThiLop.getTen());
            holder.tvMaSV.setText(itemKetQuaThiLop.getMsv());
            holder.tvL1.setText(itemKetQuaThiLop.getdL1());
            holder.tvL2.setText(itemKetQuaThiLop.getdL2());
            holder.tvGC.setText(itemKetQuaThiLop.getGhiChu());
            holder.stt.setText(""+(position+1));
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);

//            Bundle bundle=new Bundle();
//            bundle.putString(MainActivity.MA_SV,data.get(itemPosition).getMsv());
//            intent.putExtra(MainActivity.MA_SV,bundle);
            Intent intent=getIntent();
            intent.putExtra(MainActivity.MA_SV,data.get(itemPosition).getMsv());
            setResult(Activity.RESULT_OK,intent);
            android.util.Log.e("faker1","putExtra");
            finish();
        }
    }
    class ItemDiemThanhPhan extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tvTenSV;
        TextView tvMaSV;
        TextView tvD1;
        TextView tvD2;
        TextView tvD3;
        TextView tvD4;
        TextView tvSoTietNghi;
        TextView tvDTB;
        TextView tvDieuKien;
        TextView stt;
        public ItemDiemThanhPhan(View itemView) {
            super(itemView);
            this.tvTenSV = (TextView) itemView.findViewById(R.id.id_item_diem_sv_tensv);
            this.tvMaSV = (TextView) itemView.findViewById(R.id.id_item_diem_sv_masv);
            this.tvD1 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d1);
            this.tvD2 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d2);
            this.tvD3 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d3);
            this.tvD4 = (TextView) itemView.findViewById(R.id.id_item_diem_sv_d4);
            this.tvSoTietNghi = (TextView) itemView.findViewById(R.id.id_item_diem_sv_so_tiet_nghi);
            this.tvDTB = (TextView) itemView.findViewById(R.id.id_item_diem_sv_dtb);
            this.tvDieuKien = (TextView) itemView.findViewById(R.id.id_item_diem_sv_dieuKien);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_sv_stt);
        }
    }
    private class AdapterListPointClass extends RecyclerView.Adapter<ListActivity.ItemDiemThanhPhan> implements RecyclerView.OnClickListener {
        private  ArrayList<ItemBangDiemThanhPhan> data;
        public AdapterListPointClass( ArrayList<ItemBangDiemThanhPhan> data) {
            this.data = data;
        }
        @Override
        public ListActivity.ItemDiemThanhPhan onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.list_point_student, parent, false);
            view.setOnClickListener(this);
            ListActivity.ItemDiemThanhPhan holder = new ListActivity.ItemDiemThanhPhan(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ListActivity.ItemDiemThanhPhan holder, int position) {
            ItemBangDiemThanhPhan itemBangDiemThanhPhan=data.get(position);
            holder.tvMaSV.setText(itemBangDiemThanhPhan.getMsv());
            holder.tvTenSV.setText(itemBangDiemThanhPhan.getTenSv());
            holder.tvD1.setText(itemBangDiemThanhPhan.getD1());
            holder.tvD2.setText(itemBangDiemThanhPhan.getD2());
            holder.tvD3.setText(itemBangDiemThanhPhan.getD3());
            holder.tvD4.setText(itemBangDiemThanhPhan.getD4());
            holder.tvDieuKien.setText(itemBangDiemThanhPhan.getDieuKien());
            holder.tvSoTietNghi.setText(itemBangDiemThanhPhan.getSoTietNghi());
            holder.tvDTB.setText(itemBangDiemThanhPhan.getdTB());
            holder.stt.setText(""+(position+1));
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            Intent returnIntent=getIntent();
            returnIntent.putExtra(MainActivity.MA_SV,data.get(itemPosition).getMsv());
            setResult(Activity.RESULT_OK,returnIntent);
            android.util.Log.e("faker1","setResult");
            finish();
        }

    }
}
