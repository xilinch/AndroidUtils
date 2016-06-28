package com.xiaocoder.android_xcfw.http.IHttp;

import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCRespInfo;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 对http请求发出之前和http回调结束的监听
 */
public interface XCIHttpNotify {

    /**
     * 正准备发，还没有发出去,即在respHandler的onReadySendRequest()之前调用该方法
     *
     * @return true 为继续调用后面的方法，false 为拦截后面的方法即不会调用sucess /failure end
     */
    boolean onReadySendNotify(XCReqInfo reqInfo);

    /**
     * 一个请求完全结束后，即调用完end后,回调该方法
     */
    void onEndNotify(XCReqInfo reqInfo, XCRespInfo respInfo);

}
