package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xiaocoder.android_ui.view.pf.PFHorizontalListView;
import com.xiaocoder.android_xcfw.function.adapter.XCAdapterTest;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class HListViewActivity extends Activity {

    PFHorizontalListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlist_view);

        listview = (PFHorizontalListView) findViewById(R.id.xc_id_horizontallistview);
        listview.setAdapter(new XCAdapterTest(this, null));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAtPosition = parent.getItemAtPosition(position);
                Toast.makeText(HListViewActivity.this, itemAtPosition + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}