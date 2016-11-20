package com.ken.hauiclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ken.hauiclass.R;
import com.ken.hauiclass.database.SQLiteManager;
import com.ken.hauiclass.fragment.BangDiemThanhPhan;
import com.ken.hauiclass.fragment.KetQuaThiFragment;
import com.ken.hauiclass.fragment.LichThiFragment;
import com.ken.hauiclass.fragment.ListMoreFragment;
import com.ken.hauiclass.item.SinhVien;
import com.ken.hauiclass.log.Log;
import com.ken.hauiclass.service.MyService;
import com.ken.hauiclass.task.ParserKetQuaHocTap;

/**
 * Created by Duong on 11/20/2016.
 */

public class MainActivity extends AppCompatActivity {
    public static final String MA_SV = "masv";
    private ViewPager viewPager;
    private KetQuaThiFragment ketQuaThiFragment;
    private LichThiFragment lichThiFragment;
    private BangDiemThanhPhan bangDiemThanhPhan;
    private String maSV;
    private SQLiteManager sqLiteManager;
    private TabLayout tabLayout;
    private com.ken.hauiclass.log.Log log;
    private Bundle bundle;
    private TextView tvTitle,tv1,tv2;
    private SinhVien sv;
    public void setTitleTab(String titleTab) {
        sv=sqLiteManager.getSV(maSV);
        if (sv!=null){
            tvTitle.setText(sv.getTenSV());
            tv1.setText(sv.getMaSV()+" : "+sv.getLopDL());
            tv2.setText(titleTab);
        }else{
             Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    try{
                        switch (msg.arg1){
                            case 6:
                                sv= (SinhVien) msg.obj;
                                if (sv!=null){ // nếu bên trong databse mà có dữ liệu thì ta sẽ
                                    sqLiteManager.insertSV(sv);
                                    tvTitle.setText(sv.getTenSV());
                                    tv1.setText(sv.getLopDL());
                                    tv2.setText(sv.getMaSV());
                                }else {
                                    startLogin();
                                }
                                break;
                        }
                    }catch (NullPointerException e){
                        startLogin();
                    }
                }
            };
            ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(2,handler);
            ketQuaHocTapTheoMon.execute(maSV);
        }
    }
    @Override
    public void onBackPressed() {
        if (log.getID().equals(maSV)){
            super.onBackPressed();
            this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
        }else {
            startView(log.getID());
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("id");
                maSV=result;
                android.util.Log.e("faker1",result);
                log.putID(result);
                startView(result);
            }else if (resultCode==Activity.RESULT_CANCELED) {
                finish();
            }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log=new Log(this);
        maSV=log.getID();
        if (maSV.isEmpty()){
            startLogin();
        }else{
            startView(maSV);
        }
    }
    public void startLogin() {
        Intent intent=new Intent(this,InputActivity.class);
        startActivityForResult(intent,0);
        this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }
    public void startView(String id) {
        sqLiteManager=new SQLiteManager(this);
        setContentView(R.layout.activity_main);
        creatFrament(id);
        initUI();
    }
    private void creatFrament(String id) {
         bundle=new Bundle();
        bundle.putString(MA_SV,id);
        try {
            Intent intent=getIntent();
            int index=intent.getBundleExtra(MyService.KEY_TAB).getInt(MyService.TAB_POSITON);
            bundle.putInt(MyService.TAB_POSITON,index);
        }catch (NullPointerException e ){
            bundle.putInt(MyService.TAB_POSITON,0);
        }
    }
    private void initUI() {
        tvTitle= (TextView) findViewById(R.id.tb_title);
        tv1= (TextView) findViewById(R.id.tb_text1);
        tv2= (TextView) findViewById(R.id.tb_text2);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position){
                    case 0:
                        setTitleTab("Kết quả học tập");
                        bangDiemThanhPhan=new BangDiemThanhPhan();
                        bangDiemThanhPhan.setArguments(bundle);
                        return bangDiemThanhPhan;
                    case 1:
                        setTitleTab("Kết quả thi");
                        ketQuaThiFragment=new KetQuaThiFragment();
                        ketQuaThiFragment.setArguments(bundle);
                        return ketQuaThiFragment;
                    case 2:
                        setTitleTab("Lịch thi");
                        lichThiFragment=new LichThiFragment();
                        lichThiFragment.setArguments(bundle);
                        return lichThiFragment;
                    case 3:
                        setTitleTab("Biểu đồ");
                        return new ListMoreFragment();
                    case 4:default:
                        setTitleTab("Nhiều hơn");
                        return new ListMoreFragment();
                }
            }
            @Override
            public int getCount() {
                return 5;
            }
        });
        tabLayout= (TabLayout) findViewById(R.id.tablayout_fa);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_result);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_test);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_lich_thi_2);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_lich_thi_2);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_lich_thi_2);
    }









}
