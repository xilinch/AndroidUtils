package com.example.middle;

import android.content.Context;

import com.xiaocoder.android_xcfw.application.XCActivity;
import com.xiaocoder.android_xcfw.io.XCSP;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 以下代码可以通过 SpCodeGenerator_V1.0.jar 生成
 */
public class Sp {

    public static final String USER_NAME = "userName";
    public static final String USER_ID = "userId";
    public static final String USER_TOKEN = "userToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER_HEAD = "userHeader";
    public static final String USER_PHONE_NUM = "userPhoneNum";
    public static final String IS_INSTALL = "isInstall";

    public static boolean isFirstInstallApp() {

        return XCSP.spGet(IS_INSTALL, true);

    }

    public static String getUserId() {

        return XCSP.spGet(USER_ID, "");

    }

    public static String getUserToken() {

        return XCSP.spGet(USER_TOKEN, "");

    }

    public static String getUserName() {

        return XCSP.spGet(USER_NAME, "");

    }

    public static String getUserHead() {

        return XCSP.spGet(USER_HEAD, "");
    }

    public static String getUserPhoneNum() {

        return XCSP.spGet(USER_PHONE_NUM, "");
    }

    /**
     * 获取登录状态
     */
    public static boolean isLogin() {

        return XCSP.spGet(IS_LOGIN, false);

    }

    public static void setUserId(String userId) {

        XCSP.spPut(USER_ID, userId);

    }

    public static void setUserToken(String userToken) {

        XCSP.spPut(USER_TOKEN, userToken);

    }

    public static void setUserName(String userName) {

        XCSP.spPut(USER_NAME, userName);

    }

    public static void setUserHeader(String userHeader) {

        XCSP.spPut(USER_HEAD, userHeader);

    }

    public static void setUserPhoneNum(String userPhoneNum) {

        XCSP.spPut(USER_PHONE_NUM, userPhoneNum);

    }

    /**
     * 保存登录状态
     */
    public static void setLogin(boolean isLogin) {

        XCSP.spPut(IS_LOGIN, isLogin);

    }

    public static void setFirstInstallApp(boolean value) {

        XCSP.spPut(IS_INSTALL, value);

    }

    public static void loginOut(Class<? extends XCActivity> classes, Context context) {
        setLogin(false);
        setUserId("");
        setUserToken("");
        setUserHeader("");
        setUserName("");
        setUserPhoneNum("");
        //TODO 注销的代码 。。

    }


}
