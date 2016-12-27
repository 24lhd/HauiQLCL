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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
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
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;
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
        objects=new ArrayList<>();
        objects.addAll(bangKetQuaHocTaps);
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds();
        RecyclerView.Adapter adapter = new KetQuaAdaptor(objects);
        recyclerView.setAdapter(adapter);
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
    private void setUpAndLoadNativeExpressAds() {
        // Use a Runnable to ensure that the RecyclerView has been laid out before setting the
        // ad size for the Native Express ad. This allows us to set the Native Express ad's
        // width to match the full width of the RecyclerView.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                final float density = mainActivity.getResources().getDisplayMetrics().density;
                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                for (int i = 0; i <= objects.size(); i += ITEMS_PER_AD) {
                    final NativeExpressAdView adView = (NativeExpressAdView) objects.get(i);
                    AdSize adSize = new AdSize((int) (recyclerView.getWidth()/density),132);
                    adView.setAdSize(adSize);
                    adView.setAdUnitId(MainActivity.AD_UNIT_ID);
                }
                // Load the first Native Express ad in the items list.
                loadNativeExpressAd(0);
            }
        });
    }
    private void loadNativeExpressAd(final int index) {
        if (index >= objects.size()) {
            return;
        }
        Object item = objects.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

         final NativeExpressAdView adView = (NativeExpressAdView) item;
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }
        });
        // Load the Native Express ad.
        adView.loadAd(new AdRequest.Builder().build());
    }
    private void showCustomViewDialog(final ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] list = new String[]{"Bảng điểm học tâp", "Kế hoạch thi theo lớp","Xem điểm " +
                ""+itemBangKetQuaHocTap.getTenMon(),"Dự tính kết quả thi ^^"};
        final AlertDialog.Builder alertDialogPro=new AlertDialog.Builder(getContext());
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
    public class KetQuaAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerView.OnClickListener{
        private List<Object> mRecyclerViewItems;
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            if (mRecyclerViewItems.get(itemPosition) instanceof  ItemBangKetQuaHocTap){
                ItemBangKetQuaHocTap diemHocTapTheoMon= (ItemBangKetQuaHocTap) mRecyclerViewItems.get(itemPosition);
                showCustomViewDialog(diemHocTapTheoMon);
            }

        }
        public class ItemDanhSachLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
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
        public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
            NativeExpressAdViewHolder(View view) {
                super(view);
            }
        }
        @Override
        public int getItemViewType(int position) {
//            if (mainActivity.isOnline(mainActivity))
            return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
//            return   MENU_ITEM_VIEW_TYPE;
        }

        public KetQuaAdaptor( List<Object> recyclerViewItems) {
            this.mRecyclerViewItems = recyclerViewItems;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ads, parent, false);
                    return new NativeExpressAdViewHolder(nativeExpressLayoutView);
                    // fall through
                default:
                case MENU_ITEM_VIEW_TYPE:
                    View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bang_diem_thanh_phan, parent, false);
                    menuItemLayoutView.setOnClickListener(this);
                    return new ItemDanhSachLop(menuItemLayoutView);
            }
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
                    NativeExpressAdView adView = (NativeExpressAdView) mRecyclerViewItems.get(position);
                    ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    // Add the Native Express ad to the native express ad view.
                    adCardView.addView(adView);
                    break;
                default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                    ItemDanhSachLop itemDanhSachLop= (ItemDanhSachLop) holder;
                    ItemBangKetQuaHocTap item = (ItemBangKetQuaHocTap) mRecyclerViewItems.get(position);
                    itemDanhSachLop.tvTenLop.setText(item.getTenMon());
                    itemDanhSachLop.tvMaLop.setText(item.getMaMon());
                    itemDanhSachLop.tvD1.setText(item.getD1());
                    itemDanhSachLop.tvD2.setText(item.getD2());
                    itemDanhSachLop.tvD3.setText(item.getD3());
                    itemDanhSachLop.tvDDK.setText(item.getdGiua());
                    itemDanhSachLop.tvDieuKien.setText(item.getDieuKien());
                    itemDanhSachLop.tvSoTietNghi.setText(item.getSoTietNghi());
                    itemDanhSachLop.tvDTB.setText(item.getdTB());
                    itemDanhSachLop.stt.setText(""+(position+1));
                    break;



            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }
    }

}
