package com.lhd.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.item.ItemDiemThiTheoMon;
import com.lhd.task.ParserKetQuaThiTheoMon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Double.parseDouble;

/**
 * Created by Faker on 8/25/2016.
 */
public class KetQuaThiFragment extends FrameFragment {
    private  ArrayList<ItemDiemThiTheoMon> itemDiemThiTheoMons;
    private AlertDialog.Builder builder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_frame_fragment,container,false);
        initView(view);
        return view;
    }
    public void refesh() {
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (MainActivity.isOnline(getActivity())){
                    sqLiteManager.deleteDThiMon(maSV);
                    startParser();
                }else{
                    pullRefreshLayout.setRefreshing(false);
                }

            }
        });
    }
    public void checkDatabase() {
        showProgress();
        itemDiemThiTheoMons =sqLiteManager.getAllDThiMon(maSV);
        if (!itemDiemThiTheoMons.isEmpty()){
            showRecircleView();
            setRecyclerView();
        }else{
            loadData();
        }


    }
    public void startParser() {
        ParserKetQuaThiTheoMon parserKetQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
        parserKetQuaThiTheoMon.execute(maSV);


    }
    public void setRecyclerView() {
        Collections.sort(itemDiemThiTheoMons, new Comparator<ItemDiemThiTheoMon>() {
            @Override
            public int compare(ItemDiemThiTheoMon o1, ItemDiemThiTheoMon o2) {

                String[] str1=o1.getNgay1().split("/");
                String[] str2=o2.getNgay1().split("/");
                if (str1.length==1){
                    return -1;
                }
                if (str2.length==1){
                    return 1;
                }
                return str1[2].compareTo(str2[2]);
            }
        });
        AdapterDiemThiMon adapterDiemThiMon=new AdapterDiemThiMon(itemDiemThiTheoMons);
        recyclerView.removeAllViews();
        recyclerView.setAdapter(adapterDiemThiMon);


    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try{
                        itemDiemThiTheoMons = (ArrayList<ItemDiemThiTheoMon>) msg.obj;
                        if (!itemDiemThiTheoMons.isEmpty()){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                            if (sqLiteManager.getAllDThiMon(maSV).size()< itemDiemThiTheoMons.size()){
                                for (ItemDiemThiTheoMon diemHocTapTheoLop: itemDiemThiTheoMons){
                                    sqLiteManager.insertDThiMon(diemHocTapTheoLop,maSV);
                                }
                            }
                            showRecircleView();
                            pullRefreshLayout.setRefreshing(false);
                            setRecyclerView();
                        }
            }catch (NullPointerException e){
                startParser();
            }
        }
    };
    class ItemDiemThiMon extends RecyclerView.ViewHolder{ // tao mot đói tượng
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
        }
    }
    private class AdapterDiemThiMon extends RecyclerView.Adapter<ItemDiemThiMon> implements RecyclerView.OnClickListener {
        private  ArrayList<ItemDiemThiTheoMon> data;
        public AdapterDiemThiMon( ArrayList<ItemDiemThiTheoMon> data) {
            this.data = data;
        }
        @Override
        public ItemDiemThiMon onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_diem_thi_theo_mon, parent, false);
            view.setOnClickListener(this);
            ItemDiemThiMon holder = new ItemDiemThiMon(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(ItemDiemThiMon holder, int position) {
            ItemDiemThiTheoMon itemDiemThiTheoMon =data.get(position);
            holder.tenMon.setText(itemDiemThiTheoMon.getTenMon());
            holder.dLan1.setText(itemDiemThiTheoMon.getdLan1());
            holder.dLan2.setText(itemDiemThiTheoMon.getdLan2());
            holder.dCuoiCung.setText(itemDiemThiTheoMon.getdCuoiCung());
            String tk1= itemDiemThiTheoMon.getdTKLan1().trim();
            String tk2= itemDiemThiTheoMon.getdTKLan2().trim();
            holder.dTKLan2.setText(tk2);
            holder.dTKLan1.setText(tk1);
            holder.ngay1.setText(itemDiemThiTheoMon.getNgay1());
            holder.ngay2.setText(itemDiemThiTheoMon.getNgay2());
            holder.ghiChu.setText(itemDiemThiTheoMon.getGhiChu());
            holder.stt.setText(""+(position+1));
            String dc= itemDiemThiTheoMon.getdCuoiCung().split(" ")[0];
            dc=dc.trim();
            double th = 0;
            double n = 0;
            if (itemDiemThiTheoMon.getNgay1().split("").length>3){
                n=Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[2]);
                th = Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[1]);
            }
            if (dc.equals("(I)")){
                holder.dCuoiCung.setText("*");
                holder.dCuoiCung.setTextColor(Color.parseColor("#42A5F5"));
            }else{
                holder.dCuoiCung.setText(dc);
                if (isDouble(dc)){
                    double d= Double.parseDouble(dc);
                    if (d>=8.5){
                        holder.dCuoiCung.setTextColor(Color.parseColor("#FF0000"));
                        holder.dCuoiCung.setText("A");
                    }else if(d>=7.7&&n>=2015){
                        if (n==2015&&th<=9){
                            holder.dCuoiCung.setText("B");
                            holder.dCuoiCung.setTextColor(Color.parseColor("#FFD600"));
                        }else{
                            holder.dCuoiCung.setText("B+");
                            holder.dCuoiCung.setTextColor(Color.parseColor("#FF8C00"));
                        }
                    }else if(d>=7.0){
                        holder.dCuoiCung.setText("B");
                        holder.dCuoiCung.setTextColor(Color.parseColor("#FFD600"));
                    }else if(d>=6.2&&n>=2015){
                        if (n==2015&&th<=9){
                            holder.dCuoiCung.setTextColor(Color.parseColor("#CCFF90"));
                            holder.dCuoiCung.setText("C");
                        }else{
                            holder.dCuoiCung.setText("C+");
                            holder.dCuoiCung.setTextColor(Color.parseColor("#64DD17"));
                        }
                    }else if(d>=5.5){
                        holder.dCuoiCung.setText("C");
                        holder.dCuoiCung.setTextColor(Color.parseColor("#CCFF90"));
                    }else if(d>=4.7&&n>=2015){

                        if (n==2015&&th<=9){
                            holder.dCuoiCung.setTextColor(Color.parseColor("#84FFFF"));
                            holder.dCuoiCung.setText("D");
                        }else{
                            holder.dCuoiCung.setText("D+");
                            holder.dCuoiCung.setTextColor(Color.parseColor("#00B8D4"));
                        }
                    }else if(d>=4.0){
                        holder.dCuoiCung.setText("D");
                        holder.dCuoiCung.setTextColor(Color.parseColor("#84FFFF"));
                    }else{
                        holder.dCuoiCung.setText("F");
                        holder.dCuoiCung.setTextColor(Color.parseColor("#D500F9"));
                    }
                }
            }

        }
        @Override
        public int getItemCount() {
            return data.size();
        }
        @Override
        public void onClick(View view) {
            final int itemPosition = recyclerView.getChildLayoutPosition(view);
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(data.get(itemPosition).getTenMon());
            final String [] list={" Xem kết quả thi lớp","Xem điểm "+data.get(itemPosition).getTenMon()};
            builder.setItems(list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i==0){
                        Intent intent=new Intent(getActivity(),ListActivity.class);
                        intent.putExtra(KEY_OBJECT, (Serializable) data.get(itemPosition));
                        Bundle bundle=new Bundle();
                        bundle.putInt(KEY_ACTIVITY,3);
                        intent.putExtra("action",bundle);
                        getActivity().startActivityForResult(intent,1);
                        getActivity().overridePendingTransition(R.anim.left_end, R.anim.right_end);
                    }else{
                        builder = new AlertDialog.Builder(getActivity());builder.create();
                        String str="<!DOCTYPE html>" +
                                "<html>" +
                                "<head>" +
                                "<title></title>" +
                                "<style type=\"text/css\" media=\"screen\">" +
                                "*{" +
                                "margin: auto;" +
                                "text-align: center;" +
                                "background: white;" +
                                "}" +
                                "h2{" +
                                "color: #FF4081;" +
                                "}" +
                                "p{" +
                                "color: #42A5F5;" +
                                "}" +
                                "table{" +
                                "width: 100%;" +
                                "}"+
                                "th {" +
                                "background-color: #42A5F5;" +
                                "color: white;" +
                                "padding: 15px;" +
                                "}" +
                                "td{" +
                                "padding: 15px;" +
                                "background-color: #f2f2f2;" +
                                "font-weight:bold;" +
                                "color: red;" +
                                "}" +
                                "</style>" +
                                "</head>" +
                                "<body>" +
                                "<h2>" +
                                data.get(itemPosition).getTenMon() +
                                "</h2>" +
                                "<p>" +
                                data.get(itemPosition).getNgay1() +
                                "</p>" +
                                "<small>" +
                                data.get(itemPosition).getGhiChu()+
                                "</small>" +
                                "<table>" +
                                "<tr>" +
                                "" +
                                "<th>Điểm thi</th>" +
                                "<th>Tổng kết</th>" +
                                "<th>Điểm chữ</th>" +
                                "" +
                                "</tr>" +
                                "<tr>" +
                                "<td>" +
                                data.get(itemPosition).getdLan1() +
                                "</td>" +
                                "<td>" +
                                data.get(itemPosition).getdTKLan1()+
                                "</td>" +
                                "<td>" +
                                charPoint(data.get(itemPosition))+
                                "</td>" +
                                "</tr>" +
                                "</table>" +
                                "<em>Copyright  © Gà công nghiệp</em>"+
                                "</body>" +
                                "</html>";
                        builder.setTitle("Kết quả thi của ");
                        WebView webView=new WebView(getActivity());
                        webView.setBackgroundColor(getResources().getColor(R.color.bg_text));
                        webView.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
                        builder.setView(webView);
                        builder.setNeutralButton("SMS",null);
                        builder.setPositiveButton("IMG",null);
                        AlertDialog mAlertDialog = builder.create();
                        mAlertDialog.show();
                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.sreenShort(view,getActivity());
                            }
                        });
                        Button c = mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                        c.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.shareText(getActivity(),"Kết quả thi", data.get(itemPosition).toString());
                            }
                        });
                    }
                }
            });

            builder.show();

        }
    }

    public static String charPoint(ItemDiemThiTheoMon itemDiemThiTheoMon) {
        if (itemDiemThiTheoMon.getdCuoiCung().length()<=1) return "*";
        if (itemDiemThiTheoMon.getdCuoiCung().contains("*")) return "*";
        double n;
        try {
            n=Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[2]);
        }catch (Exception e){
            n=0;
        }
        double th;
        try {
            th= Double.parseDouble(itemDiemThiTheoMon.getNgay1().split("/")[1]);
        }catch (Exception e){
            th=0;
        }
        double d;
        try {
         d= Double.parseDouble(itemDiemThiTheoMon.getdCuoiCung().split(" ")[0]);
        }catch (NumberFormatException e){
            return "*";
        }

        if (d>=8.5){
            return "A";
        }else if(d>=7.7&&n>=2015){
            if (n==2015&&th<=9){
                return "B";
            }else{
                return "B+";
            }
        }else if(d>=7.0){
            return "B";
        }else if(d>=6.2&&n>=2015){
            if (n==2015&&th<=9){
                return "C";
            }else{
                return "C+";
            }
        }else if(d>=5.5){
            return "C";
        }else if(d>=4.7&&n>=2015){
            if (n==2015&&th<=9){
                return "D";
            }else{
                return "D+";
            }
        }else if(d>=4.0){
            return "D";
        }else{
            return "F";
        }
    }

    public static boolean  isDouble(String str) {
        try {
            parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
