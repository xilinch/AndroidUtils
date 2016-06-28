package com.xiaocoder.android_ui_demo.pickerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xiaocoder.android_ui.view.sx.SXPickerView;
import com.xiaocoder.android_ui_demo.R;
import com.xiaocoder.android_xcfw.io.XCIOAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class PickerViewActivity extends AppCompatActivity {

    private AddressDialog dialog;

    public static final String NO_ADDRESS = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_view);

        dialog = new AddressDialog(this, getAddressData());

        dialog.show();

    }

    private AddressModel getAddressData() {
        try {
            JSONObject obj = new JSONObject(XCIOAndroid.getStringFromRaw(this,R.raw.citydata));

            AddressModel model = new AddressModel();

            JSONArray provinces = obj.optJSONArray("data");

            if (provinces != null && provinces.length() > 0) {

                for (int provinceNum = 0; provinceNum < provinces.length(); provinceNum++) {
                    // 解析省份

                    JSONObject province = provinces.getJSONObject(provinceNum);

                    String provinceId = province.getString("p_id");
                    String provinceName = province.getString("name");

                    model.getProvinces().add(new SXPickerView.Item(provinceId, provinceName));

                    JSONArray cities = province.optJSONArray("city");

                    List<SXPickerView.Item> citiesList = new ArrayList<>();

                    if (cities != null && cities.length() > 0) {

                        //解析城市
                        for (int cityNum = 0; cityNum < cities.length(); cityNum++) {
                            JSONObject city = cities.getJSONObject(cityNum);

                            String cityId = city.getString("c_id");
                            String cityName = city.getString("name");
                            citiesList.add(new SXPickerView.Item(cityId, cityName));

                            JSONArray districts = city.optJSONArray("district");

                            List<SXPickerView.Item> districtsList = new ArrayList<>();

                            if (districts != null && districts.length() > 0) {

                                for (int districtsNum = 0; districtsNum < districts.length(); districtsNum++) {

                                    JSONObject distinct = districts.getJSONObject(districtsNum);

                                    String distinctId = distinct.getString("d_id");
                                    String distinctName = distinct.getString("name");
                                    districtsList.add(new SXPickerView.Item(distinctId, distinctName));
                                }
                                model.getDistrict().put(cityId, districtsList);
                            } else {
                                districtsList.add(new SXPickerView.Item(NO_ADDRESS, NO_ADDRESS));
                                model.getDistrict().put(cityId, districtsList);
                            }
                        }
                        model.getCities().put(provinceId, citiesList);
                    } else {
                        // 当城市为空的时候，给个填充的符号
                        SXPickerView.Item city = new SXPickerView.Item(NO_ADDRESS, NO_ADDRESS);
                        citiesList.add(city);
                        model.getCities().put(provinceId, citiesList);
                        // 城市为空的时候，区也为空
                        List<SXPickerView.Item> districtsList = new ArrayList<>();
                        SXPickerView.Item distinct = new SXPickerView.Item(NO_ADDRESS, NO_ADDRESS);
                        districtsList.add(distinct);
                        model.getDistrict().put(city.getId(), districtsList);
                    }
                }
            }
            return model;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
