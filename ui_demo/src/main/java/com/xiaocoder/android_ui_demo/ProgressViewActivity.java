package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.xiaocoder.android_ui.view.sx.SXProgressView;

import java.util.concurrent.Executors;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ProgressViewActivity extends Activity {

    private SXProgressView view;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initWidgets();
        showProgressView();

    }

    public void initWidgets() {
        view = (SXProgressView) findViewById(R.id.sx_id_progress_view);
    }

    private void showProgressView() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                while (view.getProgress() < 100) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setProgress(view.getProgress() + 2);
                        }
                    });
                }
            }
        });
    }

}
