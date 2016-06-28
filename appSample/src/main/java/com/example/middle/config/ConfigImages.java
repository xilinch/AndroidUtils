package com.example.middle.config;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.app.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.xiaocoder.android_xcfw.io.XCIOAndroid;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class ConfigImages {

    /**
     * 默认图片加载option的配置
     */
    // TODO 修改默认图片
    public static DisplayImageOptions default_image_options = new DisplayImageOptions.Builder()

            .showImageOnLoading(R.drawable.ic_launcher) // 设置图片在下载期间显示的图片

            .showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片

            .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片

            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中

            .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中

            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示

            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//

            // .displayer(new FadeInBitmapDisplayer(0))// 是否图片加载好后渐入的动画时间
            .displayer(new SimpleBitmapDisplayer())
            // .displayer(new RoundedBitmapDisplayer(50)) // 圆形图片 ， 这个不要与XCRoundImageView同时使用，选一个即可

            .build();// 构建完成

    /**
     * imageloader 的配置
     */
    public static ImageLoader getImageloader(Context context) {
        ImageLoader.getInstance().init(
                new ImageLoaderConfiguration

                        .Builder(context)

                        .memoryCacheExtraOptions(480, 800)
                        // max width, max height，即保存的每个缓存文件的最大长宽

                        .threadPoolSize(3)
                        // 线程池内加载的数量

                        .threadPriority(Thread.NORM_PRIORITY - 2)

                        .denyCacheImageMultipleSizesInMemory()

                        .memoryCache(new WeakMemoryCache())
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                        // .memoryCacheSize(5 * 1024 * 1024)
                        .discCacheSize(50 * 1024 * 1024)

                        .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密

                        .tasksProcessingOrder(QueueProcessingType.LIFO)

                        .discCacheFileCount(500)
                        // 缓存的文件数量

                        .discCache(new UnlimitedDiscCache(XCIOAndroid.createDirInAndroid(context, ConfigFile.CACHE_DIR)))
                        // 自定义缓存路径

                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple())

                        .imageDownloader(
                                new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout

                        .writeDebugLogs() // Remove for release app

                        .build());// 开始构建
        return ImageLoader.getInstance();
    }

}
