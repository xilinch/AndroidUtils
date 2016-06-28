package com.xiaocoder.android_xcfw.http;

import com.xiaocoder.android_xcfw.application.XCModel;
import com.xiaocoder.android_xcfw.http.IHttp.XCIHttpNotify;
import com.xiaocoder.android_xcfw.util.UtilCollections;
import com.xiaocoder.android_xcfw.util.UtilString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description http请求的信息
 */
public class XCReqInfo extends XCModel {
    /**
     * 原始的发送的时间  “时间戳”+“_”+格式化
     */
    private String sendTime;
    /**
     * POST  GET
     */
    private XCReqType reqType;
    /**
     * 接口地址
     */
    private String url;
    /**
     * http的请求头
     */
    private Map<String, List<String>> headersMap;
    /**
     * http的原始请求参数，未加密
     */
    private Map<String, Object> originParamsMap;
    /**
     * 该次http请求是否加密，不会影响别的http请求
     */
    private boolean isSecretParam;
    /**
     * http请求发给服务器的参数（如果加密了，就是加密后的；如果不加密，则和originParamsMap的内容相等）
     */
    private Map<String, Object> finalRequestParamsMap;
    /**
     * 是否显示加载dialog
     */
    private boolean isShowDialog;
    /**
     * postString的ContentType
     */
    private String postStringContentType = "";
    /**
     * postString的内容
     */
    private String postString = "";
    /**
     * http的请求开始与结束的监听
     */
    private XCIHttpNotify httpNotify;

    public String getSendTime() {
        if (sendTime == null || sendTime.trim().length() == 0) {
            sendTime = "";
        }
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        if (sendTime == null || sendTime.trim().length() == 0) {
            sendTime = "";
        }
        this.sendTime = sendTime;
    }

    public XCReqType getReqType() {
        return reqType;
    }

    public void setReqType(XCReqType reqType) {
        this.reqType = reqType;
    }

    public String getUrl() {
        if (url == null || url.trim().length() == 0) {
            url = "";
        }
        return url;
    }

    public void setUrl(String url) {
        if (url == null || url.trim().length() == 0) {
            url = "";
        }
        this.url = url;
    }

    public Map<String, List<String>> getHeadersMap() {
        if (headersMap == null) {
            headersMap = new HashMap<>();
        }
        return headersMap;
    }

    public void setHeadersMap(Map<String, List<String>> headersMap) {
        if (headersMap == null) {
            headersMap = new HashMap<>();
        }
        this.headersMap = headersMap;
    }

    public Map<String, Object> getOriginParamsMap() {
        if (originParamsMap == null) {
            originParamsMap = new HashMap<>();
        }
        return originParamsMap;
    }

    public void setOriginParamsMap(Map<String, Object> originParamsMap) {
        if (originParamsMap == null) {
            originParamsMap = new HashMap<>();
        }
        this.originParamsMap = originParamsMap;
    }

    public boolean isSecretParam() {
        return isSecretParam;
    }

    public void setSecretParam(boolean secretParam) {
        isSecretParam = secretParam;
    }

    public Map<String, Object> getFinalRequestParamsMap() {


        if (finalRequestParamsMap == null) {
            finalRequestParamsMap = new HashMap<>();
        }

        if (finalRequestParamsMap.isEmpty()) {
            // 克隆一份originParams
            finalRequestParamsMap.putAll(getOriginParamsMap());
        }

        return finalRequestParamsMap;
    }

    public boolean isShowDialog() {
        return isShowDialog;
    }

    public void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    public XCIHttpNotify getHttpNotify() {
        return httpNotify;
    }

    public void setHttpNotify(XCIHttpNotify httpNotify) {
        this.httpNotify = httpNotify;
    }

    public static final String HINT_AFTER_SECRET = "发给服务器的参数-->";
    public static final String HINT_NO_SECRET = "原始参数-->";

    @Override
    public String toString() {
        return "XCReqInfo{" +
                "sendTime='" + getSendTime() + '\'' +
                ", reqType=" + getReqType() +
                ", url='" + getUrl() + '\'' +
                ", headersMap=" + getHeadersMap() +
                ", " + HINT_NO_SECRET + " originParamsMap=" + getOriginParamsMap() +
                ", isSecretParam=" + isSecretParam() +
                ", " + HINT_AFTER_SECRET + " finalRequestParamsMap=" + getFinalRequestParamsMap() +
                ", isShowDialog=" + isShowDialog() +
                ", postStringContentType=" + getPostStringContentType() +
                ", postString=" + getPostString() +
                '}';
    }

    public XCReqInfo() {
    }

    public XCReqInfo(XCReqType reqType, String url, Map<String, Object> originParamsMap) {
        this.reqType = reqType;
        this.url = url;
        this.originParamsMap = originParamsMap;
    }

    public boolean isGet() {
        return getReqType() == XCReqType.GET;
    }

    public boolean isPost() {
        return getReqType() == XCReqType.POST;
    }

    public static final String AND = "&";

    public static final String EQUAL = "=";

    public static final String QUESTION = "?";

    /**
     * 构建get方式的参数串（未拼接ur）
     *
     * @return ?abc=123
     */
    public String buildGetParams(Map<String, Object> params) {

        if (UtilCollections.isMapAvaliable(params)) {
            // 有参数,就一定会多一个AND
            StringBuilder sb = new StringBuilder();

            sb.append(QUESTION);

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                sb.append(entry.getKey() + EQUAL + entry.getValue() + AND);
            }

            return UtilString.getBeforeLastSimbolString(sb.toString(), AND);

        } else {
            // 无参数
            return "";
        }
    }

    public String getPostStringContentType() {
        return postStringContentType;
    }

    public void setPostStringContentType(String postStringContentType) {
        this.postStringContentType = postStringContentType;
    }

    public String getPostString() {
        return postString;
    }

    public void setPostString(String postString) {
        this.postString = postString;
    }

}