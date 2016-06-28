package com.xiaocoder.android_ui_demo.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaocoder.android_ui_demo.R;


public class XCReflectPopupWindow extends XCBasePopupWindow implements View.OnClickListener {

    // pop所属的activity销毁时，才会消失

    public XCReflectPopupWindow(Context context, int width, int height) {
        super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.pop_reflect, null), width, height);
    }

    @Override
    public void initWidgets() {
        setAllTime(this);
        setFocusable(false);
        setOutsideTouchable(false);
    }

    @Override
    public void listener() {
    }

    @Override
    public void onClick(View v) {

    }


}
