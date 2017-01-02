package com.lhd.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ken.hauiclass.R;
import com.lhd.database.SQLiteManager;
import com.lhd.fragment.KetQuaHocTapFragment;
import com.lhd.fragment.KetQuaThiFragment;
import com.lhd.fragment.LichThiFragment;
import com.lhd.fragment.MoreFragment;
import com.lhd.fragment.RadarChartFragment;
import com.lhd.fragment.ThongBaoDtttcFragment;
import com.lhd.log.Log;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.KetQuaHocTap;
import com.lhd.object.SinhVien;
import com.lhd.object.Version;
import com.lhd.recerver.MyReserver;
import com.lhd.service.MyService;
import com.lhd.task.ParserKetQuaHocTap;
import com.lhd.task.TimeTask;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import duong.update.code.UpdateApp;

/**
 * Created by Duong on 11/20/2016.
 */

public class MainActivity extends AppCompatActivity {
    private StartAppAd startAppAd = new StartAppAd(this);

    public static final int ITEMS_PER_AD = 9;
    // The Native Express ad height.
    public static final int NATIVE_EXPRESS_AD_HEIGHT = 132;
    // The Native Express ad unit ID.
    public static final String AD_UNIT_ID_KQHT= "ca-app-pub-7062977963627166/8003707335";
    public static final String AD_UNIT_ID_LICH_THI= "ca-app-pub-7062977963627166/1521364939";
    public static final String AD_UNIT_ID_KET_QUA_THI ="ca-app-pub-7062977963627166/6849874931";
    public static final String AD_UNIT_ID_TB_DTTC ="ca-app-pub-7062977963627166/6274465334";
    public static final String AD_UNIT_ID_DIEM_LOP ="ca-app-pub-7062977963627166/5936388132";
    public static final String AD_UNIT_ID_DIEM_THI_LOP ="ca-app-pub-7062977963627166/9029455333";

    public static final String SINH_VIEN = "SINH_VIEN";
    public static final String MA_SV = "MA_SINH_VIEN";
    // A menu item view type.
    public static final int MENU_ITEM_VIEW_TYPE = 0;
    // The Native Express ad view type.
    public static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;
    private ViewPager viewPager;
    private KetQuaThiFragment ketQuaThiFragment;
    private LichThiFragment lichThiFragment;
    private KetQuaHocTapFragment ketQuaHocTapFragment;
    private SQLiteManager sqLiteManager;
    private TabLayout tabLayout;
    private com.lhd.log.Log log;
    private TextView tvTitle,tv1,tv2;
    private SinhVien sinhVien;
    private MoreFragment moreFragment;
    private TextView tietView;
    private TextView timeView;
    private RadarChartFragment radarChartFragment;
    private ThongBaoDtttcFragment thongBaoDtttcFragment;
    private PackageInfo info;
    private LinearLayout layoutTime;
    private FirebaseDatabase database;

    public static void showError(final Activity activity) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);
        builder.setMessage("Hình như sai mã sinh viên -_-\nBạn nhập lại nhé...");
        builder.setNegativeButton("Nhập MSV", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startLogin(activity);
                activity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
            }
        });
        builder.show();
    }
    public void setTitleTab(String s) {
        try {
            tvTitle.setText(sinhVien.getTenSV());
            tv1.setText(sinhVien.getLopDL()+" : "+sinhVien.getMaSV());
            tv2.setText(s);
        }catch (Exception e){
            tvTitle.setText(s);
            tv1.setText(s);
            tv2.setText(s);
        }
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
        context.startActivity(Intent.createChooser(sharingIntent, "Gà công nghiệp chia sẻ"));
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
        initUI();
    }
    public void getSV(String maSinhVien) {
        initViewStart();
        if (sqLiteManager.getSV(maSinhVien) instanceof SinhVien){
            setSinhVien(sqLiteManager.getSV(maSinhVien));
            return;
        }
        ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    if (MainActivity.isOnline(MainActivity.this)) showError(MainActivity.this);
                    else  Toast.makeText(MainActivity.this, "Không có kêt nối nternet!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (msg.obj instanceof KetQuaHocTap){
                    KetQuaHocTap ketQuaHocTap= (KetQuaHocTap) msg.obj;
                    for (ItemBangKetQuaHocTap bangKetQuaHocTap:ketQuaHocTap.getBangKetQuaHocTaps()) {
                        sqLiteManager.insertDMon(bangKetQuaHocTap,ketQuaHocTap.getSinhVien().getMaSV());
                    }
                    sqLiteManager.insertSV(ketQuaHocTap.getSinhVien());
                    setSinhVien(sqLiteManager.getSV(ketQuaHocTap.getSinhVien().getMaSV()));
                    return;
                }
            }
        });
        ketQuaHocTapTheoMon.execute(maSinhVien);
    }
    @Override
    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        if (log.getID().equals(sinhVien.getMaSV())) {
            finish();
            this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
        }else
            getSV(log.getID());
    }

    public void showStartADS() {
        startAppAd.showAd(MainActivity.this);
    }
    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here.
     */
    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }

    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here
     * for the home button exit ad integration.
     */
    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0){
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra(MainActivity.MA_SV);
                log.putID(result);

               checkLogin();
            }else if (resultCode ==Activity.RESULT_CANCELED)
                finish();
        }else if (requestCode==1){
                if(resultCode == Activity.RESULT_OK){
                    String result=data.getStringExtra(MainActivity.MA_SV);
                    getSV(result);
                }else if (resultCode ==Activity.RESULT_CANCELED)
                    finish();

            }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);
