package com.xiaocoder.android_ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.xiaocoder.android_ui.R;
import com.xiaocoder.android_ui.view.open.OPCircleProgressBar;


public class OPMaterialLoadingDialog extends Dialog {

    public static int TRAN_STYLE = R.style.xc_s_dialog;

    private LayoutInflater dialogInflater;

    private ViewGroup dialogLayout;

    private Context mContext;

    private OPCircleProgressBar progress;

    public OPMaterialLoadingDialog(Context context) {
        super(context, TRAN_STYLE);
        dialogInflater = LayoutInflater.from(context);
        mContext = context;
        initDialog();
    }


    public void initDialog() {

        dialogLayout = (ViewGroup) dialogInflater.inflate(R.layout.xc_l_dialog_meterial, null);

        progress = (OPCircleProgressBar) dialogLayout.findViewById(R.id.meterial_dialog);
        progress.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_red_light, android.R.color.holo_green_light);

        setContentView(dialogLayout);
        setWindowLayoutStyleAttr();

    }

    public void setWindowLayoutStyleAttr() {
        dialogLayout.setBackgroundColor(mContext.getResources().getColor(R.color.c_trans));
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.9f;
        lp.dimAmount = 0.3f;
        window.setAttributes(lp);
    }

}



