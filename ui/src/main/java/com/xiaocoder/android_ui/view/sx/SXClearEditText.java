package com.xiaocoder.android_ui.view.sx;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.xiaocoder.android_ui.R;

/**
 * ClearEditText
 * 带小X的输入框，点击清空内容
 * @author songxin on 2015/10/15.
 * @version 1.2.0
 *
 * setClearIconVisible()方法，设置隐藏和显示清除图标的方法，我们这里不是调用setVisibility()方法，
 * setVisibility()这个方法是针对View的，我们可以调用
 * setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)来设置上下左右的图标
 * setOnFocusChangeListener(this) 为输入框设置焦点改变监听，如果输入框有焦点，我们判断输入框的值是否为空，为空就隐藏清除图标，否则就显示
 * addTextChangedListener(this) 为输入框设置内容改变监听，其实很简单呢，当输入框里面的内容发生改变的时候，
 * 我们需要处理显示和隐藏清除小图标，里面的内容长度不为0我们就显示，否是就隐藏，
 * 但这个需要输入框有焦点我们才改变显示或者隐藏，为什么要需要焦点，
 * 比如我们一个登陆界面，我们保存了用户名和密码，在登陆界面onCreate()的时候，
 * 我们把我们保存的密码显示在用户名输入框和密码输入框里面，输入框里面内容发生改变，
 * 导致用户名输入框和密码输入框里面的清除小图标都显示了，这显然不是我们想要的效果，所以加了一个是否有焦点的判断
 * setShakeAnimation()，这个方法是输入框左右抖动的方法，之前我在某个应用看到过类似的功能，
 * 当用户名错误，输入框就在哪里抖动，感觉挺好玩的，其实主要是用到一个移动动画，然后设置动画的变化率为正弦曲线
 */

public class SXClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    /** 图片资源*/
    private Drawable mClearDrawable;

    private ClearEditListener mClearEditListener;



    /**
     * 三个构造方法最好都实现，使用的时候三个参数的
     */
    public SXClearEditText(Context context) {
        this(context, null);
    }

    public SXClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SXClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化
     * */
    private void init() {
        //得到后drawable[]有四个成员，第三个drawable[2]得到drawableRight
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.xc_d_editview_delete);
        }
        //将drawable用draw方法画到Canvas时指定drawable的边界，就是要保留的部分
        //它是指定一个矩形区域，然后通过draw(Canvas)画的时候，就只在这个矩形区域内画图。
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                    if(null != mClearEditListener){
                        mClearEditListener.onclickClear();
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }


    public void setClearEditListener(ClearEditListener mClearEditListener) {
        this.mClearEditListener = mClearEditListener;
    }

    public interface ClearEditListener{
        void onclickClear();
    }

}
