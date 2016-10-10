package com.ken.hauiclass.recerver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.ken.hauiclass.service.MyService;
/**
 * Created by Faker on 9/5/2016.
 */
public class MyReserver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s=intent.getAction();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if( activeNetInfo != null ){
            Intent intent1=new Intent(context, MyService.class);
            boolean b=activeNetInfo.isConnectedOrConnecting();
            if (b){
                    context.startService(intent1);
            }else{
                context.stopService(intent1);
            }
        }
    }
}
