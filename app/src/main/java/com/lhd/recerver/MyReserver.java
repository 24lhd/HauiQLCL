package com.lhd.recerver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.lhd.service.MyService;

/**
 * Created by Faker on 9/5/2016.
 */
public class MyReserver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s=intent.getAction();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent intent1=new Intent(context, MyService.class);
            context.startService(intent1);
            Toast.makeText(context,"Gà Công Nghiệp đang kiểm tra xem có gì hót ^.^",Toast.LENGTH_LONG).show();
        }
//        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//            Log.e("faker", "ON MyReserver");
//        }
        if( activeNetInfo != null ){
            Intent my=new Intent(context, MyService.class);
            boolean b=activeNetInfo.isConnectedOrConnecting();
            if (b){
                context.startService(my);
                Log.e("faker", "ON isConnectedOrConnecting");
            }
        }
    }
}
