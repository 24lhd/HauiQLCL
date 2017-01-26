package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;

import com.lhd.activity.ListActivity;
import com.lhd.adaptor.DiemThiTheoLopAdaptor;
import com.lhd.object.ItemDiemThiTheoMon;
import com.lhd.object.ItemKetQuaThiLop;
import com.lhd.object.KetQuaThi;
import com.lhd.task.ParserKetQuaThiTheoLop;

import java.util.ArrayList;

/**
 * Created by d on 29/12/2016.
 */

public class DiemThiLopFragment extends FrameFragment {
    private ItemDiemThiTheoMon itemDiemThiTheoMon;
    private ListActivity listActivity;

    public ListActivity getListActivity() {
        return listActivity;
    }

    public void setListActivity(ListActivity listActivity) {
        this.listActivity = listActivity;
    }

    public KetQuaThi getKetQuaThi() {
        return ketQuaThi;
    }

    public void setKetQuaThi(KetQuaThi ketQuaThi) {
        this.ketQuaThi = ketQuaThi;
    }

    public ItemDiemThiTheoMon getItemDiemThiTheoMon() {
        return itemDiemThiTheoMon;
    }

    public void setItemDiemThiTheoMon(ItemDiemThiTheoMon itemDiemThiTheoMon) {
        this.itemDiemThiTheoMon = itemDiemThiTheoMon;
    }

    @Override
    protected void startParser() {
        ParserKetQuaThiTheoLop parserKetQuaHocTap=new ParserKetQuaThiTheoLop(handler);
        parserKetQuaHocTap.execute(itemDiemThiTheoMon.getLinkDiemThiTheoLop());
    }

    private KetQuaThi ketQuaThi;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            pullRefreshLayout.setRefreshing(false);
            try{
                  if(msg.arg1==3){
                    ketQuaThi= (KetQuaThi) msg.obj;;
                    ArrayList<ItemKetQuaThiLop> b= ketQuaThi.getKetQuaThiLops();
                    if (!b.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                       listActivity.getSupportActionBar().setTitle("Điểm thi "+ itemDiemThiTheoMon.getTenMon());
                        listActivity. getSupportActionBar().setSubtitle(ketQuaThi.getTenLopUuTien()+"_"+ketQuaThi.getSoTC()+" tín chỉ");
                        sqLiteManager.deleteDThiLop(itemDiemThiTheoMon.getLinkDiemThiTheoLop());
                        for (ItemKetQuaThiLop diemHocTapTheoLop:b){
                            sqLiteManager.insertDThiLop(itemDiemThiTheoMon.getLinkDiemThiTheoLop(),ketQuaThi.getTenLopUuTien(),ketQuaThi.getSoTC(),diemHocTapTheoLop);
                        }
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
    public void setRecyclerView() {
        objects=new ArrayList<>();
        objects.addAll(ketQuaThi.getKetQuaThiLops());
        addNativeExpressAds();
//        addNativeExpressAds(MainActivity.AD_UNIT_ID_LIST_ACTIVITY,320);
        DiemThiTheoLopAdaptor adapterNoti=new DiemThiTheoLopAdaptor(objects,
                recyclerView,
                this,ketQuaThi.getKetQuaThiLops(),itemDiemThiTheoMon);
        recyclerView.setAdapter(adapterNoti);
        showRecircleView();    }
    @Override
    public void checkDatabase() {
        ketQuaThi=sqLiteManager.getAllDThiLop(itemDiemThiTheoMon.getLinkDiemThiTheoLop());
        if (ketQuaThi!=null&&!ketQuaThi.getKetQuaThiLops().isEmpty()){
            listActivity.  getSupportActionBar().setTitle("Điểm thi "+ itemDiemThiTheoMon.getTenMon());
            listActivity.   getSupportActionBar().setSubtitle(ketQuaThi.getTenLopUuTien()+"_"+ketQuaThi.getSoTC()+" tín chỉ");
            setRecyclerView();
        }else  loadData();
    }

}
