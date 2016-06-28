package com.xiaocoder.android_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.xiaocoder.android_ui.dialog.XCRotateDialog;
import com.xiaocoder.android_xcfw.function.helper.XCCleanCacheHelper;
import com.xiaocoder.android_xcfw.io.XCIOAndroid;
import com.xiaocoder.android_test_middle.base.BaseActivity;
import com.xiaocoder.android_test_middle.config.ConfigFile;

import java.io.File;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ClearCacheActivity extends BaseActivity implements View.OnClickListener {

    private Button clear;
    private XCCleanCacheHelper helper;
    private File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        clear = getViewById(R.id.clear);
        // 如果没有该dir会创建再返回，有则返回该dir
        dir = XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.APP_ROOT);
        helper = new XCCleanCacheHelper(new XCRotateDialog(this, R.drawable.ic_launcher), false);
    }

    public void setListeners() {
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        helper.removeFileAsyn(dir);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.quit();
    }
}