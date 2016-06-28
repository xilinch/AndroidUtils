package com.xiaocoder.android_test;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.xiaocoder.android_xcfw.function.fragment.XCCameraPhotoFragment;
import com.xiaocoder.android_xcfw.function.fragment.XCLocalPhotoFragment;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_test_middle.base.BaseActivity;

import java.io.File;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class CamareActivity extends BaseActivity {
    XCCameraPhotoFragment camera_fragment;
    XCLocalPhotoFragment local_fragment;
    ImageView id_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camare);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        camera_fragment = new XCCameraPhotoFragment();
        local_fragment = new XCLocalPhotoFragment();

        // camera_fragment.setIsAllowResizeImage(true);
        camera_fragment.setImage(R.drawable.ic_launcher);
        local_fragment.setImage(R.drawable.ic_launcher);
        local_fragment.setIsAllowResizeImage(true);

        id_imageview = getViewById(R.id.id_imageview);

        addFragment(R.id.xc_id_fragment_test_local, local_fragment);
        addFragment(R.id.xc_id_fragment_test_camera, camera_fragment);

    }

    public void setListeners() {
        camera_fragment.setOnCaremaSelectedFileListener(new XCCameraPhotoFragment.OnCaremaSelectedFileListener() {

            @Override
            public void onCaremaSelectedFile(File file) {
                XCLog.i(Uri.fromFile(file));
                XCLog.i(file.getAbsolutePath());
                XCLog.i(file.toURI());
                id_imageview.setImageURI(Uri.fromFile(file));
            }
        });

        local_fragment.setOnLocalSelectedFileListener(new XCLocalPhotoFragment.OnLocalSelectedFileListener() {

            @Override
            public void onLocalSelectedFile(File file) {
                XCLog.i(Uri.fromFile(file));
                XCLog.i(file.getAbsolutePath());
                XCLog.i(file.toURI());
                id_imageview.setImageURI(Uri.fromFile(file));
            }
        });
    }

}
