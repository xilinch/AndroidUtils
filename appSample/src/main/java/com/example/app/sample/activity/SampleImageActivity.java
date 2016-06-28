package com.example.app.sample.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.middle.Image;
import com.example.middle.base.BaseActivity;

public class SampleImageActivity extends BaseActivity {

    //-------------修改配置去App、Config里----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 图片加载
     */
    public void image() {

        Image.displayImage("url", new ImageView(this));

    }


}
