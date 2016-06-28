package com.xiaocoder.android_banner_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.xiaocoder.android_banner.XCBannerLayout;
import com.xiaocoder.android_xcfw.io.XCLog;

import java.util.HashMap;

/**
 * @author xiaocoder on 2015/10/9
 * @email fengjingyu@foxmail.com
 * @description
 */
public class BannerActivity extends Activity {

    private XCBannerLayout bannerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {

        HashMap<String, Object> url_maps = new HashMap<String, Object>();
        url_maps.put("one", "http://www.baidu.com/img/bdlogo.png");
        url_maps.put("two", "http://www.baidu.com/img/bdlogo.png");
        url_maps.put("three", "http://www.baidu.com/img/bdlogo.png");
        url_maps.put("four", "http://www.baidu.com/img/bdlogo.png");

        HashMap<String, Object> file_maps = new HashMap<String, Object>();
        file_maps.put("Hannibal", R.drawable.hannibal);
        file_maps.put("Big Bang Theory", R.drawable.bigbang);
        file_maps.put("House of Cards", R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        bannerLayout = (XCBannerLayout) findViewById(R.id.id_banner_layout);
        bannerLayout.setMap(file_maps);
        bannerLayout.setAnim(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bannerLayout.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bannerLayout.onStop();
    }

    public void setListeners() {

        bannerLayout.setOnImageClickListener(new XCBannerLayout.OnImageClickListener() {
            @Override
            public void onImageClickListener(int position) {
                Toast.makeText(BannerActivity.this, position + "", Toast.LENGTH_LONG).show();
            }
        });
    }

}
