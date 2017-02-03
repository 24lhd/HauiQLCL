package com.lhd.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.fragment.ThongBaoDtttcFragment;
import com.lhd.object.ItemNotiDTTC;
import com.lhd.task.ParserLinkFileNoti;

import java.util.ArrayList;
import java.util.List;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.MENU_ITEM_VIEW_TYPE;
import static com.lhd.activity.MainActivity.NATIVE_EXPRESS_AD_VIEW_TYPE;
import static com.lhd.activity.MainActivity.isOnline;

/**
 * Created by d on 29/12/2016.
 */

public class ThongBaoDTTCAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder>   {
    private  ArrayList<ItemNotiDTTC> itemNotiDTTCs;
    private  ThongBaoDtttcFragment thongBaoDtttcFragment;
    private  RecyclerView recyclerView;
    private List<Object> mRecyclerViewItems;

    class ItemNoti extends  RecyclerView.ViewHolder { // tao mot đói tượng
        TextView text;
        TextView stt;
        public ItemNoti(View itemView) {
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.tv_noti);
            this.stt = (TextView) itemView.findViewById(R.id.stt_noti);
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
        return (position % ITEMS_PER_AD == 0&&isOnline(thongBaoDtttcFragment.getActivity())) ? NATIVE_EXPRESS_AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
    }
    public ThongBaoDTTCAdaptor(List<Object> mRecyclerViewItems, RecyclerView recyclerView,
                               ThongBaoDtttcFragment thongBaoDtttcFragment, ArrayList<ItemNotiDTTC> itemNotiDTTCs) {
        this.recyclerView = recyclerView;
        this.mRecyclerViewItems = mRecyclerViewItems;
        this.thongBaoDtttcFragment = thongBaoDtttcFragment;
        this.itemNotiDTTCs = itemNotiDTTCs;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_express_ad_to, parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            default:
            case MENU_ITEM_VIEW_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noti_qlcl, parent, false);
                ItemNoti holder = new ItemNoti(view);
                return holder;
        }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                NativeExpressAdViewHolder nativeExpressAdViewHolder= (NativeExpressAdViewHolder) holder;
                thongBaoDtttcFragment.loadNativeExpressAt(nativeExpressAdViewHolder.nativeExpressAdView);
                break;
            default: case MainActivity.MENU_ITEM_VIEW_TYPE:
                ItemNoti itemNoti= (ItemNoti) holder;
                final ItemNotiDTTC itemNotiDTTC= (ItemNotiDTTC) mRecyclerViewItems.get(position);
                itemNoti.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MainActivity.isOnline(thongBaoDtttcFragment.getActivity())){
                            int itemPosition = recyclerView.getChildLayoutPosition(view);
                            ParserLinkFileNoti parserNotiDTTC=new ParserLinkFileNoti(thongBaoDtttcFragment.getActivity());
                            parserNotiDTTC.execute(itemNotiDTTC.getLink());
                        }else{
                            Toast.makeText(thongBaoDtttcFragment.getActivity(), "Không có kêt nối nternet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                itemNoti.text.setText(itemNotiDTTC.getTitle());
                itemNoti.stt.setText(""+(itemNotiDTTCs.indexOf(itemNotiDTTC)+1));
                break;
        }

    }
    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }
}