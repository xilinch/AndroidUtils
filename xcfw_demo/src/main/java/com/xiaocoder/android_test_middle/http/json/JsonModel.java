package com.xiaocoder.android_test_middle.http.json;

import com.xiaocoder.android_xcfw.json.XCJsonBean;
import com.xiaocoder.android_test_middle.http.IHttpRespInfo;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class JsonModel extends XCJsonBean implements IHttpRespInfo {

    @Override
    public String getCode() {
        return getString("code");
    }

    @Override
    public String getMsg() {
        return getString("msg");
    }
}
