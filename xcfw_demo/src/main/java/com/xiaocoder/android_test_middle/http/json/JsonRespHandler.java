package com.xiaocoder.android_test_middle.http.json;

import android.app.Activity;

import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCRespInfo;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.json.XCJsonParse;
import com.xiaocoder.android_test_middle.http.BaseRespHandler;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description JsonModel jsonparse解析实现的handler
 */
public class JsonRespHandler extends BaseRespHandler<JsonModel> {

    public JsonRespHandler(Activity activity) {
        super(activity);
    }

    public JsonRespHandler() {

    }

    @Override
    public JsonModel onParse2Model(XCReqInfo xcReqInfo, XCRespInfo xcRespInfo) {

        XCLog.i(XCConstant.TAG_RESP_HANDLER, this.toString() + "-----parseWay()");

        return XCJsonParse.getJsonParseData(xcRespInfo.getDataString(), JsonModel.class);
    }
}
