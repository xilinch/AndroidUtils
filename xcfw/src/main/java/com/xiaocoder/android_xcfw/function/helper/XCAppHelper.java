package com.xiaocoder.android_xcfw.function.helper;

import android.app.Application;
import android.content.Context;

import com.xiaocoder.android_xcfw.util.UtilScreen;
import com.xiaocoder.android_xcfw.util.UtilSystem;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 获取application的实例
 */
public class XCAppHelper {

    private static Application instance;

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static Application getApplication() {
        return instance;
    }

    public static void init(Application app) {
        instance = app;
        appContext = app;
        simpleDeviceInfo();
    }

    /**
     * 设备启动时，输出设备与app的基本信息
     */
    public static String simpleDeviceInfo() {
        return (UtilSystem.getDeviceId(getAppContext()) + "--deviceId , "
                + UtilSystem.getVersionCode(getAppContext()) + "--versionCode , "
                + UtilSystem.getVersionName(getAppContext()) + "--versionName , "
                + UtilScreen.getScreenHeightPx(getAppContext()) + "--screenHeightPx , "
                + UtilScreen.getScreenWidthPx(getAppContext()) + "--screenWidthPx , "
                + UtilScreen.getDensity(getAppContext()) + "--density , "
                + UtilScreen.getScreenHeightDP(getAppContext()) + "--screenHeightDP , "
                + UtilScreen.getScreenWidthPx(getAppContext()) + "--screenWidthDP),"
                + UtilScreen.getStatusBarHeight(getAppContext()) + "--statusBarHeightPx");
    }


}
