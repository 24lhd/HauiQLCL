package com.ken.hauiclass.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ken.hauiclass.R;
import com.ken.hauiclass.activity.LoginActivity;

/**
 * Created by Faker on 8/14/2016.
 */
public class ResetPassFragment extends Fragment {
    private AppCompatButton processButton;
    private ProgressBar progressBar;
    private LoginActivity tabActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.reset_pass,container,false);
        tabActivity= (LoginActivity) getActivity();
        progressBar= (ProgressBar) view.findViewById(R.id.pg_reset);
        processButton= (AppCompatButton) view.findViewById(R.id.btReset);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabActivity.signOut();
            }
        });
        return view;
    }

}
