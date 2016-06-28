package com.example.middle.config;


import com.xiaocoder.android_xcfw.util.UtilString;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ConfigUrl {

    /**
     * 当前的运行环境，即域名的控制, 上线前，改为ONLINE
     */
    public static RunEnvironment CURRENT_RUN_ENVIRONMENT = RunEnvironment.ONLINE;

    public enum RunEnvironment {
        DEV, TEST, ONLINE
    }

    /**
     * 域名配置
     */
    public static String ONLINE_HOST = "online.123.cn";
    public static String ONLINE_PORT = "";
    public static String ONLINE_ADDR = ONLINE_HOST + (UtilString.isBlank(ONLINE_PORT) ? "" : ":") + ONLINE_PORT;

    public static String TEST_HOST = "test.123.cn";
    public static String TEST_PORT = "";
    public static String TEST_ADDR = TEST_HOST + (UtilString.isBlank(TEST_PORT) ? "" : ":") + TEST_PORT;

    public static String DEV_HOST = "dev.123.cn";
    public static String DEV_PORT = "";
    public static String DEV_ADDR = DEV_HOST + (UtilString.isBlank(DEV_PORT) ? "" : ":") + DEV_PORT;

    public static String getUrl(String key) {

        if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.ONLINE) {

            return ONLINE_ADDR + key;

        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.TEST) {

            return TEST_ADDR + key;

        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.DEV) {

            return DEV_ADDR + key;

        } else {
            throw new RuntimeException("没有找到匹配的url");
        }
    }

}
