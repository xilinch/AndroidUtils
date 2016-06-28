package com.xiaocoder.android_scrollpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.xiaocoder.android_xcfw.util.UtilInput;
import com.xiaocoder.android_xcfw.util.UtilScreen;

import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCScrollerPageView2 extends LinearLayout {

    private Scroller mScroller;

    /**
     * 当滑动距离大于多少像素时，滚动到下一页
     */
    private static final int EFFECTIVE_DISTANCE_2_NEXT_PAGE = 300;

    /**
     * 每毫秒一个像素点的速度是有效的
     */
    private static final int EFFECTIVE_FLING_2_NEXT_PAGE = 1;

    /**
     * 当滑动距离多大时，页面才跟着touch移动
     */
    private int effectiveSlideDistance;

    private int downY;
    private int tempY;

    private long downTime;
    private long upTime;

    private int screenHeight;
    private int statusBarHeight;
    private int screenSubStatusBarHeight;
    private List<View> pagerViews;

    private int currentPageNum = 1;
    private int totalPageNum = 1;

    private boolean isScrolling = false;

    /**
     * 下一页时滚动的速度
     */
    private double speedRatio = 1.25;
    private double gravityRatio = 2.5;

    public List<View> getPagerViews() {
        return pagerViews;
    }

    public void setPagerViews(List<View> pagerViews) {
        this.pagerViews = pagerViews;
        this.totalPageNum = pagerViews.size();
    }

    public int getScreenSubStatusBarHeight() {
        return screenSubStatusBarHeight;
    }

    public int getHeight(int pageNum) {
        return pagerViews.get(pageNum - 1).getHeight();
    }

    public XCScrollerPageView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);

        effectiveSlideDistance = ViewConfiguration.get(context).getScaledTouchSlop();

        screenHeight = UtilScreen.getScreenHeightPx(context);

        statusBarHeight = UtilScreen.getStatusBarHeight(context);

        screenSubStatusBarHeight = screenHeight - statusBarHeight;

    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                UtilInput.hiddenInputMethod(getContext());

                downY = tempY = (int) ev.getRawY();
                downTime = System.currentTimeMillis();
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();

                if (Math.abs(moveY - downY) > effectiveSlideDistance) {

                    if (currentPageNum == totalPageNum) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                    return true;
                } else {
                    return false;
                }

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                int moveY = (int) event.getRawY();

                int distanceY = tempY - moveY;

                tempY = moveY;

                if (distanceY != 0) {
                    scrollBy(0, (int) (distanceY / gravityRatio));
                }

                break;
            case MotionEvent.ACTION_UP:

                upTime = System.currentTimeMillis();

                double distance = downY - event.getRawY();
                long timeGap = upTime - downTime;

                if (distance > EFFECTIVE_DISTANCE_2_NEXT_PAGE || distance / timeGap > EFFECTIVE_FLING_2_NEXT_PAGE) {
                    // 向上滑动
                    if (currentPageNum == totalPageNum) {
                        scrollOrigin();
                    } else {
                        scrollUp();
                    }
                } else if (distance < -EFFECTIVE_DISTANCE_2_NEXT_PAGE || distance / timeGap < -EFFECTIVE_FLING_2_NEXT_PAGE) {
                    // 向下滑动
                    if (currentPageNum == 1) {
                        scrollOrigin();
                    } else {
                        scrollDown();
                    }
                } else {
                    // 滑动的距离不够，还原
                    scrollOrigin();
                }

                break;
        }

        return true;
    }

    /**
     * 页面上滑动，getScrollY（）正数，移动一屏后，就是screenHeight
     */
    private void scrollUp() {
        int distance = currentPageNum * screenSubStatusBarHeight - getScrollY();
        mScroller.startScroll(0, getScrollY(), 0, distance, getSpeedTime(distance));
        postInvalidate();
        currentPageNum++;
        pageChange();
    }

    /**
     * 页面下滑动,getScrollY（）负数，移动一屏后，就是-screenHeight
     */
    private void scrollDown() {
        int distance = 0;
        if (currentPageNum == 2) {
            distance = getScrollY() - (currentPageNum - 2) * screenSubStatusBarHeight;
        } else {
            distance = getScrollY() - (currentPageNum - 2) * screenSubStatusBarHeight;
        }
        mScroller.startScroll(0, getScrollY(), 0, -distance, getSpeedTime(distance));
        postInvalidate();
        currentPageNum--;
        pageChange();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int distance = 0;
        if (getScrollY() > 0) {
            // 向上滑动
            if (currentPageNum == 1) {
                // getScrollY()这里是正数，值为已移动的距离，不是坐标点
                distance = getScrollY();
            } else {
                distance = getScrollY() - (currentPageNum - 1) * screenSubStatusBarHeight;
            }
        } else if (getScrollY() < 0) {
            // 向下滑动
            if (currentPageNum == 1) {
                distance = getScrollY();
            } else {
                distance = getScrollY() + (currentPageNum - 1) * screenSubStatusBarHeight;
            }
        }
        mScroller.startScroll(0, getScrollY(), 0, -distance, getSpeedTime(distance));
        postInvalidate();
    }

    public int getSpeedTime(int distance) {
        return (int) Math.abs((distance / speedRatio));
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
            isScrolling = true;
        } else {
            isScrolling = false;
        }
        super.computeScroll();
    }

    public void scrollToFirstPage() {
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), Math.abs(getScrollY()));
        postInvalidate();
        currentPageNum = 1;
        pageChange();
    }

    private void pageChange() {
        if (scrollerPageChangeListener != null) {
            scrollerPageChangeListener.onXCScrollerPageChange(currentPageNum);
        }
    }

    private XCScrollerPageChangeListener scrollerPageChangeListener;

    public interface XCScrollerPageChangeListener {
        void onXCScrollerPageChange(int pageNum);
    }

    public void setOnPageChangeListener(XCScrollerPageChangeListener scrollerPageChangeListener) {
        this.scrollerPageChangeListener = scrollerPageChangeListener;
    }

}
