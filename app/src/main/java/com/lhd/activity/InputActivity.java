package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ken.hauiclass.R;
import com.lhd.object.KetQuaHocTap;
import com.lhd.object.SinhVien;
import com.lhd.task.ParserKetQuaHocTap;

import java.util.ArrayList;

/**
 * Created by Duong on 11/20/2016.
 */

public class InputActivity extends AppCompatActivity{
    private EditText etId;
    private TextView tvError;
    private AppCompatButton processButton;
    private ProgressBar progressBar;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.input_msv_layout);
        database = FirebaseDatabase.getInstance();
        etId= (EditText) findViewById(R.id.et_id_input);
        tvError= (TextView) findViewById(R.id.tv_input_error);
        tvError.setVisibility(View.VISIBLE);
        progressBar= (ProgressBar) findViewById(R.id.pg_input);
        processButton= (AppCompatButton) findViewById(R.id.bt_input);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=etId.getText().toString();
                if (id.isEmpty()){
                    tvError.setText("* Không được để trống bạn ê");
                }
                else if (id.length()<10)
                    tvError.setText("* Đừng đùa thể chứ x_x \n Nhập đúng mã sinh viên đi bạn êi");
                else getSV(id);
            }
        });
    }
    public void getSV(final String maSinhVien) {
        processButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final ParserKetQuaHocTap ketQuaHocTapTheoMon=new ParserKetQuaHocTap(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==2){
                    processButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    if (MainActivity.isOnline(InputActivity.this)) tvError.setText("* Mã sinh viên không đúng bạn êi....");
                    else tvError.setText("* Không có kêt nối Iternet!");
                    return;
                }else if (msg.obj instanceof KetQuaHocTap){
                    tvError.setText("* Đợi tẹo.....................");
                    KetQuaHocTap ketQuaHocTap= (KetQuaHocTap) msg.obj;
                    try {
                        setWho(ketQuaHocTap.getSinhVien());
                    } catch (Exception e) {}
                    finally {
                        goToUI(maSinhVien);
                    }
                    return;
                }
            }
        });
        ketQuaHocTapTheoMon.execute(maSinhVien);
    }

    private void goToUI(String maSinhVien) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.MA_SV,maSinhVien);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void setWho(SinhVien sinhVien ) throws Exception{
        if (sinhVien instanceof SinhVien){
            DatabaseReference every = database.getReference("EveryOne/"+sinhVien.getMaSV());
            every.setValue(sinhVien);
        }
    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        super.onBackPressed();
    }
}
