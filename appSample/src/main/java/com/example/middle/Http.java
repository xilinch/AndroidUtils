package com.example.middle;


import com.xiaocoder.android_xcfw.http.IHttp.XCIHttpClient;
import com.xiaocoder.android_xcfw.http.IHttp.XCIRespHandler;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCReqType;

import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Http {

    private static XCIHttpClient httpClient;

    public static XCIHttpClient getHttpClient() {
        return httpClient;
    }

    public static void initHttp(XCIHttpClient client) {
        httpClient = client;
    }

    public static XCReqInfo http(XCReqInfo reqInfo, XCIRespHandler respHandler) {
        httpClient.http(reqInfo, respHandler);
        return reqInfo;
    }

    public static XCReqInfo get(String url, Map<String, Object> originParamsMap, boolean isSecretParam,
                                boolean isShowDialog, XCIRespHandler respHandler) {

        return common(url, XCReqType.GET, originParamsMap, isSecretParam, isShowDialog, respHandler);
    }

    public static XCReqInfo get(String url, Map<String, Object> originParamsMap, XCIRespHandler respHandler) {
        return common(url, XCReqType.GET, originParamsMap, true, true, respHandler);
    }

    public static XCReqInfo post(String url, Map<String, Object> originParamsMap, boolean isSecretParam,
                                 boolean isShowDialog, XCIRespHandler respHandler) {

        return common(url, XCReqType.POST, originParamsMap, isSecretParam, isShowDialog, respHandler);
    }

    public static XCReqInfo post(String url, Map<String, Object> originParamsMap, XCIRespHandler respHandler) {
        return common(url, XCReqType.POST, originParamsMap, true, true, respHandler);
    }

    /**
     * 封装请求model
     */
    private static XCReqInfo common(String url, XCReqType type, Map<String, Object> originParamsMap, boolean isSecretParam, boolean isShowDialog, XCIRespHandler respHandler) {
        XCReqInfo reqInfo = new XCReqInfo();

        reqInfo.setReqType(type);
        reqInfo.setUrl(url);
        reqInfo.setOriginParamsMap(originParamsMap);
        reqInfo.setSecretParam(isSecretParam);
        reqInfo.setShowDialog(isShowDialog);

        return http(reqInfo, respHandler);
    }
}


