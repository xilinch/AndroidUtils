package com.xiaocoder.android_test;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TitleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题,一定要在setContentView的前面设置
        // setContentView(R.layout.main);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏显示

        // setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);//在super之前设置
    }


}
