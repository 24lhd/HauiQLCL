package com.lhd.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.ken.hauiclass.R;
import com.lhd.database.SQLiteManager;

/**
 * Created by D on 12/15/2016.
 */

public class Demo extends Activity {
    private SQLiteManager sqLiteManager;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
    }

}
