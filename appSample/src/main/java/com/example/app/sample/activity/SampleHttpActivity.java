package com.example.app.sample.activity;

import android.os.Bundle;

import com.example.app.sample.model.SampleHttpModel;
import com.example.app.sample.parse.SampleRespParse;
import com.example.middle.Http;
import com.example.middle.base.BaseActivity;
import com.example.middle.http.gson.GsonRespHandler;
import com.example.middle.http.json.JsonModel;
import com.example.middle.http.json.JsonRespHandler;
import com.xiaocoder.android_xcfw.http.XCReqInfo;
import com.xiaocoder.android_xcfw.http.XCRespInfo;
import com.xiaocoder.android_xcfw.io.XCLog;

import java.io.File;
import java.util.HashMap;

public class SampleHttpActivity extends BaseActivity {

    //-------------修改配置去App、Config里----------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 网络请求，手动解析parse层
     */
    public void requestParse() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("param1", "value1");
        map.put("param2", new File("path"));

        // 可以无参数，仅访问url
        Http.get("url", null, null);

        // 请求成功，解析成功，业务状态码成功
        Http.post("url", map, new SampleRespParse() {
            @Override
            public void onSuccessAll(XCReqInfo reqInfo, XCRespInfo respInfo, SampleHttpModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                XCLog.shortToast(resultBean.getMsg());
                XCLog.longToast(resultBean.getName());
            }
        });

        // 请求成功，解析失败 或 业务状态码失败
        Http.post("url", map, new SampleRespParse(this) {
            // 请求成功，解析失败
            @Override
            public void onSuccessButParseWrong(XCReqInfo reqInfo, XCRespInfo respInfo) {
                super.onSuccessButParseWrong(reqInfo, respInfo);
            }

            // 请求成功，解析成功，业务状态码失败
            @Override
            public void onSuccessButCodeWrong(XCReqInfo reqInfo, XCRespInfo respInfo, SampleHttpModel resultBean) {
                super.onSuccessButCodeWrong(reqInfo, respInfo, resultBean);
                XCLog.dShortToast(resultBean.getCode());
            }
        });
    }

    /**
     * 网络请求，gson解析层
     */
    public void requestGson() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("param1", "value1");
        map.put("param2", new File("path"));

        // 请求成功，解析成功，业务状态码成功
        Http.post("url", map, new GsonRespHandler<SampleHttpModel>(this, SampleHttpModel.class) {
            @Override
            public void onSuccessAll(XCReqInfo reqInfo, XCRespInfo respInfo, SampleHttpModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                XCLog.shortToast(resultBean.getMsg());
            }
        });
    }

    /**
     * 网络请求，jsonModel解析层（适用于如收藏接口，只有很少量信息返回）
     */
    public void requestJsonModel() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("param1", "value1");
        map.put("param2", "value2");

        // 有参数，回调不处理
        Http.post("url", map, new JsonRespHandler());

        // 请求成功，解析成功，业务状态码成功
        Http.post("url", map, new JsonRespHandler() {
            @Override
            public void onSuccessAll(XCReqInfo reqInfo, XCRespInfo respInfo, JsonModel resultBean) {
                super.onSuccessAll(reqInfo, respInfo, resultBean);
                XCLog.shortToast(resultBean.getMsg());
            }
        });

        // 请求失败
        Http.get("url", map, new JsonRespHandler() {
            @Override
            public void onFailure(XCReqInfo reqInfo, XCRespInfo respInfo) {
                super.onFailure(reqInfo, respInfo);
            }
        });
    }

}
