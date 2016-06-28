package com.xiaocoder.android_moveview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilScreen;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 可以跟着手指移动的view
 * <p/>
 * 不要设置相对属性，centerInH = true
 */
public class XCMoveView extends RelativeLayout {

    public static final String TAG = "XCMoveView";

    private GestureDetector mDetector;

    private OnClickListenerPlus mListener;

    public interface OnClickListenerPlus {
        void onClickListenerPlus(View view);
    }

    public void setOnClickListenerPlus(OnClickListenerPlus listener) {
        mListener = listener;
    }

    public XCMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDetector = new GestureDetector(getContext(), mGestureListener);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (viewWidth == 0 || viewHeight == 0) {
            viewWidth = getWidth();
            viewHeight = getHeight();
        }
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mListener != null) {
                mListener.onClickListenerPlus(XCMoveView.this);
            }
            XCLog.dShortToast("单击click");
            return false;
        }

    };

    /**
     * down的那一刻，点击的那个点相对屏幕的绝对坐标
     */
    float clickRawX;
    float clickRawY;

    /**
     * down的那一刻，view相对于屏幕的绝对坐标
     */
    int locationRawX;
    int locationRawY;

    /**
     * view的宽度 高度
     */
    int viewWidth;
    int viewHeight;

    public static final int KEEP_DISTANCE = 25;

    /**
     * 用于记录值的
     */
    Rect rect = new Rect();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                locationRawX = getLeft();
                locationRawY = getTop();
                clickRawX = event.getRawX();
                clickRawY = event.getRawY();
                XCLog.i("down---locationRawX = " + locationRawX + "---locationRawY = " + locationRawY);
                XCLog.i("down---clickRawX = " + clickRawX + "---clickRawY = " + clickRawY);
                break;
            case MotionEvent.ACTION_MOVE:

                float offsetX = event.getRawX() - clickRawX;
                float offsetY = event.getRawY() - clickRawY;

                rect.left = (int) (locationRawX + offsetX);
                rect.top = (int) (locationRawY + offsetY);
                rect.right = rect.left + viewWidth;
                rect.bottom = rect.top + viewHeight;

                layout(rect.left, rect.top, rect.right, rect.bottom);

                XCLog.i("move---offsetX = " + offsetX + "---offsetY = " + offsetY);
                XCLog.i("move---rect.left = " + rect.left + "---rect.top = " + rect.top + "---rect.right" + rect.right + "---rect.bottom" + rect.bottom);
                break;
            case MotionEvent.ACTION_UP:
                checkBoundry();
                fixPosition();
                break;
            default:
                break;
        }

        return true;
    }

    public static final int STATUS_BAR_DB = 20;

    private void fixPosition() {
        if (rect.top == 0 && rect.left == 0 && rect.right == 0 && rect.bottom == 0) {
            // 刚进界面，仅点击该view不移动，会跳到屏幕的00坐标
            return;
        }

        LayoutParams layoutParams = (LayoutParams) getLayoutParams();

        layoutParams.leftMargin = rect.left;
        layoutParams.topMargin = rect.top;

        int currentViewWidth = UtilScreen.getScreenWidthPx(getContext()) - rect.left;
        if (currentViewWidth < viewWidth) {
            // x轴出界了
            layoutParams.rightMargin = currentViewWidth - viewWidth;
        } else {
            layoutParams.rightMargin = 0;
        }

        int currentViewHeight = UtilScreen.getScreenHeightPx(getContext()) - rect.top - UtilScreen.dip2px(getContext(), STATUS_BAR_DB);
        if (currentViewHeight < viewHeight) {
            // y轴出界了
            layoutParams.bottomMargin = currentViewHeight - viewHeight;
        } else {
            layoutParams.bottomMargin = 0;
        }

        setLayoutParams(layoutParams);
    }

    private void checkBoundry() {

        int maxWidthLimit = UtilScreen.getScreenWidthPx(getContext()) + KEEP_DISTANCE;
        int maxHeightLimit = UtilScreen.getScreenHeightPx(getContext()) + KEEP_DISTANCE - UtilScreen.dip2px(getContext(), STATUS_BAR_DB);

        if (getLeft() < -KEEP_DISTANCE) {
            // 移出了左边界 -KEEP_DISTANCE
            rect.set(-KEEP_DISTANCE, rect.top, viewWidth - KEEP_DISTANCE, rect.bottom);

        } else if (getRight() > maxWidthLimit) {
            // 移出了右边界 KEEP_DISTANCE
            rect.set(maxWidthLimit - viewWidth, rect.top, maxWidthLimit, rect.bottom);

        } else if (getTop() < -KEEP_DISTANCE) {
            // 移出了上边界 -
            rect.set(rect.left, -KEEP_DISTANCE, rect.right, viewHeight - KEEP_DISTANCE);

        } else if (getBottom() > maxHeightLimit) {
            // 移除了下边界
            rect.set(rect.left, maxHeightLimit - viewHeight, rect.right, maxHeightLimit);

        }
    }

}