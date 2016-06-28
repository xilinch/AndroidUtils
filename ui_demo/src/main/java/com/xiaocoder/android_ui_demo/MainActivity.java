package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaocoder.android_ui_demo.drawer.DrawerActivity;
import com.xiaocoder.android_ui_demo.pickerview.PickerViewActivity;
import com.xiaocoder.android_ui_demo.pop.PopActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button expandlistview;
    private Button pop;
    private Button circleProgressBar;
    private Button dialogActivity;
    private Button lineProgressBarActivity;
    private Button progressViewActivity;
    private Button drawer;
    private Button pickerview;
    private Button switchButton;
    private Button pagerSliding;
    private Button roundImage;
    private Button listview2gridview;
    private Button hListview;
    private Button clipImage;
    private Button doubleCircleButton;
    private Button scrollview;
    private Button imagesZoom;
    private Button swipeAdapter;
    private Button tablayout;
    private Button webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setListeners();

    }

    public void initWidgets() {
        expandlistview = (Button) findViewById(R.id.id_expandlistview);
        pop = (Button) findViewById(R.id.id_pop);

        circleProgressBar = (Button) findViewById(R.id.id_circleProgressBar);
        dialogActivity = (Button) findViewById(R.id.id_dialogActivity);
        lineProgressBarActivity = (Button) findViewById(R.id.id_lineProgressBarActivity);
        progressViewActivity = (Button) findViewById(R.id.id_progressViewActivity);
        drawer = (Button) findViewById(R.id.id_drawer);
        pickerview = (Button) findViewById(R.id.id_pickerview);
        switchButton = (Button) findViewById(R.id.id_switchButton);
        pagerSliding = (Button) findViewById(R.id.id_pagerSliding);
        roundImage = (Button) findViewById(R.id.id_roundImage);
        listview2gridview = (Button) findViewById(R.id.id_listview2gridview);
        hListview = (Button) findViewById(R.id.id_hListview);
        clipImage = (Button) findViewById(R.id.id_clipImage);
        doubleCircleButton = (Button) findViewById(R.id.id_doubleCircleButton);
        scrollview = (Button) findViewById(R.id.id_scrollview);
        imagesZoom = (Button) findViewById(R.id.id_imagesZoom);
        swipeAdapter = (Button) findViewById(R.id.id_swipeAdapter);
        tablayout = (Button) findViewById(R.id.id_tablayout);
        webView = (Button) findViewById(R.id.id_webView);

    }

    public void setListeners() {
        expandlistview.setOnClickListener(this);
        pop.setOnClickListener(this);
        circleProgressBar.setOnClickListener(this);
        dialogActivity.setOnClickListener(this);
        lineProgressBarActivity.setOnClickListener(this);
        progressViewActivity.setOnClickListener(this);
        drawer.setOnClickListener(this);
        pickerview.setOnClickListener(this);
        switchButton.setOnClickListener(this);
        pagerSliding.setOnClickListener(this);
        roundImage.setOnClickListener(this);
        listview2gridview.setOnClickListener(this);
        hListview.setOnClickListener(this);
        clipImage.setOnClickListener(this);
        doubleCircleButton.setOnClickListener(this);
        scrollview.setOnClickListener(this);
        imagesZoom.setOnClickListener(this);
        swipeAdapter.setOnClickListener(this);
        tablayout.setOnClickListener(this);
        webView.setOnClickListener(this);

        //UtilString.setLightAppendString("你好:123", expandlistview, "#ff00cccc");
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.id_expandlistview:
                intent = new Intent(this, ExpandListActivity.class);
                break;
            case R.id.id_pop:
                intent = new Intent(this, PopActivity.class);
                break;
            case R.id.id_circleProgressBar:
                intent = new Intent(this, CircleProgressBarActivity.class);
                break;
            case R.id.id_dialogActivity:
                intent = new Intent(this, DialogActivity.class);
                break;
            case R.id.id_lineProgressBarActivity:
                intent = new Intent(this, LineProgressBarActivity.class);
                break;
            case R.id.id_progressViewActivity:
                intent = new Intent(this, ProgressViewActivity.class);
                break;
            case R.id.id_drawer:
                intent = new Intent(this, DrawerActivity.class);
                break;
            case R.id.id_pickerview:
                intent = new Intent(this, PickerViewActivity.class);
                break;
            case R.id.id_switchButton:
                intent = new Intent(this, SwitchButtonActivity.class);
                break;
            case R.id.id_pagerSliding:
                intent = new Intent(this, PagerSlidingTabActivity.class);
                break;
            case R.id.id_roundImage:
                intent = new Intent(this, RoundImageViewActivity.class);
                break;
            case R.id.id_listview2gridview:
                intent = new Intent(this, ListView2GridViewActivity.class);
                break;
            case R.id.id_hListview:
                intent = new Intent(this, HListViewActivity.class);
                break;
            case R.id.id_clipImage:
                intent = new Intent(this, ClipImageActivity.class);
                break;
            case R.id.id_doubleCircleButton:
                intent = new Intent(this, DoubleCircleButtonActivity.class);
                break;
            case R.id.id_scrollview:
                intent = new Intent(this, ScrollActivity.class);
                break;
            case R.id.id_imagesZoom:
                intent = new Intent(this, ImagesZoomActivity.class);
                break;
            case R.id.id_swipeAdapter:
                intent = new Intent(this, SwipeAdapterActivity.class);
                break;
            case R.id.id_tablayout:
                intent = new Intent(this, TabActivity.class);
                break;
            case R.id.id_webView:
                intent = new Intent(this, WebViewActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
