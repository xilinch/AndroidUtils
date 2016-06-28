package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;


import com.xiaocoder.android_ui.view.open.OPNoScrollListview;
import com.xiaocoder.android_xcfw.function.adapter.XCAdapterTest;
import com.xiaocoder.android_xcfw.function.adapter.XCAdapterViewPager;

import java.util.ArrayList;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ScrollActivity extends Activity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        initWidgets();
    }

    public void initWidgets() {
        pager = (ViewPager) findViewById(R.id.viewpager);
        ArrayList<View> list = new ArrayList<View>();
        View view = LayoutInflater.from(this).inflate(R.layout.viewpager_layout, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout3, null);

        OPNoScrollListview listview_refresh = (OPNoScrollListview) view3.findViewById(R.id.listview_refresh);

        // addHeadView
        View item = LayoutInflater.from(this).inflate(R.layout.viewpager_layout3_item, null);
        View item2 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout3_item2, null);
        View item3 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout3_item2, null);
        listview_refresh.addHeaderView(item);
        listview_refresh.addHeaderView(item2);

        listview_refresh.setAdapter(new XCAdapterTest(this, null));

        list.add(view);
        list.add(view2);
        list.add(view3);

        XCAdapterViewPager adapter = new XCAdapterViewPager(list, null);
        pager.setAdapter(adapter);
    }

}
