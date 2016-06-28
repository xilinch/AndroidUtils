package com.xiaocoder.android_ptr_demo;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.xiaocoder.android_ptr.XCGridRefreshLayout;
import com.xiaocoder.android_ptr.XCIRefreshHandler;
import com.xiaocoder.android_xcfw.util.UtilView;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class GridRefreshActivity extends Activity {

    private TestAdapter adapter;
    private XCGridRefreshLayout xcGridRefreshLayout;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_grid_refresh);
        super.onCreate(savedInstanceState);
        // 配置了autorefresh属性
        // reqeust();

        initWidgets();
        setListeners();
    }

    public void initWidgets() {

        adapter = new TestAdapter(GridRefreshActivity.this, null);
        xcGridRefreshLayout = (XCGridRefreshLayout) findViewById(R.id.xc_id_grid_refreshlayout);

        gridview = (GridView) xcGridRefreshLayout.getListView();
        UtilView.setGridViewStyle(gridview, false, 1, 1, 2);

        xcGridRefreshLayout.getListView().setAdapter(adapter);
        // 零数据背景
        xcGridRefreshLayout.setBgZeroHintInfo("无数据", "点击刷新", R.drawable.ic_launcher);
    }

    public static String url = "http://yyf.7lk.com/api/goods/category-goods-list?userId=399&token=c2a623a6f3c7d6e1a126f1655c13b3f0&_m=&catId=515&_v=1.0.0&page=1&num=20&ts=1438155912203&_c=&_p=android&sig=96702f0846e8cb5d2701f5e39f28ba95";

    public void reqeust() {
        xcGridRefreshLayout.setTotalPage("3");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟网络加载的延迟
                xcGridRefreshLayout.updateListAdd(UtilData.getList(), adapter);
                xcGridRefreshLayout.completeRefresh(true);
            }
        }, 2000);
    }

    public void setListeners() {
        xcGridRefreshLayout.setHandler(new XCIRefreshHandler() {
            @Override
            public boolean canRefresh() {
                return true;
            }

            @Override
            public boolean canLoad() {
                return true;
            }

            @Override
            public void refresh(View view, int request_page) {
                reqeust();
            }

            @Override
            public void load(View view, int request_page) {
                reqeust();
            }
        });

    }


}
