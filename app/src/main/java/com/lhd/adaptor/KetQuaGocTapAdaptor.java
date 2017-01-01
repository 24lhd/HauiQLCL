package com.lhd.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.fragment.FrameFragment;
import com.lhd.fragment.KetQuaHocTapFragment;
import com.lhd.object.ItemBangKetQuaHocTap;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;

/**
 * Created by d on 28/12/2016.
 */

    public class KetQuaGocTapAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final KetQuaHocTapFragment ketQuaHocTapFragment;
    private final RecyclerView recyclerView;
    private final ArrayList<ItemBangKetQuaHocTap> bangKetQuaHocTaps;
    private List<Object> mRecyclerViewItems;
        public class ItemDanhSachLop extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tvTenLop;
        TextView tvMaLop;
        TextView tvD1;
        TextView tvD2;
        TextView tvD3;
        TextView tvDDK;
        TextView tvSoTietNghi;
        TextView tvDTB;
        TextView tvDieuKien;
        TextView stt;
        public ItemDanhSachLop(View itemView) {
            super(itemView);
            this.tvTenLop = (TextView) itemView.findViewById(R.id.id_item_diem_lop_tenlop);
            this.tvMaLop = (TextView) itemView.findViewById(R.id.id_item_diem_lop_masv);
            this.tvD1 = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d1);
            this.tvD2 = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d2);
            this.tvD3 = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d3);
            this.tvDDK = (TextView) itemView.findViewById(R.id.id_item_diem_lop_d4);
            this.tvSoTietNghi = (TextView) itemView.findViewById(R.id.id_item_diem_lop_so_tiet_nghi);
            this.tvDTB = (TextView) itemView.findViewById(R.id.id_item_diem_lop_dtb);
            this.tvDieuKien = (TextView) itemView.findViewById(R.id.id_item_diem_lop_dieuKien);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_lop_stt);
        }
    }

        @Override
        public int getItemViewType(int position) {
            return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
        }
        public KetQuaGocTapAdaptor(List<Object> recyclerViewItems,
                                   RecyclerView recyclerView, KetQuaHocTapFragment ketQuaHocTapFragment,
                                   ArrayList<ItemBangKetQuaHocTap> bangKetQuaHocTaps) {
            this.mRecyclerViewItems = recyclerViewItems;
            this.recyclerView=recyclerView;
            this.ketQuaHocTapFragment=ketQuaHocTapFragment;
            this.bangKetQuaHocTaps=bangKetQuaHocTaps;

        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ads, parent, false);
                    return new FrameFragment.NativeExpressAdViewHolder(nativeExpressLayoutView);
                // fall through
                default:
                case MENU_ITEM_VIEW_TYPE:
                    View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bang_diem_thanh_phan, parent, false);
                    return new ItemDanhSachLop(menuItemLayoutView);
            }
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
                    // Add the Native Express ad to the native express ad view.
                    adCardView.addView(adView);
                    break;
                default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                    ItemDanhSachLop itemDanhSachLop= (ItemDanhSachLop) holder;
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int itemPosition = recyclerView.getChildLayoutPosition(view);
                            if (mRecyclerViewItems.get(itemPosition) instanceof ItemBangKetQuaHocTap){
                                ItemBangKetQuaHocTap diemHocTapTheoMon= (ItemBangKetQuaHocTap) mRecyclerViewItems.get(itemPosition);
                                ketQuaHocTapFragment.showCustomViewDialog(diemHocTapTheoMon);
                            }
                        }
                    });
                    ItemBangKetQuaHocTap item = (ItemBangKetQuaHocTap) mRecyclerViewItems.get(position);
                    itemDanhSachLop.tvTenLop.setText(item.getTenMon());
                    itemDanhSachLop.tvMaLop.setText(item.getMaMon());
                    itemDanhSachLop.tvD1.setText(item.getD1());
                    itemDanhSachLop.tvD2.setText(item.getD2());
                    itemDanhSachLop.tvD3.setText(item.getD3());
                    itemDanhSachLop.tvDDK.setText(item.getdGiua());
                    itemDanhSachLop.tvDieuKien.setText(item.getDieuKien());
                    itemDanhSachLop.tvSoTietNghi.setText(item.getSoTietNghi());
                    itemDanhSachLop.tvDTB.setText(item.getdTB());
                    itemDanhSachLop.stt.setText(""+(bangKetQuaHocTaps.indexOf(item)+1));
                    break;



            }
        }
        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }
    }
