package com.xiaocoder.android_ui.view.yr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaocoder.android_ui.R;
import com.xiaocoder.android_xcfw.util.UtilScreen;

import java.util.ArrayList;

/**
 * 滑动右侧字母
 *
 * @author 崔毅然
 * @version 2.0
 */
public class YRSlideBar_V2 extends View {
    protected OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    public static String[] b = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    protected int choose = -1;
    protected Paint paint = new Paint();
    /** 屏幕中间弹出的当前选中字母的dialog */
    protected TextView mTextDialog;
    /** 每个字母高度 */
    public static int singleHeight = 36;
    /** 屏幕密度 */
    int density = 0;

    public YRSlideBar_V2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public YRSlideBar_V2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YRSlideBar_V2(Context context) {
        super(context);
        init();
    }

    /** 初始化设置每个字高度 */
    public void init(){
        if (getContext() != null) {
            density = UtilScreen.getScreenSizeByMetric(getContext())[2];
            // 屏幕适配
            if (density >= 480) {
                singleHeight = 40;
            } else if (density >= 360) {
                singleHeight = 36;
            } else if (density >= 240) {
                singleHeight = 30;
            } else {
                singleHeight = 24;
            }
        }
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (b != null) {
            int width = getWidth();
            for (int i = 0; i < b.length; i++) {
                paint.setColor(getResources().getColor(R.color.c_blue_2b98f6));
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setAntiAlias(true);
                // 屏幕适配
                if (density >= 480) {
                    paint.setTextSize(36);
                } else if (density >= 360) {
                    paint.setTextSize(32);
                } else if (density >= 240) {
                    paint.setTextSize(26);
                } else {
                    paint.setTextSize(20);
                }
                if (i == choose) {
                    paint.setFakeBoldText(true);
                }
                float xPos = width / 2 - paint.measureText(b[i]) / 2;
                float yPos = singleHeight * i + singleHeight;
                canvas.drawText(b[i], xPos, yPos, paint);
                paint.reset();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
//        final int c = (int) (y / getHeight() * b.length);
        final int c = (int) (y/singleHeight);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(0x00000000);
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.color.c_gray_f0f1f5);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
       void onTouchingLetterChanged(String s);
    }

    /** 动态设置右侧字母表 */
    public void setABC(ArrayList<String> parentList){
        if (parentList.size()>0) {
            String[] abc = new String[parentList.size()];
            for (int i = 0; i <parentList.size() ; i ++) {
                abc[i] = parentList.get(i);
            }
            this.b = abc;
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)getLayoutParams();
            params.height = singleHeight * (b.length + 1);
            setLayoutParams(params);
            invalidate();
            setVisibility(View.VISIBLE);
        }
    }

}
