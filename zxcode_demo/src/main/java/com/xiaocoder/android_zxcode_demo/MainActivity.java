package com.xiaocoder.android_zxcode_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaocoder.android_xcfw.util.UtilActivity;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends AppCompatActivity {

    private Button id_generate_code;
    private Button id_scanCode_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_generate_code = (Button) findViewById(R.id.id_generate_code);
        id_scanCode_code = (Button) findViewById(R.id.id_scanCode_code);

        id_generate_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CodeActivity.class);
                startActivity(intent);
            }
        });

        id_scanCode_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

    }
}
