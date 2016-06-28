package com.xiaocoder.android_xcfw.json;

import com.xiaocoder.android_xcfw.io.XCLog;
import com.xiaocoder.android_xcfw.util.UtilString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description
 */
public class XCJsonParse {

    /**
     * 该tag可以查看json返回的字段的数据类型
     */
    public static final String TAG_JSON_TYPE = "JsonType";
    /**
     * 列出json返回的字段，并以假bean的形式打印到控制台，复制粘贴后可创建一个假bean类，假bean类里有字符串常量和get方法set方法
     * <p/>
     * 注：假bean类不在用了， 现在用studio的gsonformat插件，自动生成一个真model
     */
    public static final String TAG_JSON_BEAN = "JsonBean";

    /**
     * { } 解析json对象
     * <p/>
     * 注：解析失败一定得返回null
     */
    public static <T extends XCJsonBean> T getJsonParseData(String json, Class<T> beanClass) {

        try {
            if (!UtilString.isBlank(json)) {
                T result = getBean(beanClass);
                JSONObject obj = new JSONObject(json);
                return parse(result, obj, beanClass);
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static XCJsonBean getJsonParseData(String json) {

        return getJsonParseData(json, XCJsonBean.class);

    }

    /**
     * [ ] 解析json数组
     */
    public static <T extends XCJsonBean> List<T> getJsonListParseData(String json, Class<T> beanClass) {
        try {
            List<T> list = new ArrayList<T>();
            JSONArray array = new JSONArray(json);
            int size = array.length();
            for (int i = 0; i < size; i++) {
                JSONObject json_object = array.getJSONObject(i);
                if (json_object != null) {
                    T bean = getJsonParseData(json_object.toString(), beanClass);
                    if (bean == null) {
                        // 表示有解析失败的json
                        return null;
                    }
                    list.add(bean);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * [ ] 解析json数组
     */
    public static List<XCJsonBean> getJsonListParseData(String json) {
        return getJsonListParseData(json, XCJsonBean.class);
    }

    /**
     * 创建一个空的XCJsonBean对象
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    private static <T extends XCJsonBean> T getBean(Class<T> beanClass) {
        try {
            Constructor<T> con = beanClass.getConstructor();
            return con.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析
     *
     * @param result
     * @param obj
     * @param beanClass
     * @param <T>
     */
    private static <T extends XCJsonBean> T parse(T result, JSONObject obj, Class<T> beanClass) {
        try {
            Object o, object;
            JSONArray array;
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                o = obj.get(key);
                if (o instanceof JSONObject) {
                    T bean = getBean(beanClass);
                    parse(bean, (JSONObject) o, beanClass);
                    result.add(key, bean);
                } else if (o instanceof JSONArray) {
                    array = (JSONArray) o;
                    List list = new ArrayList();
                    result.add(key, list);
                    for (int i = 0; i < array.length(); i++) {
                        object = array.get(i);
                        if (object instanceof JSONObject) {
                            T bean = getBean(beanClass);
                            parse(bean, (JSONObject) object, beanClass);
                            list.add(bean);
                        } else {
                            list.add(object);
                        }
                    }
                } else {
                    if (XCLog.isOutput) {
                        if (o instanceof Boolean) {
                            XCLog.i(TAG_JSON_TYPE, key.toString() + "---->" + o.toString() + "----is boolean");
                        } else if (o instanceof Integer) {
                            XCLog.i(TAG_JSON_TYPE, key.toString() + "---->" + o.toString() + "----is Integer");
                        } else if (o instanceof String) {
                            XCLog.i(TAG_JSON_TYPE, key.toString() + "---->" + o.toString() + "----is String");
                        } else if (o instanceof Long) {
                            XCLog.i(TAG_JSON_TYPE, key.toString() + "---->" + o.toString() + "----is Long");
                        } else if (o instanceof Double) {
                            XCLog.i(TAG_JSON_TYPE, key.toString() + "---->" + o.toString() + "----is Double");
                        } else {
                            XCLog.i(TAG_JSON_TYPE, key.toString() + "---->" + o.toString() + "----is Else Type");
                        }
                    }
                    result.add(key, o);
                }
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建假bean类 , 这里只是打印接口字段的常量出来了而已,然后复制粘贴字段
     */
    public static void json2Bean(String json) {

        if (XCLog.isOutput && json != null) {
            LinkedHashSet<String> set = new LinkedHashSet<String>();
            json = json.replace("\"", "");
            json = json.replace(" ", "");
            json = json.replace("{", "{,");
            json = json.replace("[", "[,");
            String[] items = json.split(",");
            StringBuilder builder = new StringBuilder("");

            builder.append("public class  文件名  extends MBean {");

            LinkedHashSet<String> sub = new LinkedHashSet<String>();

            for (String item : items) {

                //可能中文的语句之间有，
                if (!item.contains(":")) {
                    continue;
                }

                String[] keyvalues = item.split(":");

                if (UtilString.isBlank(keyvalues[0]) || set.contains(keyvalues[0])) {
                    // key为空  或者  已经加入了集合,则返回
                    continue;
                } else {
                    // 未加入集合
                    if (keyvalues[1] != null && (keyvalues[1].contains("[") || keyvalues[1].contains("{"))) {
                        // 表示该字段是 如  list：{}  ， list：[]  set 和 get方法不需要该字段，先记录这些字段
                        sub.add(keyvalues[0]);
                    }
                    set.add(keyvalues[0]);
                    builder.append("public String " + keyvalues[0].trim() + "=" + "\"" + keyvalues[0].trim() + "\"" + ";");
                }
            }

            // 把get 与 set 不需要的key删除
            for (String sub_key : sub) {
                set.remove(sub_key);
            }

            for (String key : set) {

                builder.append("public String get")
                        .append(UtilString.setFirstLetterBig(key))
                        .append("() { return getString(" + key + ");")
                        .append("}");

                builder.append("public void set")
                        .append(UtilString.setFirstLetterBig(key))
                        .append("( Object value) { add(" + key + " , value);")
                        .append("}");

            }

            builder.append("}");

            XCLog.i(TAG_JSON_BEAN, builder.toString());
        }
    }

    public static void getJsonFiled(byte[] bytes) {

        try {
            String json = new String(bytes, "utf-8");
            json = json.replace("\"", "");
            String[] items = json.split(",");

            StringBuilder sb = new StringBuilder();

            for (String item : items) {
                String[] keyvalues = item.split(":");
                sb.append("bean." + keyvalues[0].trim() + "= jsonObj.getString(\"" + keyvalues[0].trim() + "\");");
            }

            XCLog.i(TAG_JSON_BEAN, sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static final String TAB = "\t\t";
    public static final String RETURN = "\n";

    public static String format(String jsonString) {

        try {

            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            boolean isInQuotationMark = false;
            String currentTab = "";

            for (int i = 0; i < jsonString.length(); i++) {

                String c = jsonString.substring(i, i + 1);

                switch (c) {

                    case "\"": {
                        if (i > 0) {
                            if (jsonString.charAt(i - 1) != '\\') {
                                isInQuotationMark = !isInQuotationMark;
                            }
                            sb.append(c);
                        }
                        break;
                    }

                    case "[":
                    case "{": {
                        if (!isInQuotationMark) {
                            currentTab += TAB;
                            sb.append(c);
                            sb.append(RETURN + currentTab);
                        } else {
                            sb.append(c);
                        }
                        break;
                    }

                    case "]":
                    case "}": {
                        if (!isInQuotationMark) {
                            currentTab = currentTab.substring(TAB.length());
                            sb.append(RETURN + currentTab);
                            sb.append(c);
                            sb.append(RETURN);
                        } else {
                            sb.append(c);
                        }

                        break;
                    }
                    case ",": {

                        if (!isInQuotationMark) {
                            sb.append(c);
                            sb.append(RETURN + currentTab);
                        } else {
                            sb.append(c);
                        }

                        break;
                    }
                    default: {
                        sb.append(c);
                    }
                }
            }
            return sb.toString();

        } catch (Exception e) {
            XCLog.e("格式化json异常----jsonformat---" + jsonString, e);
            return jsonString;
        }
    }

}
