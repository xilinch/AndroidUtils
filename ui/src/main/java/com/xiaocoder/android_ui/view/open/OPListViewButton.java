package com.xiaocoder.android_ui.view.open;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * @author xiaocoder on 2016/2/26.
 * @modifier xiaocoder 2016/2/26 11:19.
 * @description
 */

public class OPListViewButton extends Button {
    public OPListViewButton(Context context) {
        super(context);
    }

    public OPListViewButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performClick();
                break;
            default:
                break;
        }
        return false;
    }
}

