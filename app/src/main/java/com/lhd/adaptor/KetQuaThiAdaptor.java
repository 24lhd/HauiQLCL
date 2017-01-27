package com.lhd.adaptor;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.fragment.KetQuaThiFragment;
import com.lhd.object.ItemDiemThiTheoMon;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;
import static com.lhd.fragment.KetQuaThiFragment.isDouble;

/**
 * Created by d on 28/12/2016.
 */


public class KetQuaThiAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  KetQuaThiFragment ketQuaThiFragment;
    private  ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMons;
    private  RecyclerView recyclerView;
    private List<Object> mRecyclerViewItems;
    public class ItemDiemThiMon extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tenMon;
        TextView dLan1;
        TextView dTKLan1;
        TextView dLan2;
        TextView dTKLan2;
        TextView dCuoiCung;
        TextView ngay1;
        TextView ngay2;
        TextView ghiChu;
        TextView stt;
        ImageView im_diem;
        TextView fdl2;
        TextView ftkl2;
        TextView fngay2;
        public ItemDiemThiMon(View itemView) {
            super(itemView);
            this.tenMon = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_tenlop);
            this.dLan1 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_l1);
            this.dTKLan1 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_tk1);
            this.dLan2 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_l2);
            this.dTKLan2 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_tk2);
            this.dCuoiCung = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_dc);
            this.ngay1 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_n1);
            this.ngay2 = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_n2);
            this.ghiChu = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_gc);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_diem_thi_lop_stt);
            this.im_diem = (ImageView) itemView.findViewById(R.id.im_diem);
            this.fdl2 = (TextView) itemView.findViewById(R.id.name_l2);
            this.ftkl2 = (TextView) itemView.findViewById(R.id.name_tkl2);
            this.fngay2 = (TextView) itemView.findViewById(R.id.name_ngay2);
        }
    }

    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdView nativeExpressAdView;
        public NativeExpressAdViewHolder(View view) {
            super(view);
            this.nativeExpressAdView= (NativeExpressAdView) view.findViewById(R.id.ads_navite_nho);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0) return MENU_ITEM_VIEW_TYPE;
        return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
    }
    public KetQuaThiAdaptor(List<Object> recyclerViewItems,
                            RecyclerView recyclerView, KetQuaThiFragment ketQuaThiFragment,
                            ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMons) {
        this.mRecyclerViewItems = recyclerViewItems;
        this.recyclerView=recyclerView;
        this.ketQuaThiFragment=ketQuaThiFragment;
        this.itemDiemThiTheoMons=itemDiemThiTheoMons;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ads_nho, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            // fall through
            default:
            case MENU_ITEM_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diem_thi_theo_mon, parent, false);
                ItemDiemThiMon holder = new ItemDiemThiMon(view);
                return holder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
//                NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
//                NativeExpressAdView adView = (NativeExpressAdView) mRecyclerViewItems.get(position);
//                adView= (NativeExpressAdView) ketQuaThiFragment.getActivity().getLayoutInflater().inflate(R.layout.native_express_ad_vua,null);
//                ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) holder;
                ketQuaThiFragment.loadNativeExpressAt(nativeExpressAdViewHolder.nativeExpressAdView);
//                float density = ketQuaThiFragment.getActivity().getResources().getDisplayMetrics().density;
//                AdSize adSize = null;
//                adSize = new AdSize((int) (recyclerView.getWidth()/density),MainActivity.NATIVE_EXPRESS_AD_HEIGHT);

//                nativeExpressAdViewHolder.nativeExpressAdView.setAdSize(adSize);
//                nativeExpressAdViewHolder.nativeExpressAdView.setAdUnitId(MainActivity.AD_UNIT_ID_KQHT);
//                ViewGroup adCardView = (ViewGroup) ketQuaThiFragment.getActivity().getLayoutInflater().inflate(R.layout.native_express_ad_vua,null);
//                try {
//                    if (adCardView.getChildCount() > 0) {
//                        adCardView.removeAllViews();
//                    }
//                }catch (IllegalStateException e){}
//                // Add the Native Express ad to the native express ad view.
//                adCardView.addView(adView);
                break;
            default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                ItemDiemThiMon itemDiemThiMon= (ItemDiemThiMon) holder;
                itemDiemThiMon.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ketQuaThiFragment.showDiglog(view);
                    }
                });
                ItemDiemThiTheoMon itemDiemThiTheoMon = (ItemDiemThiTheoMon) mRecyclerViewItems.get(position);
                itemDiemThiMon.tenMon.setText(itemDiemThiTheoMon.getTenMon());
                itemDiemThiMon.dLan1.setText(itemDiemThiTheoMon.getdLan1());
                itemDiemThiMon.dLan2.setText(itemDiemThiTheoMon.getdLan2());
                itemDiemThiMon.dCuoiCung.setText(itemDiemThiTheoMon.getdCuoiCung());
                String tk1= itemDiemThiTheoMon.getdTKLan1().trim();
                String tk2= itemDiemThiTheoMon.getdTKLan2().trim();
                itemDiemThiMon.dTKLan2.setText(tk2);
                itemDiemThiMon.dTKLan1.setText(tk1);
                itemDiemThiMon.ngay1.setText(itemDiemThiTheoMon.getNgay1());
                itemDiemThiMon.ngay2.setText(itemDiemThiTheoMon.getNgay2());
                itemDiemThiMon.ghiChu.setText(itemDiemThiTheoMon.getGhiChu());
