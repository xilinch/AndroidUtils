package com.xiaocoder.android_test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaocoder.android_test_middle.base.BaseActivity;
import com.xiaocoder.android_xcfw.function.adapter.XCBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class BaseAdapterActivity extends BaseActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseadapter);

        listview = getViewById(R.id.id_listview);
        listview.setAdapter(new DemoAdapter(this, getData()));
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    class DemoAdapter extends XCBaseAdapter<String> {

        public DemoAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            bean = list.get(position);

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.view_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textview.setText(bean);
            return convertView;
        }

        class ViewHolder {
            TextView textview;

            public ViewHolder(View view) {
                this.textview = (TextView) view.findViewById(R.id.id_content);
            }
        }
    }
}
