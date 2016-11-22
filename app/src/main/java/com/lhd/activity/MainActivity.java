package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.lhd.database.SQLiteManager;
import com.lhd.fragment.BangDiemThanhPhan;
import com.lhd.fragment.KetQuaThiFragment;
import com.lhd.fragment.LichThiFragment;
import com.lhd.fragment.MoreFragment;
import com.lhd.item.SinhVien;
import com.lhd.log.Log;
import com.lhd.service.MyService;
import com.lhd.task.ParserKetQuaHocTap;

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
    private com.lhd.log.Log log;
    private Bundle bundle;
    private TextView tvTitle,tv1,tv2;
    private SinhVien sv;
    private int currentView;
    private int currenItem;
    private MoreFragment moreFragment;

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
        switch (currentView) {
            case 4:
                setTitleTab("Ngoài ra");
                if (getCurrenItem()!=8){
                    moreFragment.setCurrenView(8);
                    break;
                }
            default:
                if (log.getID().equals(maSV)) {
                    android.util.Log.e("fakerequals", maSV);
                    finish();
                    this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                } else {
                    startView(log.getID());
                    android.util.Log.e("fakerelse", maSV);

                }
                break;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        android.util.Log.e("faker1",""+requestCode);
        if (requestCode==0){
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra(MainActivity.MA_SV);
                maSV=result;

                log.putID(maSV);
                startView(maSV);
            }else if (resultCode ==Activity.RESULT_CANCELED){
                finish();
            }else {
              checkLogin();
            }
        }
        if (requestCode==1){
                if(resultCode == Activity.RESULT_OK){
                    android.util.Log.e("fakera","RESULT_OK");
                    String result=data.getStringExtra(MainActivity.MA_SV);
                    maSV=result;
                    android.util.Log.e("fakera",maSV);
                    startView(maSV);
                }else if (resultCode ==Activity.RESULT_CANCELED){
                    android.util.Log.e("fakera","RESULT_CANCELED");
                    finish();
                }else {
                    android.util.Log.e("fakera","checkLogin");
                    checkLogin();
                }
            }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            TextView tvVersion= (TextView) findViewById(R.id.tv_version);
            tvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        }, 1000);


    }

    private void checkLogin() {
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
        maSV=id;
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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentView=position;
                switch (position){
                    case 4:
                        setTitleTab("Ngoài ra");
                        break;
                    case 1:
                        setTitleTab("Kết quả thi");
                        break;
                    case 2:
                        setTitleTab("Lịch thi");
                        break;
                    case 3:
                        setTitleTab("Biểu đồ");
                        break;
                    default:
                        setTitleTab("Kết quả học tập");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position){
                    case 0:
                        setTitleTab("Điểm học tập");
                        bangDiemThanhPhan=new BangDiemThanhPhan();
                        bangDiemThanhPhan.setArguments(bundle);
                        return bangDiemThanhPhan;
                    case 1:
                        ketQuaThiFragment=new KetQuaThiFragment();
                        ketQuaThiFragment.setArguments(bundle);
                        return ketQuaThiFragment;
                    case 2:
                        lichThiFragment=new LichThiFragment();
                        lichThiFragment.setArguments(bundle);
                        return lichThiFragment;
                    case 3:
                        setTitleTab("Điểm học tập");
                        bangDiemThanhPhan=new BangDiemThanhPhan();
                        bangDiemThanhPhan.setArguments(bundle);
                        return bangDiemThanhPhan;
                    case 4:default:
                       moreFragment=new MoreFragment();
                    return moreFragment;

                }
            }
            @Override
            public int getCount() {
                return 5;
            }

        });
        viewPager.setCurrentItem(0);
        tabLayout= (TabLayout) findViewById(R.id.tablayout_fa);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_result);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_test);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_lich_thi);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_chart);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_more);
    }


    public void setCurrenItem(int currenItem) {
        this.currenItem = currenItem;
    }

    public int getCurrenItem() {
        return currenItem;
    }
}
