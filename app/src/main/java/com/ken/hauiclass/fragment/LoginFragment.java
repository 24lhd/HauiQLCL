package com.ken.hauiclass.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ken.hauiclass.R;
import com.ken.hauiclass.activity.LoginActivity;
/**
 * Created by Faker on 8/14/2016.
 */

public class LoginFragment extends Fragment {
    private EditText etId;
    private EditText etPass;
    private AppCompatButton processButton;
    private AppCompatCheckBox animatedSwitch;
    private ProgressBar progressBar;
    private TextView tvState;
    private LoginActivity tabActivity;
    private boolean isState;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment,container,false);
         tabActivity= (LoginActivity) getActivity();
        etId= (EditText) view.findViewById(R.id.et_id_login);
        progressBar= (ProgressBar) view.findViewById(R.id.pg_ligin);
        etPass= (EditText) view.findViewById(R.id.et_pass_login);
        tvState= (TextView) view.findViewById(R.id.state_login);
        animatedSwitch= (AppCompatCheckBox) view.findViewById(R.id.pin_state_login);
        animatedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isState=animatedSwitch.isChecked();
                if (isState){
                    tvState.setText(R.string.save_state_login);
                }else{
                    tvState.setText(R.string.un_save_state_login);
                }
            }
        });
        tvState= (TextView) view.findViewById(R.id.state_login);
        processButton= (AppCompatButton) view.findViewById(R.id.bt_login);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=etId.getText().toString();
                String pass=etPass.getText().toString();
                if (!id.isEmpty()||!pass.isEmpty()){
                    tabActivity.login(id,pass);
                    processButton.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getContext(), "Không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
