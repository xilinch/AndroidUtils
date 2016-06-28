package com.xiaocoder.android_zxcode.decoding;

import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;
import com.xiaocoder.android_zxcode.view.ViewfinderView;

public interface IDecode {

    void drawViewfinder();

    void handleDecode(Result result, Bitmap barcode);

    ViewfinderView getViewfinderView();

    Handler getHandler();

}