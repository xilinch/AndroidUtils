package com.xiaocoder.android_ui.view.js;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @description 自定义裁图范围显示视图控件
 * @author 徐金山
 * @version 1.5.0
 */
public class ClipImageBorderView extends View {
    /** 水平方向与View的边距 */
    private int mHorizontalPadding;
    /** 垂直方向与View的边距 */
    private int mVerticalPadding;
    /** 绘制的矩形的宽度 */
    private int mWidth;
    /** 边框的颜色，默认为白色 */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /** 边框的宽度 单位dp */
    private int mBorderWidth = 1;
    /** 画笔 */
    private Paint mPaint;

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 计算矩形区域的宽度
        mWidth = getWidth() - 2 * mHorizontalPadding;
        // 计算距离屏幕垂直边界的边距
        mVerticalPadding = (getHeight() - mWidth) / 2;
        mPaint.setColor(Color.parseColor("#aa000000"));
        mPaint.setStyle(Style.FILL);
        // 绘制左边1
        canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mPaint);
        // 绘制右边2
        canvas.drawRect(getWidth() - mHorizontalPadding, 0, getWidth(), getHeight(), mPaint);
        // 绘制上边3
        canvas.drawRect(mHorizontalPadding, 0, getWidth() - mHorizontalPadding, mVerticalPadding, mPaint);
        // 绘制下边4
        canvas.drawRect(mHorizontalPadding, getHeight() - mVerticalPadding, getWidth() - mHorizontalPadding, getHeight(), mPaint);
        // 绘制中间圆
        drawClipImageCircle(canvas);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    private void drawClipImageCircle(Canvas canvas) {
        // 左部弧区
        Path path1 = new Path();
        float x1 = 0;
        float y1 = 0;
        x1 = getWidth() / 2;
        y1 = mVerticalPadding;
        path1.moveTo(x1, y1);
        path1.lineTo(mHorizontalPadding, y1);
        path1.lineTo(mHorizontalPadding, getHeight() - mVerticalPadding);
        path1.lineTo(x1, getHeight() - mVerticalPadding);
        RectF oval = new RectF(mHorizontalPadding, mVerticalPadding, getWidth() - mHorizontalPadding, getHeight() - mVerticalPadding);
        path1.arcTo(oval, 90, 180);
        path1.close();
        canvas.drawPath(path1, mPaint);

        // 右部弧区
        Path path2 = new Path();
        float x2 = 0;
        float y2 = 0;
        x2 = getWidth() / 2;
        y2 = mVerticalPadding;
        path2.moveTo(x2, y2);
        path2.lineTo(getWidth() - mHorizontalPadding, y2);
        path2.lineTo(getWidth() - mHorizontalPadding, getHeight() - mVerticalPadding);
        path2.lineTo(x2, getHeight() - mVerticalPadding);
        RectF oval2 = new RectF(mHorizontalPadding, mVerticalPadding, getWidth() - mHorizontalPadding, getHeight() - mVerticalPadding);
        path2.arcTo(oval2, 90, -180);
        path2.close();
        canvas.drawPath(path2, mPaint);

        // 中间圆
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Style.STROKE);
        float radius = mWidth/2;
        float x_coordinate = mHorizontalPadding + radius;
        float y_coordinate = mVerticalPadding + radius;
        canvas.drawCircle(x_coordinate, y_coordinate, radius, mPaint);
    }
}
