/**
 *
 */
package com.xiaocoder.android_xcfw.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaocoder.android_xcfw.io.XCLog;

/**
 * @author xiaocoder on 2015-3-2
 * @email fengjingyu@foxmail.com
 * @description 输入法的弹出 与 关闭
 */
public class UtilInput {

    /**
     * 弹出输入法
     */
    public static void openInputMethod(EditText view, final Context context) {
        // 弹出输入法
        view.setFocusable(true);
        view.requestFocus();
        view.selectAll();
        // 必须是handler.否则无法弹出 why?
        new Handler().postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
            }
        }, 500);
    }

    public static void hiddenInputMethod(Context context) {
        if (((Activity) context).getCurrentFocus() != null) {
            if (((Activity) context).getCurrentFocus().getWindowToken() != null) {
                // 隐藏键盘
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void copyText(Context context, TextView textView) {
        int versionInt = UtilSystem.getOSVersionSDKINT();
        if (versionInt > 10) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(textView.getText().toString().trim());
            XCLog.shortToast("已经复制到粘贴板");

        } else {
            android.text.ClipboardManager clipboardManager1 = (android.text.ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
            clipboardManager1.setText(textView.getText().toString().trim());
            XCLog.shortToast("已经复制到粘贴板");
        }
    }

}
