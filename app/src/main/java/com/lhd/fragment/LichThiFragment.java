package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;

import com.baoyz.widget.PullRefreshLayout;
import com.lhd.activity.MainActivity;
import com.lhd.adaptor.LichThiAdaptor;
import com.lhd.object.LichThi;
import com.lhd.object.SinhVien;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserLichThiTheoMon;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Faker on 8/25/2016.
 */
public class LichThiFragment extends FrameFragment {
    private  ArrayList<LichThi> lichThis;
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(getActivity())){

                   loadData();
                }else
                    pullRefreshLayout.setRefreshing(false);


            }
        });
    }
    public void checkDatabase() {
        showProgress();
        lichThis=sqLiteManager.getAllLThi(sv.getMaSV());
        if (!lichThis.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else
            loadData();

    }
    public void startParser() {
        ParserLichThiTheoMon parserKetQuaHocTap=new ParserLichThiTheoMon(handler);
        parserKetQuaHocTap.execute(sv.getMaSV());
    }
    public void setRecyclerView() {
        Collections.reverse(lichThis);
        objects=new ArrayList<>();
        objects.addAll(lichThis);
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds(MainActivity.AD_UNIT_ID_LICH_THI,132);
        LichThiAdaptor lichThiAdaptor=new LichThiAdaptor(objects,recyclerView,this,lichThis);
        recyclerView.setAdapter(lichThiAdaptor);
    }
    public void showDialog(LichThi lichThi, String toi) {
        showAlert(lichThi.getMon(), UIFromHTML.uiLichThi(lichThi,toi),
                "Lịch thi môn "+lichThi.getMon(), lichThi.toString(),getMainActivity());
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.arg1){
                    case 5:
                        lichThis= (ArrayList<LichThi>) msg.obj;
                        if (!lichThis.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            if (sqLiteManager.getAllLThi(sv.getMaSV()).size()<lichThis.size()){
                                sqLiteManager.deleteDLThi(sv.getMaSV());
                                for (LichThi lichThiLop:lichThis){
                                    sqLiteManager.insertlthi(lichThiLop,sv.getMaSV());
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
            }
        }
    };

}
