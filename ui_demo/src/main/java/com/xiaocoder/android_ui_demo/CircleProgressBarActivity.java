package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.xiaocoder.android_ui.view.open.OPCircleProgressBar;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class CircleProgressBarActivity extends Activity {

    private int progress = 0;
    private OPCircleProgressBar progress1;
    private OPCircleProgressBar progress2;
    private OPCircleProgressBar progressWithArrow;
    private OPCircleProgressBar progressWithoutBg;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        progress1 = (OPCircleProgressBar) findViewById(R.id.progress1);
        progress2 = (OPCircleProgressBar) findViewById(R.id.progress2);
        progressWithArrow = (OPCircleProgressBar) findViewById(R.id.progressWithArrow);
        progressWithoutBg = (OPCircleProgressBar) findViewById(R.id.progressWithoutBg);


//        progress1.setColorSchemeResources(android.R.color.holo_blue_bright);
        progress2.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        progressWithArrow.setColorSchemeResources(android.R.color.holo_orange_light);
        progressWithoutBg.setColorSchemeResources(android.R.color.holo_red_light);

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (finalI * 10 >= 90) {
                        progress1.setVisibility(View.VISIBLE);
                        progress2.setVisibility(View.INVISIBLE);
                    } else {
                        progress2.setProgress(finalI * 10);
                    }
                }
            }, 1000 * (i + 1));
        }

    }

}
