package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiaocoder.android_ui.view.xc.XCTabLayout;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TabActivity extends Activity {
    XCTabLayout layout;
    XCTabLayout layout1;
    XCTabLayout layout2;
    XCTabLayout layout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_block);

        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        layout = (XCTabLayout) findViewById(R.id.tab_layout);
        // layout.setContents(new String[] { "电影", "音乐", "游戏" }, 0, false);
        layout.setContents(new String[]{"电影", "音乐", "游戏", "软件", "学习"});
        layout.setInitSelected(2, 4, true, 10); // 默认选中第三个
        layout.create();

        layout1 = (XCTabLayout) findViewById(R.id.tab_layout1);
        // layout1.setInitSelected(2, 4, false, 20);
        layout1.setInitSelected(2, 4, true, 10);
        layout1.setImageUris(new String[]{"drawable://" + R.drawable.ic_launcher, "drawable://" + R.drawable.ic_launcher, "drawable://" + R.drawable.ic_launcher,
                "drawable://" + R.drawable.ic_launcher, "drawable://" + R.drawable.ic_launcher, "drawable://" + R.drawable.ic_launcher});
        layout1.setContents(new String[]{null, "音乐", null, "软件", "学习", "棋牌", "水果"});
        layout1.create();


        layout2 = (XCTabLayout) findViewById(R.id.tab_layout2);
        layout2.setInitSelected(2, 4, false, 2);
        layout2.setImageUris(new String[]{"http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png",
                "http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png"});
        layout2.setContents(new String[]{null, null, "", null, "", "", ""});
        layout2.create();

        layout3 = (XCTabLayout) findViewById(R.id.tab_layout3);
        layout3.setInitSelected(2, 4, true, 2);
        layout3.setImageUris(new String[]{"http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png",
                "http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png", "http://www.baidu.com/img/bdlogo.png"});
        layout3.setContents(new String[]{null, null, null, null, null, null});
        layout3.create();

    }

    public void setListeners() {
        layout.setOnClickMoveListener(new XCTabLayout.OnClickMoveListener() {
            @Override
            public void onClickMoveListener(int position, ViewGroup current_item, ImageView current_imageview, ViewGroup last_item, ImageView last_imageview) {
                // position 为点击了第几个
                Toast.makeText(TabActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        layout.setOnImageLoaderListener(new XCTabLayout.OnImageLoaderListener() {
            @Override
            public void onImageLoaderListener(ImageView view, String url) {
                view.setImageResource(R.drawable.ic_launcher);
            }
        });

        layout1.setOnClickMoveListener(new XCTabLayout.OnClickMoveListener() {
            @Override
            public void onClickMoveListener(int position, ViewGroup current_item, ImageView current_imageview, ViewGroup last_item, ImageView last_imageview) {
                Toast.makeText(TabActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        layout1.setOnImageLoaderListener(new XCTabLayout.OnImageLoaderListener() {
            @Override
            public void onImageLoaderListener(ImageView view, String url) {
                view.setImageResource(R.drawable.ic_launcher);
            }
        });

        layout2.setOnClickMoveListener(new XCTabLayout.OnClickMoveListener() {
            @Override
            public void onClickMoveListener(int position, ViewGroup current_item, ImageView current_imageview, ViewGroup last_item, ImageView last_imageview) {
                Toast.makeText(TabActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        layout2.setOnImageLoaderListener(new XCTabLayout.OnImageLoaderListener() {
            @Override
            public void onImageLoaderListener(ImageView view, String url) {
                view.setImageResource(R.drawable.ic_launcher);
            }
        });

        layout3.setOnClickMoveListener(new XCTabLayout.OnClickMoveListener() {
            @Override
            public void onClickMoveListener(int position, ViewGroup current_item, ImageView current_imageview, ViewGroup last_item, ImageView last_imageview) {
                Toast.makeText(TabActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        layout3.setOnImageLoaderListener(new XCTabLayout.OnImageLoaderListener() {
            @Override
            public void onImageLoaderListener(ImageView view, String url) {
                view.setImageResource(R.drawable.ic_launcher);
            }
        });
    }

}
