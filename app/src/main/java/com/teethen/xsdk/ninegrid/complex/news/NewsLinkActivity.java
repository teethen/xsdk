package com.teethen.xsdk.ninegrid.complex.news;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;

import butterknife.BindView;

public class NewsLinkActivity extends BaseActivity {

    public static String TAG_LINK_URL = "link_url";

    @BindView(R.id.webView) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_link);

        String link = getIntent().getStringExtra(TAG_LINK_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(link);
    }
}
