package com.xiaocoder.android_ui_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaocoder.android_ui.view.xc.XCWebView;
/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class WebViewActivity extends AppCompatActivity {

    private XCWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (XCWebView) findViewById(R.id.id_webView);

        if (webView != null) {
            webView.setUrl("http://www.baidu.com");
            webView.load();
        }
    }
}
