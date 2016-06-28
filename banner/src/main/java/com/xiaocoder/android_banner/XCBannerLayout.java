package com.xiaocoder.android_banner;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.daimajia.androidanimations.library.attention.StandUpAnimator;
import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.daimajia.slider.library.Animations.ChildAnimationExample;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.R;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.Map;


/**
 * @author xiaocoder on 2015/10/9
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCBannerLayout extends FrameLayout implements BaseSliderView.OnSliderClickListener {

    private static final String ARG_PARAM1 = "extra";
    private static final String ARG_PARAM2 = "position";

    private SliderLayout mDemoSlider;
    private long timeGap = 5000;
    private SliderLayout.Transformer st = SliderLayout.Transformer.Accordion;
    private SliderLayout.PresetIndicators sp = SliderLayout.PresetIndicators.Center_Bottom;
    private BaseAnimationInterface anim = new DescriptionAnimation();
    private BaseSliderView.ScaleType scale = BaseSliderView.ScaleType.Fit;
    private boolean isAutoSlider = true;

    /**
     * int File String类型
     */
    private Map<String, Object> map;

    public void setTimeGap(long time_gap) {

        this.timeGap = time_gap;
    }

    public void setTf(SliderLayout.Transformer st) {
        this.st = st;
    }

    public void setTp(SliderLayout.PresetIndicators sp) {
        this.sp = sp;
    }

    /**
     * 设置指示器的动画效果
     */
    public void setAnim(BaseAnimationInterface anim) {
        this.anim = anim;
    }

    public void setScale(BaseSliderView.ScaleType scale) {
        this.scale = scale;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
        if (map == null || map.size() == 1) {
            isAutoSlider = false;
            mDemoSlider.isSinglePic(true);
        }
        initData();
    }

    public void setIsAutoSlider(boolean isAutoSlider) {
        this.isAutoSlider = isAutoSlider;
    }

    public interface OnImageClickListener {
        void onImageClickListener(int position);
    }

    OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        if (mDemoSlider != null && isAutoSlider) {
            mDemoSlider.stopAutoCycle();
        }
    }

    public void onStart() {
        if (mDemoSlider != null && isAutoSlider) {
            mDemoSlider.startAutoCycle();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        if (onImageClickListener != null) {
            onImageClickListener.onImageClickListener(slider.getBundle().getInt(ARG_PARAM2, -1));
        }
    }

    public XCBannerLayout(Context context) {
        super(context);
    }

    public XCBannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_banner, this, true);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

    }

    private void initData() {
        int index = 0;
        for (String key : map.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.description("")
                    .image(map.get(key))
                    .setScaleType(scale)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString(ARG_PARAM1, key);
            textSliderView.getBundle().putInt(ARG_PARAM2, index++);
            mDemoSlider.addSlider(textSliderView);
        }

        if (!isAutoSlider) {
            mDemoSlider.stopAutoCycle();
        }

//        mDemoSlider.setPresetTransformer(st);
//        mDemoSlider.setPresetIndicator(sp);
//        mDemoSlider.setCustomAnimation(anim);

//        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        mDemoSlider.setDuration(timeGap);
    }

    public XCBannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
