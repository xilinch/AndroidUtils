package com.xiaocoder.android_xcfw.http;

import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.application.XCModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description http返回的信息
 */
public class XCRespInfo extends XCModel {

    /**
     * 返回的成功失败的类型，目前有5个类型
     */
    private XCRespType respType;

    private int httpCode;

    private Map<String, List<String>> respHeaders;

    private byte[] dataBytes;

    private String dataString;

    /**
     * throwable null为success，非空为fail
     */
    private Throwable throwable;

    public XCRespInfo(XCRespType respType, int httpCode, Map<String, List<String>> respHeaders, byte[] dataBytes, String dataString, Throwable throwable) {
        this.respType = respType;
        this.httpCode = httpCode;
        this.respHeaders = respHeaders;
        this.dataBytes = dataBytes;
        this.dataString = dataString;
        this.throwable = throwable;
    }

    public XCRespInfo() {
    }

    public XCRespType getRespType() {
        return respType;
    }

    public void setRespType(XCRespType respType) {
        this.respType = respType;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, List<String>> getRespHeaders() {
        if (respHeaders == null) {
            respHeaders = new HashMap<>();
        }
        return respHeaders;
    }

    public void setRespHeaders(Map<String, List<String>> respHeaders) {
        if (respHeaders == null) {
            respHeaders = new HashMap<>();
        }
        this.respHeaders = respHeaders;
    }

    public byte[] getDataBytes() {
        if (dataBytes == null) {
            dataBytes = new byte[1];
        }
        return dataBytes;
    }

    public void setDataBytes(byte[] dataBytes) {
        if (dataBytes == null) {
            dataBytes = new byte[1];
        }
        this.dataBytes = dataBytes;
    }

    public String getDataString() {
        if (dataString == null) {
            dataString = "";
        }
        return dataString;
    }

    public void setDataString(String dataString) {
        if (dataString == null) {
            dataString = "";
        }
        this.dataString = dataString;
    }

    public void setDataString(byte[] bytes) {
        try {
            dataString = new String(bytes, XCConstant.ENCODING_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
            dataString = "";
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isSuccessAll() {
        return XCRespType.SUCCESS_ALL == respType;
    }
}
