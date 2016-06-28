package com.example.middle.http.json;

import com.example.middle.http.IHttpRespInfo;
import com.xiaocoder.android_xcfw.json.XCJsonBean;

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
