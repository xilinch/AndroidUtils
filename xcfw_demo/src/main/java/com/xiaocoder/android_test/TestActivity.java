package com.xiaocoder.android_test;

import android.os.Bundle;
import android.util.Base64;

import com.xiaocoder.android_xcfw.encryption.aes.AesEncryptAndDecrypt;
import com.xiaocoder.android_xcfw.encryption.des.DesEncryptAndDecrypt;
import com.xiaocoder.android_xcfw.encryption.md5.UtilMd5;
import com.xiaocoder.android_xcfw.encryption.rsa.UtilBase64;
import com.xiaocoder.android_xcfw.io.XCIO;
import com.xiaocoder.android_xcfw.io.XCIOAndroid;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.io.XCSP;
import com.xiaocoder.android_xcfw.json.XCJsonBean;
import com.xiaocoder.android_xcfw.json.XCJsonParse;
import com.xiaocoder.android_xcfw.util.UtilString;
import com.xiaocoder.android_xcfw.util.UtilSystem;
import com.xiaocoder.android_test_middle.base.BaseActivity;
import com.xiaocoder.android_test_middle.config.ConfigFile;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
    }

    public void initWidgets() {
        testSp();
        testLog();
        testUUID();
        testClone();
        testSecret();
        testJsonFormat();
        testLinkedBlockQueue();
    }

    private void testSp() {
        XCSP.spPut("1", "123 ");
        XCSP.spPut("2", 456);
        XCSP.spPut("3", 789.1F);
        XCSP.spPut("4", false);
        XCSP.spPut("5", " abc ");

        //String result = XCSP.spGet("1", "abc") + XCSP.spGet("2", 0) + XCSP.spGet("3", 0.1f)+ XCSP.spGet("4", true) + XCSP.spGet("5", "abc") + XCSP.spGet("6", null) + XCSP.spGet("7", "    jkl");
        //XCLog.shortToast(result);

        XCSP.spPut("1", 0.1f);

        String result2 = XCSP.spGet("5", "java") + XCSP.spGet("1", 0.0f) + XCSP.spGet("2", 0) + XCSP.spGet("3", 0.1f)
                + XCSP.spGet("4", true) + XCSP.spGet("6", null) + XCSP.spGet("7", "    jkl");
        XCLog.shortToast(result2);
    }

    private void testLog() {
        TestModel model = null;
        // 不会报错
        XCLog.i(model);

        XCLog.i(1);
        XCLog.i(true);
        XCLog.i(false);
        Object obj = null;
        XCLog.i(obj);

        try {
            XCLog.e("123");
            XCLog.e("345");
            XCLog.e("678");
            int i = 1 / 0;
        } catch (Exception e) {
            XCLog.e(this, "--oncreate()--", e);
        }
        // XCLog.clearLog();
        XCLog.e(this, "1234567890");
        XCLog.tempPrint("android--" + System.currentTimeMillis());

        XCLog.i(XCIO.getAllFilesByDirQueue(XCIOAndroid.createDirInSDCard(ConfigFile.APP_ROOT), new ArrayList<File>()));

        XCIO.toFileByBytes(XCIOAndroid.createFileInAndroid(this, ConfigFile.APP_ROOT, "lalala.txt"), "写入的内容--1234567890987654321abc".getBytes(), true);
    }

    private void testUUID() {
        XCLog.i("UUID----" + UUID.randomUUID() + "----" + UUID.randomUUID().toString().length());
    }

    private void testClone() {
        TestModel testModel = new TestModel();
        testModel.setCode(200);
        testModel.setMsg("ceshi");

        TestModel simple = (TestModel) testModel.simpleClone();

        TestModel deep = (TestModel) testModel.deepClone();

        XCLog.i("原始数据---" + testModel);
        XCLog.i("浅克隆---" + simple);
        XCLog.i("深克隆---" + deep);

        TestModel simple2 = (TestModel) testModel.simpleClone();
        simple2.setMsg("123");
        simple2.setCode(300);

        TestModel deep2 = (TestModel) testModel.deepClone();
        deep2.setMsg("123123");
        deep2.setCode(302);

        XCLog.i("原始数据---" + testModel);
        XCLog.i("浅克隆---" + simple2);
        XCLog.i("深克隆---" + deep2);
    }

    private void testSecret() {
        String one = UtilMd5.MD5Encode("123456abc");
        String two = UtilMd5.MD5Encode2("123456abc");
        XCLog.i(one);
        XCLog.i(two);
        XCLog.i(UtilString.equals(one, two)); // true

        String des_close = DesEncryptAndDecrypt.encodeRequestStr("today is haha 123");
        String des_open = DesEncryptAndDecrypt.decodeResponseStr(des_close);
        XCLog.i(des_close);
        XCLog.i(des_open);

        String aes_close = AesEncryptAndDecrypt.encodeRequestStr("computer 123 macpro");
        String aes_open = AesEncryptAndDecrypt.decodeResponseStr(aes_close);
        XCLog.i(aes_close);
        XCLog.i(aes_open);

        try {
            String base64_e = UtilBase64.encode("123  HEHE".getBytes());
            String base64_d = new String(UtilBase64.decode(base64_e), "utf-8");
            XCLog.i(base64_e);
            XCLog.i(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String base64_e = new String(Base64.encode("123  HEHE".getBytes(), Base64.DEFAULT), "utf-8");
            String base64_d = new String(Base64.decode(base64_e.getBytes(), Base64.DEFAULT), "utf-8");
            XCLog.i(base64_e);
            XCLog.i(base64_d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testLinkedBlockQueue() {

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");

//        try {
//            XCLog.i(queue.take()); // a  //take 类似 poll remove
//            XCLog.i(queue.take()); // b
//            XCLog.i(queue.take()); // c
//            XCLog.i(queue.take()); // d
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        XCLog.i(queue.peek()); //a  // peek 类似 element
        XCLog.i(queue.peek()); //a
        XCLog.i(queue.peek()); //a
        XCLog.i(queue.peek()); //a
        queue.remove("a");
        XCLog.i(queue.peek()); //b
        XCLog.i(queue.peek()); //b
        XCLog.i(queue.peek()); //b
    }

    private void testJsonFormat() {
        String str = "{\n" +
                "    \"code\": 0,\n" +
                "    \"msg\": \"success\",\n" +
                "    \"data\": [\n" +
                "\"/cr/100/200/aaa.mp4\"\n" +
                "    ]\n" +
                "}\n";

        String str2 = " {\"code\":0,\"msg\":\"成功\",\"data\":[[\"板蓝根\",\"白云山\"]]}";

        XCJsonBean bean = XCJsonParse.getJsonParseData(str2, XCJsonBean.class);
        List beans = bean.getListList("data", new ArrayList<ArrayList>());

        try {
            XCLog.i(beans.toString());
            XCLog.i(beans.get(0).toString());
            if (beans.get(0) instanceof List) {
                XCLog.i("List");
            } else if (beans.get(0) instanceof String[]) {
                XCLog.i("string[]");
            } else {
                XCLog.i(beans.get(0).getClass().toString());

                JSONArray array = (JSONArray) beans.get(0);
                int count = array.length();
                for (int i = 0; i < count; i++) {
                    XCLog.i((String) array.get(i));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            XCLog.e(this.toString() + "---exception");
        }

        XCLog.i(UtilSystem.getDeviceId(this) + "--------------deviceId");
    }


}