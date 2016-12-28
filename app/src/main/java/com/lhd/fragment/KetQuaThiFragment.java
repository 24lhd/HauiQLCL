package com.lhd.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.adaptor.KetQuaThiAdaptor;
import com.lhd.object.ItemDiemThiTheoMon;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserKetQuaThiTheoMon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Double.parseDouble;

/**
 * Created by Faker on 8/25/2016.
 */
public class KetQuaThiFragment extends FrameFragment {
    private  ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMons;
    private AlertDialog.Builder builder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_frame_fragment,container,false);
        initView(view);
        return view;
    }
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(getActivity())){
                    sqLiteManager.deleteDThiMon(sv.getMaSV());
                    startParser();
                }else{
                    pullRefreshLayout.setRefreshing(false);
                }

            }
        });
    }
    public void checkDatabase() {
        showProgress();
        itemDiemThiTheoMons =sqLiteManager.getAllDThiMon(sv.getMaSV());
        if (!itemDiemThiTheoMons.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else{
            loadData();
        }


    }
    public void startParser() {
        ParserKetQuaThiTheoMon parserKetQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
        parserKetQuaThiTheoMon.execute(sv.getMaSV());
    }
    public void setRecyclerView() {
        Collections.sort(itemDiemThiTheoMons, new Comparator<ItemDiemThiTheoMon>() {
            @Override
            public int compare(ItemDiemThiTheoMon o1, ItemDiemThiTheoMon o2) {
                String[] str1=o1.getNgay1().split("/");
                String[] str2=o2.getNgay1().split("/");
                if (str1.length==1){
                    return -1;
                }
                if (str2.length==1){
                    return 1;
                }
                String ngay1=str1[2]+str1[1];
                String ngay2=str2[2]+str2[1];
                return ngay1.compareTo(ngay2);
            }
        });
        Collections.reverse(itemDiemThiTheoMons);
        objects=new ArrayList<>();
        objects.addAll(itemDiemThiTheoMons);
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds(MainActivity.AD_UNIT_ID_KET_QUA_THI,320);
        RecyclerView.Adapter adapter = new KetQuaThiAdaptor(objects,recyclerView,this);
        recyclerView.setAdapter(adapter);



    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                        itemDiemThiTheoMons = (ArrayList<ItemDiemThiTheoMon>) msg.obj;
                        if (!itemDiemThiTheoMons.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            if (sqLiteManager.getAllDThiMon(sv.getMaSV()).size()< itemDiemThiTheoMons.size()){
                                for (ItemDiemThiTheoMon diemHocTapTheoLop: itemDiemThiTheoMons){
                                    sqLiteManager.insertDThiMon(diemHocTapTheoLop,sv.getMaSV());
                                }
                            }
                            showRecircleView();
                            pullRefreshLayout.setRefreshing(false);
                            setRecyclerView();
                        }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };

    public void showDiglog(View view) {
        final int itemPosition = recyclerView.getChildLayoutPosition(view);
        if (objects.get(itemPosition) instanceof  ItemDiemThiTheoMon){
            final ItemDiemThiTheoMon itemDiemThiTheoMon= (ItemDiemThiTheoMon) objects.get(itemPosition);
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(itemDiemThiTheoMon.getTenMon());
            final String [] list={" Xem kết quả thi lớp","Xem điểm "+itemDiemThiTheoMon.getTenMon()};
            builder.setItems(list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i==0){
                        Intent intent=new Intent(getActivity(),ListActivity.class);
                        intent.putExtra(KEY_OBJECT, (Serializable) itemDiemThiTheoMon);
                        Bundle bundle=new Bundle();
                        bundle.putInt(KEY_ACTIVITY,3);
                        intent.putExtra("action",bundle);
                        getActivity().startActivityForResult(intent,1);
                        getActivity().overridePendingTransition(R.anim.left_end, R.anim.right_end);
                        mainActivity.showStartADS();
                    }else{
                        showAlert("Kết quả thi của "+sv.getTenSV(),UIFromHTML.uiKetQuaThi(itemDiemThiTheoMon),
                                "Kết quả thi",itemDiemThiTheoMon.toString(),getActivity());
                    }
                }
            });

            builder.show();
        }
    }



    public static String charPoint(ItemDiemThiTheoMon itemDiemThiTheoMon) {
        if (itemDiemThiTheoMon.getdCuoiCung().length()<=1) return "*";
        if (itemDiemThiTheoMon.getdCuoiCung().contains("*")) return "*";
        double n;
        try {
            n=Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[2]);
        }catch (Exception e){
            n=0;
        }
        double th;
        try {
            th= Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[1]);
        }catch (Exception e){
            th=0;
        }
        double d;
        try {
         d= Double.parseDouble(itemDiemThiTheoMon.getdCuoiCung().split(" ")[0]);
        }catch (NumberFormatException e){
            return "*";
        }
        if (d>=8.5){
            return "A";
        }else if(d>=7.7&&n>=2015){
            if (n==2015&&th<=9){
                return "B";
            }else{
                return "B+";
            }
        }else if(d>=7.0){
            return "B";
        }else if(d>=6.2&&n>=2015){
            if (n==2015&&th<=9){
                return "C";
            }else{
                return "C+";
            }
        }else if(d>=5.5){
            return "C";
        }else if(d>=4.7&&n>=2015){
            if (n==2015&&th<=9){
                return "D";
            }else{
                return "D+";
            }
        }else if(d>=4.0){
            return "D";
        }else{
            return "F";
        }
    }

    public static boolean  isDouble(String str) {
        try {
            parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
