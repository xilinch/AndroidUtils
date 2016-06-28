package com.xiaocoder.android_test.stack;

import android.os.Bundle;

import com.xiaocoder.android_xcfw.function.searchdb.XCSearchRecordModel;
import com.xiaocoder.android_xcfw.function.searchdb.XCSearchRecordModelDb;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilActivity;
import com.xiaocoder.android_test.R;
import com.xiaocoder.android_test_middle.base.BaseActivity;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class SearchActivity extends BaseActivity {

    // 搜索历史界面
    SearchRecordFragment record_fragment;
    // 搜索title
    TitleSearchFragment title_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initWidgets();
        setListeners();
    }

    public void initWidgets() {
        title_fragment = new TitleSearchFragment();
        title_fragment.setDbParams(XCSearchRecordModelDb.TABLE_1);
        addFragment(R.id.xc_id_model_titlebar, title_fragment);
    }

    public void setListeners() {
        // 点击edittext输入框 , 则弹出键盘和历史记录的背景
        title_fragment.setOnEditTextClicklistener(new TitleSearchFragment.OnEditTextClickedListener() {
            @Override
            public void clicked() {
                // 点击显示记录 , clicked()仅监听 从隐藏状态 -- > 显示状态

                // 为空则创建并设置监听 , record_fragment里面的监听器可以监听键盘的显示到隐藏的状态
                if (record_fragment == null) {
                    record_fragment = new SearchRecordFragment();
                    record_fragment.setDbParams(XCSearchRecordModelDb.TABLE_1);

                    // 点击键盘中的隐藏键盘按钮
                    record_fragment.setOnKeyBoardStatusListener(new SearchRecordFragment.OnKeyBoardStatusListener() {
                        @Override
                        public void onStatusChange(boolean is_key_board_show) {
                            // onStatusChange()仅监听 从显示状态 -- > 隐藏状态 , 关闭记录页面
                            if (!is_key_board_show) {
                                hideFragment(record_fragment);
                            }
                            XCLog.shortToast("change");
                        }
                    });

                    record_fragment.setOnRecordItemClickListener(new SearchRecordFragment.OnRecordItemClickListener() {
                        @Override
                        public void onRecordItemClickListener(XCSearchRecordModel model, String key_word, int position) {
                            XCLog.shortToast(key_word);
                            UtilActivity.startActivity(SearchActivity.this, SearchActivity2.class);
                        }
                    });
                    addFragment(R.id.xc_id_model_content, record_fragment);
                    return;
                }
                // 不为空 , 则显示记录页面 ,隐藏搜索页面
                if (record_fragment.isHidden()) {
                    showFragment(record_fragment);
                }
            }
        });

        // 点击键盘的搜索按钮 , 会关闭键盘, 然后开启一个搜索结果的activity
        title_fragment.setOnPressSearchlistener(new TitleSearchFragment.OnKeyBoardSearchListener() {
            @Override
            public void searchKeyDown(String key_word) {
                XCLog.shortToast(key_word);
                UtilActivity.startActivity(SearchActivity.this, SearchActivity2.class);
            }
        });
    }


}
