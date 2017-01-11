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
import com.lhd.fragment.LichThiFragment;
import com.lhd.object.LichThi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;

/**
 * Created by d on 29/12/2016.
 */

public class LichThiAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  ArrayList<LichThi> lichThis;
    private LichThiFragment lichThiFragment;
    private  RecyclerView recyclerView;
    private List<Object> mRecyclerViewItems;
    public class ItemLichThi extends RecyclerView.ViewHolder{ // tao mot đói tượng
        TextView tenMon;
        TextView sbd;
        TextView thuThi;
        TextView phong;
        TextView ngayThi;
        TextView caThi;
        TextView lanThi;
        TextView stt;
        public ItemLichThi(View itemView) {
            super(itemView);
            this.tenMon = (TextView) itemView.findViewById(R.id.id_item_lich_thi_tenlop);
            this.sbd = (TextView) itemView.findViewById(R.id.id_item_lich_thi_sbd);
            this.thuThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_thu);
            this.phong = (TextView) itemView.findViewById(R.id.id_item_lich_thi_phong);
            this.ngayThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_ngay);
            this.caThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_gio);
            this.lanThi = (TextView) itemView.findViewById(R.id.id_item_lich_thi_lan);
            this.stt = (TextView) itemView.findViewById(R.id.id_item_lich_thi_stt);

        }
    }
    public LichThiAdaptor(List<Object> mRecyclerViewItems, RecyclerView recyclerView,
                          LichThiFragment lichThiFragment, ArrayList<LichThi> lichThis) {
        this.recyclerView = recyclerView;
        this.mRecyclerViewItems = mRecyclerViewItems;
        this.lichThiFragment = lichThiFragment;
        this.lichThis = lichThis;
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
                View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_thi, parent, false);
                return new ItemLichThi(menuItemLayoutView);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0) return MENU_ITEM_VIEW_TYPE;
        return (position % ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
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
                ItemLichThi itemLichThi=(ItemLichThi) holder;
                final LichThi lichThi= (LichThi) mRecyclerViewItems.get(position);
                itemLichThi.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Date today=new Date(System.currentTimeMillis());
                        SimpleDateFormat timeFormat= new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
                        String s=timeFormat.format(today.getTime());
                        s=s.split(" ")[1];
                        String ngay1=s.split("/")[0];
                        String thang1=s.split("/")[1];
                        String nam1=s.split("/")[2];
                        String ngay2=lichThi.getNgay().split("/")[0];
                        String thang2=lichThi.getNgay().split("/")[1];
                        String nam2=lichThi.getNgay().split("/")[2];
                        final String toi;
                        if (thang1.equals(thang2)&&nam1.equals(nam2)){
                            if ((Double.parseDouble(ngay2)-Double.parseDouble(ngay1))<0)
                                toi="Đã thi";
                            else{
                                int i=(int)(Double.parseDouble(ngay2)-Double.parseDouble(ngay1));
                                if (i==0) toi="Chúc bạn hôm nay thi tốt nhé ^.^ !!!";
                                else toi="Còn lại "+i+ " ngày để ôn :)";
                            }
                        }else if (Double.parseDouble(thang1)<Double.parseDouble(thang2) &&nam1.equals(nam2)){
                            toi="Chuẩn bị thi :(";
                        }else{
                            toi="Đã thi !!!";
                        }
                        lichThiFragment.showDialog(lichThi,toi);

                    }
                });
                itemLichThi.tenMon.setText(lichThi.getMon()+"");
                itemLichThi.sbd.setText(lichThi.getSbd()+"");
                itemLichThi.thuThi.setText(lichThi.getThu()+"");
                itemLichThi.phong.setText(lichThi.getPhong()+"");
                itemLichThi.ngayThi.setText(lichThi.getNgay()+"");
                itemLichThi.caThi.setText(lichThi.getGio()+"");
                itemLichThi.lanThi.setText(lichThi.getLanthi()+"");
                itemLichThi.stt.setText(""+(lichThis.indexOf(lichThi)+1));
                break;
        }

    }
    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }



}

