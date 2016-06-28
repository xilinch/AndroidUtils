package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;


import com.xiaocoder.android_ui.view.pf.PFPagerSlidingTab;
import com.xiaocoder.android_xcfw.function.adapter.XCAdapterViewPager;

import java.util.ArrayList;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class PagerSlidingTabActivity extends Activity {
    PFPagerSlidingTab pagerTab;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_sliding_tab);
        initWidgets();
    }

    public void initWidgets() {
        pagerTab = (PFPagerSlidingTab) findViewById(R.id.test_id_pager_sliding_tab);
        viewPager = (ViewPager) findViewById(R.id.test_view_pager);
        setTabsValue();
    }

    private void setTabsValue() {
        ArrayList<View> list = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        getData(list, titles);

        viewPager.setAdapter(new XCAdapterViewPager(list, titles));
        pagerTab.setViewPager(viewPager);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        // 设置Tab是自动填充满屏幕的 ，如果是铺满全屏传true
        pagerTab.setShouldExpand(false);
        // 设置Tab的分割线是透明的
        pagerTab.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        pagerTab.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, displayMetrics));
        // 设置Tab底部线的padding
        pagerTab.setUnderlinePadding((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics));
        // 设置Tab Indicator的高度
        pagerTab.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, displayMetrics));
        // 设置Tab标题文字的大小
        pagerTab.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 15, displayMetrics));
        // 设置Tab Indicator的颜色
        pagerTab.setIndicatorColor(Color.parseColor("#20aae0"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        pagerTab.setSelectedTextColor(Color.parseColor("#20aae0"));
        // 取消点击Tab时的背景色
        pagerTab.setTabBackground(0);
    }

    private void getData(ArrayList<View> list, ArrayList<String> titles) {
        for (int i = 0; i < 3; i++) {
            View view = new View(this);
            if (i == 0) {
                view.setBackgroundColor(0xff00ff00);
            } else if (i == 1) {
                view.setBackgroundColor(0xffffff00);
            } else if (i == 2) {
                view.setBackgroundColor(0xff00ffff);
            } else if (i == 3) {
                view.setBackgroundColor(0xff0000ff);
            } else if (i == 4) {
                view.setBackgroundColor(0xffff00ff);
            }
            titles.add(i + "标题");
            list.add(view);
        }
    }

}
