package com.xiaocoder.android_ui_demo.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.xiaocoder.android_ui_demo.R;

/**
 * @author songxin on 2015/11/3.
 * @description 抽屉Menu菜单基类
 */
public abstract class BaseDrawerActivity extends Activity {

    private DrawerLayout sx_id_drawer_layout;
    private FrameLayout sx_id_drawer_content_frame;
    private RelativeLayout sx_id_drawer_left_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        sx_id_drawer_layout = (DrawerLayout) findViewById(R.id.sx_id_drawer_layout);
        sx_id_drawer_content_frame = (FrameLayout) findViewById(R.id.sx_id_drawer_content_frame);
        sx_id_drawer_left_drawer = (RelativeLayout) findViewById(R.id.sx_id_drawer_left_drawer);
    }

    public void setListeners() {
        //拦截焦点,防止焦点传到下一层
        sx_id_drawer_left_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * 子类里面调用setContentFrame（），即把子类的布局添加到acticity_drawer_main里面
     *
     * @param view
     */
    public void setContentFrame(View view) {
        sx_id_drawer_content_frame.addView(view);
    }

    /**
     * 子类里面调用setDrawerFrame（），即把抽屉的布局添加到sx_id_drawer_left_drawer里面
     *
     * @param view
     */
    public void setDrawerFrame(View view) {
        sx_id_drawer_left_drawer.addView(view);
    }

    /**
     * 显示抽屉
     */
    public void openDrawer() {
        sx_id_drawer_layout.openDrawer(sx_id_drawer_left_drawer);
    }

    /**
     * 隐藏抽屉
     */
    public void closeDrawer() {
        sx_id_drawer_layout.closeDrawer(sx_id_drawer_left_drawer);
    }
}
