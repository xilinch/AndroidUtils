package com.xiaocoder.android_xcfw.function.helper;

import android.app.Dialog;
import android.os.Handler;

import java.io.File;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 删除缓存 ，是在子线程运行的（文件或文件夹）
 */
public class XCCleanCacheHelper {

    private Handler handler = new Handler();

    public interface RemoveDirListener {

        /**
         * 即将要删除的文件
         * <p/>
         * 这个方法是在子线程中运行的
         * <p/>
         * 是否删除这个文件，true删除(即removing()返回true后，该类执行file.delete的代码)，false不删除
         */
        boolean onRemoving(File file);

        /**
         * 子线程的删除文件代码执行完成，handler到主线程
         * <p/>
         * 这个方法是在主线程中运行的
         */
        void onFinish();
    }

    RemoveDirListener mRemoveDirListener;

    public void setRemoveDirListener(RemoveDirListener removeDirListener) {
        this.mRemoveDirListener = removeDirListener;
    }

    boolean isGoOnDeleting;

    boolean isDeleteRootDir;

    Dialog mDeletingDialog;

    public XCCleanCacheHelper(Dialog deletingDialog, boolean deleteRootDir) {

        mDeletingDialog = deletingDialog;
        isDeleteRootDir = deleteRootDir;
        // 如果是实际中, 可能存在正在删除, 而页面此时退出的情况
        isGoOnDeleting = true;

    }

    private void removeDir(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                // 如果目录是系统级文件夹，java没有访问权限，那么会返回null数组
                for (File file : files) {
                    if (isGoOnDeleting) {
                        if (file.isDirectory()) {
                            removeDir(file);
                        } else {
                            if (mRemoveDirListener == null || mRemoveDirListener.onRemoving(file)) {
                                file.delete();
                            }
                        }
                    }
                }
            }
            if (isDeleteRootDir) {
                dir.delete();
            }
        }
    }

    /**
     * 子线程中删除
     */
    public void removeFileAsyn(final File file) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果文件不存在
                if (!file.exists()) {
                    return;
                }
                // 文件存在，则开始转圈
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mDeletingDialog != null) {
                            mDeletingDialog.show();
                        }
                    }
                });

                if (file.isDirectory()) {
                    removeDir(file);
                } else {
                    if (mRemoveDirListener == null || mRemoveDirListener.onRemoving(file)) {
                        file.delete();
                    }
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mRemoveDirListener != null) {
                            mRemoveDirListener.onFinish();
                        }

                        if (mDeletingDialog != null) {
                            mDeletingDialog.cancel();
                        }
                    }
                }, 2000);
            }
        }).start();
    }

    public void quit() {
        isGoOnDeleting = false;

        if (mDeletingDialog != null) {
            mDeletingDialog.dismiss();
            mDeletingDialog = null;
        }
    }
}
