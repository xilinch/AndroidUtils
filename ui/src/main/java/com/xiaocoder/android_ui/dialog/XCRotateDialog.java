package com.xiaocoder.android_ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaocoder.android_ui.R;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 旋转imageview
 */
public class XCRotateDialog extends Dialog {
    public static int TRAN_STYLE = R.style.xc_s_dialog;

    /*
     * 如果这里使用getLayoutInflater(),则获取不到双圈的dialog，用LayoutInflater.from可以
     */
    public LayoutInflater dialogInflater;

    public ViewGroup dialogLayout;

    public Context mContext;

    ImageView imageview;
    Animation anim;
    TextView textview;

    public ImageView getImageview() {
        return imageview;
    }

    public TextView getTextview() {
        return textview;
    }

    public Animation getAnim() {
        return anim;
    }

    public XCRotateDialog(Context context, int imageViewId) {
        super(context, TRAN_STYLE);
        dialogInflater = LayoutInflater.from(context);
        mContext = context;

        initDialog(imageViewId);
    }

    public void initDialog(int imageViewId) {
        dialogLayout = (ViewGroup) dialogInflater.inflate(R.layout.xc_l_dialog_rotate_imageview, null);
        setContentView(dialogLayout);
        setWindowLayoutStyleAttr();

        textview = (TextView) dialogLayout.findViewById(R.id.xc_id_dialog_rotate_textview);

        anim = getRatoteAnimation();

        imageview = (ImageView) dialogLayout.findViewById(R.id.xc_id_dialog_rotate_imageview);
        imageview.setImageResource(imageViewId);

    }

    public void setWindowLayoutStyleAttr() {
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.7f;
        lp.dimAmount = 0.2f;
        window.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        imageview.startAnimation(anim);
    }

    public Animation getRatoteAnimation() {
        RotateAnimation animation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(6000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        return animation;
    }
}