//        Intent intent1=new Intent(this, MyService.class);
//        stopService(intent1);
        registerReceiver(new MyReserver(), new IntentFilter(Intent.ACTION_SCREEN_ON));
        sqLiteManager=new SQLiteManager(this);
        log=new Log(this);
        try {
            PackageManager manager = getPackageManager();
             info = manager.getPackageInfo(getPackageName(), 0);
            String version = "Phiên bản "+info.versionName;
            TextView tvVersion= (TextView) findViewById(R.id.tv_version);
            tvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        }, 1000);
    }
    private void checkLogin() {
        StartAppSDK.init(this, "211282097", false);
        if (log.getID().length()==10) getSV(log.getID());
        else startLogin(MainActivity.this);
    }

    public static void startLogin(Activity activity) {
        Intent intent=new Intent(activity,InputActivity.class);
        activity.startActivityForResult(intent,0);
        activity.overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }
    public void checkUpdate(final int i) throws Exception{
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("updateGaCongNghiep");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Version version=dataSnapshot.getValue(Version.class);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                if (!version.getVerstionName().equals(info.versionName)&&i==1){
                    builder.setTitle("Cập nhật phiên bản "+version.getVerstionName());
                    builder.setCancelable(false);
                    builder.setMessage("- Nội dung:\n\t"+version.getContent()+"\n- Hướng dẫn cài đặt: Cài đặt> Không rõ nguồn gốc."+"\n- Khi " +
                            "cài sẽ thay thể ứng dụng hiện tại và giữ nguyên dữ liệu đang có" +
                            " bạn muốn tải về và cài đặt ngay?");
                    builder.setPositiveButton("Cài ngay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UpdateApp updateApp=new UpdateApp();
                            try {
                                updateApp.getAndInstallAppLication(MainActivity.this,
                                        "Ga.apk",version.getUrl(),
                                        "đang tải "+getApplication().getString(R.string.app_name)+" phiên bản mới nhất",
                                        "Đang cập nhật "+getApplication().getString(R.string.app_name) );
                            }catch (Exception e){}
                        }
                        });
                    builder.setNeutralButton("Từ từ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }else if (!version.getVerstionName().equals(info.versionName)&&i==0){
                    Snackbar snackbar=Snackbar.make(viewPager,"Đã có phiên bản Gà Công Nghiệp "+version.getVerstionName(),Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Cập nhật", new View.OnClickListener() {
                        UpdateApp updateApp=new UpdateApp();
                        @Override
                        public void onClick(View view) {
                            updateApp.getAndInstallAppLication(MainActivity.this,
                                    "Ga.apk",version.getUrl(),
                                    "đang tải "+getApplication().getString(R.string.app_name)+" phiên bản mới nhất",
                                    "Đang cập nhật "+getApplication().getString(R.string.app_name) );
                        }
                    });
                    snackbar.show();
                }else if (i==1){
                    builder.setTitle("Cập nhật phiên bản");
                    builder.setMessage("Bạn đang dùng phiên bản mới nhất\nGà Công Nghiệp "+version.getVerstionName());
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        try {
            setCount();
        } catch (Exception e) {}
    }

    public void setCount() throws Exception{
         final DatabaseReference count = database.getReference("count");
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        final String macAddress = wInfo.getMacAddress();
            final DatabaseReference mac = database.getReference("macWIFI/"+macAddress);
        count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long countIndex=dataSnapshot.getValue(Long.class);
                countIndex=countIndex+1;
                count.setValue(countIndex);
                mac.setValue(countIndex);
                count.removeEventListener(this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    private boolean isCick;
    private void initUI() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        setTitleTab("Kết quả học tập");
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
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
                Bundle bundle=new Bundle();
                bundle.putSerializable(SINH_VIEN,sinhVien);
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
                        thongBaoDtttcFragment.setArguments(bundle);
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
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_result);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_test);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_lich_thi);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_chart);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_tab_noti_dttc);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_more);
        progressBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
    }
    protected ProgressBar progressBar;
    private void initViewStart() {
        setContentView(R.layout.activity_main);
        layoutTime= (LinearLayout) findViewById(R.id.view_time);
        tietView= (TextView) findViewById(R.id.tv_tiet_hientai);
        timeView= (TextView) findViewById(R.id.tv_time_conlai);

        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCick=!isCick;
                if (isCick){
                    TimeTask timeTask =new TimeTask(handlertime);
                    timeTask.execute();
                }
            }
        });
        progressBar= (ProgressBar) findViewById(R.id.pg_loading_main);
        tvTitle= (TextView) findViewById(R.id.tb_title);
        tv1= (TextView) findViewById(R.id.tb_text1);
        tv2= (TextView) findViewById(R.id.tb_text2);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        setTitleTab("Đang lấy dữ liệu.....");
        tabLayout= (TabLayout) findViewById(R.id.tablayout_fa);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.setupWithViewPager(viewPager);
        progressBar.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        isCick=true;
        startTimeView();
        if (isOnline(this)){
            try {
                checkUpdate(0);
            } catch (Exception e) {}

            Intent intent1=new Intent(this, MyService.class);
            this.startService(intent1);
        }
    }
    private Handler handlertime=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (isCick){
                TimeTask timeTask =new TimeTask(this);
                timeTask.execute();
            }
            String s= (String) msg.obj;
            tietView.setText(s.split("-")[0]);
            timeView.setText(s.split("-")[1]);
        }
    };
    private void startTimeView() {

            TimeTask timeTask =new TimeTask(handlertime);
            timeTask.execute();
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
