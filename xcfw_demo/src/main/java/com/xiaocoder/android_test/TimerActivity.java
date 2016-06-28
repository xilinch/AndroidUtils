package com.xiaocoder.android_test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_test_middle.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class TimerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer();
        timer2();
        timer3();

    }


    int count = 0;

    private void timer() {

        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                XCLog.i(count++);
            }

            @Override
            public void onFinish() {
                XCLog.i(XCConstant.TAG_TEST, count + "--onEnd");
            }
        };

        timer.start();

    }


    Timer timer;

    private void timer2() {

        timer = new Timer();
        TimerTask task = new TimerTask() {
            int index = 0;

            @Override
            public void run() {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        index = index + 1;
                        XCLog.shortToast(index);
                    }
                });

            }
        };
        timer.schedule(task, 1000, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
        scheduled.shutdown();
    }

    ScheduledExecutorService scheduled;

    private void timer3() {
        scheduled = Executors.newScheduledThreadPool(3);
        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                XCLog.i("5秒后执行一次");
            }
        }, 5, TimeUnit.SECONDS);

        scheduled.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                XCLog.i("2秒后开始执行，每隔6秒执行一次");
            }
        }, 2, 6, TimeUnit.SECONDS);
    }


}
