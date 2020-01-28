package com.pinkhatapps.adbanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/*
 * Read the guide to know more about displaying AdMob ads:
 * https://developers.google.com/admob/android/quick-start
 *
 * Testing ads:
 * https://developers.google.com/admob/android/test-ads
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //This is mandatory
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);

        //This is optional
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.e("ADERR","Ad failed due to error: " + i);
            }
        });

        //Load the ad
        AdRequest.Builder builder = new AdRequest.Builder();
        //builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        //Replace the following id with your device id (find it in your Logcat output)
        builder.addTestDevice("5C021D0C1E0FC78317FC21620C79F5B2");
        AdRequest adRequest = builder.build();
        mAdView.loadAd(adRequest);

    }
}
