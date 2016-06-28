package com.xiaocoder.android_ptr_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.xiaocoder.android_ptr.XCIRefreshHandler;
import com.xiaocoder.android_ptr.XCMaterialListPinRefreshLayout;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ListMaterialActivity extends Activity {

    private TestAdapter adapter;
    private XCMaterialListPinRefreshLayout xcListRefreshLayout;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list_materail);
        super.onCreate(savedInstanceState);

        initWidgets();
        setListeners();
        reqeust();
    }

    public void initWidgets() {

        adapter = new TestAdapter(this, null);
        xcListRefreshLayout = (XCMaterialListPinRefreshLayout) findViewById(R.id.xc_id_material_list_refreshlayout);

        listview = (ListView) xcListRefreshLayout.getListView();

        xcListRefreshLayout.getListView().setAdapter(adapter);
        // http请求中获取，这里为模拟数据
        xcListRefreshLayout.setBgZeroHintInfo("无数据", "点击刷新", R.drawable.ic_launcher);
    }

    public static String url = "http://yyf.7lk.com/api/goods/category-goods-list?userId=399&token=c2a623a6f3c7d6e1a126f1655c13b3f0&_m=&catId=515&_v=1.0.0&page=1&num=20&ts=1438155912203&_c=&_p=android&sig=96702f0846e8cb5d2701f5e39f28ba95";

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
