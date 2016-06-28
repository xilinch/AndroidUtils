package com.xiaocoder.android_ui_demo;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.xiaocoder.android_ui.view.js.ClipImageLayout;
import com.xiaocoder.android_xcfw.util.UtilImage;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ClipImageActivity extends Activity {

    ClipImageLayout id_clipimagelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        initWidgets();
    }

    public void initWidgets() {
        id_clipimagelayout = (ClipImageLayout) findViewById(R.id.id_clipimagelayout);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        id_clipimagelayout.setClipImage(UtilImage.drawableToBitmap(drawable));
    }


}
