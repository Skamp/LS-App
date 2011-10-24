package com.lsn.LoadSensing;

import greendroid.app.GDActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

public class LSLicenseActivity extends GDActivity {

    public static final String EXTRA_CONTENT_URL = "com.lsn.LoadSensing.extra.CONTENT_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String contentUrl = getIntent().getStringExtra(EXTRA_CONTENT_URL);
        if (!TextUtils.isEmpty(contentUrl)) {
            setActionBarContentView(R.layout.license);
            final WebView webView = (WebView) findViewById(R.id.license);
                webView.loadUrl(contentUrl);

        }
    }

}