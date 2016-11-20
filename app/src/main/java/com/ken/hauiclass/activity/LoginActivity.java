package com.ken.hauiclass.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ken.hauiclass.R;
import com.ken.hauiclass.fragment.LoginFragment;
import com.ken.hauiclass.fragment.ResetPassFragment;
import com.ken.hauiclass.fragment.SiginFragment;
import xyz.santeri.wvp.WrappingFragmentPagerAdapter;
import xyz.santeri.wvp.WrappingViewPager;
import static com.ken.hauiclass.R.string.reset_pass_text;

/**
 * Created by Faker on 8/14/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private WrappingViewPager wrappingViewPager;
    private LoginFragment loginFragment;
    private SiginFragment siginFragment;
    private ResetPassFragment resetPassFragment;
    /**
     * Firebase
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_sigin_layout);

        creatTabLoginSigin();
        creatFirebase();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.left_end, R.anim.right_end);
    }

    private void creatTabLoginSigin() {
        wrappingViewPager= (WrappingViewPager) findViewById(R.id.login_sigin_viewPager);
        loginFragment=new LoginFragment();
        siginFragment=new SiginFragment();
        resetPassFragment=new ResetPassFragment();
        wrappingViewPager.setAdapter(new WrappingFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return loginFragment;
                    case 1:
                        return  siginFragment;
                    case 2:
                    default:
                        return resetPassFragment;
                }
            }
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return getResources().getString(R.string.login_text);
                    case 1:
                        return getResources().getString(R.string. sigin_text);
                    case 2:
                    default:
                        return getResources().getString(reset_pass_text);
                }
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.login_sigin_tablayout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(wrappingViewPager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_login);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_register);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_reset);
        }
    }
    /**
     * khởi tạo FB
     */
    public void creatFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    android.util.Log.e("faker","user != null");
                } else {
                    android.util.Log.e("faker","else");
                }
            }
        };
    }
    /**
     *
     * Đăng xuất
     *
     * */
    public void signOut() {
        mAuth.signOut();
    }
    /**
     * đăng ký tài khoản với :
     *  id: mã sinh viên
     *  pass: mật khẩu
     *
     * */
    public void register(final String id, final String pass) {
//            Toast.makeText(this,"Không có kết nối internet",Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(id+"@haui.com",pass ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Đăng ký không thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Đăng ký thành công",
                                        Toast.LENGTH_SHORT).show();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("id",id);
                                returnIntent.putExtra("pass",pass);
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }
                        }
                    });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
    }
    /**
     * đăng nhập
     * */
    public void login(final String id, final String pass) {
        mAuth.signInWithEmailAndPassword(id+"@haui.com", pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Sai mã sinh viên hoặc mật khẩu",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT).show();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("id",id);
                            returnIntent.putExtra("pass",pass);
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                    }
                });
    }
    /**
     * kiểm tra dăng nhập
     *
     * true là đã đăng nhập
     * false là không
     * */

    /**
     * cập nhật mật khẩu với đối số là mật khẩu cũ
     * */
    public void updatePass(final String pass) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    user.updatePassword(pass);
                    signOut();
                } else {

                }
            }
        };
    }
}
