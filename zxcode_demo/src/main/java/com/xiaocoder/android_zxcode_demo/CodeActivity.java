package com.xiaocoder.android_zxcode_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.xiaocoder.android_xcfw.util.UtilImage;
import com.xiaocoder.android_zxcode.UtilGenerateCode;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 生成二维码、生成带有logo的二维码
 */
public class CodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_code);
        super.onCreate(savedInstanceState);
        initWidgets();
    }

    public void initWidgets() {
        ImageView imageview = (ImageView) findViewById(R.id.xc_id_code);
        ImageView imageviewLogo = (ImageView) findViewById(R.id.xc_id_code_logo);

        imageview.setImageBitmap(UtilGenerateCode.createCode("http://www.baidu.com/1234567890/1234567890/123456789/123456789/1234567/"));
        imageviewLogo.setImageBitmap(UtilGenerateCode.createLogoCode("http://www.baidu.com/1234567890/1234567890/123456789/123456789/1234567/", 400, 400, UtilImage.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher))));
    }

}
