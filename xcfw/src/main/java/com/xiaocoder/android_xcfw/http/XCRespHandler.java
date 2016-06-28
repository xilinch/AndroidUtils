package com.xiaocoder.android_xcfw.http;

import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.http.IHttp.XCIRespHandler;
import com.xiaocoder.android_xcfw.io.XCLog;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 回调接口的一个抽象类
 */
public abstract class XCRespHandler<T> implements XCIRespHandler<T> {

    @Override
    public void onReadySendRequest(XCReqInfo reqInfo) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----onReadySendRequest()");
    }

    @Override
    public void onFailure(XCReqInfo reqInfo, XCRespInfo respInfo) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----onFailure()");
    }

    @Override
    public boolean onMatchAppStatusCode(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----onMatchAppStatusCode()");
        return false;
    }

    @Override
    public void onSuccessAll(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----onSuccessAll()");
    }

    @Override
    public void onSuccessButParseWrong(XCReqInfo reqInfo, XCRespInfo respInfo) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----onSuccessButParseWrong()");
    }

    @Override
    public void onSuccessButCodeWrong(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----onSuccessButCodeWrong()");
    }

    @Override
    public void onEnd(XCReqInfo reqInfo, XCRespInfo respInfo) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "----onEnd()");
    }

    @Override
    public void onProgressing(XCReqInfo reqInfo, long bytesWritten, long totalSize, double percent) {
        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "----onProgressing()" + (percent * 100) + "%");
    }

}

