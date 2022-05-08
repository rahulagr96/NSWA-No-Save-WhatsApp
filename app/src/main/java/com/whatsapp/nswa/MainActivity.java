package com.whatsapp.nswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    EditText numEditText, msgEditText;
    Button submitBtn, submitBtn2;
    CountryCodePicker codePicker;
    ImageButton pasteBtn;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = new AdView(this);

        mAdView.setAdSize(AdSize.BANNER);

        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        // TODO: Add adView to your view hierarchy.
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdEvents();

        numEditText = findViewById(R.id.numEditText);
        msgEditText = findViewById(R.id.msgEditText);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn2 = findViewById(R.id.submitBtn2);
        codePicker = findViewById(R.id.codePicker);
        pasteBtn = findViewById(R.id.pastebutton);

        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        pasteBtn.setOnClickListener(view -> {
            ClipData pasteData = manager.getPrimaryClip();
            ClipData.Item item = pasteData.getItemAt(0);
            String paste = item.getText().toString();
            if (paste.matches("^[0-9]*$") && paste.length() > 2) {
                numEditText.setText(paste);
            }
        });

        submitBtn.setOnClickListener(view -> {
            String phoneNum = codePicker.getSelectedCountryCode() + numEditText.getText().toString();
            String text = "";
            String msg = msgEditText.getText().toString();
            if (msg != null) {
                if (msg.contains(" ")) {
                    msg.replace(" ", "%20");
                }
                text = "&text=" + msg;
            }
            String _uri = "http://api.whatsapp.com/send?phone=" + phoneNum + text;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.whatsapp");
            intent.setData(Uri.parse(_uri));
            startActivity(intent);

        });

        submitBtn2.setOnClickListener(view -> {
            String phoneNum = codePicker.getSelectedCountryCode() + numEditText.getText().toString();
            String text = "";
            String msg = msgEditText.getText().toString();
            if (msg != null) {
                if (msg.contains(" ")) {
                    msg.replace(" ", "%20");
                }
                text = "&text=" + msg;
            }
            String _uri = "http://api.whatsapp.com/send?phone=" + phoneNum + text;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.whatsapp.w4b");
            intent.setData(Uri.parse(_uri));
            startActivity(intent);

        });
    }

    private void AdEvents() {
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }
}