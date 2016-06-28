package com.xiaocoder.android_test.stack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaocoder.android_xcfw.function.helper.XCActivityHelper;
import com.xiaocoder.android_xcfw.io.XCIO;
import com.xiaocoder.android_test.MainActivity;
import com.xiaocoder.android_test.R;
import com.xiaocoder.android_test_middle.base.BaseActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class StackActivity extends BaseActivity {

    TextView stack_desc;
    Button to_main_activity;
    Button to_search_activity1;
    Button to_search_activity2;
    Button finish_activity;
    Button finish_activity2;
    Button finish_current_activity;
    Button finish_all_activity;
    Button finish_activity_then_startActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        stack_desc = getViewById(R.id.stack_desc);
        to_main_activity = getViewById(R.id.to_main_activity);
        to_search_activity1 = getViewById(R.id.to_search_activity1);
        to_search_activity2 = getViewById(R.id.to_search_activity2);
        finish_activity = getViewById(R.id.finish_activity);
        finish_activity2 = getViewById(R.id.finish_activity2);
        finish_current_activity = getViewById(R.id.finish_current_activity);
        finish_all_activity = getViewById(R.id.finish_all_activity);
        finish_activity_then_startActivity = getViewById(R.id.finish_activity_then_startActivity);

        desc();

    }

    private void desc() {
        stack_desc.setText("");
        stack_desc.append(XCActivityHelper.getStack().size() + "-----栈的大小---" + XCIO.LINE_SEPARATOR);
        for (Activity item : XCActivityHelper.getStack()) {
            stack_desc.append(item.getClass() + "----" + XCIO.LINE_SEPARATOR);
        }
        stack_desc.append("MainActivity是否存在--" + XCActivityHelper.isActivityExist(MainActivity.class) + XCIO.LINE_SEPARATOR);
        stack_desc.append("SearchActivity是否存在--" + XCActivityHelper.isActivityExist(SearchActivity.class) + XCIO.LINE_SEPARATOR);
        stack_desc.append("SearchActivity2是否存在--" + XCActivityHelper.isActivityExist(SearchActivity2.class) + XCIO.LINE_SEPARATOR);
        stack_desc.append("StackActivity是否存在--" + XCActivityHelper.isActivityExist(StackActivity.class) + XCIO.LINE_SEPARATOR);
        stack_desc.append("当前页面--" + XCActivityHelper.getCurrentActivity() + XCIO.LINE_SEPARATOR);
        stack_desc.append("SearchActivity是否存在--" + XCActivityHelper.getActivity(SearchActivity.class) + XCIO.LINE_SEPARATOR);
        stack_desc.append("SearchActivity2是否存在--" + XCActivityHelper.getActivity(SearchActivity2.class) + XCIO.LINE_SEPARATOR);
    }

    public void setListeners() {
        to_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.toActivity(MainActivity.class);
            }
        });

        to_search_activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.toActivity(SearchActivity.class);
            }
        });

        to_search_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.toActivity(SearchActivity2.class);
            }
        });

        finish_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.finishActivity(SearchActivity.class);
                desc();
            }
        });
        finish_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.finishActivity(SearchActivity2.class);
                desc();
            }
        });

        finish_all_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.finishAllActivity();
            }
        });

        finish_current_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCActivityHelper.finishCurrentActivity();
            }
        });

        finish_activity_then_startActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 以下两种方式的周期是一样的
                XCActivityHelper.finishCurrentActivity();
                startActivity(new Intent(getXCActivity(), SearchActivity.class));
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---finish
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onPause
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onCreate
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onStart
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onResume
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onStop
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onDestroy


//                startActivity(new Intent(getXCActivity(),SearchActivity.class));
//                XCActivityHelper.finishCurrentActivity();
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---finish
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onPause
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onCreate
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onStart
//                com.xiaocoder.android_test.stack.SearchActivity@25c4d4fb---onResume
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onStop
//                com.xiaocoder.android_test.stack.StackActivity@2de63894---onDestroy
            }
        });

    }

}
