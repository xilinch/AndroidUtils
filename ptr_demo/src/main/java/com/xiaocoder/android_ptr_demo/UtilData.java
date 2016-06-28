package com.xiaocoder.android_ptr_demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class UtilData {

    public static List<TestModel> getList() {
        List<TestModel> list = new ArrayList<>();
        for (int count = 0; count < 50; count++) {
            list.add(new TestModel(count + ""));
        }
        return list;
    }

}
