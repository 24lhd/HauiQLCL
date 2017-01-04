package com.lhd.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.object.ItemDiemThiTheoMon;
import com.lhd.object.ItemKetQuaThiLop;
import com.lhd.object.KetQuaThi;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserKetQuaThiTheoLop;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;

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
            pullRefreshLayout.setRefreshing(false);
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
    public void refesh() {
        if (MainActivity.isOnline(getActivity())){
            loadData();

        }else  pullRefreshLayout.setRefreshing(false);

    }
    @Override
    public void setRecyclerView() {
        showRecircleView();
        objects=new ArrayList<>();
        objects.addAll(ketQuaThi.getKetQuaThiLops());
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds(MainActivity.AD_UNIT_ID_DIEM_LOP,100);
        AdapterDiemThiLop adapterNoti=new AdapterDiemThiLop(objects,recyclerView);
        recyclerView.setAdapter(adapterNoti);
    }
    @Override
    public void checkDatabase() {
        ketQuaThi=sqLiteManager.getAllDThiLop(itemDiemThiTheoMon.getLinkDiemThiTheoLop());
        if (ketQuaThi!=null&&!ketQuaThi.getKetQuaThiLops().isEmpty()){
            listActivity.  getSupportActionBar().setTitle("Điểm thi "+ itemDiemThiTheoMon.getTenMon());
            listActivity.   getSupportActionBar().setSubtitle(ketQuaThi.getTenLopUuTien()+"_"+ketQuaThi.getSoTC()+" tín chỉ");
            setRecyclerView();
        }else  loadData();
    }
    class ItemDiemThiLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tvTenSV;
        TextView tvMaSV;
        TextView tvL1;
        TextView tvL2;
        TextView tvGC;
        TextView stt;
        public ItemDiemThiLop(View itemView) {
            super(itemView);
            this.tvTenSV = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_tensv);
            this.tvMaSV = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_masv);
            this.tvL1 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_l1);
            this.tvL2 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_l2);
            this.tvGC = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_gc);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_thi_sv_stt);

        }
    }
    private class AdapterDiemThiLop extends RecyclerView.Adapter<RecyclerView.ViewHolder>
            implements RecyclerView.OnClickListener {
        private  RecyclerView recyclerView;
        private List<Object> mRecyclerViewItems;
        public AdapterDiemThiLop(List<Object> mRecyclerViewItems, RecyclerView recyclerView ) {
            this.recyclerView = recyclerView;
            this.mRecyclerViewItems = mRecyclerViewItems;
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
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ket_qua_thi, parent, false);
                    view.setOnClickListener(this);
                    ItemDiemThiLop holder = new ItemDiemThiLop(view);
                    return holder;
            }
        }
        @Override
        public int getItemViewType(int position) {
            return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);

            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    FrameFragment.NativeExpressAdViewHolder nativeExpressHolder = (FrameFragment.NativeExpressAdViewHolder) holder;
                    NativeExpressAdView adView = (NativeExpressAdView) mRecyclerViewItems.get(position);
                    ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    adCardView.addView(adView);
                    break;
                default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                     ItemKetQuaThiLop itemKetQuaThiLop= (ItemKetQuaThiLop) mRecyclerViewItems.get(position);
                    ItemDiemThiLop itemDiemThiLop= (ItemDiemThiLop) holder;
                    itemDiemThiLop.tvTenSV.setText(itemKetQuaThiLop.getTen());
                    itemDiemThiLop.tvMaSV.setText(itemKetQuaThiLop.getMsv());
                    itemDiemThiLop.tvL1.setText(itemKetQuaThiLop.getdL1());
                    itemDiemThiLop.tvL2.setText(itemKetQuaThiLop.getdL2());
                    itemDiemThiLop.tvGC.setText(itemKetQuaThiLop.getGhiChu());
                    itemDiemThiLop.stt.setText(""+(ketQuaThi.getKetQuaThiLops().indexOf(itemKetQuaThiLop)+1));
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
            AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
            final ItemKetQuaThiLop itemKetQuaThiLop= (ItemKetQuaThiLop) mRecyclerViewItems.get(itemPosition);
            builder.setTitle(itemKetQuaThiLop.getTen());
            final String [] list={" Xem thông tin","Xem điểm thi "+ itemDiemThiTheoMon.getTenMon()};
            builder.setItems(list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i==0){
                        Intent intent=listActivity.getIntent();
                        intent.putExtra(MainActivity.MA_SV,itemKetQuaThiLop.getMsv());
                        Log.e("ListActivity",itemKetQuaThiLop.getMsv());
                        listActivity.setResult(Activity.RESULT_OK,intent);
                        listActivity.finish();
                        listActivity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                    }else{
                        showAlert("Điểm thi "+ itemDiemThiTheoMon.getTenMon(), UIFromHTML.uiDiemThiListAC(itemKetQuaThiLop)
                                ,itemDiemThiTheoMon.getTenMon(),itemKetQuaThiLop.toString(),listActivity);
                    }
                }
            });

            builder.show();

        }
    }
}
