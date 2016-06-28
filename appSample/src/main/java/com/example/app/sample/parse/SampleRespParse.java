package com.example.app.sample.parse;

import android.app.Activity;

import com.example.app.sample.model.SampleHttpModel;
import com.example.middle.http.BaseRespHandler;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCRespInfo;

/**
 * @author xiaocoder on 2016/6/27 16:54
 * @email fengjingyu@foxmail.com
 * @description 解析类，多个界面如果都调到同一个接口的时候，可以重复用handler
 */
public class SampleRespParse extends BaseRespHandler<SampleHttpModel> {

    public SampleRespParse(Activity activityContext) {
        super(activityContext);
    }

    public SampleRespParse() {
    }

    @Override
    public SampleHttpModel onParse2Model(XCReqInfo xcReqInfo, XCRespInfo xcRespInfo) {

        String respString = xcRespInfo.getDataString();
        // 解析respString，这里省略
        // 假数据
        SampleHttpModel model = new SampleHttpModel();
        model.setCode("1");
        model.setMsg("msg");
        model.setName("name");

        return model;
    }
}
