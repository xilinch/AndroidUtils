package com.example.app.sample.activity;

import android.os.Bundle;

import com.example.app.sample.db.SampleDb;
import com.example.app.sample.model.SampleDbModel;
import com.example.middle.base.BaseActivity;
import com.xiaocoder.android_xcfw.function.helper.XCAppHelper;

import java.util.ArrayList;

public class SampleDbActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * db
     */
    public void db() {

        SampleDb instance = SampleDb.getInstance(XCAppHelper.getAppContext());

        instance.queryCount();

        instance.queryAllByIdAsc();

        SampleDbModel model = new SampleDbModel("test", "test", "test", "test", "test", "test");

        instance.insert(model);

        instance.inserts(getData());

        instance.updateThree(model, model.getName());

        instance.deleteTwo(model.getName());
    }

    private ArrayList<SampleDbModel> getData() {
        ArrayList<SampleDbModel> list = new ArrayList<>();
        list.add(new SampleDbModel("10abcdefghijklmn", "小明10", "0", "70", "92", "篮球"));
        list.add(new SampleDbModel("3abcdefghijklmn", "小红3", "1", "35", "70", "篮球"));
        list.add(new SampleDbModel("9abcdefghijklmn", "小红9", "1", "65", "66", "篮球"));
        list.add(new SampleDbModel("0abcdefghijklmn", "小明0", "0", "18", "50", "篮球"));
        list.add(new SampleDbModel("1abcdefghijklmn", "小红1", "1", "25", "60", "篮球"));
        list.add(new SampleDbModel("4abcdefghijklmn", "小明4", "0", "40", "97", "篮球"));
        list.add(new SampleDbModel("14abcdefghijklmn", "小明14", "0", "90", "34", "篮球"));
        list.add(new SampleDbModel("5abcdefghijklmn", "小红5", "1", "45", "40", "排球"));
        list.add(new SampleDbModel("2abcdefghijklmn", "小明2", "0", "30", "90", "足球"));
        list.add(new SampleDbModel("6abcdefghijklmn", "小明6", "0", "50", "20", "网球"));
        list.add(new SampleDbModel("12abcdefghijklmn", "小明12", "0", "80", "77", "台球"));
        list.add(new SampleDbModel("8abcdefghijklmn", "小明8", "0", "60", "86", "排球"));
        list.add(new SampleDbModel("13abcdefghijklmn", "小红13", "1", "85", "57", "台球"));
        list.add(new SampleDbModel("11abcdefghijklmn", "小红11", "1", "75", "99", "足球"));
        list.add(new SampleDbModel("7abcdefghijklmn", "小红7", "1", "55", "67", "网球"));
        return list;
    }
}
