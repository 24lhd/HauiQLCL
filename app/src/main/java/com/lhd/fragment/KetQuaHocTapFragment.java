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
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.adaptor.KetQuaGocTapAdaptor;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.KetQuaHocTap;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserKetQuaHocTap;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

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
        objects=new ArrayList<>();
        objects.addAll(bangKetQuaHocTaps);
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds(MainActivity.AD_UNIT_ID_KQHT,MainActivity.NATIVE_EXPRESS_AD_HEIGHT);
        RecyclerView.Adapter adapter = new KetQuaGocTapAdaptor(objects,recyclerView,this,bangKetQuaHocTaps);
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
//    InterstitialAd mInterstitialAd;
//    private void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//
//        mInterstitialAd.loadAd(adRequest);
//    }
public void showCustomViewDialog(final ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
//    mInterstitialAd = new InterstitialAd(mainActivity);
//    mInterstitialAd.setAdUnitId("ca-app-pub-7062977963627166/2631498137");
//
//    mInterstitialAd.setAdListener(new AdListener() {
//        @Override
//        public void onAdClosed() {
//            requestNewInterstitial();
//            showDuTinhDiem(itemBangKetQuaHocTap);
//        }
//    });
//
//    requestNewInterstitial();





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
//                    if (mInterstitialAd.isLoaded()) {
//                        mInterstitialAd.show();
//                    }else{
//                        showDuTinhDiem(itemBangKetQuaHocTap);
//                    }

                    showDuTinhDiem(itemBangKetQuaHocTap);

                }else
                if (i==2){
                            showAlert("Kết quả học tập của "+sv.getTenSV(),
                                    UIFromHTML.uiKetQuaHocTap(itemBangKetQuaHocTap),
                                    itemBangKetQuaHocTap.getTenMon(),itemBangKetQuaHocTap.toString(),getActivity());
                }else {
                    Intent intent=new Intent(getActivity(),ListActivity.class);
                    intent.putExtra(KEY_OBJECT, (Serializable) itemBangKetQuaHocTap);
                    Bundle bundle=new Bundle();
                    bundle.putInt(KEY_ACTIVITY,i);
                    intent.putExtra("action",bundle);
                    getActivity().startActivityForResult(intent,1);
                    getActivity().overridePendingTransition(R.anim.left_end, R.anim.right_end);
//                    getMainActivity().showStartADS();
                }
            }
        }).show();
    }

    public void showDuTinhDiem( final ItemBangKetQuaHocTap itemBangKetQuaHocTap) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    if (itemBangKetQuaHocTap.getDieuKien().equalsIgnoreCase("Học lại"))
                        webView.loadDataWithBaseURL(null,getUIWeb(itemBangKetQuaHocTap.getdTB(),
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
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                final StartAppAd rewardedVideo = new StartAppAd(getMainActivity());
                rewardedVideo.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                        rewardedVideo.showAd();
                        Log.e("faker","show dia");
                    }

                    @Override
                    public void onFailedToReceiveAd(Ad ad) {

                    }
                });
            }
        });
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
    }

    private String getDiemDuTinh(int position, String diemTb) throws Exception{
            double start=((3*diemChus[position].getStart())-Double.parseDouble(diemTb))/2;
        double end=((3*diemChus[position].getEnd())-Double.parseDouble(diemTb))/2;
        DecimalFormat df = new DecimalFormat("#.0");
        if (start==end)  return "khoảng "+df.format(start);
        if (start>10)  return "chệu";
        if (start<0)  return "khoảng "+0+" đến "+ df.format(end);
        if (end>10)  return "khoảng "+df.format(start)+" đến "+ 10;
        if (end<=0)  return "chệu";
        return "khoảng "+df.format(start)+" đến "+
                df.format(end);
    }


}
