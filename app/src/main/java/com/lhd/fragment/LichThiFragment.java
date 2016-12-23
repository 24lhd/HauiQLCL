package com.lhd.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.MainActivity;
import com.lhd.object.LichThi;
import com.lhd.object.SinhVien;
import com.lhd.object.UIFromHTML;
import com.lhd.task.ParserLichThiTheoMon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Faker on 8/25/2016.
 */
public class LichThiFragment extends FrameFragment {
    private  ArrayList<LichThi> lichThis;
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(getActivity())){
                    sqLiteManager.deleteDLThi(sv.getMaSV());
                    startParser();
                }else{
                    pullRefreshLayout.setRefreshing(false);
                }

            }
        });
    }
    public void checkDatabase() {
        showProgress();
        lichThis=sqLiteManager.getAllLThi(sv.getMaSV());
        if (!lichThis.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else{
            loadData();
        }
    }
    public void startParser() {
        ParserLichThiTheoMon parserKetQuaHocTap=new ParserLichThiTheoMon(handler);
        parserKetQuaHocTap.execute(sv.getMaSV());
    }
    public void setRecyclerView() {
        Collections.reverse(lichThis);
        AdapterLichThi  adapterLichThi=new AdapterLichThi(lichThis);
        recyclerView.setAdapter(adapterLichThi);
    }
    class ItemLichThi extends RecyclerView.ViewHolder{ // tao mot đói tượng
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
    private class AdapterLichThi extends RecyclerView.Adapter<ItemLichThi> implements RecyclerView.OnClickListener {
        private  ArrayList<LichThi> data;
        public AdapterLichThi( ArrayList<LichThi> data) {
            this.data = data;
        }
        @Override
        public ItemLichThi onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_lich_thi, parent, false);
            view.setOnClickListener(this);
            ItemLichThi holder = new ItemLichThi(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ItemLichThi holder, int position) {
            LichThi lichThi=data.get(position);
            holder.tenMon.setText(lichThi.getMon()+"");
            holder.sbd.setText(lichThi.getSbd()+"");
            holder.thuThi.setText(lichThi.getThu()+"");
            holder.phong.setText(lichThi.getPhong()+"");
            holder.ngayThi.setText(lichThi.getNgay()+"");
            holder.caThi.setText(lichThi.getGio()+"");
            holder.lanThi.setText(lichThi.getLanthi()+"");
            holder.stt.setText((position+1)+"");
        }
        @Override
        public int getItemCount() {
            return data.size();
        }
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
            String ngay2=data.get(itemPosition).getNgay().split("/")[0];
            String thang2=data.get(itemPosition).getNgay().split("/")[1];
            String nam2=data.get(itemPosition).getNgay().split("/")[2];
            String toi;
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
            }else
                toi="Đã thi !!!";
                showAlert(data.get(itemPosition).getMon(),UIFromHTML.uiLichThi(data.get(itemPosition),toi),
                    "Lịch thi môn "+data.get(itemPosition).getMon(), data.get(itemPosition).toString(),getActivity());
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.arg1){
                    case 5:
                        lichThis= (ArrayList<LichThi>) msg.obj;
                        if (!lichThis.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            if (sqLiteManager.getAllLThi(sv.getMaSV()).size()<lichThis.size()){
                                for (LichThi lichThiLop:lichThis){
                                    sqLiteManager.insertlthi(lichThiLop,sv.getMaSV());
                                }
                            }
                            pullRefreshLayout.setRefreshing(false);
                            showRecircleView();
                            setRecyclerView();
                        }else{
                            showTextNull();
                            tVnull.setText("Không có lịch thi theo lớp...");
                        }
                        break;
                    case 6:
                        sv= (SinhVien) msg.obj;
                        if (sv!=null){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            sqLiteManager.insertSV(sv);
                        }
                        break;
                }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };

}
