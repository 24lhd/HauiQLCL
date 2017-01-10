package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;

import com.lhd.activity.MainActivity;
import com.lhd.adaptor.DiemThiTheoLopAdaptor;
import com.lhd.object.BangDiemThanhPhan;
import com.lhd.object.ItemBangDiemThanhPhan;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.task.ParserDiemThanhPhan;

import java.util.ArrayList;

/**
 * Created by d on 29/12/2016.
 */

public class DiemHocTapTheoLopFragment extends FrameFragment {
    private BangDiemThanhPhan bangDiemThanhPhan;
    public void setItemBangKetQuaHocTap(ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
        this.itemBangKetQuaHocTap = itemBangKetQuaHocTap;
    }

    public BangDiemThanhPhan getBangDiemThanhPhan() {
        return bangDiemThanhPhan;
    }

    @Override
    protected void startParser() {
        ParserDiemThanhPhan parserDiemThanhPhan=new ParserDiemThanhPhan(handler);
        parserDiemThanhPhan.execute(itemBangKetQuaHocTap.getLinkDiemLop());
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                if(msg.arg1==1){
                    bangDiemThanhPhan = (BangDiemThanhPhan) msg.obj;
                    ArrayList<ItemBangDiemThanhPhan> b= bangDiemThanhPhan.getBangDiemThanhPhan();
                    if (!b.isEmpty()){
                        sqLiteManager.deleteDLop(itemBangKetQuaHocTap.getMaMon());
                        for (ItemBangDiemThanhPhan diemHocTapTheoLop:b){
                            sqLiteManager.insertDLop(diemHocTapTheoLop, bangDiemThanhPhan.getMaLopDL(), bangDiemThanhPhan.getTenLopUuTien(), bangDiemThanhPhan.getSoTin());
                        }
                        getListActivity().getSupportActionBar().setTitle("Điểm thành phần "+itemBangKetQuaHocTap.getTenMon());
                        getListActivity().getSupportActionBar().setSubtitle(bangDiemThanhPhan.getTenLopUuTien()+"_"+ bangDiemThanhPhan.getSoTin()+" tín chỉ");
                        setRecyclerView();
                    }
                }
            }catch (NullPointerException e){
                // neu bị null nó sẽ vào đây
                startParser();
            }
        }
    };
    @Override
    public void refesh() {
        if (MainActivity.isOnline(getActivity())){
            loadData();

        }else  pullRefreshLayout.setRefreshing(false);

    }

    @Override
    public void setRecyclerView() {
        objects = new ArrayList<>();
        objects.addAll(bangDiemThanhPhan.getBangDiemThanhPhan());
        addNativeExpressAds(MainActivity.AD_UNIT_ID_KQHT, MainActivity.NATIVE_EXPRESS_AD_HEIGHT);
        DiemThiTheoLopAdaptor adapterNoti = new DiemThiTheoLopAdaptor(objects, recyclerView, getListActivity(), getItemBangDiemThanhPhan(),bangDiemThanhPhan.getBangDiemThanhPhan(),this);
        recyclerView.setAdapter(adapterNoti);
        showRecircleView();
    }
    private ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    @Override
    public void checkDatabase() {
        showProgress();
        bangDiemThanhPhan = sqLiteManager.getAllDLop(itemBangKetQuaHocTap.getMaMon());
        if (bangDiemThanhPhan !=null&&!bangDiemThanhPhan.getBangDiemThanhPhan().isEmpty()){
            getListActivity().getSupportActionBar().setTitle("Điểm thành phần "+itemBangKetQuaHocTap.getTenMon());
            getListActivity().getSupportActionBar().setSubtitle(bangDiemThanhPhan.getTenLopUuTien()+"_"+ bangDiemThanhPhan.getSoTin()+" tín chỉ");
            setRecyclerView();
        }else
           loadData();

    }

    public ItemBangKetQuaHocTap getItemBangDiemThanhPhan() {
        return itemBangKetQuaHocTap;
    }
}
