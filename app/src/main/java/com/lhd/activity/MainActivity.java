package com.lhd.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ken.hauiclass.R;
import com.lhd.database.SQLiteManager;
import com.lhd.fragment.KetQuaHocTapFragment;
import com.lhd.fragment.KetQuaThiFragment;
import com.lhd.fragment.LichThiFragment;
import com.lhd.fragment.MoreFragment;
import com.lhd.fragment.RadarChartFragment;
import com.lhd.fragment.ThongBaoDtttcFragment;
import com.lhd.item.SinhVien;
import com.lhd.log.Log;
import com.lhd.service.MyService;
import com.lhd.task.ParserKetQuaHocTap;
import com.lhd.task.TimeTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Duong on 11/20/2016.
 */

public class MainActivity extends AppCompatActivity {
    public static final String MA_SV = "masv";
    public static final String SINH_VIEN = "sinh vien";
    private ViewPager viewPager;
    private KetQuaThiFragment ketQuaThiFragment;
    private LichThiFragment lichThiFragment;
    private KetQuaHocTapFragment ketQuaHocTapFragment;
    private String maSV;
    private SQLiteManager sqLiteManager;
    private TabLayout tabLayout;
    private com.lhd.log.Log log;
    private Bundle bundle;
    private TextView tvTitle,tv1,tv2;
    private SinhVien sv;
    private int currentView;
    private MoreFragment moreFragment;
    private TextView tietView;
    private TextView timeView;
    private RadarChartFragment radarChartFragment;
    private ThongBaoDtttcFragment thongBaoDtttcFragment;
    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }catch (Exception e) {
            return false;
        }
    }
    public static void showErr(final Activity activity) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setMessage("Chả lấy được dữ liệu gì cả. Bạn nhập lại mã sinh viên nhé !!!");
        builder.setNegativeButton("Nhập MSV", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(activity,InputActivity.class);
                activity.startActivityForResult(intent,0);
                activity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    public static void sreenShort(View viewInput,Context context) {
         Date now = new Date();
         ByteArrayOutputStream bytearrayoutputstream;
         Bitmap bitmap;
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File file;
        bytearrayoutputstream = new ByteArrayOutputStream();
        FileOutputStream fileoutputstream;
        View view=viewInput.getRootView();
       view.setDrawingCacheEnabled(true);
        bitmap = view.getDrawingCache();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
        file = new File( Environment.getExternalStorageDirectory() + "/GaCongNghiep/"+now+".png");
        file.getParentFile().mkdirs();
        try{
            file.createNewFile();
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(bytearrayoutputstream.toByteArray());
            fileoutputstream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context,""+file.getPath(),Toast.LENGTH_SHORT).show();
        shareImage(file,context);
    }
    public static void shareImage(File file,Context context){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            context.startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Không tìm thấy ứng dụng để mở file", Toast.LENGTH_SHORT).show();
        }
    }
    public static void shareText(Context context, String tenMon, String text) {
        String shareBody = tenMon+" \n"+text;
        Toast.makeText(context,shareBody,Toast.LENGTH_SHORT).show();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Gà công nghiệp chia sẻ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Chia sẻ thông tin"));
    }
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
            default:
                if (log.getID().equals(maSV)) {
                    finish();
                    this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
                } else {
                    startView(log.getID());

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
                    String result=data.getStringExtra(MainActivity.MA_SV);
                    maSV=result;
                    startView(maSV);
                }else if (resultCode ==Activity.RESULT_CANCELED){
                    finish();
                }else if (resultCode ==Activity.RESULT_FIRST_USER){
                    return;
                }else{
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
            String version = "Phiên bản "+info.versionName;
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
        if (isOnline()){
            Intent intent1=new Intent(this, MyService.class);
            this.startService(intent1);
        }
        maSV=id;
        sqLiteManager=new SQLiteManager(this);
        setContentView(R.layout.activity_main);
        creatFrament(id);
        initUI();
    }
    private void creatFrament(String id) {
         bundle=new Bundle();
        bundle.putString(MA_SV,id);
        bundle.putSerializable(SINH_VIEN,sv);
        try {
            Intent intent=getIntent();
            int index=intent.getBundleExtra(MyService.KEY_TAB).getInt(MyService.TAB_POSITON);
            bundle.putInt(MyService.TAB_POSITON,index);
        }catch (NullPointerException e ){
            bundle.putInt(MyService.TAB_POSITON,0);
        }
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            String s= (String) msg.obj;
            tietView.setText(s.split("-")[0]);
            timeView.setText(s.split("-")[1]);
            if (!isCick){
                TimeTask timeTask =new TimeTask(this);
                timeTask.execute();
            }
        }
    };
    boolean isCick=false;
    private void initUI() {
        TimeTask timeTask =new TimeTask(handler);
        timeTask.execute();
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.view_time);
        tietView= (TextView) findViewById(R.id.tv_tiet_hientai);
        timeView= (TextView) findViewById(R.id.tv_time_conlai);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCick=!isCick;
                if (!isCick){
                    TimeTask timeTask =new TimeTask(handler);
                    timeTask.execute();
                }
            }
        });
        tvTitle= (TextView) findViewById(R.id.tb_title);
        tv1= (TextView) findViewById(R.id.tb_text1);
        tv2= (TextView) findViewById(R.id.tb_text2);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        setTitleTab("Kết quả học tập");
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentView=position;
                switch (position){
                    case 5:
                        setTitleTab("Ngoài ra");
                        break;
                    case 4:
                        setTitleTab("Thông báo");
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
            public void onPageScrollStateChanged(int state) {}
        });
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position){
                    case 0:
                        ketQuaHocTapFragment =new KetQuaHocTapFragment();
                        ketQuaHocTapFragment.setArguments(bundle);
                        return ketQuaHocTapFragment;
                    case 1:
                        ketQuaThiFragment=new KetQuaThiFragment();
                        ketQuaThiFragment.setArguments(bundle);
                        return ketQuaThiFragment;
                    case 2:
                        lichThiFragment=new LichThiFragment();
                        lichThiFragment.setArguments(bundle);
                        return lichThiFragment;
                    case 3:
                         radarChartFragment =new RadarChartFragment();
                        radarChartFragment.setArguments(bundle);
                        return radarChartFragment;
                    case 4:
                         thongBaoDtttcFragment=new ThongBaoDtttcFragment();
                        return thongBaoDtttcFragment;
                    case 5:default:
                        moreFragment=new MoreFragment();
                        return moreFragment;

                }
            }
            @Override
            public int getCount() {
                return 6;
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
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_spider);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_tab_noti_dttc);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_more);
    }
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }catch (Exception e) {
            return false;
        }
    }
}
