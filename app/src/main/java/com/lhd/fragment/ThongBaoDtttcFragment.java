package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.object.ItemNotiDTTC;
import com.lhd.task.ParserLinkFileNoti;
import com.lhd.task.ParserNotiDTTC;

import java.util.ArrayList;

/**
 * Created by D on 12/15/2016.
 */

public class ThongBaoDtttcFragment extends FrameFragment {
    private ArrayList<ItemNotiDTTC> itemNotiDTTCs;
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(mainActivity)){
                    sqLiteManager.deleteItemNotiDTTC();
                    startParser();
                    pullRefreshLayout.setRefreshing(false);
                }else{
                    pullRefreshLayout.setRefreshing(false);
                }

            }
        });
    }
    public void checkDatabase() {
        showProgress();
        itemNotiDTTCs=sqLiteManager.getNotiDTTC();
        if (!itemNotiDTTCs.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else{
            loadData();
        }


    }
    public void startParser() {
        ParserNotiDTTC parserNotiDTTC=new ParserNotiDTTC(handler);
        parserNotiDTTC.execute();

    }
    public void setRecyclerView() {
        AdapterNoti adapterNoti=new AdapterNoti(itemNotiDTTCs);
        recyclerView.removeAllViews();
        recyclerView.setAdapter(adapterNoti);

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                itemNotiDTTCs= (ArrayList<ItemNotiDTTC>) msg.obj;
                setRecyclerView();
                showRecircleView();
                sqLiteManager.deleteItemNotiDTTC();
                for (ItemNotiDTTC itemNotiDTTC:itemNotiDTTCs) {
                    sqLiteManager.insertItemNotiDTTC(itemNotiDTTC);
                }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
    class ItemNoti extends  RecyclerView.ViewHolder { // tao mot đói tượng
        TextView text;
        TextView stt;
        public ItemNoti(View itemView) {
            super(itemView);

            this.text = (TextView) itemView.findViewById(R.id.tv_noti);
            this.stt = (TextView) itemView.findViewById(R.id.stt_noti);
        }
    }
    class AdapterNoti extends RecyclerView.Adapter<ItemNoti> implements RecyclerView.OnClickListener {
        private ArrayList<ItemNotiDTTC> data;
        public AdapterNoti( ArrayList<ItemNotiDTTC> data) {
            this.data = data;
        }
        @Override
        public ItemNoti onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_noti_qlcl, parent, false);
            view.setOnClickListener(this);
            ItemNoti holder = new ItemNoti(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ItemNoti holder, int position) {
            holder.text.setText(data.get(position).getTitle());
            holder.stt.setText(""+position);

        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            if (MainActivity.isOnline(getActivity())){
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                ParserLinkFileNoti parserNotiDTTC=new ParserLinkFileNoti(getActivity());
                parserNotiDTTC.execute(data.get(itemPosition).getLink());
            }else{
                Toast.makeText(mainActivity, "Không có kêt nối nternet!", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
