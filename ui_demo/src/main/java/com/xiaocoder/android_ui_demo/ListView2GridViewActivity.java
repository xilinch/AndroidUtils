package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaocoder.android_ui.view.pf.gridviewadapter.PFGridViewAdapter;

import java.util.ArrayList;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ListView2GridViewActivity extends Activity {

    ListView xc_listview_2_gridview;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2_grid_view);

        initWidgets();
        setListeners();

        data = getData();

        PFGridViewAdapter adapter = new PFGridViewAdapter(this, 2, 5, 5, new PFGridViewAdapter.IGridViewCallback() {
            @Override
            public View getGridItemView(final int position, View convertView, ViewGroup parent, LinearLayout rowLayout, Object item) {
                String bean = data.get(position);
                if (convertView == null) {
                    TextView textView = new TextView(ListView2GridViewActivity.this);
                    textView.setTextSize(22);
                    textView.setText(bean);
                    convertView = textView;
                } else {
                    ((TextView) convertView).setText(bean);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(ListView2GridViewActivity.this, position + "----" + data.get(position), Toast.LENGTH_SHORT).show();

                    }
                });
                return convertView;
            }
        });

        adapter.changeData(data);

        xc_listview_2_gridview.setAdapter(adapter);


    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<String>();
        String[] strs = {" 0 ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 00 ", " 11 ", " 22 ", " 33 ", " 44 ", " 55 ", " 66 ", " 77 ",
                " 0 ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 000 ", " 111 ", " 222 ", " 333 ", " 444 ", " 555 ",
                " 666 ", " 777 "};
        for (String str : strs) {
            data.add(str);
        }
        return data;
    }

    public void initWidgets() {
        xc_listview_2_gridview = (ListView) findViewById(R.id.id_listview_2_gridview);
    }

    public void setListeners() {
        xc_listview_2_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 无效
                Toast.makeText(ListView2GridViewActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
