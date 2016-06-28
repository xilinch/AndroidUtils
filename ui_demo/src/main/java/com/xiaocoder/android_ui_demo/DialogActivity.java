package com.xiaocoder.android_ui_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.xiaocoder.android_ui.dialog.XCFrameAnimHDialog;
import com.xiaocoder.android_ui.dialog.XCFrameAnimVDialog;
import com.xiaocoder.android_ui.dialog.XCMenuDialog;
import com.xiaocoder.android_ui.dialog.XCQueryDialog;
import com.xiaocoder.android_ui.dialog.XCRotateDialog;
import com.xiaocoder.android_ui.dialog.XCSystemHDialog;
import com.xiaocoder.android_ui.dialog.XCSystemVDialog;
import com.xiaocoder.android_ui.dialog.XCSystemVDialogFragment;
import com.xiaocoder.android_xcfw.io.XCLog;

import java.util.ArrayList;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class DialogActivity extends FragmentActivity implements View.OnClickListener{

    Button systemh;
    XCSystemHDialog systemh_dialog;

    Button systemv;
    XCSystemVDialog systemv_dialog;

    Button animframe_h;
    XCFrameAnimHDialog animframe_dialog_h;

    Button animframe_v;
    XCFrameAnimVDialog animframe_dialog_v;

    Button query;
    XCQueryDialog query_dialog;

    Button menu;
    XCMenuDialog menu_dialog;

    Button rotate;
    XCRotateDialog rotate_dialog;

    Button xc_id_dialog_activity;

    Button xc_id_dialog_v_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_activity);


        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        systemh = (Button) findViewById(R.id.xc_id_dialog_systemh);
        systemv = (Button) findViewById(R.id.xc_id_dialog_systemv);
        animframe_h = (Button) findViewById(R.id.xc_id_dialog_animframe_h);
        animframe_v = (Button) findViewById(R.id.xc_id_dialog_animframe_v);
        query = (Button) findViewById(R.id.xc_id_dialog_query);
        menu = (Button) findViewById(R.id.xc_id_dialog_menu);
        rotate = (Button) findViewById(R.id.xc_id_dialog_rotate);
        xc_id_dialog_activity = (Button) findViewById(R.id.xc_id_dialog_activity);
        xc_id_dialog_v_fragment = (Button) findViewById(R.id.xc_id_dialog_v_fragment);
    }

    public void setListeners() {
        systemh.setOnClickListener(this);
        systemv.setOnClickListener(this);
        animframe_h.setOnClickListener(this);
        animframe_v.setOnClickListener(this);
        query.setOnClickListener(this);
        menu.setOnClickListener(this);
        rotate.setOnClickListener(this);
        xc_id_dialog_activity.setOnClickListener(this);
        xc_id_dialog_v_fragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xc_id_dialog_systemh:
                showSystemHDialog();
                break;
            case R.id.xc_id_dialog_systemv:
                showSystemVDialog();
                break;
            case R.id.xc_id_dialog_animframe_h:
                showAnimFrameHDialog();
                break;
            case R.id.xc_id_dialog_animframe_v:
                showAnimFrameVDialog();
                break;
            case R.id.xc_id_dialog_query:
                showQueryDialog();
                break;
            case R.id.xc_id_dialog_menu:
                showMenuDialog();
                break;
            case R.id.xc_id_dialog_rotate:
                showRotateDialog();
                break;
            case R.id.xc_id_dialog_activity:
                Intent intent = new Intent(this,DialogSytleActivity.class);
                startActivity(intent);
                break;
            case R.id.xc_id_dialog_v_fragment:
                showSystemVDialogFragment();
                break;
            default:
                break;
        }
    }

    private void showSystemVDialogFragment() {
        XCSystemVDialogFragment dialog = XCSystemVDialogFragment.newInstance();
        dialog.show(getSupportFragmentManager(), "XCSystemVDialogFragment");
    }

    private void showRotateDialog() {
        rotate_dialog = new XCRotateDialog(this, R.drawable.ic_launcher);
        rotate_dialog.show();
    }

    private void showMenuDialog() {
        menu_dialog = new XCMenuDialog(this);
        menu_dialog.update("菜单", new String[]{"android", "ios", "java", "swift", "c"});
        menu_dialog.setOnDialogItemClickListener(new XCMenuDialog.OnDialogItemClickListener() {
            @Override
            public void onClick(View view, String hint) {
                XCLog.shortToast(((Button) view).getText());
            }
        });
        menu_dialog.show();
    }

    private void showQueryDialog() {
        query_dialog = new XCQueryDialog(this, "温馨提示", "123456\r\n123456789", new String[]{"取消", "确定"}, false);
        query_dialog.setOnDecideListener(new XCQueryDialog.OnDecideListener() {
            @Override
            public void confirm() {
                XCLog.dShortToast("confirm");
                query_dialog.dismiss();
            }

            @Override
            public void cancle() {
                XCLog.dShortToast("cancle");
                query_dialog.dismiss();
            }
        });
        query_dialog.show();
    }

    public void showSystemHDialog() {
        systemh_dialog = new XCSystemHDialog(this);
        systemh_dialog.getTextview().setText("loading..");
        systemh_dialog.show();
    }

    public void showSystemVDialog() {
        systemv_dialog = new XCSystemVDialog(this);
        systemv_dialog.getTextview().setText("loading..");
        systemv_dialog.show();
    }

    private void showAnimFrameHDialog() {
        animframe_dialog_h = new XCFrameAnimHDialog(this, R.drawable.anim_framelist);
        animframe_dialog_h.getTextView().setText("lala");
        animframe_dialog_h.show();
    }

    private void showAnimFrameVDialog() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.test_1);
        list.add(R.drawable.test_2);
        animframe_dialog_v = new XCFrameAnimVDialog(this, list, 150);
        animframe_dialog_v.getTextView().setText("test");
        animframe_dialog_v.show();
    }

}
