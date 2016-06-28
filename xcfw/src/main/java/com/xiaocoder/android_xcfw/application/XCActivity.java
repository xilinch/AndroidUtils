package com.xiaocoder.android_xcfw.application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.xiaocoder.android_xcfw.function.helper.XCActivityHelper;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilInput;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class XCActivity extends FragmentActivity {

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {

        return (T) findViewById(id);
    }

    /**
     * 别的应用或页面调用时传进来的
     */
    protected Uri interceptUri() {
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                return uri;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            XCLog.e(this, "回收后重新创建");
        }

        addActivity2Stack();
    }

    private void addActivity2Stack() {
        XCActivityHelper.addActivityToStack(this);
    }

    private void removeActivityFromStack() {
        XCActivityHelper.delActivityFromStack(this);
    }

    /**
     * 该方法不删除 ，防止fragment被保存
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivityFromStack();
    }

    @Override
    public void finish() {
        // 隐藏键盘
        UtilInput.hiddenInputMethod(this);
        super.finish();
    }

    public void addFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void addFragment(int layout_id, Fragment fragment, String tag) {
        addFragment(layout_id, fragment, tag, false);
    }

    public void addFragment(int layout_id, Fragment fragment) {
        addFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void replaceFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void replaceFragment(int layout_id, Fragment fragment, String tag) {
        replaceFragment(layout_id, fragment, tag, false);
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void hideFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T getFragmentByTag(String tag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * 之前必须有add
     */
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * add + show
     */
    public Fragment showFragmentByClass(Class<? extends Fragment> fragment_class, int layout_id) {
        // 显示点击的哪个fragment
        Fragment fragment = getFragmentByTag(fragment_class.getSimpleName());
        if (fragment == null) {
            try {
                Constructor<? extends Fragment> cons = fragment_class.getConstructor();
                fragment = cons.newInstance();
                addFragment(layout_id, fragment, fragment.getClass().getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showFragment(fragment);
        }
        return fragment;
    }

    /**
     * title等别的fragment不隐藏
     */
    public void hideBodyFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (((XCFragment) fragment).isBodyFragment()) {
                    hideFragment(fragment);
                }
            }
        }
    }

    /**
     * 隐藏所有fragment
     */
    public void hideAllFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null) {
            for (Fragment fragment : fragments) {
                hideFragment(fragment);
            }
        }
    }

    /**
     * 这里得重写,否则startforresult时, 无法回调到fragment中的方法 ,
     * 如果fragment中又有嵌套的话,fragmetn中的该方法也得重写
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XCLog.i(this + "---onActivityResult");
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                XCLog.i(this + "onActivityResult---" + fragment.toString());
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public XCActivity getXCActivity() {
        return this;
    }

}
