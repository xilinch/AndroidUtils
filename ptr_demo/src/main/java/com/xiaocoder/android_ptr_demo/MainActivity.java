package com.xiaocoder.android_ptr_demo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button id_gridmaterialactivity;
    private Button id_gridrefreshactivity;
    private Button id_listmaterialactivity;
    private Button id_listrefreshactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_gridmaterialactivity = (Button) findViewById(R.id.id_gridmaterialactivity);
        id_gridrefreshactivity = (Button) findViewById(R.id.id_gridrefreshactivity);
        id_listmaterialactivity = (Button) findViewById(R.id.id_listmaterialactivity);
        id_listrefreshactivity = (Button) findViewById(R.id.id_listrefreshactivity);


        id_gridmaterialactivity.setOnClickListener(this);
        id_gridrefreshactivity.setOnClickListener(this);
        id_listmaterialactivity.setOnClickListener(this);
        id_listrefreshactivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.id_gridmaterialactivity:
                intent = new Intent(this, GridMaterialActivity.class);
                break;
            case R.id.id_gridrefreshactivity:
                intent = new Intent(this, GridRefreshActivity.class);

                break;
            case R.id.id_listmaterialactivity:
                intent = new Intent(this, ListMaterialActivity.class);

                break;
            case R.id.id_listrefreshactivity:
                intent = new Intent(this, ListRefreshActivity.class);

                break;
        }

        if (intent != null) {
            startActivity(intent);
        }

    }
}
