package com.xiaocoder.android_test.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaocoder.android_test.R;
import com.xiaocoder.android_test_middle.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class RecycleViewActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;

    private RecycleAdapter adapter;

    private Button id_list;
    private Button id_grid;
    private Button id_waterfall_v;
    private Button id_waterfall_h;

    private DividerGridItemDecoration gridItemDecoration;
    private DividerListItemDecoration listItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        id_list = getViewById(R.id.id_list);
        id_grid = getViewById(R.id.id_grid);
        id_waterfall_v = getViewById(R.id.id_waterfall_v);
        id_waterfall_h = getViewById(R.id.id_waterfall_h);

        id_list.setOnClickListener(this);
        id_grid.setOnClickListener(this);
        id_waterfall_v.setOnClickListener(this);
        id_waterfall_h.setOnClickListener(this);

        recyclerView = getViewById(R.id.id_recyclerView);
        gridItemDecoration = new DividerGridItemDecoration(this);
        listItemDecoration = new DividerListItemDecoration(this, DividerListItemDecoration.VERTICAL_LIST);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(listItemDecoration);
        recyclerView.setAdapter(adapter = new RecycleAdapter(getData()));

        // recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

        List<String> list;

        public RecycleAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.view_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecycleAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_content);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_list:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.removeItemDecoration(listItemDecoration);
                recyclerView.removeItemDecoration(gridItemDecoration);
                recyclerView.addItemDecoration(listItemDecoration);
                recyclerView.setAdapter(adapter = new RecycleAdapter(getData()));
                break;
            case R.id.id_grid:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                recyclerView.removeItemDecoration(listItemDecoration);
                recyclerView.removeItemDecoration(gridItemDecoration);
                recyclerView.addItemDecoration(gridItemDecoration);
                recyclerView.setAdapter(adapter = new RecycleAdapter(getData()));
                break;
            case R.id.id_waterfall_v:
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.removeItemDecoration(listItemDecoration);
                recyclerView.removeItemDecoration(gridItemDecoration);
                recyclerView.addItemDecoration(gridItemDecoration);
                recyclerView.setAdapter(adapter = new RecycleAdapter(getData()));
                break;
            case R.id.id_waterfall_h:
                // 需要设置宽高
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
                recyclerView.removeItemDecoration(listItemDecoration);
                recyclerView.removeItemDecoration(gridItemDecoration);
                recyclerView.addItemDecoration(gridItemDecoration);
                recyclerView.setAdapter(adapter = new RecycleAdapter(getData()));
                break;
            default:
                break;
        }
    }
}
