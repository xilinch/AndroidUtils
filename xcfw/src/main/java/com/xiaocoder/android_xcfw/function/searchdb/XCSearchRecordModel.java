package com.xiaocoder.android_xcfw.function.searchdb;

import java.io.Serializable;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCSearchRecordModel implements Serializable {

    private static final long serialVersionUID = 7806487539561624886L;

    private String key_word;
    private String time;

    @Override
    public String toString() {
        return "SearchRecordBean{" + "key_word='" + key_word + '\''
                + ", time='" + time + '\'' + '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public XCSearchRecordModel(String key_word, String time) {
        this.key_word = key_word;
        this.time = time;
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public XCSearchRecordModel() {
    }
}
