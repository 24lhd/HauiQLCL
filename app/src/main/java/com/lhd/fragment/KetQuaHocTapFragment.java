package com.lhd.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import com.alertdialogpro.AlertDialogPro;
import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.KetQuaHocTap;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserKetQuaHocTap;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.lhd.object.Haui.diemChus;
import static com.lhd.object.UIFromHTML.getUIWeb;

/**
 * Created by Faker on 8/25/2016.
 */

public class KetQuaHocTapFragment extends FrameFragment {
    private ArrayList<ItemBangKetQuaHocTap> bangKetQuaHocTaps;
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(getContext())){
                            sqLiteManager.deleteDMon(sv.getMaSV());
                    startParser();
                }else{
                    pullRefreshLayout.setRefreshing(false);
                }

            }
        });
    }
    public void checkDatabase() {
                showProgress();

                bangKetQuaHocTaps=sqLiteManager.getBangKetQuaHocTap(sv.getMaSV());
                if (!bangKetQuaHocTaps.isEmpty()){
                    showRecircleView();
                    setRecyclerView();
                }else{
                    loadData();
                }
    }
    public void startParser() {
        ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(handler);
        ketQuaHocTapTheoMon.execute(sv.getMaSV());
    }
    public void setRecyclerView() {
        Collections.sort(bangKetQuaHocTaps, new Comparator<ItemBangKetQuaHocTap>() {
            @Override
            public int compare(ItemBangKetQuaHocTap o1, ItemBangKetQuaHocTap o2) {
                try {
                    Double.parseDouble(o2.getMaMon());
                    return o1.getMaMon().compareTo(o2.getMaMon());
                }catch (NumberFormatException e){
                    return 0;
                }
            }
        });
        Collections.reverse(bangKetQuaHocTaps);
        AdapterDiemHocTapTheoMon adapterDiemHocTapTheoMon=new AdapterDiemHocTapTheoMon(bangKetQuaHocTaps);
        recyclerView.setAdapter(adapterDiemHocTapTheoMon);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.arg1){
                    case 0:
                        KetQuaHocTap b;
                        b= (KetQuaHocTap) msg.obj; // lay tren internet
                        if (b.getSinhVien()!=null&&!b.getBangKetQuaHocTaps().isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            sqLiteManager.insertSV(b.getSinhVien());
                            bangKetQuaHocTaps=b.getBangKetQuaHocTaps();
                            if (sqLiteManager.getBangKetQuaHocTap(sv.getMaSV()).size()<bangKetQuaHocTaps.size()){
                                for (ItemBangKetQuaHocTap diemHocTapTheoMon:bangKetQuaHocTaps){
                                    sqLiteManager.insertDMon(diemHocTapTheoMon,b.getSinhVien().getMaSV());
                                }
                            }
                            pullRefreshLayout.setRefreshing(false);
                            showRecircleView();
                            setRecyclerView();
                        }
                        break;
                }
            }catch (NullPointerException e){}
        }
    };
    class ItemDanhSachLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tvTenLop;
        TextView tvMaLop;
        TextView tvD1;
        TextView tvD2;
        TextView tvD3;
        TextView tvDDK;
        TextView tvSoTietNghi;
        TextView tvDTB;
        TextView tvDieuKien;
        TextView stt;
        public ItemDanhSachLop(View itemView) {
            super(itemView);

            this.tvTenLop = (TextView) itemView.findViewById(R.id.id_item_diem_lop_tenlop);
            this.tvMaLop = (TextView) itemView.findViewById(R.id.id_item_diem_lop_masv);
            this.tvD1 = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d1);
            this.tvD2 = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d2);
            this.tvD3 = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d3);
            this.tvDDK = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d4);
            this.tvSoTietNghi = (TextView) itemView.findViewById(R.id.id_item_diem_lop_so_tiet_nghi);
            this.tvDTB = (TextView) itemView.findViewById(R.id.id_item_diem_lop_dtb);
            this.tvDieuKien = (TextView) itemView.findViewById(R.id.id_item_diem_lop_dieuKien);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_lop_stt);
        }
    }
    class AdapterDiemHocTapTheoMon extends RecyclerView.Adapter<ItemDanhSachLop> implements RecyclerView.OnClickListener {
        private  ArrayList<ItemBangKetQuaHocTap> data;
        public AdapterDiemHocTapTheoMon( ArrayList<ItemBangKetQuaHocTap> data) {
            this.data = data;
        }
        @Override
        public ItemDanhSachLop onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_bang_diem_thanh_phan, parent, false);
            view.setOnClickListener(this);
            ItemDanhSachLop holder = new ItemDanhSachLop(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ItemDanhSachLop holder, int position) {
            ItemBangKetQuaHocTap item = bangKetQuaHocTaps.get(position);
            holder.tvTenLop.setText(item.getTenMon());
            holder.tvMaLop.setText(item.getMaMon());
            holder.tvD1.setText(item.getD1());
            holder.tvD2.setText(item.getD2());
            holder.tvD3.setText(item.getD3());
            holder.tvDDK.setText(item.getdGiua());
            holder.tvDieuKien.setText(item.getDieuKien());
            holder.tvSoTietNghi.setText(item.getSoTietNghi());
            holder.tvDTB.setText(item.getdTB());
            holder.stt.setText(""+(position+1));
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            ItemBangKetQuaHocTap diemHocTapTheoMon=bangKetQuaHocTaps.get(itemPosition);
            showCustomViewDialog(diemHocTapTheoMon);
        }
    }
    private void showCustomViewDialog(final ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] list = new String[]{"Bảng điểm học tâp", "Kế hoạch thi theo lớp","Xem điểm "+itemBangKetQuaHocTap.getTenMon(),"Dự tính kết quả thi :v"};
        final AlertDialogPro.Builder alertDialogPro=new AlertDialogPro.Builder(getContext(), mTheme);
        alertDialogPro.setTitle(itemBangKetQuaHocTap.getTenMon()).setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialogPro.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                });
                if (i==3){
                    View view=getLayoutInflater().inflate(R.layout.du_tinh_layout,null);
                    TabLayout tabLayout= (TabLayout) view.findViewById(R.id.tab_point);
                    final WebView webView= (WebView) view.findViewById(R.id.web_dutinh);
                    builder.setTitle("Dự tính kết quả thi  "+itemBangKetQuaHocTap.getTenMon());
                    TabLayout.Tab tabA=tabLayout.newTab();
                    tabA.setText("A");
                    tabLayout.addTab(tabA);
                    TabLayout.Tab tabBB=tabLayout.newTab();
                    tabBB.setText("B+");
                    tabLayout.addTab(tabBB);
                    TabLayout.Tab tabB=tabLayout.newTab();
                    tabB.setText("B");
                    tabLayout.addTab(tabB);
                    TabLayout.Tab tabCC=tabLayout.newTab();
                    tabCC.setText("C+");
                    tabLayout.addTab(tabCC);
                    TabLayout.Tab tabC=tabLayout.newTab();
                    tabC.setText("C");
                    tabLayout.addTab(tabC);
                    TabLayout.Tab tabDD=tabLayout.newTab();
                    tabDD.setText("D+");
                    tabLayout.addTab(tabDD);
                    TabLayout.Tab tabD=tabLayout.newTab();
                    tabD.setText("D");
                    tabLayout.addTab(tabD);
                    TabLayout.Tab tabF=tabLayout.newTab();
                    tabF.setText("F");
                    tabLayout.addTab(tabF);
                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
                    tabLayout.setSelectedTabIndicatorHeight(10);
                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            try {
                                if (itemBangKetQuaHocTap.getDieuKien().equalsIgnoreCase("Học lại")) webView.loadDataWithBaseURL(null,getUIWeb(itemBangKetQuaHocTap.getdTB(),
                                        "Học lại rồi bạn ê -_-"),"text/html","utf-8",null);
                                else
                                webView.loadDataWithBaseURL(null,getUIWeb(itemBangKetQuaHocTap.getdTB(),
                                        getDiemDuTinh(tab.getPosition(),itemBangKetQuaHocTap.getdTB())),"text/html","utf-8",null);
                            } catch (Exception e) {
                                if (!KetQuaThiFragment.isDouble(itemBangKetQuaHocTap.getdTB())){
                                    webView.loadDataWithBaseURL(null,getUIWeb(itemBangKetQuaHocTap.getdTB(),
                                            "chệu"),"text/html","utf-8",null);
                                }
                            }
                        }
                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {}
                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {}
                    });
                    try {
                        webView.loadDataWithBaseURL(null,getUIWeb(itemBangKetQuaHocTap.getdTB(),
                                getDiemDuTinh(0,itemBangKetQuaHocTap.getdTB())),"text/html","utf-8",null);
                    } catch (Exception e) {
                        if (!KetQuaThiFragment.isDouble(itemBangKetQuaHocTap.getdTB())){
                            webView.loadDataWithBaseURL(null,getUIWeb(itemBangKetQuaHocTap.getdTB(),
                                    "chệu"),"text/html","utf-8",null);
                        }
                    }
                    builder.setView(view);
                    builder.setPositiveButton("ok",null);
                    builder.setNeutralButton("IMG", null);
                    final AlertDialog mAlertDialog = builder.create();
                    mAlertDialog.show();
                    Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAlertDialog.dismiss();
                        }
                    });
                    Button c = mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.sreenShort(view,getContext());

                        }
                    });
                }else
                if (i==2){
                    showAlert("Kết quả học tập của "+sv.getTenSV(),UIFromHTML.uiKetQuaHocTap(itemBangKetQuaHocTap),itemBangKetQuaHocTap.getTenMon(),itemBangKetQuaHocTap.toString(),getActivity());
                }else {
                    Intent intent=new Intent(getActivity(),ListActivity.class);
                    intent.putExtra(KEY_OBJECT, (Serializable) itemBangKetQuaHocTap);
                    Bundle bundle=new Bundle();
                    bundle.putInt(KEY_ACTIVITY,i);
                    intent.putExtra("action",bundle);
                    getActivity().startActivityForResult(intent,1);
                    getActivity().overridePendingTransition(R.anim.left_end, R.anim.right_end);
                }
            }
        }).show();
    }
    private String getDiemDuTinh(int position, String diemTb) throws Exception{
            double start=((3*diemChus[position].getStart())-Double.parseDouble(diemTb))/2;
        double end=((3*diemChus[position].getEnd())-Double.parseDouble(diemTb))/2;
        DecimalFormat df = new DecimalFormat("#.0");
        if (start==end)  return "tầm "+df.format(start);
        if (start>10)  return "chệu";
        if (start<0)  return "tầm "+0+" đến "+ df.format(end);
        if (end>10)  return "tầm "+df.format(start)+" đến "+ 10;
        if (end<=0)  return "chệu";
        return "tầm "+df.format(start)+" đến "+
                df.format(end);
    }

}
