package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;

import com.baoyz.widget.PullRefreshLayout;
import com.lhd.activity.MainActivity;
import com.lhd.adaptor.ThongBaoDTTCAdaptor;
import com.lhd.object.ItemNotiDTTC;
import com.lhd.task.ParserNotiDTTC;

import java.util.ArrayList;

/**
 * Created by D on 12/15/2016.
 */

public class ThongBaoDtttcFragment extends FrameFragment {
    private ArrayList<ItemNotiDTTC> itemNotiDTTCs;
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(getMainActivity())){
                    sqLiteManager.deleteItemNotiDTTC();
                    loadData();
                }else
                    pullRefreshLayout.setRefreshing(false);


            }
        });
    }
    public void checkDatabase() {
        showProgress();
        itemNotiDTTCs=sqLiteManager.getNotiDTTC();
        if (!itemNotiDTTCs.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else loadData();
    }
    public void startParser() {
//        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handler);
//        parserNotiDTTC.execute();
    }
    public void setRecyclerView() {
        objects=new ArrayList<>();
        objects.addAll(itemNotiDTTCs);
         addNativeExpressAds();
        setUpAndLoadNativeExpressAds(MainActivity.AD_UNIT_ID_TB_DTTC,320);
        ThongBaoDTTCAdaptor adapterNoti=new ThongBaoDTTCAdaptor(objects,recyclerView,this,itemNotiDTTCs);
        recyclerView.setAdapter(adapterNoti);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
              setRecyclerView();
                showRecircleView();
                sqLiteManager.deleteItemNotiDTTC();
                for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs) {
                    sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
}
