package com.xiaocoder.android_xcfw.function.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 不会回收的viewpagerfragment
 */
public class XCAdapterViewPagerFragment extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public XCAdapterViewPagerFragment(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titles) {
            return titles.get(position);
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    // 这里空实现， 否则会回收
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
