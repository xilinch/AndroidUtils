package com.example.middle.config;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ConfigFile {

    //TODO 修改app的名称
    public static String APP_NAME = "test";
    /**
     * app的根目录
     */
    public static String APP_ROOT = "app_" + APP_NAME;
    /**
     * crash日志目录
     */
    public static String CRASH_DIR = APP_ROOT + "/crash";
    /**
     * 图片加载缓存目录的目录
     */
    public static String CACHE_DIR = APP_ROOT + "/cache";
    /**
     * chat目录
     */
    public static String MEDIA_DIR = APP_ROOT + "/media";
    /**
     * 目录
     */
    public static String PHOTO_DIR = MEDIA_DIR + "/photo";
    /**
     * 目录
     */
    public static String VIDEO_DIR = MEDIA_DIR + "/voice";
    /**
     * 目录
     */
    public static String MOIVE_DIR = MEDIA_DIR + "/moive";
    /**
     * 日志文件名
     */
    public static String LOG_FILE = APP_ROOT + "_log";
    /**
     * sp文件名
     */
    public static String SP_FILE = APP_ROOT + "_sp";

}
