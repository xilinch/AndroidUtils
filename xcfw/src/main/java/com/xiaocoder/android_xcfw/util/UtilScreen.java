package com.xiaocoder.android_xcfw.util;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author xiaocoder on 2015/8/3
 * @email fengjingyu@foxmail.com
 * @description
 */
public class UtilScreen {

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenHeightPx(Context context) {
        return getScreenSizeByMetric(context)[1];

    }

    public static int getScreenWidthPx(Context context) {
        return getScreenSizeByMetric(context)[0];
    }

    public static int getScreenHeightDP(Context context) {
        return px2dip(context, getScreenHeightPx(context));
    }

    public static int getScreenWidthDP(Context context) {
        return px2dip(context, getScreenWidthPx(context));
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(Context context, float sp) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 获取屏幕分辨率
     */
    public static int[] getScreenSizeByMetric(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metric);
        int[] size = new int[3];
        size[0] = metric.widthPixels;
        size[1] = metric.heightPixels;
        size[2] = metric.densityDpi;
        return size;
    }

    /**
     * 触摸的点是否在一个view内
     */
    public static boolean isTouchInsideView(List<? extends View> views, MotionEvent ev, int extraDistance) {

        if (UtilCollections.isListBlank(views)) {
            return false;
        }

        for (View v : views) {

            int[] sizes = new int[2];
            v.getLocationOnScreen(sizes);

            Rect mRect = new Rect();
            mRect.left = sizes[0] - extraDistance;
            mRect.top = sizes[1] - extraDistance;
            mRect.right = sizes[0] + v.getWidth() + extraDistance;
            mRect.bottom = sizes[1] + v.getHeight() + extraDistance;

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 系统默认的scroll有效距离
     */
    public static int getTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}


/**
 * getRowX：触摸点相对于屏幕的坐标
 * getX： 触摸点相对于按钮的坐标
 * getTop： 按钮左上角相对于父view（LinerLayout）的y坐标
 * getLeft： 按钮左上角相对于父view（LinerLayout）的x坐标
 * 可以想象 getRight()等同于下面的计算：getLeft()+getWidth()。
 * <p/>
 * //            int[] sizes = new int[2];
 * //            v.getLocationOnScreen(sizes);
 * //            XCLog.i("button" ,"x()--"+sizes[0]);
 * //            XCLog.i("button" ,"y()--"+sizes[1]);
 * //            XCLog.i("button" ,"getX()--"+v.getX());
 * //            XCLog.i("button" ,"getY()--"+v.getY());
 * //            XCLog.i("button" ,"getLeft()--"+v.getLeft());
 * //            XCLog.i("button" ,"getRight()--"+v.getRight());
 * //            XCLog.i("button" ,"getTop()--"+v.getTop());
 * //            XCLog.i("button" ,"getBottom()--"+v.getBottom());
 * //            XCLog.i("button" ,"ev.getX()--"+ev.getX());
 * //            XCLog.i("button" ,"ev.getY()--"+ev.getY());
 * //            XCLog.i("button" ,"ev.getRawX()--"+ev.getRawX());
 */

/**
 * 华为：荣耀6 density为3
 * getScreenHeightPx
 *
 * 如果导航栏是开着的 screenHeight 为 1776px（包含了状态栏的高度，状态栏的高度为75px），导航栏的高度为144px
 * 如果导航栏是关着的 screenHeight 为 1920px（包含了状态栏的高度，状态栏的高度为75px）
 */
