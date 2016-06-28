package com.xiaocoder.android_test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaocoder.android_ui.dialog.XCSystemHDialog;
import com.xiaocoder.android_xcfw.function.adapter.XCBaseAdapter;
import com.xiaocoder.android_xcfw.function.thread.XCExecutor;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilContacts;
import com.xiaocoder.android_xcfw.util.UtilView;
import com.xiaocoder.android_test_middle.Image;
import com.xiaocoder.android_test_middle.base.BaseActivity;

import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ContactsActivity extends BaseActivity {
    private ListView contacts_listview;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initWidgets();
    }

    class ContactsAdapter extends XCBaseAdapter<UtilContacts.XCContactModel> {

        public ContactsAdapter(Context context, List<UtilContacts.XCContactModel> list) {
            super(context, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            bean = list.get(position);

            ContactViewHolder holder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_contact_item, null);
                holder = new ContactViewHolder();
                holder.textview = (TextView) convertView.findViewById(R.id.contact_item);
                holder.imageview = (ImageView) convertView.findViewById(R.id.contact_imageview);
                convertView.setTag(holder);

            } else {
                holder = (ContactViewHolder) convertView.getTag();
            }

            // 获取和设置控件的显示值
            holder.textview.setText(bean.name + "--" + bean.email + "--" + bean.phone_number);
            Image.displayImage("http://www.baidu.com/img/bdlogo.png", holder.imageview);

            return convertView;

        }

        class ContactViewHolder {
            TextView textview;
            ImageView imageview;
        }
    }

    public void initWidgets() {
        // listview
        contacts_listview = getViewById(R.id.contacts_list);
        UtilView.setListViewStyle(contacts_listview, null, 1, false);


        final XCSystemHDialog dialog = new XCSystemHDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        XCExecutor.getCache().execute(new Runnable() {
            @Override
            public void run() {
                // 获取联系人
                final List<UtilContacts.XCContactModel> list = UtilContacts.getContacts(ContactsActivity.this);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        XCLog.i(list);

                        // 创建adapter
                        ContactsAdapter adpater = new ContactsAdapter(ContactsActivity.this, list);
                        // 设置adapter
                        contacts_listview.setAdapter(adpater);

                        dialog.dismiss();
                    }
                });
            }
        });


    }

}
