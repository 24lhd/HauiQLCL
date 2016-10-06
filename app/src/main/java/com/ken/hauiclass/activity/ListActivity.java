package com.ken.hauiclass.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.ken.hauiclass.database.SQLiteManager;
import com.ken.hauiclass.fragment.KetQuaHocTapFragment;
import com.ken.hauiclass.item.DiemThanhPhan;
import com.ken.hauiclass.item.DiemThiTheoMon;
import com.ken.hauiclass.item.ItemBangDiemThanhPhan;
import com.ken.hauiclass.item.ItemBangKetQuaHocTap;
import com.ken.hauiclass.item.ItemKetQuaThiLop;
import com.ken.hauiclass.item.KetQuaThi;
import com.ken.hauiclass.item.LichThiLop;
import com.ken.hauiclass.task.ParserDiemThanhPhan;
import com.ken.hauiclass.task.ParserKetQuaThiTheoLop;
import com.ken.hauiclass.task.ParserLichThiTheoLop;

import java.util.ArrayList;

/**
 * Created by Faker on 8/30/2016.
 */
public class ListActivity extends AppCompatActivity {
    private SQLiteManager sqLiteManager;
    private RecyclerView recyclerView;
    private TextView textNull;
    private ProgressBar progressBar;
    private LinearLayout toolbar;
    private TextView tvTenLop;
    private TextView tvTenLopUuTien;
    private TextView tvSoTc;
    private Intent intent;
    private PullRefreshLayout pullRefreshLayout;
    private Bundle bundle;
    private TabLayout tabLayout;
    private int index;
    private ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    private DiemThiTheoMon diemThiTheoMon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gerenal);
        initView();
        checkDatabases();
    }
    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.left_end,
                R.anim.right_end);
    }
    private void initView( ) {
        sqLiteManager=new SQLiteManager(this);
        toolbar= (LinearLayout) findViewById(R.id.toolbar_list_activity);
        tvTenLop= (TextView) toolbar.findViewById(R.id.tb_title);
        pullRefreshLayout= (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        tvTenLopUuTien= (TextView) toolbar.findViewById(R.id.tb_text1);
        tvSoTc= (TextView) toolbar.findViewById(R.id.tb_text2);
        tabLayout= (TabLayout) findViewById(R.id.tablayout_fa);
        tabLayout.setVisibility(View.GONE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
            case 1:
                ParserLichThiTheoLop lichThiTheoLop=new ParserLichThiTheoLop(handler);
                lichThiTheoLop.execute(itemBangKetQuaHocTap.getLinkLichThiLop());
                break;
            case 2:

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
                    DiemThanhPhan diemThanhPhan = sqLiteManager.getAllDLop(itemBangKetQuaHocTap.getMaMon());
                    tvTenLopUuTien.setText(itemBangKetQuaHocTap.getMaMon());
                    tvTenLop.setText(itemBangKetQuaHocTap.getTenMon());
                    if (diemThanhPhan!=null&&!diemThanhPhan.getBangDiemThanhPhan().isEmpty()){
                        showRecircleView();
                        tvTenLopUuTien.setText(""+diemThanhPhan.getTenLopUuTien());
                        tvSoTc.setText("Điểm thành phần");
                        AdapterListPointClass  adapter=new AdapterListPointClass(diemThanhPhan.getBangDiemThanhPhan());
                        recyclerView.setAdapter(adapter);
                    }else{
                        if (isOnline()){
                            showProgress();
                            startParser();
                        }else{
                            cantLoadData();
                        }
                    }
                    break;
                case 1:
                    itemBangKetQuaHocTap= (ItemBangKetQuaHocTap) intent.getSerializableExtra(KetQuaHocTapFragment.KEY_OBJECT);
                    tvTenLop.setText(itemBangKetQuaHocTap.getTenMon());
                    tvTenLopUuTien.setText(""+itemBangKetQuaHocTap.getMaMon());
                    tvSoTc.setText("Kê hoạch thi");
                    ArrayList<LichThiLop> lichThiLops=sqLiteManager.getAllLThiLop(itemBangKetQuaHocTap.getMaMon());
                    if (!lichThiLops.isEmpty()){
                        showRecircleView();
                        AdapterLichThiLop  adapter=new AdapterLichThiLop(lichThiLops);
                        recyclerView.setAdapter(adapter);
                    }else{
                        if (isOnline()){
                            showProgress();
                            startParser();
                        }else{
                            cantLoadData();
                        }
                    }
                    break;
                case 2:

                    break;
                case 3:
                    diemThiTheoMon= (DiemThiTheoMon) intent.getSerializableExtra(KetQuaHocTapFragment.KEY_OBJECT);
                    tvTenLop.setText(diemThiTheoMon.getTenMon());
                    KetQuaThi ketQuaThi=sqLiteManager.getAllDThiLop(diemThiTheoMon.getLinkDiemThiTheoLop());
                    if (ketQuaThi!=null&&!ketQuaThi.getKetQuaThiLops().isEmpty()){
                        tvTenLopUuTien.setText(""+ketQuaThi.getTenLopUuTien());
                        tvSoTc.setText("Điểm thi");
                        showRecircleView();
                        AdapterDiemThiLop  adapter=new AdapterDiemThiLop(ketQuaThi.getKetQuaThiLops());
                        recyclerView.setAdapter(adapter);
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
            case 1:
                sqLiteManager.deleteLThiLop(itemBangKetQuaHocTap.getMaMon());
                break;
            case 2:
                break;
            case 3:
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
                    DiemThanhPhan diemThanhPhan= (DiemThanhPhan) msg.obj;
                    ArrayList<ItemBangDiemThanhPhan> b= diemThanhPhan.getBangDiemThanhPhan();
                    if (!b.isEmpty()){
                        for (ItemBangDiemThanhPhan diemHocTapTheoLop:b){
                           sqLiteManager.insertDLop(diemHocTapTheoLop,diemThanhPhan.getMaLopDL(),diemThanhPhan.getTenLopUuTien(),diemThanhPhan.getSoTin());
                        }
                        tvTenLopUuTien.setText("Điểm thành phần \n"+diemThanhPhan.getTenLopUuTien());
                        tvSoTc.setText(diemThanhPhan.getSoTin()+" tín chỉ");
                        showRecircleView();
                        AdapterListPointClass  adapter=new AdapterListPointClass(b);
                        recyclerView.setAdapter(adapter);
                    }
                }else if(msg.arg1==3){
                    KetQuaThi ketQuaThi= (KetQuaThi) msg.obj;;
                    ArrayList<ItemKetQuaThiLop> b= ketQuaThi.getKetQuaThiLops();
                    if (!b.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                        tvTenLopUuTien.setText("Điểm thi \n"+ketQuaThi.getTenLopUuTien());
                        tvSoTc.setText(ketQuaThi.getSoTC()+" tín chỉ");
                        for (ItemKetQuaThiLop diemHocTapTheoLop:b){
                            sqLiteManager.insertDThiLop(diemThiTheoMon.getLinkDiemThiTheoLop(),ketQuaThi.getTenLopUuTien(),ketQuaThi.getSoTC(),diemHocTapTheoLop);
                        }
                        showRecircleView();
                        AdapterDiemThiLop  adapter=new AdapterDiemThiLop(b);
                        recyclerView.setAdapter(adapter);
                    }
                }else if(msg.arg1==4){
                    ArrayList<LichThiLop> lichThiLops= (ArrayList<LichThiLop>) msg.obj;
                    ArrayList<LichThiLop> lichThiLopOld=sqLiteManager.getAllLThiLop(itemBangKetQuaHocTap.getMaMon());
                    if (!lichThiLops.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                        tvTenLop.setText(itemBangKetQuaHocTap.getTenMon());
                        tvTenLopUuTien.setText(itemBangKetQuaHocTap.getMaMon());
                        tvSoTc.setText("Kê hoạch thi");
                        if (lichThiLopOld.size()<lichThiLops.size()){
                            for (LichThiLop lichThiLop:lichThiLops){
                                sqLiteManager.insertlthilop(lichThiLop);
                            }
                        }
                        showRecircleView();
                        AdapterLichThiLop  adapter=new AdapterLichThiLop(lichThiLops);
                        recyclerView.setAdapter(adapter);
                    }else{
                        showTextNull();
                        textNull.setText("Không có lịch thi theo lớp...");
                    }
                }
            }catch (NullPointerException e){
                // neu bị null nó sẽ vào đây
                startParser();
            }
        }
    };
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
            Intent intent=new Intent(getApplicationContext(),ViewKetQuaHocTap.class);
            Bundle bundle=new Bundle();
            bundle.putString("MA_SV",data.get(itemPosition).getMsv());
            intent.putExtra("KEY_MSV",bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            finish();

        }
    }
    class ItemLichThiLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView ngayThi;
        TextView caThi;
        TextView lanThi;
        TextView tenLop;
        TextView stt;
        public ItemLichThiLop(View itemView) {
            super(itemView);
            this.ngayThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_nt);
            this.caThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_ca);
            this.lanThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_lt);
            this.tenLop = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_tenlop);
            this.tenLop = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_tenlop);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_stt);

        }
    }
    private class AdapterLichThiLop extends RecyclerView.Adapter<ListActivity.ItemLichThiLop> implements RecyclerView.OnClickListener {
        private  ArrayList<LichThiLop> data;
        public AdapterLichThiLop( ArrayList<LichThiLop> data) {
            this.data = data;
        }
        @Override
        public ListActivity.ItemLichThiLop onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.item_lich_thi_lop, parent, false);
            view.setOnClickListener(this);
            ListActivity.ItemLichThiLop holder = new ListActivity.ItemLichThiLop(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ListActivity.ItemLichThiLop holder, int position) {
            LichThiLop itemBangDiemThanhPhan=data.get(position);
            holder.ngayThi.setText(itemBangDiemThanhPhan.getNgayThi());
            holder.caThi.setText(itemBangDiemThanhPhan.getGioThi());
            holder.lanThi.setText(itemBangDiemThanhPhan.getLanThi());
            holder.tenLop.setText(itemBangDiemThanhPhan.getTenLop());
            holder.stt.setText(position+"");
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
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
            Intent intent=new Intent(getApplicationContext(),ViewKetQuaHocTap.class);
            Bundle bundle=new Bundle();
            bundle.putString("MA_SV",data.get(itemPosition).getMsv());
            intent.putExtra("KEY_MSV",bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            finish();

        }
    }
}
