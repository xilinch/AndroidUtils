package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class DialogSytleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_sytle);
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.width = 60;
        wl.height = 60;
        wl.format = PixelFormat.TRANSLUCENT;
        wl.type = WindowManager.LayoutParams.TYPE_TOAST;
        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        ;
        getWindow().setAttributes(wl);
    }

}
