package com.xiaocoder.android_ui.view.xc;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaocoder.android_ui.R;
import com.xiaocoder.android_ui.view.open.OPZoomImageView;
import com.xiaocoder.android_xcfw.function.adapter.XCAdapterViewPagerRecyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCZoomViewPager extends FrameLayout implements View.OnClickListener {

    ViewPager xc_id_viewpager;
    TextView xc_id_viewpager_count;
    List<ImageView> imageviews;
    List<String> urls;
    XCAdapterViewPagerRecyle adapter;

    int current_location = 0;
    int total_images;

    public interface OnLoadImage {
        void onLoadImage(ImageView imageview, String url);
    }

    OnLoadImage on_load_image_listener;

    public interface OnImageClickListener {
        void onImageClickListener(int position);
    }

    OnImageClickListener listener;

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onImageClickListener((Integer) (v.getTag()));
        }
    }

    public void setData(List<String> urls, int defaultSelectedIndex, OnLoadImage on_load_image_listener) {
        if (urls != null) {
            this.current_location = defaultSelectedIndex;
            this.urls = urls;
            this.total_images = urls.size();
            this.on_load_image_listener = on_load_image_listener;
            createImageViews();
            createViewPager();
            update();
        }
    }

    private void createImageViews() {
        imageviews = new ArrayList<ImageView>();
        for (int i = 0; i < total_images; i++) {
            // 创建images
            ImageView imageview = getZoomImageView(i);
            // 加载图片
            if (on_load_image_listener != null) {
                on_load_image_listener.onLoadImage(imageview, urls.get(i));
            } else {
                throw new RuntimeException("未传入图片的加载方式");
            }
            imageviews.add(imageview);
        }
    }

    private OPZoomImageView getZoomImageView(int index) {
        OPZoomImageView imageview = new OPZoomImageView(getContext());
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.enable();
        imageview.setOnClickListener(this);
        imageview.setTag(index);
        return imageview;
    }

    protected void createViewPager() {
        adapter = new XCAdapterViewPagerRecyle(imageviews, null);
        xc_id_viewpager.setAdapter(adapter);
        xc_id_viewpager.setCurrentItem(current_location);
    }

    public void update() {
        int index = current_location + 1;
        xc_id_viewpager_count.setText(index + " / " + total_images);
    }

    private void init(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.xc_l_view_viewpager_zoom, this, true);

        xc_id_viewpager = (ViewPager) findViewById(R.id.xc_id_fragment_viewpager);
        xc_id_viewpager_count = (TextView) findViewById(R.id.xc_id_fragment_viewpager_count);

        xc_id_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                current_location = position;
                update();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    public XCZoomViewPager(Context context) {
        super(context);
        init(context);

    }

    public XCZoomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public XCZoomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


}
