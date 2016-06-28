package com.xiaocoder.android_xcfw.application;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaocoder.android_xcfw.io.XCLog;

import java.util.List;
/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public abstract class XCFragment extends Fragment {

    public ViewGroup mContainer;

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {
        return (T) mContainer.findViewById(id);
    }

    public View init(LayoutInflater inflater, int layout_id) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer = (ViewGroup) inflater.inflate(layout_id, null);
        mContainer.setLayoutParams(lp);
        // 如果没有以上的会变成包裹高度
        return mContainer;
    }

    public XCActivity getXCActivity() {
        if (getActivity() != null && getActivity() instanceof XCActivity) {
            return (XCActivity) getActivity();
        }
        return null;
    }

    public void addChildFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public void addChildFragment(int layout_id, Fragment fragment, String tag) {
        addChildFragment(layout_id, fragment, tag, false);
    }

    public void addChildFragment(int layout_id, Fragment fragment) {
        addChildFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void hideChildFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
    }

    public void showChildFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T getFragmentByTag(String tag) {
        return (T) getChildFragmentManager().findFragmentByTag(tag);
    }

    /**
     * 页面滑动切换的时候，用得到
     * <p/>
     * @return
     */
    public boolean isBodyFragment() {
        return true;
    }

    public void finishActivity() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().finish();
        }
    }

    public Intent getIntent() {
        if (getActivity() != null) {
            return getActivity().getIntent();
        }
        return null;
    }

    /**
     * hidden为true时, 是隐藏的状态 ; hidden为false时,是显示的状态
     * 该方法只有在对fragment进行了hide和show操作时,才会被调用,如果是被别的界面遮住了,是不会调用的
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 调用子fragment中的onActivityResult方法
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                XCLog.i(fragment.toString() + "----onActivityResult");
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
/**
 * 会等父fragment的onActivityCreated完成后才会调用子fragment的onCreate()方法
 *
 * MyFragment onAttach() 粘贴到activity上
 * <p/>
 * MyFragment onCreate() fragment创建
 * <p/>
 * MyFragment onCreateView() fragment创建自己的视图
 * <p/>
 * MainActivity onCreate()
 * <p/>
 * MyFragment onActivityCreated() 可以处理fragment数据的初始化
 * <p/>
 * MainActivity onStart()
 * <p/>
 * MyFragment onStart()
 * <p/>
 * MainActivity onResume()
 * <p/>
 * MyFragment onResume()
 * <p/>
 * 按后退键 MyFragment onPause()
 * <p/>
 * MainActivity onPause()
 * <p/>
 * MyFragment onStop()
 * <p/>
 * MainActivity onStop()
 * <p/>
 * MyFragment onDestoryView() 销毁掉自己的视图
 * <p/>
 * MyFragment onDestory()
 * <p/>
 * MyFragment onDetach() 解除和activity的关系
 * <p/>
 * MainActivity onDetory()
 */