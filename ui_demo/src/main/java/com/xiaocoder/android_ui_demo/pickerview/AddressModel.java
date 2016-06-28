package com.xiaocoder.android_ui_demo.pickerview;

import com.xiaocoder.android_ui.view.sx.SXPickerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class AddressModel {

    /**
     * "code":"1",
     * "msg":"",
     * "datas":{
     * "name":"\u6fb3\u95e8\u7279\u522b\u884c\u653f\u533a",
     * "p_id":"820000",
     * "city":[
     * {
     * "c_id":"650100",
     * "name":"\u4e4c\u9c81\u6728\u9f50\u5e02",
     * "district":[
     * {
     * "d_id":"650101",
     * "name":"\u5e02\u8f96\u533a"
     * }
     **/

    private ArrayList<SXPickerView.Item> provinces = new ArrayList<>();
    /**
     * String是provinceid
     */
    private Map<String, List<SXPickerView.Item>> cities = new LinkedHashMap<>();
    /**
     * String是cityid
     */
    private Map<String, List<SXPickerView.Item>> district = new LinkedHashMap<>();

    public ArrayList<SXPickerView.Item> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<SXPickerView.Item> provinces) {
        this.provinces = provinces;
    }

    public Map<String, List<SXPickerView.Item>> getCities() {
        return cities;
    }

    public void setCities(Map<String, List<SXPickerView.Item>> cities) {
        this.cities = cities;
    }

    public Map<String, List<SXPickerView.Item>> getDistrict() {
        return district;
    }

    public void setDistrict(Map<String, List<SXPickerView.Item>> district) {
        this.district = district;
    }

}


