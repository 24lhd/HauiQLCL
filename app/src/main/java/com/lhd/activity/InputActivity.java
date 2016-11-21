package com.lhd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ken.hauiclass.R;

/**
 * Created by Duong on 11/20/2016.
 */

public class InputActivity extends AppCompatActivity{
    private EditText etId;
    private TextView tvError;
    private AppCompatButton processButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.input_msv_layout);
        etId= (EditText) findViewById(R.id.et_id_input);
        tvError= (TextView) findViewById(R.id.tv_input_error);
        tvError.setVisibility(View.GONE);
        progressBar= (ProgressBar) findViewById(R.id.pg_input);
        processButton= (AppCompatButton) findViewById(R.id.bt_input);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setVisibility(View.GONE);
                String id=etId.getText().toString();
                if (id.isEmpty()) {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("* Không được để trống bạn ê");
                }else if (id.length()<10){
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("* Đừng đùa thể chứ x_x \n Nhập đúng mã sinh viên đi bạn ê");
                }else {
                    processButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(MainActivity.MA_SV,id);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        super.onBackPressed();
    }
}
