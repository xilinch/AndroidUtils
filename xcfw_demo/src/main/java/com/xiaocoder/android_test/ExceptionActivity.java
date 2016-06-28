package com.xiaocoder.android_test;

import android.os.Bundle;

import com.xiaocoder.android_test_middle.base.BaseActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ExceptionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int a = 1 / 0;

    }

}