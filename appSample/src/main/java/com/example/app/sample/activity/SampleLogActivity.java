package com.example.app.sample.activity;

import android.os.Bundle;

import com.example.middle.Sp;
import com.example.middle.base.BaseActivity;
import com.xiaocoder.android_xcfw.io.XCLog;

public class SampleLogActivity extends BaseActivity {

    //-------------修改配置去App、Config里----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 日志 提示
     */
    public void log() {
        XCLog.i(1);
        XCLog.i(null);
        XCLog.i(new Object());
        XCLog.i("demo");
        XCLog.i(false);

        XCLog.shortToast(1);
        XCLog.shortToast(null);
        XCLog.shortToast(new Object());
        XCLog.shortToast("demo");
        XCLog.shortToast(false);

        XCLog.dLongToast(1);
        XCLog.dLongToast(null);
        XCLog.dLongToast(new Object());
        XCLog.dLongToast("demo");
        XCLog.dLongToast(false);
    }

    /**
     * sp
     */
    public void sp() {
        Sp.setUserId("1");
        Sp.getUserId();

        Sp.setLogin(false);
        Sp.isLogin();
    }

}
