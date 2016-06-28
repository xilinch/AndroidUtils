package com.example.app.sample.model;

import com.example.middle.http.IHttpRespInfo;

/**
 * @author xiaocoder on 2016/6/27 16:54
 * @email fengjingyu@foxmail.com
 * @description 模拟http返回解析的model
 */
public class SampleHttpModel implements IHttpRespInfo {

    private String code = "";
    private String msg = "";

    private String name = "";

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SampleHttpModel{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleHttpModel that = (SampleHttpModel) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (msg != null ? !msg.equals(that.msg) : that.msg != null) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + name.hashCode();
        return result;
    }
}
