package com.lhd.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.ken.hauiclass.R;
import com.lhd.activity.ListActivity;
import com.lhd.activity.MainActivity;
import com.lhd.database.SQLiteManager;
import com.lhd.object.SinhVien;

import java.util.ArrayList;

import static com.lhd.activity.MainActivity.ITEMS_PER_AD;
import static com.lhd.activity.MainActivity.isOnline;

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
    private  WebView webView;
    private  AlertDialog.Builder builder;
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
    protected SQLiteManager sqLiteManager;
    protected PullRefreshLayout pullRefresh;
    protected SinhVien sv;
    protected ArrayList<Object> objects;

    public void showAlert(final String title, String html, final String titleSMS, final String SMS, final Activity activity) {
        showADSFull();
        builder = new AlertDialog.Builder(getActivity());
        webView = new WebView(getActivity());
        builder.setTitle(title);
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        builder.setView(webView);
        builder.setNeutralButton("Chia sẻ SMS", null);
        builder.setPositiveButton("Chia sẻ ảnh", null);
        AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
//                showADSFull();
            }
        });
        mAlertDialog.show();
        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setTextColor(getResources().getColor(R.color.colorPrimary));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.sreenShort(view, activity);
            }
        });
        Button c = mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        c.setTextColor(getResources().getColor(R.color.colorPrimary));
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.shareText(activity, titleSMS, SMS);
            }
        });

    }
    public void loadNativeExpressAds(final String id, final int height) {

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final float density = getActivity().getResources().getDisplayMetrics().density;
                    for (int i = 0; i <= objects.size(); i += ITEMS_PER_AD) {
                        NativeExpressAdView adView = (NativeExpressAdView) objects.get(i);
                        AdSize adSize = null;
                         adSize = new AdSize((int) (recyclerView.getWidth()/density),height);

                        adView.setAdSize(adSize);
                        adView.setAdUnitId(id);
                    }
                    if (isOnline(getActivity())) loadNativeExpressAd(0);
                }catch (NullPointerException e){

                }
            }
        });

    }
    public void loadNativeExpressAd(final int index) {
        Log.e("faker","show "+index);
        if (index>= objects.size()) {
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
                Log.e("faker"," onAdLoaded "+index);
                loadNativeExpressAd(index + ITEMS_PER_AD);
                adView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("faker","loi onAdFailedToLoad "+index);
                loadNativeExpressAd(index + ITEMS_PER_AD);
                adView.setVisibility(View.GONE);
            }
        });
        try {
             adView.loadAd(new AdRequest.Builder().build());
        }catch (IllegalStateException e){
            Log.e("faker","loi show "+index);
        }
    }
    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
    public void addNativeExpressAds(String adUnitIdKqht, int nativeExpressAdHeight) {
        for (int i = 0; i <= objects.size(); i += ITEMS_PER_AD) {
            final NativeExpressAdView adView = new NativeExpressAdView(getActivity());
            objects.add(i, adView);
        }
      loadNativeExpressAds(adUnitIdKqht,nativeExpressAdHeight);
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

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        tVnull.setVisibility(View.GONE);
    }
    public void showTextNull() {
        pullRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        tVnull.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    public void loadData() {
//        if (!MainActivity.wifiIsEnable(getActivity())&&MainActivity.isOnline(getActivity())){
//            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//            builder.setTitle("Cảnh báo !");
//            builder.setMessage("Bạn đang sử dụng dữ liệu di động.\n" +
//                    "Tránh tiêu tốn dữ liệu.\nTôi khuyên bạn nên dùng mạng wifi để sử dụng.");
//            builder.setPositiveButton("Bật wifi", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                    WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
//                    if (!MainActivity.wifiIsEnable(getActivity())) wifiManager.setWifiEnabled(true);
//                    Toast.makeText(getActivity(), "Đã bật wifi", Toast.LENGTH_SHORT).show();
//                }
//            });
//            builder.setNegativeButton("Tiếp tục", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                    if (MainActivity.isOnline(getContext())){
//                        showProgress();
//                        startParser();
//                    }else cantLoadData();
//                }
//            });
//            builder.show();
//        }
        if (MainActivity.isOnline(getContext())){
            showProgress();
            startParser();
        }else cantLoadData();

    }
    private  InterstitialAd mInterstitialAd;;
    public  void showADSFull() {
       if (mInterstitialAd.isLoaded()) mInterstitialAd.show();
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    public void initView(View view) {
        pullRefresh= (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(MainActivity.AD_UNIT_ID_FULL);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showADSFull();
            }
        });
        requestNewInterstitial();
        sqLiteManager=new SQLiteManager(getContext());
        try {sv= (SinhVien) getArguments().getSerializable(MainActivity.SINH_VIEN);
        }catch (NullPointerException e){}
        progressBar= (ProgressBar) view.findViewById(R.id.pg_loading);
        tVnull= (TextView) view.findViewById(R.id.text_null);
        recyclerView= (RecyclerView) view.findViewById(R.id.recle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkDatabase();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public ListActivity getListActivity() {
        return listActivity;
    }
    public void setListActivity(ListActivity listActivity) {
        this.listActivity = listActivity;
    }
    private ListActivity listActivity;
    public abstract void setRecyclerView();
    public abstract void checkDatabase();
    public void showRecircleView() {
        pullRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tVnull.setVisibility(View.GONE);
    }








}
