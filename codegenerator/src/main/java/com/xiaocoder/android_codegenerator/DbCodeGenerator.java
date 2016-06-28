package com.xiaocoder.android_codegenerator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 1 model中的static的字段不会生成在db类中
 * 2 @ignore可以忽略model的某一个字段不生成在db类中
 * 3 默认会生成 insert（存入一个） inserts(批量存入) deleteAll(删除所有) queryCount(查询所有数量) queryAll（查询所有） queryPage（分页）方法
 * 4 model的字段之上的注释（仅多行注释）会生成在db类中
 * 5 生成的db类默认是单例的，且每个方法是线程安全的
 * 6 通过配置@query @update @delete可以生成对应的删查改方法
 */
public class DbCodeGenerator {
    public static String LINE = System.getProperty("line.separator");
    /**
     * 创建文件的类名 = modelName+“。db”
     */
    public static String fileName = "";
    /**
     * model名
     */
    public static String modelName = "";

    public static File file;

    public static LinkedHashMap<String, String> mapFileds = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> getMethods = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> setMethods = new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> mapComments = new LinkedHashMap<String, String>();
    public static ArrayList<LinkedHashMap<String, String>> querysAnnotationListMap = new ArrayList<LinkedHashMap<String, String>>();
    public static ArrayList<LinkedHashMap<String, String>> updateAnnotationListMap = new ArrayList<LinkedHashMap<String, String>>();
    public static ArrayList<LinkedHashMap<String, String>> deleteAnnotationListMap = new ArrayList<LinkedHashMap<String, String>>();

    public static final String UPDATES = "@Updates";
    public static final String UPDATE = "@Update";
    public static final String DELETES = "@Deletes";
    public static final String DELETE = "@Delete";
    public static final String QUERYS = "@Querys";
    public static final String QUERY = "@Query";

    public static final String ANNOTATION_END = "})";
    public static final String ANNOTATION_START = "({";

    public static final String METHOD_NAME = "methodName";
    public static final String COLUMNS = "columns";
    public static final String WHERE = "where";
    public static final String ORDER_BY = "orderBy";
    public static final String LIMIT = "limit";
    public static final String GROUP_BY = "groupBy";
    public static final String HAVING = "having";

    public static JFrame frame;
    public static JButton button;
    public static JTextArea area;
    public static JTextField area_title;
    public static JScrollPane scrollPane;
    public static String ENCODING = "utf-8";

    public static void main(String[] args) {
        initUI();
    }

