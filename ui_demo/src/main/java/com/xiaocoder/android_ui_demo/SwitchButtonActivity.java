package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;

import com.xiaocoder.android_ui.view.open.OPSwitchButton;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class SwitchButtonActivity extends Activity implements OPSwitchButton.SwitchButtonListener {

    OPSwitchButton slide;
    OPSwitchButton slide2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_switchbutton);
        super.onCreate(savedInstanceState);
        initWidgets();
    }

    public void initWidgets() {
        slide = (OPSwitchButton) findViewById(R.id.swit);
        slide2 = (OPSwitchButton) findViewById(R.id.swit2);

        slide.setState(false);
    }


    @Override
    public void open() {
    }

    @Override
    public void close() {
    }

}

