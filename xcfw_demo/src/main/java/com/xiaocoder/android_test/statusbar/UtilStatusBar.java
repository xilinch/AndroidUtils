package com.xiaocoder.android_test.statusbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.xiaocoder.android_xcfw.R;
import com.xiaocoder.android_xcfw.util.UtilScreen;
import com.xiaocoder.android_xcfw.util.UtilSystem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xiaocoder on 2016/1/8.
 * @modifier xiaocoder 2016/1/8 16:29.
 * @description
 */
public class UtilStatusBar {

    public static final String XIAOMI = "xiaomi";
    public static final String MEIZU = "meizu";

    /**
     * @param activity
     * @param isLikeWhiteColor 是否是类白色的颜色， 即页面最顶部的颜色（有title就是title的颜色，没title就是页面背景的颜色）
     *                         这个是布局文件在状态栏的下方
     *                         这个方式，键盘可以顶起布局，但是无法监听键盘了
     */
    public static void setStatusBarColor(Activity activity, int color_id, boolean isLikeWhiteColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            OpSystemBarTintManager tintManager = new OpSystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);

            String brandName = UtilSystem.getPhoneBrand();

            if (brandName == null) {
                brandName = "";
            }

            if (!isLikeWhiteColor || XIAOMI.equals(brandName.toLowerCase()) || MEIZU.equals(brandName.toLowerCase())) {

                // 如果不是白色一类，则不存在状态栏白色字体与背景的冲突，按照传入的状态栏颜色设置
                // 或者是类白色，但是小米和魅族可以设置状态栏的字体为黑色，则按照传入的颜色设置状态栏的背景
                if (isLikeWhiteColor) {

                    // 如果是类白色背景的状态栏，系统自带的也是白色字体，看不清
                    // 状态栏图片字体设置为黑色
                    if (XIAOMI.equals(brandName.toLowerCase())) {
                        setStatusBarDarkMode(true, activity);
                    } else if (MEIZU.equals(brandName.toLowerCase())) {
                        setStatusBarDarkIcon(activity.getWindow(), true);
                    }
                }

                tintManager.setTintColor(activity.getResources().getColor(color_id));

            } else {
                // 小米 魅族以外的手机暂时还无法设置状态栏的字体为黑色，所以状态栏背景如果为白色，就与字体和图片的白色重叠了，所以背景改为c_gray_e5e5e5
                tintManager.setTintColor(activity.getResources().getColor(R.color.c_gray_30ebebeb));
            }
            View rootView = ((ViewGroup) (activity.getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            ((ViewGroup) rootView).setClipToPadding(true);
        }
    }

    /**
     * 透明的状态栏（如果用了这个方法，那么如果页面中有键盘，则无法顶起布局，只能用于无键盘的页面）
     *
     * @param activity
     * @param isLikeWhiteColor 是否是类白色的颜色，即页面最顶部的颜色（有title就是title的颜色，没title就是页面背景的颜色）
     *                         <p/>
     *                         这个是布局文件全屏，状态栏透明盖子布局文件上
     */
    public static void setStatusBarTrans(Activity activity, boolean isLikeWhiteColor, int title_id) {
        setStatusBarTrans(activity, null, isLikeWhiteColor, title_id);
    }

    public static void setStatusBarTrans(Activity activity, ViewGroup container, boolean isLikeWhiteColor, int title_id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            String brandName = UtilSystem.getPhoneBrand();

            if (brandName == null) {
                brandName = "";
            }

            if (!isLikeWhiteColor || XIAOMI.equals(brandName.toLowerCase()) || MEIZU.equals(brandName.toLowerCase())) {

                if (isLikeWhiteColor) {
                    // 状态栏图片字体设置为黑色
                    if (XIAOMI.equals(brandName.toLowerCase())) {
                        setStatusBarDarkMode(true, activity);
                    } else if (MEIZU.equals(brandName.toLowerCase())) {
                        setStatusBarDarkIcon(activity.getWindow(), true);
                    }
                }

                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                ViewGroup containerView = null;

                if (container == null) {
                    containerView = (ViewGroup) ((ViewGroup) (activity.getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);
                } else {
                    // 如果是fragment，就传入fragment的layout
                    containerView = container;
                }

                if (containerView != null) {
                    ViewGroup title = (ViewGroup) containerView.findViewById(title_id);
                    if (title == null) {
                        // 没有title
                    } else {
                        // 有title
                        int barHeight = UtilStatusBar.getStatusBarHeight(activity);
                        ViewGroup.LayoutParams layoutParams = title.getLayoutParams();
                        layoutParams.height = UtilScreen.dip2px(activity, 50) + barHeight;
                        title.setLayoutParams(layoutParams);
                        title.setPadding(0, barHeight, 0, 0);
                    }
                }
            } else {
                // 小米 魅族以外的手机暂时还无法设置状态栏的字体为黑色,这里不设置
            }
        }
    }

    /**
     * 设置小米手机的状态栏字体
     *
     * @param darkmode
     * @param activity
     */
    private static void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 魅族手机的状态栏字体
     *
     * @param window
     * @param dark
     * @return
     */
    private static boolean setStatusBarDarkIcon(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                Log.e("MeiZu", "setStatusBarDarkIcon: failed");
            }
        }
        return result;
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
