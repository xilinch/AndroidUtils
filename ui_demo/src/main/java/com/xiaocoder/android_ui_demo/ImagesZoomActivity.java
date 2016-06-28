package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


import com.xiaocoder.android_ui.view.open.OPZoomImageView;
import com.xiaocoder.android_ui.view.xc.XCZoomViewPager;
import com.xiaocoder.android_xcfw.io.XCLog;

import java.util.ArrayList;
/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ImagesZoomActivity extends Activity {

    XCZoomViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_zoom);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        viewpager = (XCZoomViewPager) findViewById(R.id.xc_id_xczoomviewpagerlayout);
        ArrayList<String> list = new ArrayList<String>();
        list.add("http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        list.add("http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        list.add("http://cdn3.nflximg.net/images/3093/2043093.jpg");
        list.add("http://www.baidu.com/img/bdlogo.png");
        // 默认选中第二张图片
        viewpager.setData(list, 1, new XCZoomViewPager.OnLoadImage() {

            @Override
            public void onLoadImage(ImageView imageview, final String url) {
                // 这里用本地的图片模拟 ,
                // ------------->补充这里即可--------->用你的图片加载方式加载--->url为图片的链接
                imageview.setImageResource(R.drawable.ic_launcher);
                // displayImage(url, imageview);
                ((OPZoomImageView) imageview).setOnLongPressListener(new OPZoomImageView.OnLongPressListener() {
                    @Override
                    public void onLongPress() {
                        Toast.makeText(ImagesZoomActivity.this, "long click" + url, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setListeners() {

        viewpager.setOnImageClickListener(new XCZoomViewPager.OnImageClickListener() {

            @Override
            public void onImageClickListener(int position) {
                XCLog.shortToast(position);
            }
        });
    }

}
