package com.lhd.adaptor;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.fragment.DiemThiLopFragment;
import com.lhd.object.ItemDiemThiTheoMon;
import com.lhd.object.ItemKetQuaThiLop;
import com.lhd.object.UIFromHTML;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;
/**
 * Created by d on 29/12/2016.
 */
public class DiemThiTheoLopAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    private ItemDiemThiTheoMon diemThiTheoMon;
    private  ArrayList<ItemKetQuaThiLop> ketQuaThiLops;
    private DiemThiLopFragment diemThiLopFragment;
    private  RecyclerView recyclerView;
        private List<Object> mRecyclerViewItems;
        public DiemThiTheoLopAdaptor(List<Object> mRecyclerViewItems,
                                     RecyclerView recyclerView,
                                     DiemThiLopFragment diemThiLopFragment,
                                     ArrayList<ItemKetQuaThiLop> ketQuaThiLops, ItemDiemThiTheoMon diemThiTheoMon) {
            this.diemThiLopFragment = diemThiLopFragment;
            this.recyclerView = recyclerView;
            this.ketQuaThiLops = ketQuaThiLops;
            this.mRecyclerViewItems = mRecyclerViewItems;
            this.diemThiTheoMon=diemThiTheoMon;
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
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_to);
        }
    }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
                    View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad_to, parent, false);
                    return new NativeExpressAdViewHolder(nativeExpressLayoutView);
                // fall through
                default:
                case MENU_ITEM_VIEW_TYPE:
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ket_qua_thi, parent, false);
                    ItemDiemThiLop holder = new ItemDiemThiLop(view);
                    return holder;
            }
        }
        @Override
        public int getItemViewType(int position) {
            if (position==0) return MENU_ITEM_VIEW_TYPE;
            return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);

            switch (viewType) {
                case NATIVE_EXPRESS_AD_VIEW_TYPE:
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
                    NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) holder;
                    diemThiLopFragment.loadNativeExpressAt(nativeExpressAdViewHolder.nativeExpressAdView);
                    break;
                default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                    ItemKetQuaThiLop itemKetQuaThiLop= (ItemKetQuaThiLop) mRecyclerViewItems.get(position);
                    ItemDiemThiLop itemDiemThiLop= (ItemDiemThiLop) holder;
                    itemDiemThiLop.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final int itemPosition = recyclerView.getChildLayoutPosition(view);
                            AlertDialog.Builder builder = new AlertDialog.Builder(diemThiLopFragment.getActivity());
                            final ItemKetQuaThiLop itemKetQuaThiLop= (ItemKetQuaThiLop) mRecyclerViewItems.get(itemPosition);
                            builder.setTitle(itemKetQuaThiLop.getTen());
                            final String [] list={" Xem thông tin","Xem điểm thi "+ diemThiTheoMon.getTenMon()};
                            builder.setItems(list, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i==0){
                                        Intent intent=diemThiLopFragment.getActivity().getIntent();
                                        intent.putExtra(MainActivity.MA_SV,itemKetQuaThiLop.getMsv());
                                        Log.e("ListActivity",itemKetQuaThiLop.getMsv());
                                        diemThiLopFragment.getActivity().setResult(Activity.RESULT_OK,intent);
                                        diemThiLopFragment.getActivity().finish();
                                        diemThiLopFragment.getActivity().overridePendingTransition(R.anim.left_end, R.anim.right_end);
                                    }else{
                                        diemThiLopFragment.showAlert("Điểm thi "+ diemThiTheoMon.getTenMon(), UIFromHTML.uiDiemThiListAC(itemKetQuaThiLop)
                                                ,diemThiTheoMon.getTenMon(),diemThiTheoMon.toString(),diemThiLopFragment.getActivity());
                                    }
                                }
                            });

                            builder.show();

                        }
                    });
                    itemDiemThiLop.tvTenSV.setText(itemKetQuaThiLop.getTen());
                    itemDiemThiLop.tvMaSV.setText(itemKetQuaThiLop.getMsv());
                    itemDiemThiLop.tvL1.setText(itemKetQuaThiLop.getdL1());
                    itemDiemThiLop.tvL2.setText(itemKetQuaThiLop.getdL2());
                    itemDiemThiLop.tvGC.setText(itemKetQuaThiLop.getGhiChu());
                    itemDiemThiLop.stt.setText(""+(ketQuaThiLops.indexOf(itemKetQuaThiLop)+1));
                    break;
            }

        }
        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }

    }

