package com.lhd.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.ken.hauiclass.R;
import com.lhd.activity.InputActivity;
import com.lhd.activity.MainActivity;
import com.lhd.chart.RadarMarkerView;
import com.lhd.database.SQLiteManager;
import com.lhd.item.DiemThiTheoMon;
import com.lhd.item.SinhVien;
import com.lhd.task.ParserKetQuaThiTheoMon;

import java.util.ArrayList;

public class RadarChartFragment extends Fragment {
    private RadarChart mChart;
    private int dd;
    private int d;
    private int f;
    private int c;
    private int cc;
    private int a;
    private int bb;
    private int b;
    private SQLiteManager sqLiteManager;
    private View view;
    private SinhVien sinhVien;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.activity_radarchart_noseekbar,container,false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        sqLiteManager=new SQLiteManager(getActivity());
        getDiemThiTheoMons(handler,getArguments().getString(MainActivity.MA_SV));
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            diemThiTheoMons= (ArrayList<DiemThiTheoMon>) msg.obj;
            if (diemThiTheoMons==null){
                MainActivity.showErr(getActivity());

            }else{
                for (DiemThiTheoMon diemThiTheoMon:diemThiTheoMons) {
                    if (!KetQuaThiFragment.isDouble(diemThiTheoMon.getdCuoiCung().split(" ")[0])){
                        diemThiTheoMon.setdCuoiCung("*");
                    }
                }
                for (int i = 0; i <diemThiTheoMons.size()-1 ; i++){
                    for (int u = i+1; u <diemThiTheoMons.size() ; u++) {
                        if (diemThiTheoMons.get(i).getTenMon().contains(diemThiTheoMons.get(u).getTenMon())){
                            if (diemThiTheoMons.get(i).getdCuoiCung().contains("*")){
                                continue;
                            }
                            if (diemThiTheoMons.get(u).getdCuoiCung().contains("*")){
                                continue;
                            }
                            if (Double.parseDouble(diemThiTheoMons.get(i).getdCuoiCung().split(" ")[0])>Double.parseDouble(diemThiTheoMons.get(u).getdCuoiCung().split(" ")[0])){
                                diemThiTheoMons.get(u).setdCuoiCung("*");
                            }else{
                                diemThiTheoMons.get(i).setdCuoiCung("*");
                                continue;
                            }
                        }
                    }
                }
                a=0; bb=0; b=0; cc=0; c=0; dd=0; d=0; f=0;
                int size=0;
                for (DiemThiTheoMon diemThiTheoMon:diemThiTheoMons) {
                    if (diemThiTheoMon.getdCuoiCung().length()<=1) continue;
                    if (diemThiTheoMon.getdCuoiCung().contains("*")) continue;
                    String dc=diemThiTheoMon.getdCuoiCung().split(" ")[0];
                    double n;
                    if (KetQuaThiFragment.isDouble(dc)){
                        try {
                            n=Double.parseDouble(diemThiTheoMon.getNgay1().split("/")[2]);
                        }catch (Exception e){
                            n=0;
                        }
                        double th;
                        try {
                            th= Double.parseDouble(diemThiTheoMon.getNgay1().split("/")[1]);
                        }catch (Exception e){
                            th=0;
                        }
                        size++;
                        switch(KetQuaThiFragment.charPoint(dc,n,th)){
                            case "A":
                                a++;
                                break;
                            case "B+":
                                bb++;
                                break;
                            case "B":
                                b++;
                                break;
                            case "C+":
                                cc++;
                                break;
                            case "C":
                                c++;
                                break;
                            case "D+":
                                dd++;
                                break;
                            case "D":
                                d++;
                                break;
                            case "F":
                                f++;
                                break;
                        }
                    }

                }
                mChart = (RadarChart) view.findViewById(R.id.chart1);
                mChart.setBackgroundColor(Color.WHITE);
                mChart.getDescription().setEnabled(false);
                mChart.setWebLineWidth(1f);
                mChart.setWebColor(getResources().getColor(R.color.colorPrimary));
                mChart.setWebLineWidthInner(1f);
                mChart.setWebColorInner(getResources().getColor(R.color.colorPrimary));
                mChart.setWebAlpha(100);
                MarkerView mv = new RadarMarkerView(getActivity(), R.layout.radar_markerview);
                mv.setChartView(mChart); // For bounds control
                mChart.setMarker(mv); // Set the marker to the chart
                mChart.animateXY(1400, 1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);
                XAxis xAxis = mChart.getXAxis();
                xAxis.setTextSize(20f);
                xAxis.setYOffset(0f);
                xAxis.setXOffset(0f);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    private String[] mActivities = new String[]{"A", "B+", "B", "C+", "C", "D+", "D", "F"};
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return mActivities[(int) value % mActivities.length];
                    }
                    @Override
                    public int getDecimalDigits() {
                        return 0;
                    }
                });
                xAxis.setTextColor(getResources().getColor(R.color.colorAccent));
                YAxis yAxis = mChart.getYAxis();
                yAxis.setLabelCount(4, true);
                yAxis.setTextSize(25f);
                yAxis.setAxisMinimum(0f);
                yAxis.setAxisMaximum(150f);
                yAxis.setDrawLabels(false);
                entries1=new ArrayList<>();
                Log.e("faker",size+"");
                float d1,d2,d3,d4,d5,d6,d7,d8;
                Log.e("faker",""+a+" "+bb+" "+b+" "+cc+" "+c+" "+dd+" "+d+" "+f+" ");
                d2= (float) (bb*100.0/size)+50;
                d3= (float) (b*100.0/size)+50;
                d4= (float) (cc*100.0/size)+50;
                d5= (float) (c*100.0/size)+50;
                d6= (float) (dd*100.0/size)+50;
                d7= (float) (d*100.0/size)+50;
                d8= (float) (f*100.0/size)+50;
                d1=  500-(d2+d3+d4+d5+d6+d7+d8);
                Log.e("fakerd",""+d2+" "+d3+" "+d4+" "+d5+" "+d6+" "+d7+" "+d8+" "+d1+" ");
                Log.e("faker",""+d2+" "+d3+" "+d4+" "+d5+" "+d6+" "+d7+" "+d8+" "+d1+" ");
                entries1.add(new RadarEntry(d1));
                entries1.add(new RadarEntry(d2));
                entries1.add(new RadarEntry(d3));
                entries1.add(new RadarEntry(d4));
                entries1.add(new RadarEntry(d5));
                entries1.add(new RadarEntry(d6));
                entries1.add(new RadarEntry(d7));
                entries1.add(new RadarEntry(d8));
                RadarDataSet set1 = new RadarDataSet(entries1, "Tổng kết điểm hiện tại");
                set1.setColor(getResources().getColor(R.color.colorAccent));
                set1.setFillColor(getResources().getColor(R.color.colorAccent));
                set1.setDrawFilled(true);
                set1.setFillAlpha(20);
                set1.setLineWidth(1f);
                set1.setDrawHighlightCircleEnabled(true);
                set1.setDrawHighlightIndicators(false);
                ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
                sets.add(set1);
                RadarData data = new RadarData(sets);
                data.setValueTextSize(8f);
                data.setDrawValues(false);
                data.setValueTextColor(Color.WHITE);
                mChart.setData(data);
                mChart.invalidate();

                sinhVien=sqLiteManager.getSV(getArguments().getString(MainActivity.MA_SV));
                TextView tvTen = (TextView) view.findViewById(R.id.tv_ten);
                tvTen.setTextColor(getResources().getColor(R.color.colorAccent));
                tvTen.setText(sinhVien.getTenSV());
                TextView tvTT = (TextView) view.findViewById(R.id.tv_tt);
                tvTT.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvTT.setText(sinhVien.getMaSV()+"\n"+sinhVien.getLopDL());

            }
            }

    };



    public void getDiemThiTheoMons(Handler handler,String msv) {
        diemThiTheoMons=new ArrayList<>();
        diemThiTheoMons=sqLiteManager.getAllDThiMon(msv);
        if (!diemThiTheoMons.isEmpty()){
            Message message=new Message();
            message.obj=diemThiTheoMons;
            handler.sendMessage(message);
        }else{
            ParserKetQuaThiTheoMon parserKetQuaThiTheoMon=new ParserKetQuaThiTheoMon(handler);
            parserKetQuaThiTheoMon.execute(msv);
        }
    }
//    private  ArrayList<DiemThiTheoMon> result;
    private ArrayList<DiemThiTheoMon> diemThiTheoMons;
    private  ArrayList<RadarEntry> entries1;
}
