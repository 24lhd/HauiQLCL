package com.ken.hauiclass.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alertdialogpro.ProgressDialogPro;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.ken.hauiclass.R;
import com.ken.hauiclass.database.SQLiteManager;
import com.ken.hauiclass.fragment.KetQuaHocTapFragment;
import com.ken.hauiclass.fragment.ListMessagesFragment;
import com.ken.hauiclass.fragment.ListMoreFragment;
import com.ken.hauiclass.fragment.ListMoreFriendFragment;
import com.ken.hauiclass.fragment.ListNotificationFragment;
import com.ken.hauiclass.fragment.ListStudentFragment;
import com.ken.hauiclass.log.Log;
import com.ken.hauiclass.service.MyService;

public class MainActivity extends AppCompatActivity {
    public static final String MA_SV = "masv";
    private AHBottomNavigationAdapter navigationAdapter;
    private int[] tabColors;
    private Toolbar toolbar;
	private ViewPager viewPager;
    private AHBottomNavigation bottomNavigation;
    private int mTheme = R.style.Theme_AlertDialogPro_Holo_Light;
    private AlertDialog dialogProgess;
    private KetQuaHocTapFragment ketQuaHocTapFragment;
    private ListStudentFragment listStudentFragment;
    private ListMessagesFragment listMessagesFragment;
    private ListNotificationFragment listNotificationFragment;
    private ListMoreFriendFragment listMoreFriendFragment;
    private ListMoreFragment listMoreFragment;
    private String id;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log=new Log(this);
        id=log.getID();
        if (id.isEmpty()){
            startLogin();
        }else{
            startView(id);
        }
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
    }
    public void checkLogin() {
        if (id.isEmpty()){
            startLogin();
        }
    }
    public void startTask() {
        log=new com.ken.hauiclass.log.Log(this);
        sqLiteManager =new SQLiteManager(this);
    }
    public void startLogin() {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivityForResult(intent,0);
        this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }
    public void startView(String id) {
        startTask();
        sqLiteManager=new SQLiteManager(this);
        setContentView(R.layout.activity_main);
        creatFrament(id);
        initUI();
    }
    public AlertDialog createProgressDialog() {
        return new ProgressDialogPro(this, mTheme);
    }
    public void hideProgressDialog() {
        dialogProgess.dismiss();
    }
    public void showProgressDialog() {
        dialogProgess = createProgressDialog();
        dialogProgess.setMessage("Đang khởi tạo dữ liệu!");
        dialogProgess.setCanceledOnTouchOutside(false);
        dialogProgess.show();
    }
    // khi đã đăng nhập thành công kết quả trả về
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("id");
                log.putID(result);
                startView(result);
            }else if (resultCode==Activity.RESULT_CANCELED){
                finish();
            }
        }
    }
    private void creatFrament(String id) {
        ketQuaHocTapFragment =new KetQuaHocTapFragment();
        try {
            Intent intent=getIntent();
            int index=intent.getBundleExtra(MyService.KEY_TAB).getInt(MyService.TAB_POSITON);
            Bundle bundle=new Bundle();
            bundle.putString(MA_SV,id);
            bundle.putInt(MyService.TAB_POSITON,index);
            ketQuaHocTapFragment.setArguments(bundle);
            android.util.Log.e("faker",""+index);
        }catch (NullPointerException e ){
            android.util.Log.e("faker","vào");
            Bundle bundle=new Bundle();
            bundle.putString(MA_SV,id);
            bundle.putInt(MyService.TAB_POSITON,0);
            ketQuaHocTapFragment.setArguments(bundle);
        }


        listMoreFriendFragment=new ListMoreFriendFragment();
        listNotificationFragment=new ListNotificationFragment();

        listMessagesFragment=new ListMessagesFragment();
        listMoreFragment=new ListMoreFragment();
    }
    /**
     * Init UI
     */
    private void initUI() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position){
                    case 0: return ketQuaHocTapFragment;
                    case 1:return listMessagesFragment;
                    case 2:return listNotificationFragment;
                    case 3:return listMoreFriendFragment;
                    case 4:default: return listMoreFragment;
                }
            }
            @Override
            public int getCount() {
                return 5;
            }
        });

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        bottomNavigation.setColored(true);
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.restoreBottomNavigation(false);
        setTitleBar(0);
        tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottombar_menu_5items);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                bottomNavigation.setNotification("", position);
                bottomNavigation.setNotification("10",1);
                viewPager.setCurrentItem(position);
                setTitleBar(position);
                setColorBar(tabColors[position]);
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(tabColors[position]);
                }
                return true;}});
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                bottomNavigation.setCurrentItem(position);
                bottomNavigation.restoreBottomNavigation(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setTitleBar(int i) {
        String titleBar;
        switch (i){
            case 0:
                titleBar="Lớp Học";
                toolbar.setVisibility(View.GONE);
                break;
            case 1:
                toolbar.setVisibility(View.VISIBLE);
                titleBar="Trò Chuyện";
                break;
            case 2:
                toolbar.setVisibility(View.VISIBLE);
                titleBar="Thông tin cá nhân";
                break;
            case 3:
                toolbar.setVisibility(View.VISIBLE);
                titleBar="Tìm Bạn";
                break;
            case 4:
                toolbar.setVisibility(View.VISIBLE);
                default: titleBar="Khác";
                    break;
        }
        toolbar.setTitle(titleBar);
        setSupportActionBar(toolbar);
    }
    public void setSnackBar(String titleBar) {
        Snackbar.make(bottomNavigation, ""+titleBar,Snackbar.LENGTH_SHORT).show();
    }
    public void setColorBar(int color) {
        toolbar.setBackgroundColor(color);
        setSupportActionBar(toolbar);
    }
    public Log getLog() {
        return log;
    }
    private SQLiteManager sqLiteManager;
    private com.ken.hauiclass.log.Log log;

}
