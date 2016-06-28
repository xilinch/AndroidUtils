package com.xiaocoder.android_test;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.xiaocoder.android_test_middle.base.BaseActivity;
import com.xiaocoder.android_xcfw.util.UtilScreen;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class PropertyAnimActivity extends BaseActivity {

    private Button animButton;

    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_anim);

        animButton = getViewById(R.id.id_animButton);

        animButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    flag = false;
                    animButton.animate().
                            // x方向
                                    translationXBy(-(UtilScreen.getScreenWidthPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100))).
                            setInterpolator(new DecelerateInterpolator()).
                            // y方向
                                    translationYBy(UtilScreen.getScreenHeightPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100)).
                            setInterpolator(new AccelerateInterpolator(1.0f)).
                            // 旋转
                                    rotationBy(-1080).setDuration(2000).start();

                } else {
                    flag = true;
                    animButton.animate().
                            // x方向
                                    translationX(UtilScreen.getScreenWidthPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100)).
                            setInterpolator(new AccelerateInterpolator(1.0f)).
                            // y方向
                                    translationY(-(UtilScreen.getScreenHeightPx(PropertyAnimActivity.this) - UtilScreen.dip2px(PropertyAnimActivity.this, 100))).
                            setInterpolator(new DecelerateInterpolator()).
                            // 旋转
                                    rotation(1080).setDuration(2000).start();
                }

            }
        });

    }
}