    private static void initUI() {
        frame = new JFrame("生成android sql文件");
        button = new JButton("开始");
        area = new JTextArea(50, 100);
        area_title = new JTextField("请输入Model的绝对路径,该model必须有一个无参的构造器, androidStudio为ctrl+shift+c", 100);
        scrollPane = new JScrollPane(area);
        frame.setBounds(150, 50, 1050, 650);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(button, BorderLayout.EAST);
        frame.add(area_title, BorderLayout.NORTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area.setText("输出结果");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                reset();

                String content = getModelString(area_title.getText().toString()
                        .trim());

                getClassName();

                getClassField(content);

                String result = getDbResultString();

                area.setText("");

                area.setText(result);
            }
        });
    }

    private static void reset() {
        fileName = "";
        modelName = "";
        mapFileds.clear();
        getMethods.clear();
        setMethods.clear();
        mapComments.clear();
        querysAnnotationListMap.clear();
        updateAnnotationListMap.clear();
        deleteAnnotationListMap.clear();
    }

    private static String getDbResultString() {

        String temp = getClassNameStatement()
                + "{\n"
                + "\n"
                + getDbNameFieldStatement()
                + "\n"
                + getDbVersionFieldStatement()
                + "\n"
                + getTableFieldStatement()
                + "\n"
                + "\n"
                + getSortFieldStatement()
                + getSqlFieldsString()
                + "\n"
                + getSingleInstance()
                + getConstructMethodStatement()
                + "\n"
                + getOnCreateMethodStatement()
                + "\n"
                + getUpdateMethodStatement()
                //+ getReflectConstructMethodStatement()
                + getContentValueMethodStatement()
                + "\n"
                + createModel()
                + "\n"
                // 至少会有这些默认的方法
                + insert()
                + inserts()
                + deleteAll()
                + queryCount()
                + queryAllByIdDesc()
                + queryAllByIdAsc()
                + queryPageByIdAsc()
                + queryPageByIdDesc()

                // 根据是否有注释生成的
                + createDeletesMethod()
                + createUpdateMethod()
                + createQueryMethod()
                + "}";

        return temp;

    }


    private static String createQueryMethod() {
        StringBuilder sb = new StringBuilder();
        for (LinkedHashMap<String, String> map : querysAnnotationListMap) {
            String methodName = map.get(METHOD_NAME);
            String columns = map.get(COLUMNS);
            String where = map.get(WHERE);
            String orderBy = map.get(ORDER_BY);
            String limit = map.get(LIMIT);
            String groupBy = map.get(GROUP_BY);
            String having = map.get(HAVING);
            String whereArgs = null;
            String args = null;

            columns = getColumns(columns);

            where = getWhere(where);

            orderBy = getOrderBy(orderBy);

            limit = getParamString(limit);

            groupBy = getParamString(groupBy);

            having = getParamString(having);

            int times = countTimes(where, "?");

            args = getArgs(args, times);

            whereArgs = getWhereArgs(whereArgs, times);

            sb.append(query(methodName, columns, where, whereArgs, args, orderBy, limit, groupBy, having));
        }
        return sb.toString();
    }

    private static String getColumns(String columns) {
        if (columns != null && !"null".equals(columns)) {
            columns = columns.trim();
            for (Entry<String, String> entry : mapFileds.entrySet()) {
                if (columns.contains(entry.getValue())) {
                    columns = columns.replace(entry.getValue(), entry.getKey().toUpperCase());
                }
            }
            return "new String[] { " + columns + "}";
        } else {
            return null;
        }
    }

    private static String getWhereArgs(String whereArgs, int times) {
        if (times > 0) {
            whereArgs = "";
            for (int i = 0; i < times; i++) {
                whereArgs = whereArgs + "value" + (i + 1) + ",";
            }
            whereArgs = justDeleteLastSymbol(whereArgs, ",");
            whereArgs = "new String[]{ " + whereArgs + " }";
        }
        return whereArgs;
    }

    private static String getArgs(String args, int times) {
        if (times > 0) {
            args = "";
            for (int i = 0; i < times; i++) {
                args = args + "String value" + (i + 1) + ",";
            }
            args = justDeleteLastSymbol(args, ",");
        }
        return args;
    }

    private static String getParamString(String limit) {
        if (limit != null && !"null".equals(limit)) {
            limit = "\"" + limit + "\"";
        } else {
            limit = null;
        }
        return limit;
    }

    private static String createUpdateMethod() {
        StringBuilder sb = new StringBuilder();
        for (LinkedHashMap<String, String> map : updateAnnotationListMap) {
            String methodName = map.get(METHOD_NAME);
            String where = map.get(WHERE);
            String whereArgs = null;
            String args = null;

            where = getWhere(where);

            int times = countTimes(where, "?");

            args = getArgs(args, times);

            whereArgs = getWhereArgs(whereArgs, times);

            if (null == where) {
                // 更新如果两个都为空，则没意义了
                continue;
            }

            sb.append(update(methodName, where, whereArgs, args));
        }
        return sb.toString();
    }

    private static String createDeletesMethod() {
        StringBuilder sb = new StringBuilder();
        for (LinkedHashMap<String, String> map : deleteAnnotationListMap) {
            String methodName = map.get(METHOD_NAME);
            String where = map.get(WHERE);
            String whereArgs = null;
            String args = null;

            where = getWhere(where);

            int times = countTimes(where, "?");

            args = getArgs(args, times);

            whereArgs = getWhereArgs(whereArgs, times);

            sb.append(delete(methodName, where, whereArgs, args));
        }
        return sb.toString();
    }

    // abc = ? and bcd = 1 的字符串改为  ABC +"= ? and "+ BCD +" =1"
    // abc = ?
    // abc = ? and bcd = 1
    // "abc" = ?           "abc" = ? and "bcd" = 1
    // "+ABC+" = ?        "+ABC+"= ? and "+BCD+" =1
    // "+ABC+" = ?"	      "+ABC+"= ? and "+BCD+" =1"
    //  ABC + " = ?"        ABC+"= ? and "+BCD+" =1"
    private static String getWhere(String where) {
        if (where != null && !"null".equals(where)) {
            where = where.trim();
            for (Entry<String, String> entry : mapFileds.entrySet()) {
                if (where.contains(entry.getValue())) {
                    where = where.replace(entry.getValue(), "\"+" + entry.getKey().toUpperCase() + "+\"");
                }
            }
            where = where + "\"";
            return getAfterFirstSimbolString(where, "+");

        }
        return null;

    }

    // abc desc
    // ABC DESC
    private static String getOrderBy(String orderBy) {
        if (orderBy != null && !"null".equals(orderBy)) {
            orderBy = orderBy.trim();
            for (Entry<String, String> entry : mapFileds.entrySet()) {
                if (orderBy.contains(entry.getValue())) {
                    orderBy = orderBy.replace(entry.getValue(), entry.getKey().toUpperCase());
                }
            }

            if (orderBy.contains("_id")) {
                orderBy = orderBy.replace("_id", "_ID");
            }

            if (orderBy.contains(" desc")) {
                orderBy = orderBy.replace(" desc", " + SORT_DESC");
            } else if (orderBy.contains(" asc")) {
                orderBy = orderBy.replace(" asc", " + SORT_ASC");
            }
            return orderBy;

        }
        return null;

    }

    private static String delete(String methodName, String where, String whereArgs, String args) {

        StringBuilder sb = new StringBuilder("\n");
        if (args == null) {
            sb.append("public" + isSync() + "int " + methodName + " () {\n");
        } else {
            sb.append("public" + isSync() + "int " + methodName + " (" + args + ") {\n");
        }
        sb.append("SQLiteDatabase db = getWritableDatabase();\n");
        sb.append("int rows = db.delete(mOperatorTableName, " + where + " ," + whereArgs + ");\n");
        sb.append("	//XCLog.i(XCConfig.TAG_DB, \"" + methodName + "()-->\" + rows + \"行\");\n");
        sb.append("db.close();\n");
        sb.append("return rows;\n}\n");
        return sb.toString();
    }


    private static String update(String methodName, String where, String whereArgs, String args) {

        StringBuilder sb = new StringBuilder("\n");
        if (null == args) {
            sb.append("public" + isSync() + "int " + methodName + " (" + modelName
                    + " model) {\n");
        } else {
            sb.append("public" + isSync() + "int " + methodName + " (" + modelName
                    + " model," + args + ") {\n");
        }
        sb.append("SQLiteDatabase db = getWritableDatabase();\n");
        sb.append("ContentValues values = createContentValue(model);\n");
        sb.append("int rows = db.update(mOperatorTableName, values, " + where + " , " + whereArgs + ");\n");
        sb.append("	//XCLog.i(XCConfig.TAG_DB,\"" + methodName + "()更新了\" + rows + \"行\");\n");
        sb.append("db.close();\n");
        sb.append("return rows;\n}\n");
        return sb.toString();

    }

    private static String query(String methodName, String columns, String where, String whereArgs, String args,
                                String orderBy, String limit, String groupBy, String having) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(" public" + isSync() + "List<" + modelName
                + "> " + methodName);

        if (null == args) {
            sb.append("(){\n");
        } else {
            sb.append("(" + args + "){\n");
        }

        sb.append("SQLiteDatabase db = getReadableDatabase();\n");

        sb.append("Cursor c = db.query(mOperatorTableName, " + columns + "," + where + ", " + whereArgs + ", " + groupBy + ", " + having + ", " + orderBy + " ," + limit + ");\n");


        sb.append("List<" + modelName + "> beans = new ArrayList<"
                + modelName + ">();\n");
        sb.append("while (c.moveToNext()) {\n");
        sb.append(modelName + " bean = createModel(c);\n");
        sb.append("beans.add(bean);\n");
        sb.append("}\n");
        sb.append("c.close();\n");
        sb.append("db.close();\n");
        sb.append("return beans;\n}\n");
        return sb.toString();

    }

    public static String isSync() {
        if (true) {
            return " synchronized ";
        } else {
            return " ";
        }
    }

    private static String getReflectConstructMethodStatement() {
        return "    public static "
                + fileName
                + " instanceDb(Context context, Class<? extends SQLiteOpenHelper> dbClazz, String dbName\n"
                +
                "                                           ) {\n"
                + "        try {\n"
                +
                "            // XCLog.i(XCConfig.TAG_DB, \"dbClazz----instanceDb()\");\n"
                +
                "            Constructor constructor = dbClazz.getConstructor(Context.class, String.class);\n"
                +
                "            Object o = constructor.newInstance(context, dbName);\n"
                + "            return (" + fileName + ") o;\n"
                + "        } catch (Exception e) {\n"
                + "            e.printStackTrace();\n"
                + "            // XCLog.e(context, \"\", e);\n"
                + "            return null;\n" + "        }\n" + "    }\n";
    }

    private static String getOnCreateMethodStatement() {
        return "    @Override\n"
                + "    public void onCreate(SQLiteDatabase db) {\n"
                + "\n"
                + getSql()
                + "\n"
                + "    }\n";
    }

    private static String getUpdateMethodStatement() {
        return "    @Override\n"
                + "    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {\n\n"
                + "    }\n";
    }

    private static String getConstructMethodStatement() {
        return "    private "
                + fileName
                + " (Context context) {\n"
                + "        super(context, mDefaultDbName, null, mVersion);\n"
//                + "        if (UtilString.isBlank(dbName)) {\n"
//                + "            throw new RuntimeException(\"数据库名不能为空\");\n"
//                + "        }\n"
//                + "\n"
//                + "        if (operatorTableName == null || operatorTableName.length() < 1) {\n"
//                + "            throw new RuntimeException(\"操作的表名不能为空\");\n"
//                + "        }\n"
//                + "        mDbName = dbName;\n"
//                + "        mVersion = version;\n"
//                + "        mOperatorTableName = operatorTableName;\n"
                + "    }\n\n"
                + "    private "
                + fileName
                + " (Context context,String dbName) {\n"
                + "        super(context, dbName, null, mVersion);\n"
//                + "        if (UtilString.isBlank(dbName)) {\n"
//                + "            throw new RuntimeException(\"数据库名不能为空\");\n"
//                + "        }\n"
//                + "\n"
                + "    }\n";

    }

    private static String getSingleInstance() {

        return "  /**\n" +
                "     * 装db集合的\n" +
                "     */\n" +
                "    public static Map<String," + fileName + "> map = new LinkedHashMap<String," + fileName + ">();\n" +
                "\n" +
                "    public static " + fileName + " getInstance(Context context) {\n" +
                "\n" +
                "       return getInstance(context,mDefaultDbName);" +
                "\n" +
                "    }" + "\n\n" +
                "    public static " + fileName + " getInstance(Context context, String dbName) {\n" +
                "\n" +
                "        " + fileName + " db = map.get(dbName);\n" +
                "\n" +
                "        if (db != null) {\n" +
                "            return db;\n" +
                "        }\n" +
                "\n" +
                "        synchronized (" + fileName + ".class) {\n" +
                "            if (map.get(dbName) == null) {\n" +
                "                map.put(dbName, new " + fileName + "(context, dbName));\n" +
                "            }\n" +
                "            return map.get(dbName);\n" +
                "        }\n" +
                "\n" +
                "    }"
                + "\n" + "\n";
    }

    private static String getClassNameStatement() {
        return "public class "
                + fileName
                + " extends SQLiteOpenHelper";
    }

    private static String getSortFieldStatement() {
        return "	   /** 排序常量 */ \n"
                + "	   public static String SORT_DESC = \" DESC\";// 有个空格符号，勿删\n"
                + "	   public static String SORT_ASC = \" ASC\";// 有个空格符号，勿删";
    }

    private static String getTableFieldStatement() {
        return "    public static String mOperatorTableName = \"" + setFirstLetterSmall(modelName) + "Table\";";
    }

    private static String getDbVersionFieldStatement() {
        return "    public static int mVersion = " + 1 + ";";
    }

    private static String getDbNameFieldStatement() {
        return "    public static String mDefaultDbName = \"" + setFirstLetterSmall(modelName) + ".db\";";
    }

    private static String queryPageByIdDesc() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 分页查找 */ \n");
        sb.append(" public" + isSync() + "List<" + modelName
                + "> queryPageByIdDesc(int pageNum, int capacity){\n");
        sb.append("String offset = (pageNum - 1) * capacity + \"\"; // 偏移量\n");
        sb.append(" String len = capacity + \"\"; // 个数\n");
        sb.append("SQLiteDatabase db = getReadableDatabase();\n");
        sb.append("Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_DESC , offset + \",\" + len);\n");
        sb.append("List<" + modelName + "> beans = new ArrayList<"
                + modelName + ">();\n");
        sb.append("while (c.moveToNext()) {\n");
        sb.append(modelName + " bean = createModel(c);\n");
        sb.append("beans.add(bean);\n");
        sb.append("}\n");
        sb.append("c.close();\n");
        sb.append("db.close();\n");
        sb.append("return beans;\n}\n");
        return sb.toString();

    }

    private static String queryPageByIdAsc() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 分页查找 */ \n");
        sb.append(" public" + isSync() + "List<" + modelName
                + "> queryPageByIdAsc(int pageNum, int capacity){\n");
        sb.append("String offset = (pageNum - 1) * capacity + \"\"; // 偏移量\n");
        sb.append(" String len = capacity + \"\"; // 个数\n");
        sb.append("SQLiteDatabase db = getReadableDatabase();\n");
        sb.append("Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_ASC , offset + \",\" + len);\n");
        sb.append("List<" + modelName + "> beans = new ArrayList<"
                + modelName + ">();\n");
        sb.append("while (c.moveToNext()) {\n");
        sb.append(modelName + " bean = createModel(c);\n");
        sb.append("beans.add(bean);\n");
        sb.append("}\n");
        sb.append("c.close();\n");
        sb.append("db.close();\n");
        sb.append("return beans;\n}\n");
        return sb.toString();

    }

    private static String queryAllByIdAsc() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 查询所有*/ \n");
        sb.append(" public" + isSync() + "List<" + modelName + "> queryAllByIdAsc() {\n");
        sb.append("SQLiteDatabase db = getReadableDatabase();\n");
        sb.append("Cursor c = db.query(mOperatorTableName, null, null, null, null, null,_ID + SORT_ASC);\n");
        sb.append("List<" + modelName + "> beans = new ArrayList<"
                + modelName + ">();\n");

        sb.append("while (c.moveToNext()) {\n");
        sb.append(modelName + " bean = createModel(c);\n");
        sb.append("beans.add(bean);\n");
        sb.append("}\n");
        sb.append("c.close();\n");
        sb.append("db.close();\n");
        sb.append("return beans;\n}\n");
        return sb.toString();
    }

    private static String queryAllByIdDesc() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 查询所有*/ \n");
        sb.append(" public" + isSync() + "List<" + modelName + "> queryAllByIdDesc() {\n");
        sb.append("SQLiteDatabase db = getReadableDatabase();\n");
        sb.append("Cursor c = db.query(mOperatorTableName, null, null, null, null, null,_ID + SORT_DESC); // 条件为null可以查询所有\n");
        sb.append("List<" + modelName + "> beans = new ArrayList<"
                + modelName + ">();\n");

        sb.append("while (c.moveToNext()) {\n");
        sb.append(modelName + " bean = createModel(c);\n");
        sb.append("beans.add(bean);\n");
        sb.append("}\n");
        sb.append("c.close();\n");
        sb.append("db.close();\n");
        sb.append("return beans;\n}\n");
        return sb.toString();
    }

    private static String queryCount() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 查询共有多少条记录 */ \n");
        sb.append("public" + isSync() + "int queryCount() {\n");
        sb.append("SQLiteDatabase db = getReadableDatabase();\n");
        sb.append("Cursor c = db.query(mOperatorTableName, new String[]{\"COUNT(*)\"},null, null, null, null, null, null);\n");
        sb.append("c.moveToNext();\n");
        sb.append("int count = c.getInt(0);\n");
        sb.append("c.close();\n");
        sb.append("db.close();\n");
        sb.append("return count;\n}\n");
        return sb.toString();

    }

    private static String deleteAll() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 删除所有记录 */ \n");
        sb.append("public" + isSync() + "int deleteAll() {\n");
        sb.append("SQLiteDatabase db = getWritableDatabase();\n");
        sb.append("int raw = db.delete(mOperatorTableName, null, null);\n");
        sb.append("db.close();\n");
        sb.append("return raw;\n}\n");
        return sb.toString();
    }

    private static String insert() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("/** 插入一条记录 */ \n");
        sb.append("public" + isSync() + " long insert(" + modelName + " model) {\n");
        sb.append("SQLiteDatabase db = getWritableDatabase();\n");
        sb.append("ContentValues values = createContentValue(model);\n");
        sb.append("long id = db.insert(mOperatorTableName, _ID, values);\n");
        sb.append("	//XCLog.i(XCConfig.TAG_DB, \"insert()插入的记录的id是: \" + id);\n");
        sb.append("db.close();\n");
        sb.append("return id;\n}\n");
        return sb.toString();
    }

    private static String inserts() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("public" + isSync() + " long inserts(List<" + modelName + "> list) {\n");
        sb.append("int count = 0;\n");
        sb.append("SQLiteDatabase db = getWritableDatabase();\n");

        sb.append("for(" + modelName + " model : list){\n");

        sb.append("ContentValues values = createContentValue(model);\n");
        sb.append("long id = db.insert(mOperatorTableName, _ID, values);\n");
        sb.append("	//XCLog.i(XCConfig.TAG_DB, \"insert()插入的记录的id是: \" + id);\n");
        sb.append("	count++;\n");
        sb.append("}\n");
        sb.append("db.close();\n");
        sb.append("return count;\n}\n");
        return sb.toString();
    }

    private static String createModel() {

        StringBuilder sb = new StringBuilder("\n");
        sb.append("	public " + modelName + " createModel(Cursor c){\n");
        sb.append("	" + modelName + " model = new " + modelName
                + "();\n");
        for (Entry<String, String> entry : mapFileds.entrySet()) {
            sb.append("	model.set" + setFirstLetterBig(entry.getValue())
                    + "(c.getString(c.getColumnIndex("
                    + entry.getKey().toUpperCase() + ")));\n");
        }
        sb.append("	return model;\n	}");

        return sb.toString();
    }

    private static String getContentValueMethodStatement() {

        ArrayList<String> list = getPutContentValues();
        StringBuilder sb = new StringBuilder("\n");
        sb.append("	public ContentValues createContentValue(" + modelName
                + " model) {\n");
        sb.append("	ContentValues values = new ContentValues();\n");

        for (String str : list) {

            sb.append("	values.put(" + str + ");\n");
        }
        return sb.append("	return values;\n	}").toString();

    }

    private static ArrayList<String> getPutContentValues() {
        ArrayList<String> list = new ArrayList<>();
        for (Entry<String, String> entry : mapFileds.entrySet()) {
            list.add(entry.getKey().toUpperCase() + ", model." + "get"
                    + setFirstLetterBig(entry.getValue()) + "()");
        }
        return list;
    }

    private static String getSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("db.execSQL(");
        sb.append("\"" + "CREATE TABLE " + "\" + " + " mOperatorTableName "
                + "\n+ \"" + "(" + "\"" + "+" + "_ID " + "+" + "\""
                + " integer primary key autoincrement," + "\"\n");
        for (Entry<String, String> entry : mapFileds.entrySet()) {
            sb.append(" + ").append(entry.getKey().toUpperCase())
                    .append(" + ").append("\" text, \"\n");
        }

        String result = getBeforeLastSimbolString(sb.toString(), ",");
        result = result + ")\"";

        return result + ");";
    }

    private static String getSqlFieldsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\n").append("	").append("/").append("**").append("以下是表字段")
                .append("*").append("/").append("\n");
        sb.append("public static final String _ID = \"_id\";\n");
        for (Entry<String, String> entry : mapFileds.entrySet()) {
            // 获取注释
            if (mapComments.get(entry.getValue()) != null) {
                //sb.append("/**").append("\n");
                sb.append(" " + mapComments.get(entry.getValue())).append("\n");
                //sb.append("*/").append("\n");
            }
            // 常量语句
            sb.append("	public static final String ")
                    .append(entry.getKey().toUpperCase())
                    .append(" = ").append("\"")
                    .append(entry.getValue().toString()).append("\"")
                    .append(";").append("\n");
        }
        return sb.toString();
    }

    private static String getUnderLineConstantFieldKey(String origin) {
        for (int i = 0; i < 26; i++) {
            if (origin.contains(((char) ('A' + i)) + "")) {
                origin = origin.replace(((char) ('A' + i)) + "", "_" + ((char) ('A' + i)));
            }
        }
        // 这里没有toUpCase，否则entrySet（）出来后就变成无序的了
        return origin;
    }

    public static void getClassField(String content) {

        getSqlAnnotations(content);

        content.replace(System.getProperty("line.separator"), "");
        content = getAfterFirstSimbolString(content, "class");
        int startIndex = content.indexOf("{");
        int endIndex = content.lastIndexOf("}");
        String body = content.substring(startIndex + 1, endIndex);
        String[] split = body.split(";");
        for (String statement : split) {

            // 排除静态的字段
            if (statement.contains(" static ")) {
                continue;
            }

            // 可能是构造器的this赋值
            if (statement.contains("this.")) {
                continue;
            }

            if (statement.contains("@Ignore")) {
                continue;
            }

            if (statement.contains("{") || statement.contains("}")) {
                // 是方法语句
                statement = getBeforeLastSimbolString(statement, "(");
                String method = getAfterLastSimbolString(statement, " ");
                if (method.contains("get")) {
                    getMethods.put(method, method);
                } else if (method.contains("set")) {
                    setMethods.put(method, method);
                }
            } else {
                // 是字段声明

                // 获取字段
                if (statement.contains("=")) {
                    statement = getBeforeLastSimbolString(statement, "=").trim();
                }

                String field = getAfterLastSimbolString(statement, " ");
                if (field != null && !"".equals(field)) {
                    mapFileds.put(getUnderLineConstantFieldKey(field), field);
                }

                // 获取注释
                getComments(field, statement);
            }
        }
    }

    private static void getSqlAnnotations(String content) {
        String temp = content;

        temp = getBeforeFirstSimbolString(temp, "class");

        // 排除注释/* */
        for (int start = temp.indexOf("/*"), end = temp.indexOf("*/");
             start >= 0 && end > 0 && end > start;
             start = temp.indexOf("/*"), end = temp.indexOf("*/")) {
            String before = getBeforeFirstSimbolString(temp, "/*");
            String after = getAfterFirstSimbolString(temp, "*/");
            temp = before + System.getProperty("line.separator") + after;
        }

        // 排除注释 //
        String[] split = temp.split(System.getProperty("line.separator"));
        temp = "";
        for (String line : split) {
            line = line.trim();
            if (line.startsWith("//")) {
                continue;
            } else if (line.contains("//")) {
                line = getBeforeFirstSimbolString(line, "//");
            }
            temp = temp + line;
        }

        for (int start = temp.indexOf(ANNOTATION_START), end = temp.indexOf(ANNOTATION_END); start > 0 && end > 0 && end > start;
             start = temp.indexOf(ANNOTATION_START), end = temp.indexOf(ANNOTATION_END)) {
            String annotationString = temp.substring(start + ANNOTATION_START.length(), end);
            if (annotationString.contains(QUERY)) {
                getQuerysAnnotation(annotationString);
            } else if (annotationString.contains(UPDATE)) {
                getUpdatesAnnotation(annotationString);
            } else if (annotationString.contains(DELETE)) {
                getDeletesAnnotation(annotationString);
            }
            temp = temp.substring(end + ANNOTATION_END.length());
        }
    }


    private static ArrayList<String> getSqls(String type, String str) {
        str = str.replace("\n", "");
        String[] temp = str.split(type);
        ArrayList<String> sqls = new ArrayList<>();
        for (String sql : temp) {
            if (sql != null && !"".equals(sql.trim())) {
                sql = getAfterFirstSimbolString(sql, "(");
                sql = getBeforeLastSimbolString(sql, ")");
                sqls.add(sql);
            }
        }
        return sqls;
    }

    // methodName = "updateOne", where = "uniqueId = 123",limit = "1,2"
    private static ArrayList<LinkedHashMap<String, String>> toListMap(ArrayList<String> sqls) {
        ArrayList<LinkedHashMap<String, String>> temp = new ArrayList<LinkedHashMap<String, String>>();
        for (String sql : sqls) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            sql = sql.replace("\"", "");
            String[] strs = sql.split(",");
            for (String str : strs) {
                if (str.contains("=")) {
                    map.put(getBeforeFirstSimbolString(str, "="), getAfterFirstSimbolString(str, "="));
                } else {
                    if (isNum(map.get(LIMIT))) {
                        map.put(LIMIT, map.get(LIMIT) + "," + str);
                    } else {
                        map.put(COLUMNS, map.get(COLUMNS) + "," + str);
                    }
                }
            }

            if (map.size() > 0) {
                temp.add(map);
            }
        }
        return temp;
    }

    public static boolean isNum(String num) {
        try {
            Long.parseLong(num);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static void getDeletesAnnotation(String str) {
        ArrayList<String> sqls = getSqls(DELETE, str);
        deleteAnnotationListMap = toListMap(sqls);
    }

    //@Update(methodName = "updateOne", where = "uniqueId = 123"),
    //@Update(methodName = "updateTwo", where = "uniqueId = ?"),
    //@Update(methodName = "updateThree", where = "name = ?")
    private static void getUpdatesAnnotation(String str) {
        ArrayList<String> sqls = getSqls(UPDATE, str);
        updateAnnotationListMap = toListMap(sqls);
    }

    private static void getQuerysAnnotation(String str) {
        ArrayList<String> sqls = getSqls(QUERY, str);
        querysAnnotationListMap = toListMap(sqls);
    }

    private static void getComments(String keyField, String statement) {

        if (statement.contains("/**") && statement.contains("*/")) {
            int start = statement.indexOf("/**");
            int end = statement.lastIndexOf("*/");
            String comment = statement.substring(start, end + 2).trim();
            mapComments.put(keyField, comment);
        }

    }

    /**
     * 获取model的类名+“Db”，用以创建该自动生成的类名
     */
    public static String getClassName() {
        modelName = getBeforeLastSimbolString(file.getName(), ".");
        return fileName = modelName + "Db";
    }

    /**
     * 获取整个model的内容
     */
    public static String getModelString(String path) {
        try {
            file = new File(path);
            return getStringFromFileByUTF(file)
                    //.replace(System.getProperty("line.separator"), "")
                    //.replace("/", "")
                    //.replace("*", "")
                    ;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取origin中第一个simbol之后的字符串
     *
     * @param origin
     * @param simbol
     * @return
     */
    public static String getAfterFirstSimbolString(String origin, String simbol) {
        int index = origin.indexOf(simbol);
        if (index >= 0) {
            if (index + simbol.length() == origin.length()) {
                return "";
            } else {
                return origin.substring(index + simbol.length(), origin.length()).trim();
            }
        }
        return origin;
    }

    /**
     * 获取origin中最后一个simbol之后的字符串
     *
     * @param origin
     * @param symbol
     * @return
     */
    public static String getAfterLastSimbolString(String origin, String symbol) {
        int index = origin.lastIndexOf(symbol);
        if (index >= 0) {
            if (index + symbol.length() == origin.length()) {
                return "";
            } else {
                return origin.substring(index + symbol.length(), origin.length()).trim();
            }
        }
        return origin;
    }

    public static String getBeforeFirstSimbolString(String origin, String simbol) {
        int index = origin.indexOf(simbol);
        if (index > 0) {
            return origin.substring(0, index).trim();
        } else if (index == 0) {
            return "";
        }
        return origin;
    }

    /**
     * 去除最后一个符号后面的
     */
    public static String getBeforeLastSimbolString(String origin, String symbol) {
        int position = origin.lastIndexOf(symbol);
        if (position > 0) {
            return origin.substring(0, position).trim();
        } else if (position == 0) {
            return "";
        }
        return origin;
    }

    /**
     * 设置第一个字母为大写
     */
    public static String setFirstLetterBig(String origin) {

        char[] chars = origin.toCharArray();

        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] = (char) (chars[0] - 32);
        }

        return new String(chars);

    }

    /**
     * 设置第一个字母为小写写
     */
    public static String setFirstLetterSmall(String origin) {

        char[] chars = origin.toCharArray();

        if (chars[0] >= 65 && chars[0] < 90) {
            chars[0] = (char) (chars[0] + 32);
        }

        return new String(chars);

    }

    public static String getStringFromFileByUTF(File file) {
        if (!file.exists()) {
            return null;
        }
        BufferedReader br = null;
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), ENCODING);
            br = new BufferedReader(reader);
            StringBuilder buffer = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                buffer.append(s).append(System.getProperty("line.separator"));
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 统计一个字符串在另外一个字符串中出现的次数
     *
     * @param origin
     * @param key
     * @return
     */
    public static int countTimes(String origin, String key) {
        if (origin == null || "".equals(origin)) {
            return 0;
        }
        String temp = origin;
        int count = 0;
        while (temp.contains(key)) {
            count++;
            int position = temp.indexOf(key);
            if (position >= 0) {
                temp = temp.substring(position + key.length(), temp.length());
            }
        }
        return count;
    }

    /**
     * 删除最后一个指定的字符串，该字符串必须在最后尾才会删除，中间出现的该串不会删除
     *
     * @param symbol
     * @return
     */
    public static String justDeleteLastSymbol(String origin, String symbol) {
        if (origin.contains(symbol)) {
            origin = origin.trim();
            // 最后一个该字符的位置
            int index = origin.lastIndexOf(symbol);
            if ((index + symbol.length()) == origin.length()) {
                // 如果该字符是在最后尾
                return origin.substring(0, index);
            }
        }
        return origin;
    }
}


// public void remit(int from, int to, int amount) {
// SQLiteDatabase qlk_db = helper.getWritableDatabase();
// try {
// qlk_db.beginTransaction(); // 开始事务
// qlk_db.execSQL("UPDATE person SET balance=balance-? WHERE id=?", new
// Object[] { amount, from });
// qlk_db.execSQL("UPDATE person SET balance=balance+? WHERE id=?", new
// Object[] { amount, to });
// qlk_db.setTransactionSuccessful(); // 事务结束时, 成功点之前的操作会被提交
// } finally {
// qlk_db.endTransaction(); // 结束事务, 将成功点之前的操作提交
// qlk_db.close();
// }
// }
