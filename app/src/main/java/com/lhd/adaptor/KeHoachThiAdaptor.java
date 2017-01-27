package com.lhd.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.fragment.KeHoachThiFragment;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.LichThiLop;
import com.lhd.object.UIFromHTML;

import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;

/**
 * Created by d on 25/01/2017.
 */

public class KeHoachThiAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    private KeHoachThiFragment keHoachThiFragment;
    private  RecyclerView recyclerView;
    private List<Object> mRecyclerViewItems;

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
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_to);
        }
    }
    @Override
    public int getItemViewType(int position) {
            if (position==0) return MENU_ITEM_VIEW_TYPE;
        return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
    }
    public KeHoachThiAdaptor(List<Object> mRecyclerViewItems, RecyclerView recyclerView,
                             KeHoachThiFragment keHoachThiFragment, ItemBangKetQuaHocTap itemBangKetQuaHocTap) {
        this.recyclerView = recyclerView;
        this.mRecyclerViewItems = mRecyclerViewItems;
        this.keHoachThiFragment = keHoachThiFragment;
        this.itemBangKetQuaHocTap = itemBangKetQuaHocTap;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad_to, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case MENU_ITEM_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_thi_lop, parent, false);
                ItemLichThiLop holder = new ItemLichThiLop(view);
                return holder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) holder;
                keHoachThiFragment.loadNativeExpressAt(nativeExpressAdViewHolder.nativeExpressAdView);
//                    NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
//                    NativeExpressAdView adView = (NativeExpressAdView) mRecyclerViewItems.get(position);
////                    adView= (NativeExpressAdView) getActivity().getLayoutInflater().inflate(R.layout.native_express_ad_vua,null);
//                    ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
//                    try {
//                        if (adCardView.getChildCount() > 0) {
//                            adCardView.removeAllViews();
//                        }
//                    }catch (IllegalStateException e){}
//                    adCardView.addView(adView);
                break;
            default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                ItemLichThiLop itemLichThiLop= (ItemLichThiLop) holder;
                LichThiLop itemBangDiemThanhPhan= (LichThiLop) mRecyclerViewItems.get(position);
                itemLichThiLop.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int itemPosition = recyclerView.getChildLayoutPosition(view);
                        LichThiLop itemBangDiemThanhPhan= (LichThiLop) mRecyclerViewItems.get(itemPosition);
                        keHoachThiFragment.showAlert("Kế hoạch thi",
                                UIFromHTML.uiKeHoachThi(itemBangDiemThanhPhan,itemBangKetQuaHocTap.getTenMon()),
                                "",itemBangDiemThanhPhan.toString(),keHoachThiFragment.getActivity());
                    }
                });
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
}
