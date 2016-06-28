package com.xiaocoder.android_ui.view.xc;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaocoder.android_ui.R;
import com.xiaocoder.android_xcfw.util.UtilView;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCTitleLayout extends FrameLayout {

    private RelativeLayout id_titlebar_root_layout;
    private LinearLayout id_titlebar_left_layout;
    private ImageView id_titlebar_left_imageview;
    private TextView id_titlebar_left_textview;
    private TextView id_titlebar_center_textview;
    private LinearLayout id_titlebar_right_layout;
    private ImageView id_titlebar_right_imageview;
    private LinearLayout id_titlebar_right2_layout;
    private TextView id_titlebar_right2_textview;
    private LinearLayout id_titlebar_right2_imageview_layout;
    private ImageView id_titlebar_right2_imageview;

    public XCTitleLayout(Context context) {
        super(context);
    }

    public XCTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.xc_l_view_titlebar, this, true);
        id_titlebar_root_layout = (RelativeLayout) findViewById(R.id.id_titlebar_root_layout);
        id_titlebar_left_layout = (LinearLayout) findViewById(R.id.id_titlebar_left_layout);
        id_titlebar_left_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        id_titlebar_left_imageview = (ImageView) findViewById(R.id.id_titlebar_left_imageview);
        id_titlebar_left_textview = (TextView) findViewById(R.id.id_titlebar_left_textview);
        id_titlebar_center_textview = (TextView) findViewById(R.id.id_titlebar_center_textview);
        id_titlebar_right_layout = (LinearLayout) findViewById(R.id.id_titlebar_right_layout);
        id_titlebar_right_imageview = (ImageView) findViewById(R.id.id_titlebar_right_imageview);
        id_titlebar_right2_layout = (LinearLayout) findViewById(R.id.id_titlebar_right2_layout);
        id_titlebar_right2_textview = (TextView) findViewById(R.id.id_titlebar_right2_textview);
        id_titlebar_right2_imageview_layout = (LinearLayout) findViewById(R.id.id_titlebar_right2_imageview_layout);
        id_titlebar_right2_imageview = (ImageView) findViewById(R.id.id_titlebar_right2_imageview);
    }

    public XCTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RelativeLayout getXc_id_titlebar_root_layout() {
        return id_titlebar_root_layout;
    }

    public LinearLayout getXc_id_titlebar_left_layout() {
        return id_titlebar_left_layout;
    }

    public ImageView getXc_id_titlebar_left_imageview() {
        return id_titlebar_left_imageview;
    }

    public TextView getXc_id_titlebar_left_textview() {
        return id_titlebar_left_textview;
    }

    public TextView getXc_id_titlebar_center_textview() {
        return id_titlebar_center_textview;
    }

    public LinearLayout getXc_id_titlebar_right_layout() {
        return id_titlebar_right_layout;
    }

    public ImageView getXc_id_titlebar_right_imageview() {
        return id_titlebar_right_imageview;
    }

    public LinearLayout getXc_id_titlebar_right2_layout() {
        return id_titlebar_right2_layout;
    }

    public TextView getXc_id_titlebar_right2_textview() {
        return id_titlebar_right2_textview;
    }

    public LinearLayout getXc_id_titlebar_right2_imageview_layout() {
        return id_titlebar_right2_imageview_layout;
    }

    public ImageView getXc_id_titlebar_right2_imageview() {
        return id_titlebar_right2_imageview;
    }


    /**
     * 设置title的中心的标题
     */
    public void setTitleCenter(boolean isCenterShow, String title) {
        UtilView.setGone(isCenterShow, id_titlebar_center_textview);
        id_titlebar_center_textview.setText(title);
    }

    /**
     * 设置左边的文本, 设置true , 如果是"" 则显示的就是一个返回的图标,
     * 如果是"返回" ,则为一个返回的图标+"返回"的字样
     */
    public void setTitleLeft(boolean isLeftShow, String left_text) {
        UtilView.setGone(isLeftShow, id_titlebar_left_layout);
        id_titlebar_left_textview.setText(left_text);
    }

    public void setTitleLeft(int left_drawable_id, String left_text) {
        UtilView.setGone(true, id_titlebar_left_layout);
        id_titlebar_left_textview.setText(left_text);
        if (left_drawable_id > 0) {
            id_titlebar_left_imageview.setImageResource(left_drawable_id);
            UtilView.setGone(true, id_titlebar_left_imageview);
        } else {
            UtilView.setGone(false, id_titlebar_left_imageview);
        }
    }


    /**
     * title中间的textview也可以设置drawable , 这里默认的是textview的右边的drawable
     */
    public void setTitleCenterRightDrawable(int textview_drawable_id) {
        if (textview_drawable_id > 0) {
            Drawable drawable = getResources().getDrawable(textview_drawable_id);//R.drawable. d_arrow_down
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            id_titlebar_center_textview.setCompoundDrawables(null, null, drawable, null);
        } else {
            id_titlebar_center_textview.setCompoundDrawables(null, null, null, null);
        }
    }

    /**
     * 如果这个显示, 那么TitleRight2就不必要显示了, 这个最右边的布局只有一个图片
     */
    public void setTitleRight(boolean isRightShow, int right_drawable_id) {

        UtilView.setGone(isRightShow, id_titlebar_right_layout);
        id_titlebar_right_imageview.setBackgroundResource(right_drawable_id);
    }

    /**
     * 这个布局为 textview + iamgeview , isRight2Show如果为false,则该right2_layout不显示,
     * right2_drawable_id如果小于0 ,则imageview不显示 ,text如果为null,则textview不显示
     */
    public void setTitleRight2(boolean isRight2Show, int right2_drawable_id, String text) {

        UtilView.setGone(isRight2Show, id_titlebar_right2_layout);

        if (right2_drawable_id > 0) {
            id_titlebar_right2_imageview.setBackgroundResource(right2_drawable_id);
            UtilView.setGone(true, id_titlebar_right2_imageview);
        } else {
            UtilView.setGone(false, id_titlebar_right2_imageview);
            UtilView.setGone(false, id_titlebar_right2_imageview_layout);
        }

        if (text != null) {
            UtilView.setGone(true, id_titlebar_right2_textview);
            id_titlebar_right2_textview.setText(text);
        } else {
            UtilView.setGone(false, id_titlebar_right2_textview);
        }
    }
}
