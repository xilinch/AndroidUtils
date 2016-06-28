package com.xiaocoder.android_xcfw.http.okhttp;

import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.http.XCHttpHandlerCtrl;
import com.xiaocoder.android_xcfw.http.XCRespInfo;
import com.xiaocoder.android_xcfw.http.XCRespType;
import com.xiaocoder.android_xcfw.io.XCLog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description okhttp实现，该库的回调在子线程中的
 */
public class XCOkRespHandler implements Callback {

    private XCHttpHandlerCtrl httpHandlerCtrl;

    public XCOkRespHandler(XCHttpHandlerCtrl httpHandlerCtrl) {
        this.httpHandlerCtrl = httpHandlerCtrl;
    }

    @Override
    public void onFailure(Call call, IOException e) {

        if (httpHandlerCtrl.getRespHandler() != null) {

            XCRespInfo respInfo = new XCRespInfo();

            respInfo.setRespType(XCRespType.FAILURE);
            respInfo.setThrowable(e);

            httpHandlerCtrl.handlerSuccess(respInfo);

        } else {
            XCLog.i(XCConstant.TAG_HTTP, "onFailure--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (httpHandlerCtrl.getRespHandler() != null) {

            XCRespInfo respInfo = new XCRespInfo();
            respInfo.setHttpCode(response.code());
            respInfo.setRespHeaders(headers2Map(response.headers()));

            // 只能读一次，否则异常
            byte[] bytes = response.body().bytes();
            respInfo.setDataBytes(bytes);
            respInfo.setDataString(bytes);

            respInfo.setRespType(XCRespType.SUCCESS_WAIT_TO_PARSE);
            respInfo.setThrowable(null);

            httpHandlerCtrl.handlerSuccess(respInfo);
        } else {
            XCLog.i(XCConstant.TAG_HTTP, "onSuccess--未传入handler--" + httpHandlerCtrl.getReqInfo());
        }

    }

    private Map<String, List<String>> headers2Map(Headers headers) {
        return headers.toMultimap();
    }
}
