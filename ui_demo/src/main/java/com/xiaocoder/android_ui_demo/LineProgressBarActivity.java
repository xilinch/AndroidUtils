package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.xiaocoder.android_ui.view.open.OPLineProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class LineProgressBarActivity extends Activity implements OPLineProgressBar.OnProgressBarListener {

    private Timer timer;

    private OPLineProgressBar bnp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_progressbar);

        bnp = (OPLineProgressBar) findViewById(R.id.numberbar1);
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 1000, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onProgressChange(int current, int max) {
        if (current == max) {
            Toast.makeText(getApplicationContext(), "progressBar is finished", Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
    }

}
