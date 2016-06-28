package com.xiaocoder.android_xcfw.http.asynchttp;

import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.xiaocoder.android_xcfw.http.IHttp.XCIHttpClient;
import com.xiaocoder.android_xcfw.http.IHttp.XCIRespHandler;
import com.xiaocoder.android_xcfw.http.XCHttpHandlerCtrl;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.util.UtilCollections;

import java.util.List;
import java.util.Map;

/**
 * @author xiaocoder 2014-10-17 下午1:52:54
 * @email fengjingyu@foxmail.com
 * @description 用的是android-async-http库实现的
 */

/**
 * asyn-http-android库
 * 1 文件上传： params.put("字段", new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/0912/compass.png"));
 * 同时支持流和字节
 * <p/>
 * 2  Adding HTTP Basic Auth credentials 见github文档
 * http://loopj.com/android-async-http/
 */
public class XCAsyncClient implements XCIHttpClient {
    /**
     * 网络超时,毫秒
     */
    private static final int DEFAULT_TIME_OUT = 10000;

    private AsyncHttpClient httpClient;

    public XCAsyncClient() {
        init(DEFAULT_TIME_OUT);
    }

    public XCAsyncClient(int outTime) {
        init(outTime);
    }

    private void init(int timeOut) {
        this.httpClient = new AsyncHttpClient();
        this.httpClient.setTimeout(timeOut);
    }

    @Override
    public void http(XCReqInfo reqInfo, XCIRespHandler respHandler) {

        XCHttpHandlerCtrl httpHandlerCtrl = getHttpHandlerCtrl(reqInfo, respHandler);

        if (httpHandlerCtrl.isIntercepte()) {
            return;
        }

        addRequestHeaders(reqInfo.getHeadersMap());

        if (reqInfo.isGet()) {

            httpClient.get(reqInfo.getUrl(), getRequestParams(reqInfo.getFinalRequestParamsMap()), new XCAsyncRespHandler(httpHandlerCtrl));

        } else if (reqInfo.isPost()) {

            httpClient.post(reqInfo.getUrl(), getRequestParams(reqInfo.getFinalRequestParamsMap()), new XCAsyncRespHandler(httpHandlerCtrl));

        } else {
            throw new RuntimeException("XCAsyncClient---请求类型不匹配");
        }
    }

    @NonNull
    public XCHttpHandlerCtrl getHttpHandlerCtrl(XCReqInfo reqInfo, XCIRespHandler respHandler) {
        return new XCHttpHandlerCtrl(reqInfo, respHandler);
    }

    private void addRequestHeaders(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = entry.getValue();

            if (UtilCollections.isListAvaliable(values)) {
                httpClient.addHeader(entry.getKey(), entry.getValue().get(0));
            }

        }
    }

    /**
     * 转换参数
     */
    private RequestParams getRequestParams(Map<String, Object> secretedMap) {
        RequestParams params = new RequestParams();
        if (secretedMap != null) {
            for (Map.Entry<String, Object> item : secretedMap.entrySet()) {
                String key = item.getKey();
                Object value = item.getValue();
                params.put(key, value);
            }
        }
        return params;
    }

    public AsyncHttpClient getHttpClient() {
        return httpClient;
    }
}
