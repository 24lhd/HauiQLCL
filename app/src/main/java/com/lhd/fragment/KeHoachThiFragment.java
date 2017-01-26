package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;

import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.adaptor.KeHoachThiAdaptor;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.LichThiLop;
import com.lhd.task.ParserLichThiTheoLop;

import java.util.ArrayList;

/**
 * Created by d on 30/12/2016.
 */

public class KeHoachThiFragment extends FrameFragment {
    private ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    public void setItemBangKetQuaHocTap(ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
        this.itemBangKetQuaHocTap = itemBangKetQuaHocTap;

    }
    private ArrayList<LichThiLop> lichThiLops;
    public ListActivity getListActivity() {
        return listActivity;
    }

    public void setListActivity(ListActivity listActivity) {
        this.listActivity = listActivity;
    }

    private ListActivity listActivity;
    @Override
    protected void startParser() {
        ParserLichThiTheoLop lichThiTheoLop=new ParserLichThiTheoLop(handler);
        lichThiTheoLop.execute(itemBangKetQuaHocTap.getLinkLichThiLop());
    }


    @Override
    public void setRecyclerView() {
        objects=new ArrayList<>();
        objects.addAll(lichThiLops);
//        addNativeExpressAds(MainActivity.AD_UNIT_ID_LIST_ACTIVITY, 320);
        addNativeExpressAds();
        KeHoachThiAdaptor adapterNoti=new KeHoachThiAdaptor(objects,recyclerView,this,itemBangKetQuaHocTap);
        recyclerView.setAdapter(adapterNoti);
        showRecircleView();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                if(msg.arg1==4){
                    lichThiLops= (ArrayList<LichThiLop>) msg.obj;
                    ArrayList<LichThiLop> lichThiLopOld=sqLiteManager.getAllLThiLop(itemBangKetQuaHocTap.getMaMon());
                    if (!lichThiLops.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                        listActivity.getSupportActionBar().setTitle("Kế hoạch thi "+itemBangKetQuaHocTap.getTenMon());
                        listActivity.getSupportActionBar().setSubtitle(itemBangKetQuaHocTap.getMaMon());
                        if (lichThiLopOld.size()<lichThiLops.size()){
                            sqLiteManager.deleteLThiLop(itemBangKetQuaHocTap.getMaMon());
                            for (LichThiLop lichThiLop:lichThiLops){
                                sqLiteManager.insertlthilop(lichThiLop);
                            }
                        }
                        setRecyclerView();
                    }else{
                        showTextNull();
                        tVnull.setText("Không có lịch thi theo lớp...");
                    }
                }
            }catch (NullPointerException e){
                // neu bị null nó sẽ vào đây
            }
        }
    };

    @Override
    public void checkDatabase() {
        listActivity.getSupportActionBar().setTitle("Kế hoạch thi "+itemBangKetQuaHocTap.getTenMon());
        listActivity.getSupportActionBar().setSubtitle(itemBangKetQuaHocTap.getMaMon());
          lichThiLops=sqLiteManager.getAllLThiLop(itemBangKetQuaHocTap.getMaMon());
        if (!lichThiLops.isEmpty()){
            setRecyclerView();
        }else{
            if (MainActivity.isOnline(getContext())){
                showProgress();
                loadData();
            }else{
                cantLoadData();
            }
        }
    }

}
