package com.xiaocoder.android_ui.view.xc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaocoder.android_ui.R;
import com.xiaocoder.android_xcfw.util.UtilString;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCPointNumLayout extends FrameLayout {

    private LinearLayout id_point_num_layout;
    private TextView id_text_content;
    private TextView id_text_num_front;
    private TextView id_text_num_behind;

    public XCPointNumLayout(Context context) {
        super(context);
    }

    public XCPointNumLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.xc_l_view_point_num, this, true);

        id_point_num_layout = (LinearLayout) findViewById(R.id.id_point_num_layout);
        id_text_content = (TextView) findViewById(R.id.id_text_content);
        id_text_num_front = (TextView) findViewById(R.id.id_text_num_front);
        id_text_num_behind = (TextView) findViewById(R.id.id_text_num_behind);

    }

    public XCPointNumLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static final String SYMBOL_POINT = ".";

    public static final String BLANK = "";

    /**
     * @param desc 这个商品的指数是
     * @param num  1.1
     */
    public void setDesAndNum(String desc, String num) {

        if (!UtilString.isBlank(desc)) {
            // 描述不为空
            id_text_content.setText(desc);
        } else {
            id_text_content.setText(BLANK);
        }

        if (!UtilString.isBlank(num)) {

            // 数值不为空，可能是 11   ,   1.1，   .1   ,11.
            if (num.contains(SYMBOL_POINT)) {
                // 含小数点
                int position = num.indexOf(SYMBOL_POINT);

                if (position > 0) {
                    // 小数点不是第一位
                    id_text_num_front.setText(num.substring(0, position));
                    id_text_num_behind.setText(num.substring(position, num.length()));
                } else {
                    // 小数点在第一位
                    id_text_num_front.setText(num);
                    id_text_num_behind.setText(BLANK);
                }
            } else {
                // 不含小数点
                id_text_num_front.setText(num);
                id_text_num_behind.setText(BLANK);
            }

        } else {
            // 数值为空
            id_text_num_front.setText(BLANK);
            id_text_num_behind.setText(BLANK);
        }
    }

    public LinearLayout getId_point_num_layout() {
        return id_point_num_layout;
    }

    public TextView getId_text_content() {
        return id_text_content;
    }

    public TextView getId_text_num_front() {
        return id_text_num_front;
    }

    public TextView getId_text_num_behind() {
        return id_text_num_behind;
    }
}
