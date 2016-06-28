package com.example.app.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.app.R;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 以下ui switch 设置监听的代码 注释 成员变量字段 --> UiCodeGenerator_V1.0.jar 生成的
 */
public class SampleUiActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 注释1
     */
    private Button id_test1;
    /**
     * 注释2
     */
    private Button id_test2;
    /**
     * 注释3
     */
    private Button id_test3;
    /**
     * 注释4
     */
    private Button id_test4;
    /**
     * 注释5
     */
    private Button id_test5;
    /**
     * 注释6
     */
    private Button id_test6;
    /**
     * 注释7
     */
    private Button id_test7;
    /**
     * 注释8
     */
    private Button id_test8;
    /**
     * 注释9
     */
    private Button id_test9;
    /**
     * 注释10
     */
    private Button id_test10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        id_test1 = (Button) findViewById(R.id.id_test1);
        id_test2 = (Button) findViewById(R.id.id_test2);
        id_test3 = (Button) findViewById(R.id.id_test3);
        id_test4 = (Button) findViewById(R.id.id_test4);
        id_test5 = (Button) findViewById(R.id.id_test5);
        id_test6 = (Button) findViewById(R.id.id_test6);
        id_test7 = (Button) findViewById(R.id.id_test7);
        id_test8 = (Button) findViewById(R.id.id_test8);
        id_test9 = (Button) findViewById(R.id.id_test9);
        id_test10 = (Button) findViewById(R.id.id_test10);


        id_test1.setOnClickListener(this);
        id_test2.setOnClickListener(this);
        id_test3.setOnClickListener(this);
        id_test4.setOnClickListener(this);
        id_test5.setOnClickListener(this);
        id_test6.setOnClickListener(this);
        id_test7.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//  注释1
            case R.id.id_test1:

                break;
//  注释2
            case R.id.id_test2:

                break;
//  注释3
            case R.id.id_test3:

                break;
//  注释4
            case R.id.id_test4:

                break;
//  注释5
            case R.id.id_test5:

                break;
//  注释6
            case R.id.id_test6:

                break;
//  注释7
            case R.id.id_test7:

                break;
            default:

                break;

        }
    }
}
