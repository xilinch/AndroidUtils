package com.xiaocoder.android_ui.view.js;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.xiaocoder.android_ui.R;


/**
 * @description 自定义裁剪图片的布局
 * @author 徐金山
 * @version 1.5.0
 */
public class ClipImageLayout extends RelativeLayout {
    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    /**
     * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding = 50;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        addView(mZoomImageView, lp);
        addView(mClipImageView, lp);

        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources().getDisplayMetrics());
        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mClipImageView.setHorizontalPadding(mHorizontalPadding);
    }

    /**
     * 设置要裁剪的图片
     * @param clipImageBitmap 要裁剪的图片
     */
    public void setClipImage(Bitmap clipImageBitmap) {
        if(null != clipImageBitmap) {
            mZoomImageView.setImageBitmap(clipImageBitmap);
        }else {
            mZoomImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        }
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    /**
     * 裁剪图片
     * @return 裁剪的图片结果
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }
}
