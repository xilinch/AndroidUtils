
package com.xiaocoder.android_test.stack;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.xiaocoder.android_ui.view.open.OPClearEditText;
import com.xiaocoder.android_xcfw.application.XCFragment;
import com.xiaocoder.android_xcfw.function.searchdb.XCSearchRecordModel;
import com.xiaocoder.android_xcfw.function.searchdb.XCSearchRecordModelDb;
import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilString;
import com.xiaocoder.android_xcfw.util.UtilView;
import com.xiaocoder.android_test.R;

import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
@Deprecated
public class TitleSearchFragment extends XCFragment implements View.OnClickListener {

    OPClearEditText xc_id_fragment_search_edittext;
    TextView xc_id_fragment_search_cancle;
    XCSearchRecordModelDb dao;

    boolean isClickeble = true;
    boolean isCancleButtonVisiable;
    String buttonText;

    public void setCancleButtonVisiable(boolean isCancleButtonVisiable, String text) {
        this.isCancleButtonVisiable = isCancleButtonVisiable;
        this.buttonText = text;
    }

    /**
     * 设置edittext是否可以点击
     */
    public void setIsClickble(boolean isClickeble) {

        this.isClickeble = isClickeble;

    }

    // 点击取消按钮时（有时可能不是取消，而是搜索）
    public interface OnClickCancleButtonListener {
        void clicked(String key_word);
    }

    public void setOnClickCancleButtonListener(OnClickCancleButtonListener canclelistener) {
        this.canclelistener = canclelistener;
    }

    OnClickCancleButtonListener canclelistener;

    // 当eidttext整个被点击的时候
    public interface OnEditTextClickedListener {
        void clicked();
    }

    // 当键盘的搜索的按钮点击时
    public interface OnKeyBoardSearchListener {
        void searchKeyDown(String key_word);
    }

    public void setOnEditTextClicklistener(OnEditTextClickedListener clicklistener) {
        this.clicklistener = clicklistener;
    }

    public void setOnPressSearchlistener(OnKeyBoardSearchListener searchlistener) {
        this.searchlistener = searchlistener;
    }

    OnEditTextClickedListener clicklistener;
    OnKeyBoardSearchListener searchlistener;

    int item_layout_id;

    public void setItem_layout_id(int item_layout_id) {
        this.item_layout_id = item_layout_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (item_layout_id == 0) {
            return init(inflater, R.layout.fragment_bar_title_search_history);
        } else {
            return init(inflater, item_layout_id);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.xc_id_fragment_search_cancle) {
            if (getActivity().getCurrentFocus() != null) {
                if (getActivity().getCurrentFocus().getWindowToken() != null) {
                    // 先隐藏键盘
                    ((InputMethodManager) xc_id_fragment_search_edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
            if (canclelistener != null) {
                String keyword = xc_id_fragment_search_edittext.getText().toString().trim();
                if ("".equals(keyword)) {
                    XCLog.shortToast("关键字不能为空");
                    return;
                }

                if ("搜索".equals(xc_id_fragment_search_cancle.getText().toString().trim())) {
                    save(keyword);
                }
                canclelistener.clicked(keyword);
            } else {
                finishActivity();
            }
        } else if (id == R.id.xc_id_fragment_search_edittext) {
            if (clicklistener != null) {
                clicklistener.clicked();
            }
        }
    }

    public int mRecoderNumMax = 10;

    /**
     * 设置显示的最大记录数
     *
     * @param recoderNumMax
     */
    public void setRecoderNumMax(int recoderNumMax) {
        mRecoderNumMax = recoderNumMax;
    }

    public void save(String keyword) {

        if (UtilString.isBlank(keyword)) {
            return;
        }

        checkRecoderExistAndSave(keyword);

        checkRecoderMaxNum();
    }

    private void checkRecoderMaxNum() {
        int count = dao.queryCount();

        if (count > mRecoderNumMax) {
            List<XCSearchRecordModel> xcSearchRecordModels = dao.queryAllByDESC();
            for (int i = mRecoderNumMax; i < count; i++) {
                XCSearchRecordModel model = xcSearchRecordModels.get(i);
                if (model != null) {
                    dao.deleteByTime(model.getTime());
                }
            }
        }
    }

    private void checkRecoderExistAndSave(String keyword) {

        List<XCSearchRecordModel> xcSearchRecordModels1 = dao.queryAllByDESC();
        boolean is_exist = false;
        for (XCSearchRecordModel model : xcSearchRecordModels1) {
            if (keyword.equals(model.getKey_word())) {
                is_exist = true;
                break;
            }
        }

        // 记录存入数据库
        if (!is_exist) {
            dao.insert(new XCSearchRecordModel(keyword, System.currentTimeMillis() + ""));
        }
    }

    public void setHint(String hint) {

        this.hint = hint;

    }

    String hint;

    String mTableName;

    /**
     * @param tabName 这个fragment用到该数据库中的哪一张表
     */
    public void setDbParams(String tabName) {
        mTableName = tabName;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidgets();
        listeners();
    }

    public void initWidgets() {
        xc_id_fragment_search_edittext = getViewById(R.id.xc_id_fragment_search_edittext);
        if (!isClickeble) {
            xc_id_fragment_search_edittext.setClickable(false);
            xc_id_fragment_search_edittext.setFocusable(false);
        }

        if (hint != null) {
            xc_id_fragment_search_edittext.setHint(hint);
        }

        xc_id_fragment_search_cancle = getViewById(R.id.xc_id_fragment_search_cancle);

        if (isCancleButtonVisiable) {
            UtilView.setGone(true, xc_id_fragment_search_cancle);
            xc_id_fragment_search_cancle.setText(buttonText);
        } else {
            UtilView.setGone(false, xc_id_fragment_search_cancle);
        }

        initDao();

    }

    public void initDao() {
        dao = new XCSearchRecordModelDb(getXCActivity(), mTableName);
    }

    public void listeners() {
        xc_id_fragment_search_cancle.setOnClickListener(this);
        xc_id_fragment_search_edittext.setOnClickListener(this);
        xc_id_fragment_search_edittext.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String keyword = xc_id_fragment_search_edittext.getText().toString().trim();
                    if ("".equals(keyword)) {
                        XCLog.shortToast("关键字不能为空");
                        return false;
                    }
                    if (getActivity().getCurrentFocus() != null) {
                        if (getActivity().getCurrentFocus().getWindowToken() != null) {
                            // 先隐藏键盘
                            ((InputMethodManager) xc_id_fragment_search_edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    save(keyword);
                    // 进入搜索结果页面
                    if (searchlistener != null) {
                        searchlistener.searchKeyDown(keyword);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean isBodyFragment() {
        return false;
    }
}
