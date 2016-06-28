package com.xiaocoder.android_zxcode_demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_zxcode.camare.CameraManager;
import com.xiaocoder.android_zxcode.decoding.CaptureActivityHandler;
import com.xiaocoder.android_zxcode.decoding.IDecode;
import com.xiaocoder.android_zxcode.decoding.InactivityTimer;
import com.xiaocoder.android_zxcode.image.RGBLuminanceSource;
import com.xiaocoder.android_zxcode.view.ViewfinderView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 闪光灯、连续扫描二维码
 */
public class ScanActivity extends FragmentActivity implements Callback, View.OnClickListener, IDecode {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_SUC = 300;
    private static final int PARSE_BARCODE_FAIL = 303;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;

    // ****************** 以下是加的 *********************
    private CameraManager manager;
    private boolean isFlashOpen;
    private long start_timegap;
    private long end_timegap;
    private Button ablum;
    private String result;
    private ScanResultDialog dialog;
    private ImageView xc_id_capture_flash_imageview;
    // ******************* 以上是加的 ********************

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_coder);

        initWidgets();
        setListeners();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mProgress.dismiss();
            switch (msg.what) {
                case PARSE_BARCODE_SUC:
                    onResultHandler((String) msg.obj, scanBitmap);
                    break;
                case PARSE_BARCODE_FAIL:
                    XCLog.longToast(msg.obj);
                    break;
            }
        }
    };

    /**
     * 扫描二维码图片的方法
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, XCConstant.ENCODING_UTF8); // 设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理扫描结果
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        onResultHandler(resultString, barcode);
    }

    /**
     * 跳转到上一个页面
     */
    private void onResultHandler(final String resultString, Bitmap bitmap) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(ScanActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
            return;
        }
        /*
         * Intent resultIntent = new Intent(); Bundle bundle = new Bundle();
		 * bundle.putString("result", resultString);
		 * bundle.putParcelable("bitmap", bitmap);
		 * resultIntent.putExtras(bundle); this.setResult(RESULT_OK,
		 * resultIntent); CaptureActivity.this.onEnd();
		 */
        result = resultString;
        dialog.getContent_textview().setText(resultString);
        dialog.show();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }

        if (handler == null) {
            handler = new CaptureActivityHandler(this, this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public void initWidgets() {

        CameraManager.init(getApplication());
        manager = CameraManager.get();
        viewfinderView = (ViewfinderView) findViewById(R.id.capture_viewfinder);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        ablum = (Button) findViewById(R.id.id_ablum);

        // 闪光灯按钮
        xc_id_capture_flash_imageview = (ImageView) findViewById(R.id.xc_id_capture_flash_imageview);

        dialog = new ScanResultDialog(this, getResources().getString(R.string.dialog_query_title_scanresult),
                null, getResources().getStringArray(R.array.dialog_query_decide_scanresult), false);
    }

    public void setListeners() {
        xc_id_capture_flash_imageview.setOnClickListener(this);

        ablum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开手机中的相册
                XCLog.shortToast("rightclick");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
                intent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(intent, "选择本地二维码图片");
                startActivityForResult(wrapperIntent, REQUEST_CODE);
            }
        });

        dialog.setOnDecideListener(new ScanResultDialog.OnDecideListener() {
            @Override
            public void confirm() {
                dialog.dismiss();
                // 关闪光灯
                if (isFlashOpen) {
                    try {
                        manager.closeLight();
                        isFlashOpen = false;
                        xc_id_capture_flash_imageview.setSelected(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Uri content_url = Uri.parse(result);
                if (content_url.toString().startsWith("http")) {
                    Intent intent = new Intent(); // 打开链接
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    XCLog.shortToast("该链接有误");
                    onPause(); // 暂时实现连续扫描
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (handler != null) {
                        handler.restartPreviewAndDecode();// 连续快速的扫描,会报获取焦点异常,这里用onPause()和onResume来暂时的解决这个问题
                    }
                    onResume(); // 暂时实现连续扫描
                }
            }

            @Override
            public void cancle() {
                dialog.dismiss();
                // 关闪光灯
                if (isFlashOpen) {
                    try {
                        manager.closeLight();
                        isFlashOpen = false;
                        xc_id_capture_flash_imageview.setSelected(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                onPause(); // 暂时实现连续扫描
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (handler != null) {
                    handler.restartPreviewAndDecode();// 连续快速的扫描,会报获取焦点异常,这里用onPause()和onResume来暂时的解决这个问题
                }
                onResume(); // 暂时实现连续扫描
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (R.id.xc_id_capture_flash_imageview == v.getId()) {
            start_timegap = System.currentTimeMillis();
            if (start_timegap - end_timegap > 500) {
                if (!isFlashOpen) {
                    try {
                        manager.openLight();
                        isFlashOpen = true;
                        v.setSelected(true);
                    } catch (Exception e) {
                        XCLog.e(this, "", e);
                    }

                } else {
                    try {
                        manager.closeLight();
                        isFlashOpen = false;
                        v.setSelected(false);
                    } catch (Exception e) {
                        XCLog.e(this, "", e);
                    }
                }
                end_timegap = start_timegap;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        }
                        cursor.close();

                        mProgress = new ProgressDialog(ScanActivity.this);
                        mProgress.setMessage("正在扫描...");
                        mProgress.setCancelable(false);
                        mProgress.show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Result result = scanningImage(photo_path);
                                if (result != null) {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_SUC;
                                    m.obj = result.getText();
                                    mHandler.sendMessage(m);
                                } else {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_FAIL;
                                    m.obj = "解析数据有误";
                                    mHandler.sendMessage(m);
                                }
                            }
                        }).start();
                    } else {
                        XCLog.shortToast("解析数据有误");
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_surfaceview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

}