/**
 * ValueAnimator即表示一个动画，包含动画的开始值，结束值，持续时间等属性。
 * <p/>
 * ValueAnimator封装了一个TimeInterpolator，TimeInterpolator定义了属性值在开始值与结束值之间的插值方法。
 * <p/>
 * ValueAnimator还封装了一个TypeAnimator，根据开始、结束值与TimeIniterpolator计算得到的值计算出属性值。
 * ValueAnimator根据动画已进行的时间跟动画总时间（duration）的比计算出一个时间因子（0~1），然后根据TimeInterpolator计算出另一个因子，最后TypeAnimator通过这个因子计算出属性值，如上例中10ms时：
 * <p/>
 * 首先计算出时间因子，即经过的时间百分比：t=10ms/40ms=0.25
 * <p/>
 * 经插值计算(inteplator)后的插值因子:大约为0.15，上述例子中用了AccelerateDecelerateInterpolator，计算公式为（input即为时间因子）：
 * <p/>
 * (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
 * 最后根据TypeEvaluator计算出在10ms时的属性值：0.15*（40-0）=6pixel。上例中TypeEvaluator为FloatEvaluator，计算方法为 ：
 * <p/>
 * public Float evaluate(float fraction, Number startValue, Number endValue) {
 * float startFloat = startValue.floatValue();
 * return startFloat + fraction * (endValue.floatValue() - startFloat);
 * }
 * 参数分别为上一步的插值因子，开始值与结束值。
 * <p/>
 * 　AnimationSet提供了一个把多个动画组合成一个组合的机制，并可设置组中动画的时序关系，如同时播放，顺序播放等。
 * <p/>
 * 　　以下例子同时应用5个动画：
 * <p/>
 * 播放anim1；
 * 同时播放anim2,anim3,anim4；
 * 播放anim5。
 * AnimatorSet bouncer = new AnimatorSet();
 * bouncer.play(anim1).before(anim2);
 * bouncer.play(anim2).with(anim3);
 * bouncer.play(anim2).with(anim4)
 * bouncer.play(anim5).after(amin2);
 * animatorSet.start();
 * <p/>
 * AccelerateInterpolator　　　　　 ? ? 加速，开始时慢中间加速
 * DecelerateInterpolator　　　 　　 ? 减速，开始时快然后减速
 * AccelerateDecelerateInterolator　 ? 先加速后减速，开始结束时慢，中间加速
 * AnticipateInterpolator　　　　　　 ?反向 ，先向相反方向改变一段再加速播放
 * AnticipateOvershootInterpolator　 反向加超越，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
 * BounceInterpolator　　　　　　　 ?跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100
 * CycleIinterpolator　　　　　　　　 ?循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 * mCycles * Math.PI * input)
 * LinearInterpolator　　　　　　　　 ?线性，线性均匀改变
 * OvershottInterpolator　　　　　　 ?超越，最后超出目的值然后缓慢改变到目的值
 * TimeInterpolator　　　　　　　　　 一个接口，允许你自定义interpolator，以上几个都是实现了
 * <p/>
 * 　　keyFrame是一个 时间/值 对，通过它可以定义一个在特定时间的特定状态，而且在两个keyFrame之间可以定义不同的Interpolator，就相当多个动画的拼接，第一个动 画的结束点是第二个动画的开始点。KeyFrame是抽象类，要通过ofInt(),ofFloat(),ofObject()获得适当的 KeyFrame，然后通过PropertyValuesHolder.ofKeyframe获得PropertyValuesHolder对象，如以下 例子：
 * <p/>
 * Keyframe kf0 = Keyframe.ofInt(0, 400);
 * Keyframe kf1 = Keyframe.ofInt(0.25f, 200);
 * Keyframe kf2 = Keyframe.ofInt(0.5f, 400);
 * Keyframe kf4 = Keyframe.ofInt(0.75f, 100);
 * Keyframe kf3 = Keyframe.ofInt(1f, 500);
 * PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("width", kf0, kf1, kf2, kf4, kf3);
 * ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(btn2, pvhRotation);
 * rotationAnim.setDuration(2000);
 * 　　上述代码的意思为：设置btn对象的width属性值使其：
 * <p/>
 * 开始时 Width=400
 * 动画开始1/4时 Width=200
 * 动画开始1/2时 Width=400
 * 动画开始3/4时 Width=100
 * 动画结束时 Width=500
 * 　　第一个参数为时间百分比，第二个参数是在第一个参数的时间时的属性值。 　　定义了一些Keyframe后，通过PropertyValuesHolder类的方法ofKeyframe封装，然
 * 后通过ObjectAnimator.ofPropertyValuesHolder获得Animator。
 * 用下面的代码可以实现同样的效果：
 * ObjectAnimator oa=ObjectAnimator.ofInt(btn2, "width", 400,200,400,100,500);
 * oa.setDuration(2000);
 * oa.start();
 * <p/>
 * 在Android 3.0中给View增加了一些参数并对这些参数增加了相应的getter/setter函数（ObjectAnimator要用这些函数改变这些属性）：
 * <p/>
 * translationX,translationY:转换坐标（control where the View is located as a delta from its left and top coordinates which are set by its layout container.）
 * rotation,rotationX,rotationY:旋转，rotation用于2D旋转角度，3D中用到后两个
 * scaleX,scaleY:缩放
 * x,y:View的最终坐标（utility properties to describe the final location of the View in its container, as a sum of the left and top values and translationX and translationY values.）
 * alpha:透明度
 * 　　跟位置有关的参数有3个，以X坐标为例，可以通过getLeft(),getX(),getTranslateX()获得，若有一Button btn2，布局时其坐标为（40,0）：
 * <p/>
 * 如果需要对一个View的多个属性进行动画可以用ViewPropertyAnimator类，该类对多属性动画进行了优化，会合并一些invalidate()来减少刷新视图，该类在3.1中引入。
 * <p/>
 * 　　以下两段代码实现同样的效果：
 * <p/>
 * PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 50f);
 * PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);
 * ObjectAnimator.ofPropertyValuesHolder(myView, pvhX, pvyY).start();
 * <p/>
 * ObjectAnimator.ofFloat(animButton, RotationY, 360).setRepeatMode(ValueAnimator.INFINITE);
 * ObjectAnimator.ofFloat(mButton, translationX, 200)
 * ObjectAnimator.ofFloat(mButton, translationX, 200，300)
 * .setDuration(2000).start();
 * <p/>
 * ValueAnimator valueAnimator = ValueAnimator.ofFloat(360);
 * valueAnimator.setDuration(2000);
 * 眼熟吧。。但是这样是没有办法用的，因为没有指定对象以及属性，如果想要完成动画效果需要实现ValueAnimator.onUpdateListener接口。
 * ValueAnimator valueAnimator = ValueAnimator.ofFloat(360);
 * valueAnimator.setDuration(2000);
 * valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
 *
 * @Override public void onAnimationUpdate(ValueAnimator animation) {
 * mButton.setRotationY((Float) animation
 * .getAnimatedValue());
 * }
 * <p/>
 * ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
 * anim.setDuration(300);
 * anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
 * @Override public void onAnimationUpdate(ValueAnimator animation) {
 * float currentValue = (float) animation.getAnimatedValue();
 * Log.d("TAG", "cuurent value is " + currentValue);
 * }
 * });
 * anim.start();
 * <p/>
 * 那么textview对象中是不是有alpha属性这个值呢？没有，不仅textview没有这个属性，连它所有的父类也是没有这个属性的！这就奇怪了，textview当中并没有alpha这个属性，ObjectAnimator是如何进行操作的呢？其实ObjectAnimator内部的工作机制并不是直接对我们传入的属性名进行操作的，而是会去寻找这个属性名对应的get和set方法，因此alpha属性所对应的get和set方法应该就是：
 * [java] view plaincopy在CODE上查看代码片派生到我的代码片
 * public void setAlpha(float value);
 * public float getAlpha();
 * <p/>
 * anim.addListener(new AnimatorListener() {
 * @Override public void onAnimationStart(Animator animation) {
 * }
 * @Override public void onAnimationRepeat(Animator animation) {
 * }
 * @Override public void onAnimationEnd(Animator animation) {
 * }
 * @Override public void onAnimationCancel(Animator animation) {
 * }
 * });
 * <p/>
 * anim.addListener(new AnimatorListenerAdapter() {
 * });
 * <p/>
 * <p/>
 * 抛物线
 * public void paowuxian(View view)
 * {
 * <p/>
 * ValueAnimator valueAnimator = new ValueAnimator();
 * valueAnimator.setDuration(3000);
 * valueAnimator.setObjectValues(new PointF(0, 0));
 * valueAnimator.setInterpolator(new LinearInterpolator());
 * valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
 * {
 * // fraction = t / duration
 * @Override public PointF evaluate(float fraction, PointF startValue,
 * PointF endValue)
 * {
 * Log.e(TAG, fraction * 3 + "");
 * // x方向200px/s ，则y方向0.5 * 10 * t
 * PointF point = new PointF();
 * point.x = 200 * fraction * 3;
 * point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
 * return point;
 * }
 * });
 * <p/>
 * valueAnimator.start();
 * valueAnimator.addUpdateListener(new AnimatorUpdateListener()
 * {
 * @Override public void onAnimationUpdate(ValueAnimator animation)
 * {
 * PointF point = (PointF) animation.getAnimatedValue();
 * mBlueBall.setX(point.x);
 * mBlueBall.setY(point.y);
 * <p/>
 * }
 * });
 * }
 * <p/>
 * ObjectAnimator animX = ObjectAnimator.ofFloat(myView, "x", 50f);
 * ObjectAnimator animY = ObjectAnimator.ofFloat(myView, "y", 100f);
 * AnimatorSet animSetXY = new AnimatorSet();
 * animSetXY.playTogether(animX, animY);
 * animSetXY.start();
 * <p/>
 * <p/>
 * One ObjectAnimator
 * PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 50f);
 * PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);
 * ObjectAnimator.ofPropertyValuesHolder(myView, pvhX, pvyY).start();
 * <p/>
 * <p/>
 * ViewPropertyAnimator
 * myView.animate().x(50f).y(100f);
 */


