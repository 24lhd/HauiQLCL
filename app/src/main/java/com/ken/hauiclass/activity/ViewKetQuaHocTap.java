package com.ken.hauiclass.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import com.ken.hauiclass.R;
import com.ken.hauiclass.fragment.KetQuaHocTapFragment;
/**
 * Created by Faker on 9/20/2016.
 */
public class ViewKetQuaHocTap extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_frament);
        FrameLayout frame = (FrameLayout) findViewById(R.id.fm_fm);
        KetQuaHocTapFragment newFragment = new KetQuaHocTapFragment();
        Bundle bundle=new Bundle();
        Intent intent=getIntent();
        String msv=intent.getBundleExtra("KEY_MSV").getString("MA_SV");
        bundle.putString("MA_SV",msv);
        newFragment.setArguments(bundle);
        setContentView(frame, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        getSupportFragmentManager().beginTransaction().add(R.id.fm_fm,newFragment).commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.left_end,
                R.anim.right_end);
    }
}
