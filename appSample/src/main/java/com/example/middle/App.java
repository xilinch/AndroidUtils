package com.example.middle;

import android.app.Application;
import android.content.Context;

import com.example.middle.config.ConfigFile;
import com.example.middle.config.ConfigGeneral;
import com.example.middle.config.ConfigImages;
import com.example.middle.config.ConfigLog;
import com.example.middle.config.ConfigUrl;
import com.squareup.leakcanary.LeakCanary;
import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.exception.XCCrashHandler;
import com.xiaocoder.android_xcfw.exception.XCExceptionModel;
import com.xiaocoder.android_xcfw.exception.XCExceptionModelDb;
import com.xiaocoder.android_xcfw.exception.XCIException2Server;
import com.xiaocoder.android_xcfw.function.helper.XCAppHelper;
import com.xiaocoder.android_xcfw.function.thread.XCExecutor;
import com.xiaocoder.android_xcfw.http.asynchttp.XCAsyncClient;
import com.xiaocoder.android_xcfw.imageloader.XCAsynLoader;
import com.xiaocoder.android_xcfw.io.XCIOAndroid;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.io.XCSP;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 初始化的顺序不要去改动
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initAppHelper();

        initLeakCanary();

        createDir();

        initLog();

        initSp();

        initThreadPool();

        initImageLoader();

        initHttp();

        initCrash();

        printEnvironment();

    }

    private void printEnvironment() {
        XCLog.i(XCConstant.TAG_SYSTEM_OUT, ConfigUrl.CURRENT_RUN_ENVIRONMENT.toString() + "-----域名环境");

        XCLog.i(XCConstant.TAG_SYSTEM_OUT, ConfigLog.DEBUG_CONTROL.toString() + "-----日志环境");
    }

    private void initLeakCanary() {
        if (!(ConfigLog.DEBUG_CONTROL == ConfigLog.DebugControl.CLOSE)) {
            LeakCanary.install(this);
        }
    }

    private void initAppHelper() {
        XCAppHelper.init(this);
    }

    /**
     * sp保存文件名 与 模式
     */
    private void initSp() {
        XCSP.initXCSP(getApplicationContext(), ConfigFile.SP_FILE, Context.MODE_APPEND);// Context.MODE_MULTI_PROCESS
    }

    private void initLog() {

        XCLog.initXCLog(getApplicationContext(),
                ConfigLog.IS_DTOAST, ConfigLog.IS_OUTPUT, ConfigLog.IS_PRINTLOG,
                ConfigFile.APP_ROOT, ConfigFile.LOG_FILE, XCConstant.ENCODING_UTF8);
    }

    private void initThreadPool() {
        XCExecutor.initXCExecutor(ConfigGeneral.FIX_THREAD_NUM);
    }

    private void createDir() {
        // 应用存储日志 缓存等信息的顶层文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.APP_ROOT);
        // 图片视频等缓存的文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.MOIVE_DIR);
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.VIDEO_DIR);
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.PHOTO_DIR);
        // crash文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.CRASH_DIR);
        // cache文件夹
        XCIOAndroid.createDirInAndroid(getApplicationContext(), ConfigFile.CACHE_DIR);
    }

    private void initHttp() {
        Http.initHttp(new XCAsyncClient());
    }

    private void initImageLoader() {

        Image.initImager(new XCAsynLoader(ConfigImages.getImageloader(getApplicationContext()), ConfigImages.default_image_options));
    }

    private void initCrash() {

        XCCrashHandler.getInstance().init(ConfigLog.IS_INIT_CRASH_HANDLER, getApplicationContext(), ConfigFile.CRASH_DIR, ConfigLog.IS_SHOW_EXCEPTION_ACTIVITY);

        XCCrashHandler.getInstance().setUploadServer(new XCIException2Server() {
            @Override
            public void uploadException2Server(String info, Throwable ex, Thread thread,
                                               XCExceptionModel model, XCExceptionModelDb db) {
                // 如果IS_INIT_CRASH_HANDLER（枚举值中可设置）为false，则dao为空
                if (db != null) {
                    model.setUserId(Sp.getUserId());
                    db.updateByUniqueId(model, model.getUniqueId());
                }
            }
        });
    }
}
