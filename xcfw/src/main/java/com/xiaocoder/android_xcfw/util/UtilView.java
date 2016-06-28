package com.xiaocoder.android_xcfw.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class UtilView {

    public static void setGridViewStyle(GridView view, boolean show_bar, int num) {
        setGridViewStyle(view, show_bar, 0, 0, num);
    }

    public static void setGridViewStyle(GridView view, boolean show_bar) {
        setGridViewStyle(view, show_bar, 0, 0, 1);
    }

    public static void setGridViewStyle(GridView view, boolean show_bar, int space_h_px, int space_v_px, int num) {
        view.setCacheColorHint(Color.TRANSPARENT);
        view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        view.setVerticalScrollBarEnabled(show_bar);
        view.setHorizontalSpacing(space_h_px);
        view.setVerticalSpacing(space_v_px);
        view.setNumColumns(num);
    }

    public static void setListViewStyle(ListView view, Drawable divider_drawable, int height_px, boolean show_bar) {
        view.setCacheColorHint(Color.TRANSPARENT);
        view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        view.setDivider(divider_drawable);
        view.setDividerHeight(height_px);
        view.setVerticalScrollBarEnabled(show_bar);
    }

    public static void setListViewStyle(ListView view, boolean show_bar) {
        setListViewStyle(view, null, 0, show_bar);
    }

    public static void setExpandListViewStyle(Context context, ExpandableListView view, boolean show_bar, int groupIndicate) {
        view.setCacheColorHint(Color.TRANSPARENT);
        view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        view.setVerticalScrollBarEnabled(show_bar);
        if (groupIndicate <= 0) {
            view.setGroupIndicator(null);
        } else {
            view.setGroupIndicator(context.getResources().getDrawable(groupIndicate));
        }
    }

    public static void setGone(boolean isShow, View view) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void setVisible(boolean isShow, View view) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 是否列表处于顶部
     * 第一项也显示完全
     *
     * @param listView
     * @return
     */
    public static boolean isListViewInTop(AbsListView listView) {
        if (null == listView)
            return true;

        View firstVisibleItemView = listView.getChildAt(0);
        if (null == firstVisibleItemView) {
            return true;
        }
        return listView.getFirstVisiblePosition() == 0 && firstVisibleItemView.getTop() == 0;
    }

    /**
     * 进入页面就滑到最开始处
     */
    private void scrollUp(final ScrollView view) {
        view.post(new Runnable() {
            public void run() {
                view.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
}
