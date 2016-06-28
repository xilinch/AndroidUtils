package com.xiaocoder.android_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaocoder.android_test_middle.base.BaseActivity;
import com.xiaocoder.android_xcfw.io.XCLog;

public class HandlerActivity extends BaseActivity {
    private Button button1;

    private Button button2;

    private Button button3;

    private TextView id_content;

    private LooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        button1 = getViewById(R.id.id_button1);
        button2 = getViewById(R.id.id_button2);
        button3 = getViewById(R.id.id_button3);
        id_content = getViewById(R.id.id_content);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                id_content.setText("button1 update");
                            }
                        });
                    }
                }.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                id_content.setText("button2 update");
                            }
                        });
                    }
                }.start();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                looperThread = new LooperThread();
                looperThread.start();
                looperThread.getHandler().sendEmptyMessage(1);
            }
        });
    }

    class LooperThread extends Thread {
        private Handler mHandler;
        private final Object mLock = new Object();

        public void run() {
            Looper.prepare();// 线程和looper关联
            synchronized (mLock) {
                // 这个handler默认是与当前的线程关联
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        XCLog.i(this);
                        // id_content.setText("button3 update");// 会crash，ui只能在主线程里更新，这个mHandler不是主线程的
                    }
                };
                mLock.notifyAll();
            }
            // 如果不调用loop开始循环，则不会执行消息队列中的消息
            Looper.loop();
        }

        public Handler getHandler() {
            synchronized (mLock) {
                if (mHandler == null) {
                    try {
                        mLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
                return mHandler;
            }
        }

        public void exit() {
            getHandler().post(new Runnable() {
                public void run() {
                    Looper.myLooper().quit();
                }
            });
        }
    }
}
/**
 * 1 在ActivityThread的main()方法中创建了Looper对象并启动了Looper循环，主线程中创建的Handler默认都是与这个Looper绑定的。
 * 2 由于子线程默认是不会创建Looper对象的，如果将Handler与子线程绑定，就要在绑定前先调用Looper.prepare()和Looper.loop()方法启动Looper循环，
 * 然后才能通过Handler向其所关联的MessageQueue中发送消息，否则就会报如下异常：Can't create handler inside thread that has not calledLooper.prepare()。
 */