package com.xiaocoder.android_test;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.xiaocoder.android_xcfw.function.fragment.XCLocalPhotoFragment;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_test_middle.base.BaseActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ActivityDestroyGC extends BaseActivity {
    private int i = 10;
    private ImageView id_gc_imageview;
    private XCLocalPhotoFragment localPhotoFragment;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_point);

        initWidgets();

        localPhotoFragment = new XCLocalPhotoFragment();
        addFragment(R.id.id_gc_fragment, localPhotoFragment);
    }

    public void initWidgets() {

        id_gc_imageview = getViewById(R.id.id_gc_imageview);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 确保fragment已经添加了
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                XCLog.i(id_gc_imageview);
                XCLog.i(i);

                System.gc();
                System.gc();

                XCLog.i(localPhotoFragment.toString());
                XCLog.i(localPhotoFragment.getActivity());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        XCLog.i("handler --" + id_gc_imageview); // 不为空
                        XCLog.i("handler --" + i);// 不为空

                        XCLog.i("handler --" + localPhotoFragment.toString()); // 不为空
                        XCLog.i("handler --" + localPhotoFragment.getActivity()); // 空
                    }
                }, 10000);

            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XCLog.i(this + "--onDestroy()");
    }
}
