package com.xiaocoder.android_xcfw.http;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description http的响应结果
 */
public enum XCRespType {

    /**
     * FAILURE:网络请求失败
     * SUCCESS_WAIT_TO_PARSE:网络请求成功，还未解析，未判断状态码
     * SUCCESS_BUT_PARSE_WRONG：网络请求成功，解析失败
     * SUCCESS_BUT_CODE_WRONG：网络请求成功，解析成功，项目业务逻辑的状态码有误
     * SUCCESS_ALL:网络请求成功，解析 与 项目业务逻辑的状态码都是成功的
     */
    FAILURE,
    SUCCESS_WAIT_TO_PARSE,
    SUCCESS_BUT_PARSE_WRONG,
    SUCCESS_BUT_CODE_WRONG,
    SUCCESS_ALL

}
