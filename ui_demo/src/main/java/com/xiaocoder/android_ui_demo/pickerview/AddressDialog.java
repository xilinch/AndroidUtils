package com.xiaocoder.android_ui_demo.pickerview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xiaocoder.android_ui.view.sx.SXPickerView;
import com.xiaocoder.android_ui_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class AddressDialog extends Dialog {

    public LayoutInflater dialogInflater;

    public ViewGroup dialogLayout;

    public Context mContext;
    /**
     * 创建地址的 取消按钮
     */
    private TextView jy_id_cancle;
    /**
     * 创建地址的 完成按钮
     */
    private TextView jy_id_confirm;
    /**
     * 省份
     */
    private SXPickerView jy_id_address_province;
    /**
     * 城市
     */
    private SXPickerView jy_id_address_city;
    /**
     * 街区
     */
    private SXPickerView jy_id_address_distinct;

    private AddressModel mData;

    public interface OnAddressAction {
        void onCancle();

        void onConfirm();
    }

    private OnAddressAction onAddressAction;

    public void setOnAddressAction(OnAddressAction onAddressAction) {
        this.onAddressAction = onAddressAction;
    }

    public AddressDialog(Context context, AddressModel data) {
        super(context, com.xiaocoder.android_ui.R.style.xc_s_dialog);
        dialogInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        initDialog();
    }

    private List<SXPickerView.Item> getSelectedModels(List<SXPickerView.Item> addresses) {
        List<SXPickerView.Item> items = new ArrayList<>();

        for (SXPickerView.Item address : addresses) {

            SXPickerView.Item model = new SXPickerView.Item();
            model.setContent(address.getContent());
            model.setId(address.getId());
            items.add(model);
        }

        return items;
    }

    private SXPickerView.Item provinceModelSelected;
    private SXPickerView.Item cityModelSelected;
    private SXPickerView.Item distinctModelSelected;

    public SXPickerView.Item getProvinceModelSelected() {
        return provinceModelSelected;
    }

    public SXPickerView.Item getCityModelSelected() {
        return cityModelSelected;
    }

    public SXPickerView.Item getDistinctModelSelected() {
        return distinctModelSelected;
    }

    private void reset() {
        jy_id_address_province.setData(getSelectedModels(mData.getProvinces()));
        jy_id_address_province.setSelected(0);

        provinceChanged(mData.getProvinces().get(0));
    }

    private void provinceChanged(SXPickerView.Item  provinceModelSelected) {
        this.provinceModelSelected = provinceModelSelected;

        this.cityModelSelected = mData.getCities().get(getProvinceModelSelected().getId()).get(0);

        this.distinctModelSelected = mData.getDistrict().get(getCityModelSelected().getId()).get(0);

        updateCityUI();

        updateDistinctUI();
    }

    private void cityChanged(SXPickerView.Item  cityModelSelected) {
        this.cityModelSelected = cityModelSelected;

        this.distinctModelSelected = mData.getDistrict().get(getCityModelSelected().getId()).get(0);

        updateDistinctUI();
    }

    private void distinctChanged(SXPickerView.Item  distinctModelSelected) {
        this.distinctModelSelected = distinctModelSelected;
    }

    private void updateCityUI() {
        jy_id_address_city.setData(getSelectedModels(mData.getCities().get(getProvinceModelSelected().getId())));
        jy_id_address_city.setSelected(0);
    }

    private void updateDistinctUI() {
        jy_id_address_distinct.setData(getSelectedModels(mData.getDistrict().get(getCityModelSelected().getId())));
        jy_id_address_distinct.setSelected(0);
    }

    private void initDialog() {
        dialogLayout = (ViewGroup) dialogInflater.inflate(R.layout.dialog_choose_address, null);

        jy_id_cancle = (TextView) dialogLayout.findViewById(R.id.jy_id_cancle);
        jy_id_confirm = (TextView) dialogLayout.findViewById(R.id.jy_id_confirm);
        jy_id_address_province = (SXPickerView) dialogLayout.findViewById(R.id.jy_id_address_province);
        jy_id_address_city = (SXPickerView) dialogLayout.findViewById(R.id.jy_id_address_city);
        jy_id_address_distinct = (SXPickerView) dialogLayout.findViewById(R.id.jy_id_address_town);

        listeners();
        reset();
        setContentView(dialogLayout);
        setWindowLayoutStyleAttr();
    }

    private void listeners() {
        jy_id_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAddressAction != null) {
                    onAddressAction.onCancle();
                }
            }
        });
        jy_id_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAddressAction != null) {
                    onAddressAction.onConfirm();
                }
            }
        });
        jy_id_address_province.setOnSelectListener(new SXPickerView.onSelectListener() {
            @Override
            public void onSelect(SXPickerView.Item s) {
                provinceChanged(s);
            }
        });
        jy_id_address_city.setOnSelectListener(new SXPickerView.onSelectListener() {
            @Override
            public void onSelect(SXPickerView.Item s) {
                cityChanged(s);
            }
        });
        jy_id_address_distinct.setOnSelectListener(new SXPickerView.onSelectListener() {
            @Override
            public void onSelect(SXPickerView.Item s) {
                distinctChanged(s);
            }
        });
    }

    public void setWindowLayoutStyleAttr() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.7f;
        lp.dimAmount = 0.3f;
        lp.gravity = Gravity.BOTTOM;
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.address_dialog);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

}
