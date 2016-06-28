package com.xiaocoder.android_ui.view.open;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;


import com.xiaocoder.android_ui.R;
import com.xiaocoder.android_xcfw.util.UtilCollections;
import com.xiaocoder.android_xcfw.util.UtilScreen;

import java.util.LinkedList;
import java.util.List;


/**
 * 向右滑动删除activity
 */
public class OPSwipeBackLayout extends FrameLayout {
    private static final String TAG = OPSwipeBackLayout.class.getSimpleName();
    private View mContentView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isSilding;
    private boolean isFinish;
    private Drawable mShadowDrawable;
    private Activity mActivity;

    private int screenHalfWidth;

    /**
     * 点击到如下控件时，屏蔽
     */
    private List<View> mViews = new LinkedList<>();
    /**
     * 控件的Simple类名
     */
    private List<String> mClassNames = new LinkedList<>();

    public OPSwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OPSwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);

        mShadowDrawable = getResources().getDrawable(R.drawable.swipeback_shadow_left);

        screenHalfWidth = UtilScreen.getScreenSizeByMetric(context)[0] / 2;
    }


    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(View decorChild) {
        mContentView = (View) decorChild.getParent();
    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();

                if (isSlide(ev, moveX)) {
                    // 假设x方向手势滑动的距离大于系统定义的一个值
                    // 假设y方向手势滑动的距离小于系统定义的一个值
                    if (UtilScreen.isTouchInsideView(mViews, ev, 60)) {
                        // 如果触碰到了集合里含的view，不拦截，也就是触摸事件继续下发到下一层控件
                        return false;
                    } else {
                        // 拦截，触摸事件该控件消化
                        return true;
                    }

                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean isSlide(MotionEvent ev, int moveX) {
        return moveX - downX > mTouchSlop && Math.abs((int) ev.getRawY() - downY) < mTouchSlop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 虽然onInterceptTouchEvent拦截了，但是只有在屏幕的一半事件才有效
        if (downX < 0 || downX > screenHalfWidth) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                // 这里是tempX
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (isSlide(event, moveX)) {
                    isSilding = true;
                }

                if (moveX - downX >= 0 && isSilding) {
                    mContentView.scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                if (mContentView.getScrollX() <= -viewWidth / 2) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }

        return true;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();
            clear();
            getConflictView(this);
        }
    }

    private void clear() {
        mViews.clear();
    }

    public void setInterceptClassNames(List<String> classNames) {
        mClassNames = classNames;
    }

    private void getConflictView(ViewGroup parent) {

        if (UtilCollections.isListBlank(mClassNames)) {
            return;
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (mClassNames.contains(child.getClass().getSimpleName())) {
                mViews.add(child);
            } else if (child instanceof ViewGroup) {
                getConflictView((ViewGroup) child);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShadowDrawable != null && mContentView != null) {

            int left = mContentView.getLeft()
                    - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = mContentView.getTop();
            int bottom = mContentView.getBottom();

            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
        }

    }


    /**
     * 滚动出界面
     */
    private void scrollRight() {
        final int delta = (viewWidth + mContentView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }


}
