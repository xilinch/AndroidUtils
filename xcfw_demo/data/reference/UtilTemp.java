package reference;

/**
 * @author xiaocoder on 2016/1/12.
 * @modifier xiaocoder 2016/1/12 10:02.
 * @description
 */
public class UtilTemp {

//    // 判断信息中是否有表情
//    public SpannableString string2FaceString(String message, Context paramContext) {
//        String temp;
//        if (message.indexOf("[") > 0 && message.indexOf("]") > 0) {
//            temp = message + " ";
//        } else {
//            temp = message;
//        }
//        Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
//
//        SpannableString value = SpannableString.valueOf(temp);
//
//        Matcher localMatcher = EMOTION_URL.matcher(value);
//
//        while (localMatcher.find()) {
//            String name = localMatcher.group(0);
//
//            int k = localMatcher.start();
//            int m = localMatcher.end();
//
//            Bitmap bitmap = getBitmap(name);
//            if (bitmap != null) {
//                ImageSpan localImageSpan = new ImageSpan(paramContext, bitmap);
//                value.setSpan(localImageSpan, k, m, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return value;
//    }
//
//    // 获取表情
//    public Bitmap getBitmap(String name) {
//        try {
//            int length = name.length();
//            name = name.substring(1, length - 1);
//            printi(name);
//            InputStream inputStream = getAssets().open(XCBottomChatFragment.FACE_PATH_DIR + name);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            return Bitmap.createScaledBitmap(bitmap, UtilImage.dip2px(this, 24), UtilImage.dip2px(this, 24), true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    //-------------------------------------------------------------------------------------------
    // 创建表情布局， 在viewpager中可以滑动
//    ArrayList<LinearLayout> face_view_layout;
//    ArrayList<GridView> face_view_gridview;
//    List<View> dots;
//    int total_images;
//    int last_dot_position = 0;// 记录上一次点的位置
//    int currentItem; // 当前页面
//    LinearLayout xc_id_fragment_bottom_viewpager_dots;
//    XCAdapterViewPager viewpager_adapter;
//
//    // 创建viewpager中的gridview 和 dots
//    private void createFaceViewsAndDots() {
//        // 创建gridview
//        face_view_layout = new ArrayList<LinearLayout>();
//        face_view_gridview = new ArrayList<GridView>();
//        int gap = UtilImage.dip2px(getActivity(), 6);
//        int gap2 = UtilImage.dip2px(getActivity(), 12);
//        // 创建dots
//        dots = new ArrayList<View>();
//        total_images = TOTAL_FACE_NUM / PAGE_NUM + 1; // 85/20 + 1
//        for (int i = 0; i < total_images; i++) {
//            // 创建gridview
//            LinearLayout include_gridview_layout = (LinearLayout) getBaseActivity().base_inflater.inflate(R.layout.xc_l_view_face_gridview, null);
//            GridView gridview = (GridView) include_gridview_layout.findViewById(R.id.xc_id_fragment_face_gridview);
//            LinearLayout.LayoutParams ll_gridview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UtilImage.dip2px(getActivity(), 150));
//            gridview.setLayoutParams(ll_gridview);
//            gridview.setPadding(gap2, gap2, gap2, gap2);
//            UtilAbsList.setGridViewStyle(gridview, false, gap, gap, 7);
//            // 设置表情点击的监听
//            FaceAdapter face_adapter = new FaceAdapter(getActivity(), getFaceUrl(i + 1));
//            gridview.setAdapter(face_adapter);
//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String url = (String) (view.findViewById(R.id.xc_id_face_item_imageview).getTag() + "");
//                    String name = url.substring(url.lastIndexOf("/") + 1, url.length());
//                    // shortToast(name + "--" + url);
//                    updateEditText(name);
//                }
//            });
//            face_view_gridview.add(gridview);
//            face_view_layout.add(include_gridview_layout);
//            // 创建dots
//            View view = LayoutInflater.from(getActivity()).inflate(R.layout.xc_l_view_viewpager_dot, null);
//            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(UtilImage.dip2px(getActivity(), 7), UtilImage.dip2px(getActivity(), 7));
//            ll.setMargins(UtilImage.dip2px(getActivity(), 3), 0, UtilImage.dip2px(getActivity(), 3), 0);
//            view.setLayoutParams(ll);
//            dots.add(view);
//            if (i == 0) {
//                view.setBackgroundResource(R.drawable.xc_dd_fragment_viewpager_dot_focused);
//                last_dot_position = 0;
//            } else {
//                view.setBackgroundResource(R.drawable.xc_dd_fragment_viewpager_dot_normal);
//            }
//            xc_id_fragment_bottom_viewpager_dots.addView(view);
//        }
//    }
//
//    // 设置viewpager的监听和adapter
//    protected void createViewPager() {
//        viewpager_adapter = new XCAdapterViewPager(face_view_layout);
//        xc_id_fragment_bottom_face_viewpager.setAdapter(viewpager_adapter);
//        xc_id_fragment_bottom_face_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                dots.get(last_dot_position).setBackgroundResource(R.drawable.xc_dd_fragment_viewpager_dot_normal);
//                dots.get(position).setBackgroundResource(R.drawable.xc_dd_fragment_viewpager_dot_focused);
//                last_dot_position = position;
//                currentItem = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    public static final String FACE_PATH_DIR = "face/";
//    // 表情的总数量
//    public static int TOTAL_FACE_NUM = 85;
//    // 每页表情的数量
//    public static int PAGE_NUM = 21;
//
//    // 当选中一个表情后， 更新edittext中的文本 +表情
//    private void updateEditText(String name) {
//        try {
//            InputStream inputStream = getActivity().getAssets().open(FACE_PATH_DIR + name);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//            bitmap = Bitmap.createScaledBitmap(bitmap, UtilImage.dip2px(getActivity(), 24), UtilImage.dip2px(getActivity(), 24), true);
//
//            name = "[" + name + "]";
//            ImageSpan image_span = new ImageSpan(getActivity(), bitmap);
//            SpannableString spannable_string = new SpannableString(name);
//            spannable_string.setSpan(image_span, name.indexOf('['), name.indexOf(']') + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            xc_id_fragment_bottom_edittext.append(spannable_string);
//
//            shortToast(xc_id_fragment_bottom_edittext.getText().toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public class FaceAdapter extends XCBaseAdapter<String> {
//
//        public FaceAdapter(Context context, List<String> list) {
//            super(context, list);
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            bean = list.get(position);
//            FaceHolder holder = null;
//            if (convertView == null) {
//                holder = new FaceHolder();
//                convertView = LayoutInflater.from(context).inflate(R.layout.xc_l_view_face_gridview_item, null);
//                holder.xc_jzh_face_item_imageview = (ImageView) convertView.findViewById(R.id.xc_id_face_item_imageview);
//                // 设置每个表情的大小， 这里可以根据不同手机屏幕做判断后再设置值
//                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(UtilImage.dip2px(context, 30), UtilImage.dip2px(context, 30));
//                holder.xc_jzh_face_item_imageview.setLayoutParams(ll);
//                convertView.setTag(holder);
//            } else {
//                holder = (FaceHolder) convertView.getTag();
//            }
//
//            holder.xc_jzh_face_item_imageview.setTag(bean);
//            imageloader.displayImage(bean, holder.xc_jzh_face_item_imageview, options);
//            return convertView;
//        }
//
//        class FaceHolder {
//            ImageView xc_jzh_face_item_imageview;
//        }
//    }
//
//    // 获取资产目录下的所有表情图片的路径
//    public List<String> getFaceUrl(int page) {
//        ArrayList<String> list = new ArrayList<String>(20);
//        for (int i = (page - 1) * PAGE_NUM + 1; i <= page * PAGE_NUM; i++) {
//            if (i < 10) {
//                list.add("assets://face/00" + i + ".png");
//            } else {
//                if (i > TOTAL_FACE_NUM) {
//                    return list;
//                }
//                list.add("assets://face/0" + i + ".png");
//            }
//        }
//        return list;
//    }


//    public static SpannableString string2FaceString(String message, Context paramContext) {
//        String temp;
//        if (message.indexOf("[") > 0 && message.indexOf("]") > 0) {
//            temp = message + " ";
//        } else {
//            temp = message;
//        }
//        Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
//
//        SpannableString value = SpannableString.valueOf(temp);
//
//        Matcher localMatcher = EMOTION_URL.matcher(value);
//
//        while (localMatcher.find()) {
//            String name = localMatcher.group(0);
//
//            int k = localMatcher.start();
//            int m = localMatcher.end();
//
//            Bitmap bitmap = getBitmap(paramContext, name);
//            if (bitmap != null) {
//                ImageSpan localImageSpan = new ImageSpan(paramContext, bitmap);
//                value.setSpan(localImageSpan, k, m, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//        return value;
//    }
//
//    /**
//     * 获取表情
//     */
//    public static Bitmap getBitmap(Context context, String name) {
//        try {
//            int length = name.length();
//            name = name.substring(1, length - 1);
//            InputStream inputStream = context.getAssets().open("face/" + name);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            return Bitmap.createScaledBitmap(bitmap, UtilScreen.dip2px(context, 24), UtilScreen.dip2px(context, 24), true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
