package com.xiaocoder.android_xcfw.http.IHttp;

import com.xiaocoder.android_xcfw.http.XCReqInfo;

/**
 * @author xiaocoder 2014-10-17 下午1:52:54
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface XCIHttpClient {

    void http(XCReqInfo reqInfo, XCIRespHandler resphandler);

}
