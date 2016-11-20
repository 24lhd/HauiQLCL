package com.ken.hauiclass.activity;

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
import android.widget.Toast;

import com.ken.hauiclass.R;

/**
 * Created by Duong on 11/20/2016.
 */

public class InputActivity extends AppCompatActivity{
    private EditText etId;
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
        progressBar= (ProgressBar) findViewById(R.id.pg_input);
        processButton= (AppCompatButton) findViewById(R.id.bt_input);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=etId.getText().toString();
                if (!id.isEmpty()){
                    processButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("id",id);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }else {
                    Toast.makeText(InputActivity.this, "Không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