//                for (int i = 0; i <ketQuaThiFragment.getMainActivity().getSinhVien().getMaSV().split("").length ; i++) {
//                    Log.e("faker a",""+ketQuaThiFragment.getMainActivity().getSinhVien().getMaSV().split("")[i]);
//                }
                String [] str=ketQuaThiFragment.getMainActivity().getSinhVien().getMaSV().trim().split("");
                String khoaSV=str[1]+str[2];
//                khoaSV=khoaSV+ketQuaThiFragment.getMainActivity().getSinhVien().getMaSV().trim().split("")[1];
//                Log.e("faker xxx",str[1]+str[2]);
//                Log.e("faker 2",""+khoaSV);
                int khoa=Integer.parseInt(khoaSV);
//                Log.e("faker 3",""+ketQuaThiFragment.getMainActivity().getSinhVien().getMaSV().trim().split("").length);

//                Log.e("faker",""+khoa);
                if (khoa>9){
                    itemDiemThiMon.dTKLan2.setVisibility(View.GONE);
                    itemDiemThiMon.ngay2.setVisibility(View.GONE);
                    itemDiemThiMon.dLan2.setVisibility(View.GONE);
                    itemDiemThiMon.fdl2.setVisibility(View.GONE);
                    itemDiemThiMon.ftkl2.setVisibility(View.GONE);
                    itemDiemThiMon.fngay2.setVisibility(View.GONE);
                }
                itemDiemThiMon.stt.setText(""+(itemDiemThiTheoMons.indexOf(itemDiemThiTheoMon)+1));
                index=index+1;
                String dc= itemDiemThiTheoMon.getdCuoiCung().split(" ")[0];
                dc=dc.trim();
                double th = 0;
                double n = 0;
                if (itemDiemThiTheoMon.getNgay1().split("").length>3){
                    n=Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[2]);
                    th = Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[1]);
                }
                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_null).into(itemDiemThiMon.im_diem);
//                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                        .getResources().getDrawable(R.drawable.ic_null));
                if (dc.equals("(I)")){
                    itemDiemThiMon.dCuoiCung.setText("*");
                    Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_null).into(itemDiemThiMon.im_diem);
//                    itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                            .getResources().getDrawable(R.drawable.ic_null));
                    itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#42A5F5"));
                }else{
                    itemDiemThiMon.dCuoiCung.setText(dc);
                    if (isDouble(dc)){
                        double d= Double.parseDouble(dc);
                        if (d>=8.5){
                            itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#FF0000"));
                            itemDiemThiMon.dCuoiCung.setText("A");
                            Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_a).into(itemDiemThiMon.im_diem);
//                            itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                    .getResources().getDrawable(R.drawable.ic_a));
                        }else if(d>=7.7&&n>=2015){
                            if (n==2015&&th<=9){
                                itemDiemThiMon.dCuoiCung.setText("B");
                                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_b).into(itemDiemThiMon.im_diem);
//                                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                        .getResources().getDrawable(R.drawable.ic_b));
                                itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#FFD600"));
                            }else{
                                itemDiemThiMon.dCuoiCung.setText("B+");
                                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_bb).into(itemDiemThiMon.im_diem);
//                                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                        .getResources().getDrawable(R.drawable.ic_bb));
                                itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#FF8C00"));
                            }
                        }else if(d>=7.0){
                            itemDiemThiMon.dCuoiCung.setText("B");
                            Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_b).into(itemDiemThiMon.im_diem);
//                            itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                    .getResources().getDrawable(R.drawable.ic_b));
                            itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#FFD600"));
                        }else if(d>=6.2&&n>=2015){
                            if (n==2015&&th<=9){
                                itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#CCFF90"));
                                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_c).into(itemDiemThiMon.im_diem);
//                                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                        .getResources().getDrawable(R.drawable.ic_c));
                                itemDiemThiMon.dCuoiCung.setText("C");
                            }else{
                                itemDiemThiMon.dCuoiCung.setText("C+");
                                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_cc).into(itemDiemThiMon.im_diem);
//                                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                        .getResources().getDrawable(R.drawable.ic_cc));
                                itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#64DD17"));
                            }
                        }else if(d>=5.5){
                            itemDiemThiMon.dCuoiCung.setText("C");
                            Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_c).into(itemDiemThiMon.im_diem);
//                            itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                    .getResources().getDrawable(R.drawable.ic_c));
                            itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#CCFF90"));
                        }else if(d>=4.7&&n>=2015){

                            if (n==2015&&th<=9){
                                itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#84FFFF"));
                                itemDiemThiMon.dCuoiCung.setText("D");
                                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_d).into(itemDiemThiMon.im_diem);
//                                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                        .getResources().getDrawable(R.drawable.ic_d));
                            }else{
                                itemDiemThiMon.dCuoiCung.setText("D+");
                                Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_dd).into(itemDiemThiMon.im_diem);
//                                itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                        .getResources().getDrawable(R.drawable.ic_dd));
                                itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#00B8D4"));
                            }
                        }else if(d>=4.0){
                            itemDiemThiMon.dCuoiCung.setText("D");
//                            itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                    .getResources().getDrawable(R.drawable.ic_d));
                            Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_d).into(itemDiemThiMon.im_diem);
                            itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#84FFFF"));
                        }else{
                            itemDiemThiMon.dCuoiCung.setText("F");
//                            itemDiemThiMon.im_diem.setImageDrawable(ketQuaThiFragment.getActivity()
//                                    .getResources().getDrawable(R.drawable.ic_f));
                            Glide.with(ketQuaThiFragment.getActivity()).load(R.drawable.ic_f).into(itemDiemThiMon.im_diem);
                            itemDiemThiMon.dCuoiCung.setTextColor(Color.parseColor("#D500F9"));
                        }
                    }
                }
                break;



        }
    }
    private int index;
    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }
}