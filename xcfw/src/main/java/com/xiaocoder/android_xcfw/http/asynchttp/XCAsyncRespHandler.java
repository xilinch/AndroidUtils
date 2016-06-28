package com.xiaocoder.android_xcfw.http.asynchttp;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.function.thread.XCExecutor;
import com.xiaocoder.android_xcfw.http.XCHttpHandlerCtrl;
import com.xiaocoder.android_xcfw.http.XCRespInfo;
import com.xiaocoder.android_xcfw.http.XCRespType;
import com.xiaocoder.android_xcfw.io.XCLog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 用的是android-async-http库实现的(1.4.9不在用系统的httpClient库了)
 * 该库的回调是在主线程中的
 */
public class XCAsyncRespHandler extends AsyncHttpResponseHandler {

    private XCHttpHandlerCtrl httpHandlerCtrl;

    public XCAsyncRespHandler(XCHttpHandlerCtrl httpHandlerCtrl) {
        this.httpHandlerCtrl = httpHandlerCtrl;
    }

    @Override
    public void onSuccess(final int httpCode, final Header[] headers, final byte[] bytes) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            XCExecutor.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    XCRespInfo respInfo = new XCRespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setRespType(XCRespType.SUCCESS_WAIT_TO_PARSE);
                    respInfo.setThrowable(null);

                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    httpHandlerCtrl.handlerSuccess(respInfo);
                }
            });

        } else {
            XCLog.i(XCConstant.TAG_HTTP, "onSuccess--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }

    }

    @Override
    public void onFailure(final int httpCode, final Header[] headers, final byte[] bytes, final Throwable throwable) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            XCExecutor.getFix().execute(new Runnable() {
                @Override
                public void run() {
                    XCRespInfo respInfo = new XCRespInfo();
                    respInfo.setHttpCode(httpCode);
                    respInfo.setRespHeaders(headers2Map(headers));
                    respInfo.setDataBytes(bytes);
                    respInfo.setDataString(bytes);

                    respInfo.setRespType(XCRespType.FAILURE);
                    respInfo.setThrowable(throwable);

                    httpHandlerCtrl.handlerFail(respInfo);
                }
            });

        } else {
            XCLog.i(XCConstant.TAG_HTTP, "onFailure--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        super.onProgress(bytesWritten, totalSize);

        if (httpHandlerCtrl.getRespHandler() != null) {

            httpHandlerCtrl.handlerProgress(bytesWritten, totalSize, bytesWritten / (totalSize * 1.0D));

        }

    }

    private Map<String, List<String>> headers2Map(Header[] headers) {
        Map<String, List<String>> headersMap = new HashMap<>();

        if (headers != null) {
            for (Header header : headers) {
                if (header != null) {
                    headersMap.put(header.getName(), Arrays.asList(header.getValue()));
                }
            }
        }
        return headersMap;
    }
}
