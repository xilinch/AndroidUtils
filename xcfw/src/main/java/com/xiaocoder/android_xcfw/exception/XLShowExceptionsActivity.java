package com.xiaocoder.android_xcfw.exception;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaocoder.android_xcfw.R;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 使用时记得在清单文件中注册 XLShowExceptionsActivity
 */
public class XLShowExceptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xl_l_activity_show_exception);
        TextView tv = (TextView) findViewById(R.id.tv);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String text = bundle.getString(XCCrashHandler.EXCEPTION_INFO);
            tv.setText(text);
        }
    }
}
