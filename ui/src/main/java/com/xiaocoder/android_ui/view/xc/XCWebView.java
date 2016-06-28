/**
 *
 */
package com.xiaocoder.android_ui.view.xc;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.xiaocoder.android_ui.R;
/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCWebView extends FrameLayout {

    private WebView webview;
    private String url;
    private int progressBarVisible = View.VISIBLE;
    private ProgressBar progressBar;
    private int backGroundColor = 0xffffffff;

    public XCWebView(Context context) {
        super(context);
        init(context);
    }

    public XCWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XCWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(com.xiaocoder.android_ui.R.layout.xc_l_view_webview, this, true);

        initWidgets();
        listeners();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void load() {
        webview.loadUrl(url);
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void setProgressBarVisible(int visible) {
        this.progressBarVisible = visible;
    }

    public String getUrl() {
        return url;
    }

    public WebView getWebview() {
        return webview;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @JavascriptInterface
    public void initWidgets() {

        webview = (WebView) findViewById(R.id.id_webview);

        progressBar = (ProgressBar) findViewById(R.id.id_progressBar);

        WebSettings wSettings = webview.getSettings();
        wSettings.setJavaScriptEnabled(true);
        wSettings.setBuiltInZoomControls(true);
        wSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.setBackgroundColor(backGroundColor);
    }

    public void listeners() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setProgress(0);
                progressBar.setVisibility(progressBarVisible);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setProgress(100);
                progressBar.setVisibility(View.GONE);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });
    }
}