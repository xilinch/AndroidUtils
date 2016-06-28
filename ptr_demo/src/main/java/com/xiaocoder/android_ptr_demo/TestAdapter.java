package com.xiaocoder.android_ptr_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaocoder.android_xcfw.function.adapter.XCBaseAdapter;

import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TestAdapter extends XCBaseAdapter<TestModel> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        bean = list.get(position);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_test_item, null);
            holder = new ViewHolder();
            holder.xc_id_adapter_test_textview = (TextView) convertView.findViewById(R.id.xc_id_adapter_test_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.xc_id_adapter_test_textview.setText(bean.getContent());
        return convertView;
    }

    public TestAdapter(Context context, List<TestModel> list) {
        super(context, list);
    }

    class ViewHolder {
        TextView xc_id_adapter_test_textview;
    }
}