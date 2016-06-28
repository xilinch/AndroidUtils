package com.xiaocoder.android_ptr_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xiaocoder.android_ptr.XCIRefreshHandler;
import com.xiaocoder.android_ptr.XCListRefreshLayout;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ListRefreshActivity extends Activity {

    private TestAdapter adapter;
    private XCListRefreshLayout xcListRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list_refresh);
        super.onCreate(savedInstanceState);

        initWidgets();

        setListeners();

        reqeust();
    }


    public void initWidgets() {

        adapter = new TestAdapter(ListRefreshActivity.this, null);
        xcListRefreshLayout = (XCListRefreshLayout) findViewById(R.id.xc_id_list_refreshlayout);
        xcListRefreshLayout.getListView().setAdapter(adapter);
        // http请求中获取，这里为模拟数据
        xcListRefreshLayout.setBgZeroHintInfo("无数据", "点击刷新", R.drawable.ic_launcher);

    }

    public void reqeust() {
        xcListRefreshLayout.setTotalPage("3");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟网络加载的延迟
                xcListRefreshLayout.updateListAdd(UtilData.getList(), adapter);
                xcListRefreshLayout.completeRefresh(true);
            }
        }, 2000);
    }

    public void setListeners() {
        xcListRefreshLayout.setHandler(new XCIRefreshHandler() {
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
