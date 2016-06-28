package com.xiaocoder.android_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaocoder.android_test.recyclerview.RecycleViewActivity;
import com.xiaocoder.android_test.recyclerview.WaterFallActivity;
import com.xiaocoder.android_test.stack.SearchActivity;
import com.xiaocoder.android_test_middle.base.BaseActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button timer;

    private Button clearcache;

    private Button contacts;

    private Button lineGc;

    private Button exception;

    private Button test;

    private Button photo;

    private Button seachRecoder;

    private Button baseAdapter;

    private Button recycleView;

    private Button recycleView_waterfall;

    private Button leakcanary;

    private Button propertyAnim;

    private Button handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        timer = getViewById(R.id.id_timer);
        clearcache = getViewById(R.id.id_clearcache);
        contacts = getViewById(R.id.id_contacts);
        lineGc = getViewById(R.id.id_lineGc);
        exception = getViewById(R.id.id_exception);
        test = getViewById(R.id.id_test);
        photo = getViewById(R.id.id_photo);
        seachRecoder = getViewById(R.id.id_seachRecoder);
        baseAdapter = getViewById(R.id.id_baseAdapter);
        recycleView = getViewById(R.id.id_recyclerView);
        recycleView_waterfall = getViewById(R.id.id_recycleView_waterfall);
        leakcanary = getViewById(R.id.id_leakcanary);
        propertyAnim = getViewById(R.id.id_propertyAnim);
        handler = getViewById(R.id.id_handler);
    }

    public void setListeners() {
        timer.setOnClickListener(this);
        clearcache.setOnClickListener(this);
        contacts.setOnClickListener(this);
        lineGc.setOnClickListener(this);
        exception.setOnClickListener(this);
        test.setOnClickListener(this);
        photo.setOnClickListener(this);
        seachRecoder.setOnClickListener(this);
        baseAdapter.setOnClickListener(this);
        recycleView.setOnClickListener(this);
        recycleView_waterfall.setOnClickListener(this);
        leakcanary.setOnClickListener(this);
        propertyAnim.setOnClickListener(this);
        handler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.id_timer:
                intent = new Intent(this, TimerActivity.class);
                break;
            case R.id.id_clearcache:
                intent = new Intent(this, ClearCacheActivity.class);
                break;
            case R.id.id_contacts:
                intent = new Intent(this, ContactsActivity.class);
                break;
            case R.id.id_lineGc:
                intent = new Intent(this, ActivityDestroyGC.class);
                break;
            case R.id.id_exception:
                intent = new Intent(this, ExceptionActivity.class);
                break;
            case R.id.id_test:
                intent = new Intent(this, TestActivity.class);
                break;
            case R.id.id_photo:
                intent = new Intent(this, CamareActivity.class);
                break;
            case R.id.id_seachRecoder:
                intent = new Intent(this, SearchActivity.class);
                break;
            case R.id.id_baseAdapter:
                intent = new Intent(this, BaseAdapterActivity.class);
                break;
            case R.id.id_recyclerView:
                intent = new Intent(this, RecycleViewActivity.class);
                break;
            case R.id.id_recycleView_waterfall:
                intent = new Intent(this, WaterFallActivity.class);
                break;
            case R.id.id_leakcanary:
                intent = new Intent(this, LeakCanaryActivity.class);
                break;
            case R.id.id_propertyAnim:
                intent = new Intent(this, PropertyAnimActivity.class);
                break;
            case R.id.id_handler:
                intent = new Intent(this, HandlerActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
