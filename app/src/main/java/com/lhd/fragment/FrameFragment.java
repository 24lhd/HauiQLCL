package com.lhd.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.object.SinhVien;
import com.lhd.service.MyService;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;

/**
 * Created by D on 12/19/2016.
 */

public abstract class FrameFragment extends Fragment{
    public static final String KEY_OBJECT = "send_object";
    public static final String KEY_ACTIVITY = "key_start_activity";
    protected RecyclerView recyclerView;
    protected TextView tVnull;
    protected ProgressBar progressBar;
    protected LinearLayout toolbar;
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
    protected SQLiteManager sqLiteManager;
    protected PullRefreshLayout pullRefreshLayout;
    protected SinhVien sv;
    protected ArrayList<Object> objects;

    public static void showAlert(final String title, final String html, final String titleSMS, final String SMS, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final StartAppAd rewardedVideo;
        rewardedVideo = new StartAppAd(activity);
        builder.setTitle(title);
        WebView webView=new WebView(activity);
        webView.setBackgroundColor(activity.getResources().getColor(R.color.bg_text));
        webView.loadDataWithBaseURL(null, html,"text/html","utf-8",null);
        builder.setView(webView);
        builder.setNeutralButton("SMS",null);
        builder.setPositiveButton("IMG",null);
        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                rewardedVideo.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
//                        rewardedVideo.showAd();
                        Log.e("faker","show dia");
                    }
                    @Override
                    public void onFailedToReceiveAd(Ad ad) {
                    }
                });
            }
        });
        mAlertDialog.show();
        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.sreenShort(view,activity);
            }
        });
        Button c = mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.shareText(activity,titleSMS, SMS);
            }
        });

    }
    public static class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {
        public NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }
    public void setUpAndLoadNativeExpressAds(final String id, final int height) {
        // Use a Runnable to ensure that the RecyclerView has been laid out before setting the
        // ad size for the Native Express ad. This allows us to set the Native Express ad's
        // width to match the full width of the RecyclerView.
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                final float density = getActivity().getResources().getDisplayMetrics().density;
                for (int i = 0; i <= objects.size(); i += ITEMS_PER_AD) {
                     NativeExpressAdView adView = (NativeExpressAdView) objects.get(i);
                    AdSize adSize = new AdSize((int) (recyclerView.getWidth()/density),height);
                    adView.setAdSize(adSize);
                    adView.setAdUnitId(id);
                }
                loadNativeExpressAd(0);
            }
        });

    }
    public void loadNativeExpressAd(final int index) {
        if (index >= objects.size()) {
            return;
        }
        Object item = objects.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + ITEMS_PER_AD);

            }
        });
        // Load the Native Express ad.
        adView.loadAd(new AdRequest.Builder().build());
    }
    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
    public void addNativeExpressAds() {
        // Loop through the items array and place a new Native Express ad in every ith position in
        // the items List.
        for (int i = 0; i <= objects.size(); i += ITEMS_PER_AD) {
            final NativeExpressAdView adView = new NativeExpressAdView(getActivity());
            // add một item ADS vào list item
            objects.add(i, adView);
        }
    }
    private LayoutInflater layoutInflater;

    protected abstract void startParser();
    public void cantLoadData() {
        showTextNull();
        tVnull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.isOnline(getContext())){
                    showProgress();
                    loadData();
                }else {
                    final Snackbar snackbar=Snackbar.make(recyclerView, "Vui lòng bật kết nối internet!",Snackbar.LENGTH_SHORT);

                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    snackbar.setAction("Bật wifi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                            showProgress();
                            snackbar.dismiss();
                           loadData();
                        }
                    });
                    snackbar.show();


                }
            }
        });
    }
    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater=inflater;
        View view=inflater.inflate(R.layout.layout_frame_fragment,container,false);
        initView(view);
        return view;
    }
    public void showRecircleView() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tVnull.setVisibility(View.GONE);
    }
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tVnull.setVisibility(View.GONE);
    }
    public void showTextNull() {
        progressBar.setVisibility(View.GONE);
        tVnull.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void loadData() {
        if (!MainActivity.wifiIsEnable(getActivity())){
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("Cảnh báo !");
            builder.setMessage("Bạn đang sử dụng dữ liệu di động.\n" +
                    "Tránh tiêu tốn dữ liệu.\nTôi khuyên bạn nên dùng mạng wifi để sử dụng.");
            builder.setPositiveButton("Bật wifi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
                    if (!MainActivity.wifiIsEnable(getActivity())) wifiManager.setWifiEnabled(true);
                    Toast.makeText(getActivity(), "Đã bật wifi", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Tiếp tục", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (MainActivity.isOnline(getContext())){
                        showProgress();
                        startParser();
                        Intent intent1=new Intent(getContext(), MyService.class);
                        getActivity().startService(intent1);
                    }else cantLoadData();
                }
            });
            builder.show();
        }else{
            if (MainActivity.isOnline(getContext())){
                showProgress();
                startParser();
                Intent intent1=new Intent(getContext(), MyService.class);
                getActivity().startService(intent1);
            }else cantLoadData();
        }

    }
    public void initView(View view) {
        sqLiteManager=new SQLiteManager(getContext());
        try {

            sv= (SinhVien) getArguments().getSerializable(MainActivity.SINH_VIEN);
        }catch (NullPointerException e){}
        pullRefreshLayout= (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        progressBar= (ProgressBar) view.findViewById(R.id.pg_loading);
        tVnull= (TextView) view.findViewById(R.id.text_null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        pullRefreshLayout.setRefreshing(false);
        refesh();
        checkDatabase();
    }
    public ListActivity getListActivity() {
        return listActivity;
    }
    public void setListActivity(ListActivity listActivity) {
        this.listActivity = listActivity;
    }
    private ListActivity listActivity;
    public abstract void refesh();
    public abstract void setRecyclerView();
    public abstract void checkDatabase();












}
