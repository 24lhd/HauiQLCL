package com.lhd.object;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.lhd.activity.MainActivity;

/**
 * Created by d on 28/01/2017.
 */

public class ADSFull {
    public ADSFull(Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(MainActivity.AD_UNIT_ID_FULL);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showADSFull();
            }
        });
        requestNewInterstitial();
    }
    private InterstitialAd mInterstitialAd;;
    public  void showADSFull() {
        if (mInterstitialAd.isLoaded()) mInterstitialAd.show();
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

}
