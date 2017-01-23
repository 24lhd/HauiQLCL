package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.LichThiLop;
import com.lhd.object.NativeExpressAdViewHolder;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserLichThiTheoLop;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;

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
        addNativeExpressAds(MainActivity.AD_UNIT_ID_LIST_ACTIVITY, 320);
        AdapterLichThiLop adapterNoti=new AdapterLichThiLop(objects,recyclerView);
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
    class ItemLichThiLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView ngayThi;
        TextView caThi;
        TextView lanThi;
        TextView tenLop;
        TextView stt;
        public ItemLichThiLop(View itemView) {
            super(itemView);
            this.ngayThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_nt);
            this.caThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_ca);
            this.lanThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_lt);
            this.tenLop = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_tenlop);
            this.tenLop = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_tenlop);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lop_stt);

        }
    }
    private class AdapterLichThiLop extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerView.OnClickListener {
        private  RecyclerView recyclerView;
        private List<Object> mRecyclerViewItems;
        @Override
        public int getItemViewType(int position) {
//            if (position==0) return MENU_ITEM_VIEW_TYPE;
            return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
        }
        public AdapterLichThiLop(List<Object> mRecyclerViewItems, RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            this.mRecyclerViewItems = mRecyclerViewItems;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ads, parent, false);
                    return new NativeExpressAdViewHolder(nativeExpressLayoutView);
                default:
                case MENU_ITEM_VIEW_TYPE:
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_thi_lop, parent, false);
                    view.setOnClickListener(this);
                    ItemLichThiLop holder = new ItemLichThiLop(view);
                    return holder;
            }
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
                    NativeExpressAdView adView = (NativeExpressAdView) mRecyclerViewItems.get(position);
                    adView= (NativeExpressAdView) getActivity().getLayoutInflater().inflate(R.layout.native_express_ad_container,null);
//                    ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                    try {
//                        if (adCardView.getChildCount() > 0) {
//                            adCardView.removeAllViews();
//                        }
                    }catch (IllegalStateException e){}
//                    adCardView.addView(adView);
                    break;
                default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                    ItemLichThiLop itemLichThiLop= (ItemLichThiLop) holder;
                    LichThiLop itemBangDiemThanhPhan= (LichThiLop) mRecyclerViewItems.get(position);
                    itemLichThiLop.ngayThi.setText(itemBangDiemThanhPhan.getNgayThi());
                    itemLichThiLop.caThi.setText(itemBangDiemThanhPhan.getGioThi());
                    itemLichThiLop.lanThi.setText(itemBangDiemThanhPhan.getLanThi());
                    itemLichThiLop.tenLop.setText(itemBangDiemThanhPhan.getTenLop());
                    itemLichThiLop.stt.setText(""+((position % ITEMS_PER_AD == 0) ? position=position-1 : position));
                    break;
            }

        }
        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }
        @Override
        public void onClick(View view) {
            final int itemPosition = recyclerView.getChildLayoutPosition(view);
            LichThiLop itemBangDiemThanhPhan= (LichThiLop) mRecyclerViewItems.get(itemPosition);
            showAlert("Kế hoạch thi",
                    UIFromHTML.uiKeHoachThi(itemBangDiemThanhPhan,itemBangKetQuaHocTap.getTenMon()),
                    "",itemBangDiemThanhPhan.toString(),listActivity);
        }
    }

}
