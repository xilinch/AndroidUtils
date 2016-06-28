package com.xiaocoder.android_xcfw.http.okhttp;

import android.support.annotation.NonNull;

import com.xiaocoder.android_xcfw.http.IHttp.XCIHttpClient;
import com.xiaocoder.android_xcfw.http.IHttp.XCIRespHandler;
import com.xiaocoder.android_xcfw.http.XCFileWrapper;
import com.xiaocoder.android_xcfw.http.XCHttpHandlerCtrl;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.util.UtilCollections;
import com.xiaocoder.android_xcfw.util.UtilString;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCOkClient implements XCIHttpClient {

    private OkHttpClient httpClient = new OkHttpClient();

    @Override
    public void http(XCReqInfo reqInfo, XCIRespHandler respHandler) {

        XCHttpHandlerCtrl httpHandlerCtrl = getHttpHandlerCtrl(reqInfo, respHandler);

        if (httpHandlerCtrl.isIntercepte()) {
            return;
        }

        Request request = createRequest(reqInfo);

        httpClient.newCall(request).enqueue(new XCOkRespHandler(httpHandlerCtrl));
    }

    @NonNull
    public XCHttpHandlerCtrl getHttpHandlerCtrl(XCReqInfo reqInfo, XCIRespHandler respHandler) {
        return new XCHttpHandlerCtrl(reqInfo, respHandler);
    }

    private Request createRequest(XCReqInfo reqInfo) {

        if (reqInfo == null) {
            return null;
        }

        Request.Builder requestBuilder = new Request.Builder();
        // 构建请求方式、参数、url
        buildeTypeParamsUrl(reqInfo, requestBuilder);
        // 构建请求header
        buildHeader(reqInfo, requestBuilder);
        // 返回request
        return requestBuilder.build();
    }

    private void buildeTypeParamsUrl(XCReqInfo reqInfo, Request.Builder requestBuilder) {

        if (isGet(reqInfo, requestBuilder)) {
            return;
        }

        if (isPost(reqInfo, requestBuilder)) {
            return;
        }

        throw new RuntimeException("XCOkClient---请求类型不匹配");

    }

    private boolean isPost(XCReqInfo reqInfo, Request.Builder requestBuilder) {
        if (reqInfo.isPost()) {

            requestBuilder.url(reqInfo.getUrl());

            if (isPostString(reqInfo, requestBuilder)) {
                return true;
            }

            if (isPostForm(reqInfo, requestBuilder)) {
                return true;
            }

            if (isPostMultiForm(reqInfo, requestBuilder)) {
                return true;
            }

        }
        return false;
    }

    private boolean isPostForm(XCReqInfo reqInfo, Request.Builder requestBuilder) {
        if (isIncludeFile(reqInfo)) {
            return false;
        }

        Map<String, Object> secretParamsMap = reqInfo.getFinalRequestParamsMap();

        if (UtilCollections.isMapAvaliable(secretParamsMap)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            for (Map.Entry<String, Object> entry : secretParamsMap.entrySet()) {
                formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            requestBuilder.post(formBodyBuilder.build());
        }

        return true;
    }

    private boolean isPostMultiForm(XCReqInfo reqInfo, Request.Builder requestBuilder) {

        Map<String, Object> secretParamsMap = reqInfo.getFinalRequestParamsMap();

        if (UtilCollections.isMapAvaliable(secretParamsMap)) {

            MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
            multiBuilder.setType(MultipartBody.FORM);

            for (Map.Entry<String, Object> entry : secretParamsMap.entrySet()) {

                if (entry.getValue() == null) {
                    continue;
                }

                if (entry.getValue() instanceof File) {

                    XCFileWrapper fileWrapper = new XCFileWrapper((File) entry.getValue());

                    if (fileWrapper.isExist()) {
                        multiBuilder.addFormDataPart(entry.getKey(), fileWrapper.getName(), RequestBody.create(fileWrapper.getMediaType(), fileWrapper.getFile()));
                    } else {
                        continue;
                    }

                } else {
                    multiBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            requestBuilder.post(multiBuilder.build());
        }

        return true;
    }

    private boolean isIncludeFile(XCReqInfo reqInfo) {

        Map<String, Object> secretParamsMap = reqInfo.getFinalRequestParamsMap();

        if (UtilCollections.isMapAvaliable(secretParamsMap)) {
            for (Map.Entry<String, Object> entry : secretParamsMap.entrySet()) {
                if (entry.getValue() instanceof File) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGet(XCReqInfo reqInfo, Request.Builder builder) {
        if (reqInfo.isGet()) {

            builder.get().url(reqInfo.getUrl() + reqInfo.buildGetParams(reqInfo.getFinalRequestParamsMap()));

            return true;
        }
        return false;
    }


    private Request.Builder buildHeader(XCReqInfo reqInfo, Request.Builder builder) {
        Map<String, List<String>> headers = reqInfo.getHeadersMap();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {

            List<String> values = entry.getValue();
            String key = entry.getKey();

            if (UtilCollections.isListAvaliable(values)) {
                for (String value : values) {
                    builder.addHeader(key, value);
                }
            }
        }
        return builder;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public boolean isPostString(XCReqInfo reqInfo, Request.Builder requestBuilder) {
        if (UtilString.isAvaliable(reqInfo.getPostStringContentType()) && UtilString.isAvaliable(reqInfo.getPostString())) {

            // requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json));
            requestBuilder.post(RequestBody.create(MediaType.parse(reqInfo.getPostStringContentType()), reqInfo.getPostString()));

            return true;
        }

        return false;
    }
}
