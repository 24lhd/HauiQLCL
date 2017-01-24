package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;

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
    public void checkDatabase() {
        showProgress();
        itemNotiDTTCs=sqLiteManager.getNotiDTTC();
        if (!itemNotiDTTCs.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else loadData();
    }
    public void startParser() {
        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handler);
        parserNotiDTTC.execute();
    }


    public void setRecyclerView() {
        objects=new ArrayList<>();
        objects.addAll(itemNotiDTTCs);
         addNativeExpressAds(MainActivity.AD_UNIT_ID_KQHT,MainActivity.NATIVE_EXPRESS_AD_HEIGHT);
        ThongBaoDTTCAdaptor adapterNoti=new ThongBaoDTTCAdaptor(objects,recyclerView,this,itemNotiDTTCs);
        recyclerView.setAdapter(adapterNoti);
        showRecircleView();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
                setRecyclerView();
                if (!itemNotiDTTCs.isEmpty()){
                    sqLiteManager.deleteItemNotiDTTC();
                    for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs) {
                        sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                    }
                }

            }catch (NullPointerException e){
                startParser();
            }
        }
    };
}
