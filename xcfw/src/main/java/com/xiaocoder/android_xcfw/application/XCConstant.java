package com.xiaocoder.android_xcfw.application;
/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCConstant {
    /**
     * 不要用system.out输出，用i(),i方法里默认的是该tag
     */
    public static String TAG_SYSTEM_OUT = "System.out";
    /**
     * 可查看如url 参数 返回的json等
     */
    public static String TAG_HTTP = "http";
    /**
     * 可查看http里每个方法的调用顺序
     */
    public static String TAG_RESP_HANDLER = "respHandler";
    /**
     * DB的相关操作记录
     */
    public static String TAG_DB = "db";
    /**
     * 如异常、重要的日志等用该tag，e()方法里就是用了该tag
     */
    public static String TAG_ALOG = "alog";
    /**
     * 以下两个是临时测试查看的tag
     */
    public static String TAG_TEMP = "temp";
    public static String TAG_TEST = "test";

    /**
     * 英文逗号
     */
    public static final String COMMA_EN = ",";
    /**
     * 中文逗号
     */
    public static final String COMMA_CN = "，";

    /**
     * 人民币符号
     */
    public static final String RMB = "¥";

    /**
     * 乘号
     */
    public static final String X = "×";

    /**
     * 下滑线
     */
    public static final String UNDERLINE = "_";

    /**
     * 编码格式
     */
    public static final String ENCODING_UTF8 = "utf-8";

    /**
     * 编码格式
     */
    public static final String ENCODING_GBK = "gbk";

    public static final String GET = "GET";

    public static final String POST = "POST";

}
