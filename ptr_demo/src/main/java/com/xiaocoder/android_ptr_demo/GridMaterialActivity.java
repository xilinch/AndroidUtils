package com.xiaocoder.android_ptr_demo;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.xiaocoder.android_ptr.XCIRefreshHandler;
import com.xiaocoder.android_ptr.XCMaterialGridRefreshLayout;
import com.xiaocoder.android_xcfw.util.UtilView;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class GridMaterialActivity extends Activity {

    private TestAdapter adapter;
    private XCMaterialGridRefreshLayout xcGridRefreshLayout;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_grid_material);
        super.onCreate(savedInstanceState);
        // 配置了autorefresh属性
        // reqeust();

        initWidgets();
        setListeners();
    }

    public void initWidgets() {

        adapter = new TestAdapter(this, null);
        xcGridRefreshLayout = (XCMaterialGridRefreshLayout) findViewById(R.id.xc_id_material_grid_refreshlayout);

        gridview = (GridView) xcGridRefreshLayout.getListView();
        UtilView.setGridViewStyle(gridview, false, 1, 1, 2);

        xcGridRefreshLayout.getListView().setAdapter(adapter);
        // 零数据背景
        xcGridRefreshLayout.setBgZeroHintInfo("无数据", "点击刷新", R.drawable.ic_launcher);
    }


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
