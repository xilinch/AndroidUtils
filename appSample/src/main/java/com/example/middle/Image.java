package com.example.middle;

import android.widget.ImageView;

import com.xiaocoder.android_xcfw.imageloader.XCIImageLoader;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class Image {

    private static XCIImageLoader imageLoader;


    public static XCIImageLoader getImageLoader() {
        return imageLoader;
    }

    public static void initImager(XCIImageLoader imageLoader) {
        Image.imageLoader = imageLoader;
    }

    /**
     * 图片加载
     */
    public static void displayImage(String uri, ImageView imageView, Object... options) {
        imageLoader.display(uri, imageView, options);
    }

    public static void displayImage(String uri, ImageView imageView) {
        imageLoader.display(uri, imageView);
    }

}
