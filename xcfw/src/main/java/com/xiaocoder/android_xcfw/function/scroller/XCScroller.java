package com.xiaocoder.android_xcfw.function.scroller;

import android.content.Context;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCScroller extends Scroller {

    public XCScroller(Context context) {
        super(context);
    }

    public XCScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public XCScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public boolean isScrollFinish() {
        // computeScrollOffset()返回值为true说明滚动尚未完成，false说明滚动已经完成。
        // 这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束。
        return !computeScrollOffset();
    }

    /**
     * 滚动到目标位置
     */
    public void smoothScrollTo(int fx, int fy, View view) {
        int dx = fx - getStartX();
        int dy = fy - getStartY();
        smoothScrollBy(dx, dy, view);
    }

    /**
     * 滚动到目标位置
     */
    public void smoothScrollTo(int fx, int fy, int time, View view) {
        int dx = fx - getStartX();
        int dy = fy - getStartY();
        smoothScrollBy(dx, dy, time, view);
    }

    /**
     * 滚动相对偏移量
     */
    public void smoothScrollBy(int dx, int dy, View view) {
        startScroll(getStartX(), getStartY(), dx, dy);
        view.invalidate();
    }

    /**
     * 滚动相对偏移量
     */
    public void smoothScrollBy(int dx, int dy, int time, View view) {
        startScroll(getStartX(), getStartY(), dx, dy, time);
        view.invalidate();
    }


    public void onViewComputeScroll(View view) {

        //先判断mScroller滚动是否完成
        if (!isScrollFinish()) {

            //完成实际的滚动
            view.scrollTo(getCurrX(), getCurrY());

            //重绘，否则不一定能看到滚动效果
            view.postInvalidate();
        }

    }

}
/**
 * view 的  getScrollY   （如上滑动 为 正数 ，下滑动 为 负数，值为已移动的距离，不是坐标点）
 * <p/>
 * <p/>
 * <p/>
 * http://blog.csdn.net/xiaanming/article/details/17483273
 * <p/>
 * mScroller.getCurrX() //获取mScroller当前水平滚动的位置,getCurrX() 返回当前滚动的X方向的偏移量（距离原点X轴方向）
 * mScroller.getCurrY() //获取mScroller当前竖直滚动的位置,getCurrY() 返回当前滚动的Y方向的偏移量（距离原点Y轴方向）
 * mScroller.getFinalX() //获取mScroller最终停止的水平位置
 * mScroller.getFinalY() //获取mScroller最终停止的竖直位置
 * mScroller.setFinalX(int newX) //设置mScroller最终停留的水平位置，没有动画效果，直接跳到目标位置
 * mScroller.setFinalY(int newY) //设置mScroller最终停留的竖直位置，没有动画效果，直接跳到目标位置
 * <p/>
 * //滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量, duration为完成滚动的时间
 * mScroller.startScroll(int startX, int startY, int dx, int dy) //使用默认完成时间250ms，既然叫开始滚动，那就是对滚动的滚动之前的基本设置咯。
 * mScroller.startScroll(int startX, int startY, int dx, int dy, int duration)
 * <p/>
 * computeScroll()是View类的一个空函数，在父容器重画自己的孩子时，它会调用孩子的computScroll方法。
 * .startScroll()方法定义了滚动的路径和时间
 * <p/>
 * 可以看到，点击bt1的时候，bt1向右按照原计划向右滚动了。可是computeScroll()方法不是在bt1的父view中重写的吗？
 * <p/>
 * 据作者所写：
 * <p/>
 * 　　　　//因为调用computeScroll函数的是MyLinearLayout实例，
 * <p/>
 * 　　　　//所以调用scrollTo移动的将是该实例的孩子，也就是MyButton
 * <p/>
 * 　　　　scrollTo(mScroller.getCurrX(), 0);
 * <p/>
 * 然后查看scrollTo的api：
 * <p/>
 * Set the scrolled position of your view. This will cause a call to onScrollChanged(int, int, int, int) and the view will be invalidated.
 * <p/>
 * 依然不很理解，所以只好这么认为吧：谁调用了scrollTo，谁的孩子就滚动。
 * <p/>
 * 其实当mScroller.startScroll(0, 0, -30, -30, 500)这局代码执行之后，由于最后一个参数传入的是执行时间，在这个时间范围内，也就是滚动没有执行完的时 候，mScroller.computeScrollOffset()返回的都是true！
 * <p/>
 * 在这个过程中，mScroller.getCurrX()的值却是一直在变化的，变化的范围你懂的。我们可以看到参数中调用了mScroller.getCurrX()，这就是取得现在处于的X坐标，然后滚动到改位置上。
 * <p/>
 * 然后呢？界面怎么动呢？当然是不断的重绘了！
 * <p/>
 * 怎么不断重绘？getChildAt(0).invalidate()就是这句，然后系统会一直重复的执行computeScroll()，直到设 定的时间结束，view也会滚动指定的位置，mScroller.computeScrollOffset()返回值变为false，这样就完成了整个滚 动过程。
 * <p/>
 * 到这儿的时候差不多已经吸收了原作者要给的知识了，再看一个额外的：
 * <p/>
 * 每个view都有computeScroll()方法，那么在mScroller.startScroll()发起滚动的时候，ContentLinearLayout 能不能“听到”这个命令？
 * <p/>
 * 应该是能的！把MyLinearLayout 中的computeScroll()方法copy至ContentLinearLayout 中，方法同样会执行，那么滚动的就是layout1+layout2了！
 * <p/>
 * 试了试确实是这样的，再次验证了谁要滚找它的父布局！
 * <p/>
 * 如果是比如imageview，调用scrollto则是imageview里面的图片改变位移，imageview控件的位置并不改变
 * <p/>
 * 我们要先理解View 里面的两个成员变量mScrollX， mScrollY，X轴方向的偏移量和Y轴方向的偏移量，
 * 这个是一个相对距离，相对的不是屏幕的原点，而是View的左边缘，举个通俗易懂的例子，一列火车从吉安到深圳，途中经过赣州，那么原点就是赣州，偏移量就是 负的吉安到赣州的距离，
 * <p/>
 * scrollTo先判断传进来的(x, y)值是否和View的X, Y偏移量相等，如果不相等，就调用onScrollChanged()方法来通知界面发生改变，然后重绘界面，所以这样子就实现了移动效果啦，
 * 假如一个View,调用两次scrollTo(-10, 0)，第一次向右滚动10，第二次就不滚动了，因为mScrollX和x相等了，当我们调用两次scrollBy(-10, 0),第一次向右滚动10，第二次再向右滚动10，他是相对View的上一个位置来滚动的。
 * <p/>
 * 对于scrollTo()和scrollBy()方法还有一点需要注意，这点也很重要，假如你给一个LinearLayout调用scrollTo()方法，并不是LinearLayout滚动，而是LinearLayout里面的内容进行滚动，
 * 比如你想对一个按钮进行滚动，直接用Button调用scrollTo()一定达不到你的需求，大家可以试一试，如果真要对某个按钮进行scrollTo()滚动的话，我们可以在Button外面包裹一层Layout，
 * 然后对Layout调用scrollTo()方法。
 * <p/>
 * <p/>
 * 整理下思路，来看看View滚动的实现原理，我们先调用Scroller的startScroll()方法来进行一些滚动的初始化设置，然后迫使View进行绘制，我们调用View的invalidate()或postInvalidate()就可以重新绘制View，绘制View的时候会触发computeScroll()方法，我们重写computeScroll()，在computeScroll()里面先调用Scroller的computeScrollOffset()方法来判断滚动有没有结束，如果滚动没有结束我们就调用scrollTo()方法来进行滚动，该scrollTo()方法虽然会重新绘制View,但是我们还是要手动调用下invalidate()或者postInvalidate()来触发界面重绘，重新绘制View又触发computeScroll()，所以就进入一个循环阶段，这样子就实现了在某个时间段里面滚动某段距离的一个平滑的滚动效果
 * 也许有人会问，干嘛还要调用来调用去最后在调用scrollTo()方法，还不如直接调用scrollTo()方法来实现滚动，其实直接调用是可以，只不过scrollTo()是瞬间滚动的，给人的用户体验不太好，所以Android提供了Scroller类实现平滑滚动的效果。为了方面大家理解，我画了一个简单的调用示意图
 * <p/>
 * AccelerateDecelerateInterpolator 动画效果：开始和结束都是缓慢的，通过中间时候加速
 * <p/>
 * AccelerateInterpolator, 动画效果：开始缓慢，之后加速
 * <p/>
 * AnticipateInterpolator, 动画效果：开始后退，然后前进
 * <p/>
 * AnticipateOvershootInterpolator, 动画效果：开始后退，之后前进并超过终点位置，最终退回到终点
 * <p/>
 * BounceInterpolator, 动画效果：慢慢反弹到，弹性衰减到结束
 * <p/>
 * CycleInterpolator, 动画效果：重复循环动画，速度变化遵循正弦定律
 * <p/>
 * DecelerateInterpolator, 动画效果：刚开始快速，之后减速
 * <p/>
 * LinearInterpolator, 动画效果：不断的变化
 * <p/>
 * OvershootInterpolator 动画效果：像前超越最终点然后回来
 * <p/>
 * void	abortAnimation() 停止动画，滚到最终的x,y位置中止动画
 * boolean	computeScrollOffset() 当你想要知道新的位置时候，调用该方法。返回true:动画没结束
 * void	extendDuration(int extend) 延长滚动动画的时间。extend表示延迟时间（单位为毫秒）
 * void	fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) 在fling（快速滑动，触摸屏幕后快意移动松开）的手势基础上开始滚动，滚动距离取决于fling的初速度。
 * final void	forceFinished(boolean finished) 强制终止滚动。
 * float	getCurrVelocity() 返回当前的速度
 * final int	getCurrX() 返回当前滚动的X方向的偏移量（距离原点X轴方向）
 * final int	getCurrY() 返回当前滚动的Y方向的偏移量（距离原点Y轴方向）
 * final int	getDuration() 返回滚动事件的持续时间（毫秒）
 * final int	getFinalX() 返回滚动结束的X方向的偏移量（注：只针对fling 手势有效）（距离原点X轴方向）
 * final int	getFinalY() 返回滚动结束的Y方向的偏移量（注：只针对fling 手势有效）（距离原点Y轴方向）
 * final int	getStartX() 返回滚动起始点的X方向偏移量（距离原点X轴方向）
 * final int	getStartY() 返回滚动起始点的Y方向偏移量.（距离原点Y轴方向）
 * final boolean	isFinished() 返回scroller滚动是否结束，true:滚动结束 false:还在滚动
 * void	setFinalX(int newX) 设置scroller的终止时X方向偏移量
 * void	setFinalY(int newY) 设置scroller的终止时Y方向偏移量
 * final void	setFriction(float friction) The amount of friction applied to flings.
 * void	startScroll(int startX, int startY, int dx, int dy) 提供起始点和滚动距离，调用该方法进行滚动。（此处默认时间为250ms）
 * void	startScroll(int startX, int startY, int dx, int dy, int duration) 提供起始点和滚动距离以及滚动时间，调用该方法进行滚动。
 * int	timePassed() 返回自滚动开始经过的时间（毫秒）
 * <p/>
 * http://www.2cto.com/kf/201404/296281.html
 */