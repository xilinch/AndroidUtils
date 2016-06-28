package com.xiaocoder.android_scrollpager_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroller = (Button) findViewById(R.id.id_scroller);

        scroller.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_scroller:
                startActivity(new Intent(this, ScrollerViewActivity.class));
                break;
        }

    }
}
