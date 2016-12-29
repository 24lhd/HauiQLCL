package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ken.hauiclass.R;
import com.lhd.fragment.DiemHocTapTheoLopFragment;
import com.lhd.fragment.DiemThiLopFragment;
import com.lhd.fragment.FrameFragment;
import com.lhd.fragment.KeHoachThiFragment;
import com.lhd.object.ItemBangDiemThanhPhan;
import com.lhd.object.ItemBangKetQuaHocTap;
import com.lhd.object.ItemDiemThiTheoMon;
import com.lhd.object.ItemKetQuaThiLop;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Faker on 8/30/2016.
 */
public class ListActivity extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;
    private int index;
    private ItemBangKetQuaHocTap itemBangKetQuaHocTap;
    private Toolbar toolbarMenu;
    private DiemHocTapTheoLopFragment diemHocTapTheoLopFragment;
    private ItemDiemThiTheoMon itemDiemThiTheoMon;
    private DiemThiLopFragment diemThiLopFragment;
    private KeHoachThiFragment keHoachThiFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        initView();
        checkDatabases();
    }
    @Override
    public void onBackPressed() {
        Intent intent=getIntent();
        setResult(Activity.RESULT_FIRST_USER,intent);
        finish();
        this.overridePendingTransition(R.anim.left_end,
                R.anim.right_end);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void initView( ) {
        toolbarMenu = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbarMenu);
        toolbarMenu.setNavigationIcon(android.R.drawable.ic_input_delete);
        toolbarMenu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbarMenu.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem arg0) {
                switch (arg0.getItemId()) {
                    case R.id.cao_thap:
                        switch (index) {
                            case 0:
                                Collections.sort(diemHocTapTheoLopFragment.getBangDiemThanhPhan().getBangDiemThanhPhan(), new Comparator<ItemBangDiemThanhPhan>() {
                                    @Override
                                    public int compare(ItemBangDiemThanhPhan o1, ItemBangDiemThanhPhan o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdTB());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdTB());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);

                                    }
                                });
                                diemHocTapTheoLopFragment.setRecyclerView();
                                break;
                            case 3:
                                Collections.sort(diemThiLopFragment.getKetQuaThi().getKetQuaThiLops(), new Comparator<ItemKetQuaThiLop>() {
                                    @Override
                                    public int compare(ItemKetQuaThiLop o1, ItemKetQuaThiLop o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdL1());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdL1());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);
                                    }
                                });
                                diemThiLopFragment.setRecyclerView();
                                break;
                        }
                        return true;
                    case R.id.thap_cao:
                        switch (index) {
                            case 0:
                                Collections.sort(diemHocTapTheoLopFragment.getBangDiemThanhPhan().getBangDiemThanhPhan(), new Comparator<ItemBangDiemThanhPhan>() {
                                    @Override
                                    public int compare(ItemBangDiemThanhPhan o1, ItemBangDiemThanhPhan o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdTB());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdTB());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);

                                    }
                                });
                                Collections.reverse(diemHocTapTheoLopFragment.getBangDiemThanhPhan().getBangDiemThanhPhan());
                                diemHocTapTheoLopFragment.setRecyclerView();
                                break;
                            case 3:
                                Collections.sort(diemThiLopFragment.getKetQuaThi().getKetQuaThiLops(), new Comparator<ItemKetQuaThiLop>() {
                                    @Override
                                    public int compare(ItemKetQuaThiLop o1, ItemKetQuaThiLop o2) {
                                        double i1;
                                        double i2;
                                        try {
                                            i1 = Double.parseDouble(o1.getdL1());
                                        } catch (Exception e) {
                                            i1 = -1;
                                        }
                                        try {
                                            i2 = Double.parseDouble(o2.getdL1());
                                        } catch (Exception e) {
                                            i2 = -1;
                                        }
                                        return Double.compare(i2, i1);
                                    }
                                });
                                Collections.reverse(diemThiLopFragment.getKetQuaThi().getKetQuaThiLops());
                                diemThiLopFragment.setRecyclerView();
                                break;
                        }
                }
                return false;
            }
        });
    }
    private void checkDatabases() {
        intent=getIntent();
        bundle=intent.getBundleExtra("action");
        index=bundle.getInt(FrameFragment.KEY_ACTIVITY);
         android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        try {
            switch (index){
                case 0:
                    itemBangKetQuaHocTap= (ItemBangKetQuaHocTap) intent.getSerializableExtra(FrameFragment.KEY_OBJECT);
                     diemHocTapTheoLopFragment=new DiemHocTapTheoLopFragment();
                    diemHocTapTheoLopFragment.setItemBangKetQuaHocTap(itemBangKetQuaHocTap);
                    diemHocTapTheoLopFragment.setListActivity(this);
                    ft.replace(R.id.fm_list_ac, diemHocTapTheoLopFragment);
                    ft.commit();
                    return;
                case 1:
                    itemBangKetQuaHocTap= (ItemBangKetQuaHocTap) intent.getSerializableExtra(FrameFragment.KEY_OBJECT);
                     keHoachThiFragment=new KeHoachThiFragment();
                    keHoachThiFragment.setItemBangKetQuaHocTap(itemBangKetQuaHocTap);
                    keHoachThiFragment.setListActivity(this);
                    ft.replace(R.id.fm_list_ac, keHoachThiFragment);
                    ft.commit();
                    break;
                case 3:
                    itemDiemThiTheoMon = (ItemDiemThiTheoMon) intent.getSerializableExtra(FrameFragment.KEY_OBJECT);
                     diemThiLopFragment=new DiemThiLopFragment();
                    diemThiLopFragment.setItemDiemThiTheoMon(itemDiemThiTheoMon);
                    diemThiLopFragment.setListActivity(this);
                    ft.replace(R.id.fm_list_ac, diemThiLopFragment);
                    ft.commit();
                    break;
            }
        }catch (NullPointerException e){}
    }








}
