package com.example.middle.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.example.app.MainActivity;
import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.function.helper.XCAppHelper;
import com.xiaocoder.android_xcfw.http.XCDialogManager;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCRespHandler;
import com.xiaocoder.android_xcfw.http.XCRespInfo;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilDate;
import com.xiaocoder.android_xcfw.util.UtilString;
import com.xiaocoder.android_xcfw.util.UtilSystem;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class BaseRespHandler<T> extends XCRespHandler<T> {

    private Activity activityContext;

    public BaseRespHandler(Activity activityContext) {
        super();
        this.activityContext = activityContext;
    }

    public BaseRespHandler() {
    }

    @Override
    public void onReadySendRequest(XCReqInfo reqInfo) {
        super.onReadySendRequest(reqInfo);

        setHttpHeaders(reqInfo);
        setSendTime(reqInfo);
        setHttpSecretParams(reqInfo);

        showDialog(reqInfo);
    }

    /**
     * 可以根据不同的页面显示不同的加载dialog（判断activityContext实例）
     * <p/>
     * 也可以根据不同的接口url加载不同的dialog（reqModel里有url）
     * <p/>
     * 如果显示dialog，则isShowDialog为true 且 activityContext非空
     */
    private void showDialog(XCReqInfo reqInfo) {
        if (activityContext != null && reqInfo.isShowDialog()) {
            XCDialogManager dialogManager = XCDialogManager.getInstance(activityContext);

            if (dialogManager.isDialogNull()) {
                // TODO 更新dialog
                // setDialogListener(DIALOG)
                // dialogManager.updateHttpDialog(DIALOG);
            }
            dialogManager.show(String.valueOf(this.hashCode()));
        }
    }


    /**
     * 后台成功的返回码
     **/
    public static final String REQ_SUCCESS = "1";

    /**
     * 解析是否成功的规则，根据项目的json而定
     */
    @Override
    public boolean onMatchAppStatusCode(XCReqInfo reqInfo, XCRespInfo respInfo, T resultBean) {

        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "---onMatchAppStatusCode()");
        //TODO 解析规则
        if (resultBean instanceof IHttpRespInfo) {

            if (UtilString.equals(((IHttpRespInfo) resultBean).getCode(), REQ_SUCCESS)) {
                return true;
            } else {
                statusCodeWrongLogic(resultBean);
                return false;
            }

        } else {
            XCLog.e("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
            throw new RuntimeException("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
        }

    }

    public void statusCodeWrongLogic(T resultBean) {
        XCLog.shortToast(((IHttpRespInfo) resultBean).getMsg());
    }

    @Override
    public void onFailure(XCReqInfo reqInfo, XCRespInfo respInfo) {
        super.onFailure(reqInfo, respInfo);
        XCLog.shortToast("网络有误");
    }

    @Override
    public void onEnd(XCReqInfo reqInfo, XCRespInfo respInfo) {
        super.onEnd(reqInfo, respInfo);
        closeDialog(reqInfo.isShowDialog());
    }

    private void closeDialog(boolean isShowDialog) {
        if (activityContext != null && isShowDialog) {
            XCDialogManager.getInstance(activityContext).close(String.valueOf(hashCode()));
        }
    }

    public final void setDialogListener(Dialog dialog) {

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    closeDialog(true);

                    if (!(activityContext instanceof MainActivity)) {
                        activityContext.finish();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 配置项目的请求头
     */
    public void setHttpHeaders(XCReqInfo reqInfo) {
        //TODO 设置请求头
        Map<String, List<String>> map = new HashMap<>();
        map.put("_v", Arrays.asList(UtilSystem.getVersionCode(XCAppHelper.getAppContext()) + ""));
        map.put("_m", Arrays.asList(UtilSystem.getMacAddress(XCAppHelper.getAppContext())));
        map.put("_p", Arrays.asList("1"));
        reqInfo.setHeadersMap(map);
    }

    /**
     * 配置项目的加密规则
     */
    public void setHttpSecretParams(XCReqInfo reqInfo) {
        //TODO 设置加密
        if (reqInfo.isSecretParam()) {
            reqInfo.getFinalRequestParamsMap().put("testKey0", "java");
            reqInfo.getFinalRequestParamsMap().put("testKey3", "c");
            reqInfo.getFinalRequestParamsMap().put("testKey6", "c++");
        }
    }

    /**
     * 设置请求对象的生成时间
     */
    public void setSendTime(XCReqInfo reqInfo) {

        long time = System.currentTimeMillis();

        reqInfo.setSendTime(time + XCConstant.COMMA_EN + UtilDate.format(new Date(time)));
    }

}
