package com.xiaocoder.android_ui.view.sx;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilScreen;


/**
 * InputMethodEventView
 * 监听布局变化
 *
 * @author songxin on 2016/4/18.
 * @version 2.3.0
 */
public class SXInputMethodEventView extends RelativeLayout {

    private int SOFTKEY_MAX_HEIGHT;
    private int SOFTKEY_MIN_HEIGHT;

    private int SCREEN_HEIGHT;

    InputMethodChangeLinstener mInputMethodChangeLinstener;

    public InputMethodChangeLinstener getmInputMethodChangeLinstener() {
        return mInputMethodChangeLinstener;
    }

    public void setmInputMethodChangeLinstener(InputMethodChangeLinstener mInputMethodChangeLinstener) {
        this.mInputMethodChangeLinstener = mInputMethodChangeLinstener;
    }

    public interface InputMethodChangeLinstener {
        void onInputMethodOpen(int softinputHeight);

        void onInputMethodClose();
    }

    public void init(Context context) {
        SOFTKEY_MAX_HEIGHT = UtilScreen.getScreenHeightPx(context) / 2;
        SOFTKEY_MIN_HEIGHT = UtilScreen.dip2px(context, 100);

        SCREEN_HEIGHT = UtilScreen.getScreenHeightPx(context);
    }

    public SXInputMethodEventView(Context context) {
        super(context);
        init(context);
    }

    public SXInputMethodEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SXInputMethodEventView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mInputMethodChangeLinstener != null) {

            XCLog.i("http", "oldh=" + oldh + ", " + "h=" + h + ", " + "oldh - h=" + (oldh - h));

            if (oldh - h > SOFTKEY_MIN_HEIGHT) {
                XCLog.i("http", "onInputMethodOpen");
                mInputMethodChangeLinstener.onInputMethodOpen(Math.abs(oldh - h));
            } else {

                if (oldh != 0 && SCREEN_HEIGHT - h < SOFTKEY_MAX_HEIGHT && h - oldh > SOFTKEY_MIN_HEIGHT) {
                    XCLog.i("http", "onInputMethodClose");
                    mInputMethodChangeLinstener.onInputMethodClose();
                }
            }
        }
    }
}
