package com.xiaocoder.android_xcfw.application;


import com.xiaocoder.android_xcfw.io.XCIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 1 提供了序列化id 2 浅克隆的方法 3 深克隆的方法
 */
public abstract class XCModel implements Serializable, Cloneable {

    protected static final long serialVersionUID = 2161633826093329317L;

    protected final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * 浅克隆
     */
    public Object simpleClone() {
        return clone();
    }

    /**
     * 深克隆
     */
    public Object deepClone() {
        return XCIO.deepClone(this);
    }
}
