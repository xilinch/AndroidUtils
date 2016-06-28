package com.xiaocoder.android_test;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

/**
 * @author xiaocoder on 2016/6/7.
 * @modifier xiaocoder 2016/6/7 13:47.
 * @description
 */
public class ExampleUnitTest2 {

    @Test
    public void testAdd() {
        assertEquals(2, 1 + 1);
        System.out.println("testAdd");
    }

    @Test
    public void testSub() {
        assertEquals(2, 3 - 1);
        System.out.println("testSun");
    }

    public static void main(String[] args) {
        Class[] clazzs = new Class[]{ExampleUnitTest.class, ExampleUnitTest2.class};
        JUnitCore.runClasses(clazzs);
        // new JUnitCore().run(Request.method(ExampleUnitTest2.class, "testAdd"));
    }

}
