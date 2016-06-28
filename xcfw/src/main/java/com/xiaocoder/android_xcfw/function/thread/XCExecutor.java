package com.xiaocoder.android_xcfw.function.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author xiaocoder 2014-10-17 下午1:52:54
 * @email fengjingyu@foxmail.com
 * @description 线程池
 */
public class XCExecutor {

    private static ExecutorService threadpool_single;
    private static ExecutorService threadpool_cache;
    private static ExecutorService threadpool_fix;

    private static XCExecutor executor = new XCExecutor();

    private XCExecutor() {
    }

    public static void initXCExecutor(int fixNum) {

        if (threadpool_single == null) {
            synchronized (XCExecutor.class) {
                if (threadpool_single == null) {
                    threadpool_single = Executors.newSingleThreadExecutor();
                }
            }
        }

        if (threadpool_cache == null) {
            synchronized (XCExecutor.class) {
                if (threadpool_cache == null) {
                    threadpool_cache = Executors.newCachedThreadPool();
                }
            }
        }

        if (fixNum > 0) {
            if (threadpool_fix == null) {
                synchronized (XCExecutor.class) {
                    if (threadpool_fix == null) {
                        threadpool_fix = Executors.newFixedThreadPool(fixNum);
                    }
                }
            }
        }
    }

    public static ExecutorService getSingle() {
        return threadpool_single;
    }

    public static ExecutorService getCache() {
        return threadpool_cache;
    }

    public static ExecutorService getFix() {
        return threadpool_fix;
    }


}
