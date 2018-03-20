package com.teethen.xsdk.activity;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xdialog.nicedialog.BaseNiceDialog;
import com.teethen.sdk.xdialog.nicedialog.NiceDialog;
import com.teethen.sdk.xdialog.nicedialog.ViewConvertListener;
import com.teethen.sdk.xdialog.nicedialog.ViewHolder;
import com.teethen.sdk.xutil.ShareUtil;
import com.teethen.xsdk.R;
import com.teethen.xsdk.common.CustomTabs;
import com.teethen.xsdk.common.Tags;

import java.lang.reflect.Method;

public class WebViewActivity extends BaseActivity {

    private String OnlineOfficeUrl = "https://view.officeapps.live.com/op/view.aspx";//微软联机查看office文档url

    private String title = "";
    private String url = "";
    private String html = "";
    private WebView mWebView;
    private ProgressBar mWebProgressBar;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initData();
        initToolbar();
        initWebView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview, menu);// 右上角菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if(id == R.id.action_webview_share) {
            showShareToDialog();
        } else if (id == R.id.action_webview_small_font) {
            settings.setTextZoom(XConstant.FONTSIZE_SMALLER);
        } else if (id == R.id.action_webview_normal_font) {
            settings.setTextZoom(XConstant.FONTSIZE_NORMAL);
        } else if (id == R.id.action_webview_medium_font) {
            settings.setTextZoom(XConstant.FONTSIZE_MEDIUM);
        } else if (id == R.id.action_webview_large_font) {
            settings.setTextZoom(XConstant.FONTSIZE_LARGE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { }
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        //return super.onPrepareOptionsPanel(view, menu);
        return true;
    }

    private void initData() {
        url = getIntent().getStringExtra(Tags.WEBVIEW_URL);
        title = getIntent().getStringExtra(Tags.WEBVIEW_TITLE);
        html = getIntent().getStringExtra(Tags.WEBVIEW_HTML);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.web_toolbar);
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        initToolBar(toolbar, title);
    }

    //初始化浏览器
    public void initWebView() {
        mWebProgressBar = (ProgressBar) findViewById(R.id.web_progressBar);
        mWebView = (WebView) findViewById(R.id.web_view);

        settings = mWebView.getSettings();
        //缓存设置
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        //缩放设置
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        //字号设置
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        settings.setTextZoom(XConstant.FONTSIZE_NORMAL);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//SINGLE_COLUMN
        settings.setLoadWithOverviewMode(true);
        //JavaScript设置
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setJavaScriptEnabled(true);
        //settings.setJavaScriptCanOpenWindowsAutomatically(true);//javascript能执行自动打开窗口操作

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWebView.setWebChromeClient(new WthWebChrome());
        mWebView.setWebViewClient(new WthWebClient());

        if (!TextUtils.isEmpty(html)) {
            //mWebView.loadData(html, Constant.MIME_HTML, Constant.CHARSET_UTF8);
            mWebView.loadDataWithBaseURL(null, html, XConstant.MIME_HTML, XConstant.CHARSET_UTF8,null);
        } else {
            if (!TextUtils.isEmpty(url)) {
                String[] officeTypes = getResources().getStringArray(R.array.office_type);
                for (String suffix : officeTypes) {
                    if (url.endsWith(suffix)) {
                        url = OnlineOfficeUrl + "?src=" + url;
                        break;
                    }
                }
                mWebView.loadUrl(url);
            } else {
                showToast("无效的url，请尝试使用系统浏览器打开。");
                CustomTabs.openUrl(this, url);
            }
        }
    }

    private class WthWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mWebProgressBar.setVisibility(View.VISIBLE);
            mWebProgressBar.setProgress(newProgress);
        }
    }

    private class WthWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mWebProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
            else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setTextSize(int incrementSize) {
        settings.setTextZoom(XConstant.FONTSIZE_NORMAL + incrementSize);
    }

    //分享
    private String pkgName = "";
    private String actName = "";
    private void showShareToDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.layout_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.share_qq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkgName = ShareUtil.PKG_TencentQQ;
                                actName = ShareUtil.ACT_TencentQQ;
                                doSharing(pkgName, actName, false);
                            }
                        });

                        holder.setOnClickListener(R.id.share_qqzone, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkgName = ShareUtil.PKG_TencentQQZone;
                                actName = ShareUtil.ACT_TencentQQZone;
                                doSharing(pkgName, actName, false);
                            }
                        });

                        holder.setOnClickListener(R.id.share_wechat, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkgName = ShareUtil.PKG_WeChat;
                                actName = ShareUtil.ACT_WeChat;
                                doSharing(pkgName, actName, false);
                            }
                        });

                        holder.setOnClickListener(R.id.share_wechat_pyq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkgName = ShareUtil.PKG_WeChatFriends;
                                actName = ShareUtil.ACT_WeChatFriends;
                                doSharing(pkgName, actName, false);
                            }
                        });

                        holder.setOnClickListener(R.id.share_sina_weibo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pkgName = ShareUtil.PKG_SinaWeibo;
                                actName = ShareUtil.ACT_SinaWeibo;
                                doSharing(pkgName, actName, false);
                            }
                        });

                        holder.setOnClickListener(R.id.share_by_system, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doSharing(pkgName, actName, true);
                            }
                        });
                    }
                })
                .setDimAmount(0.3f)
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }

    private void doSharing(String pkgName, String actName, boolean bySystemShare) {
        ComponentName componentName = null;
        if (!bySystemShare) {
            componentName = new ComponentName(pkgName, actName);
        }
        ShareUtil.shareText(this, "分享内容", title, url, componentName);
    }

}
