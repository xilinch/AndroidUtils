package com.xiaocoder.android_ui_demo.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaocoder.android_ui_demo.R;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class DrawerActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContent();
        initDrawer();
    }

    private void initContent() {
        View content = LayoutInflater.from(this).inflate(R.layout.activity_drawer, null);
        setContentFrame(content);
    }

    public void initDrawer() {
        View drawer = LayoutInflater.from(this).inflate(R.layout.view_drawer, null);
        setDrawerFrame(drawer);
    }
}
