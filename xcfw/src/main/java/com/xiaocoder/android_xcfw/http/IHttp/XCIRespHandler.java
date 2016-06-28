package com.xiaocoder.android_xcfw.http.IHttp;

import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCRespInfo;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public interface XCIRespHandler<T> {

    /**
     * 发送请求之前
     * <p/>
     * 可以showdialog,加密等
     */
    void onReadySendRequest(XCReqInfo reqInfo);

    /**
     * 如果解析失败：一定得返回null
     */
    T onParse2Model(XCReqInfo reqInfo, XCRespInfo respInfo);

    /**
     * 对返回状态码的一个判断，每个项目的认定操作成功的状态码或结构可能不同，在这里统一判断
     * <p/>
     * 如果调用到这个方法，表示解析一定是成功的；如果解析失败，是不会调用这个方法的
     */
    boolean onMatchAppStatusCode(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean);

    /**
     * http 请求成功，解析失败
     */
    void onSuccessButParseWrong(XCReqInfo reqInfo, XCRespInfo respInfo);

    /**
     * http 请求成功，解析成功，项目业务逻辑的状态码有误
     */
    void onSuccessButCodeWrong(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean);

    /**
     * http请求成功，解析成功，状态码成功，回调该方法
     */
    void onSuccessAll(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean);

    /**
     * http请求失败，回调该方法
     */
    void onFailure(XCReqInfo reqInfo, XCRespInfo respInfo);

    /**
     * onSuccess%或 onFailure 的逻辑调用完后回调该方法
     * <p/>
     * 可以关闭dialog
     */
    void onEnd(XCReqInfo reqInfo, XCRespInfo respInfo);

    /**
     * 加载进度
     */
    void onProgressing(XCReqInfo reqInfo, long bytesWritten, long totalSize, double percent);

}
