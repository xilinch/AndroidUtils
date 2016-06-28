package com.example.app.sample.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app.sample.model.SampleDbModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description db
 * <p/>
 * 1 、该db的代码是根据SampleDbModel自动生成的 --> DbCodeGenerator_V0.1.jar
 * 2 、该db是线程安全的
 */
public class SampleDb extends SQLiteOpenHelper {

    public static String mDefaultDbName = "sample.db";
    public static int mVersion = 1;
    public static String mOperatorTableName = "sampleTable";

    /**
     * 排序常量
     */
    public static String SORT_DESC = " DESC";// 有个空格符号，勿删
    public static String SORT_ASC = " ASC";// 有个空格符号，勿删

    /**
     * 以下是表字段
     */
    public static final String _ID = "_id";
    /**
     * 身份证
     */
    public static final String UNIQUE_ID = "uniqueId";
    /**
     * 名字
     */
    public static final String NAME = "name";
    /**
     * 性别 0 男 ，1 女
     */
    public static final String GENDER = "gender";
    /**
     * 年龄
     */
    public static final String AGE = "age";
    /**
     * 得分
     */
    public static final String SCORE = "score";
    /**
     * 爱好
     */
    public static final String HOBBY = "hobby";

    /**
     * 装db集合的
     */
    public static Map<String, SampleDb> map = new LinkedHashMap<String, SampleDb>();

    public static SampleDb getInstance(Context context) {

        return getInstance(context, mDefaultDbName);
    }

    public static SampleDb getInstance(Context context, String dbName) {

        SampleDb db = map.get(dbName);

        if (db != null) {
            return db;
        }

        synchronized (SampleDb.class) {
            if (map.get(dbName) == null) {
                map.put(dbName, new SampleDb(context, dbName));
            }
            return map.get(dbName);
        }

    }

    private SampleDb(Context context) {
        super(context, mDefaultDbName, null, mVersion);
    }

    private SampleDb(Context context, String dbName) {
        super(context, dbName, null, mVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + mOperatorTableName
                + "(" + _ID + " integer primary key autoincrement,"
                + UNIQUE_ID + " text, "
                + NAME + " text, "
                + GENDER + " text, "
                + AGE + " text, "
                + SCORE + " text, "
                + HOBBY + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public ContentValues createContentValue(SampleDbModel model) {
        ContentValues values = new ContentValues();
        values.put(UNIQUE_ID, model.getUniqueId());
        values.put(NAME, model.getName());
        values.put(GENDER, model.getGender());
        values.put(AGE, model.getAge());
        values.put(SCORE, model.getScore());
        values.put(HOBBY, model.getHobby());
        return values;
    }

    public SampleDbModel createModel(Cursor c) {
        SampleDbModel model = new SampleDbModel();
        model.setUniqueId(c.getString(c.getColumnIndex(UNIQUE_ID)));
        model.setName(c.getString(c.getColumnIndex(NAME)));
        model.setGender(c.getString(c.getColumnIndex(GENDER)));
        model.setAge(c.getString(c.getColumnIndex(AGE)));
        model.setScore(c.getString(c.getColumnIndex(SCORE)));
        model.setHobby(c.getString(c.getColumnIndex(HOBBY)));
        return model;
    }

    /**
     * 插入一条记录
     */
    public synchronized long insert(SampleDbModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(model);
        long id = db.insert(mOperatorTableName, _ID, values);
        //XCLog.i(XCConstant.TAG_DB, "insert()插入的记录的id是: " + id);
        db.close();
        return id;
    }

    public synchronized long inserts(List<SampleDbModel> list) {
        int count = 0;
        SQLiteDatabase db = getWritableDatabase();
        for (SampleDbModel model : list) {
            ContentValues values = createContentValue(model);
            long id = db.insert(mOperatorTableName, _ID, values);
            //XCLog.i(XCConstant.TAG_DB, "insert()插入的记录的id是: " + id);
            count++;
        }
        db.close();
        return count;
    }

    /**
     * 删除所有记录
     */
    public synchronized int deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        int raw = db.delete(mOperatorTableName, null, null);
        db.close();
        return raw;
    }

    /**
     * 查询共有多少条记录
     */
    public synchronized int queryCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, new String[]{"COUNT(*)"}, null, null, null, null, null, null);
        c.moveToNext();
        int count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

    /**
     * 查询所有
     */
    public synchronized List<SampleDbModel> queryAllByIdDesc() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_DESC); // 条件为null可以查询所有
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 查询所有
     */
    public synchronized List<SampleDbModel> queryAllByIdAsc() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_ASC);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 分页查找
     */
    public synchronized List<SampleDbModel> queryPageByIdAsc(int pageNum, int capacity) {
        String offset = (pageNum - 1) * capacity + ""; // 偏移量
        String len = capacity + ""; // 个数
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_ASC, offset + "," + len);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    /**
     * 分页查找
     */
    public synchronized List<SampleDbModel> queryPageByIdDesc(int pageNum, int capacity) {
        String offset = (pageNum - 1) * capacity + ""; // 偏移量
        String len = capacity + ""; // 个数
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, _ID + SORT_DESC, offset + "," + len);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized int deleteOne() {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, NAME + " = 123", null);
        //XCLog.i(XCConstant.TAG_DB, "deleteOne()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int deleteTwo(String value1) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, NAME + " = ?", new String[]{value1});
        //XCLog.i(XCConstant.TAG_DB, "deleteTwo()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int deleteThree() {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, GENDER + " = 1", null);
        //XCLog.i(XCConstant.TAG_DB, "deleteThree()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int deleteFour(String value1, String value2) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, GENDER + " = ? and " + AGE + " = ?", new String[]{value1, value2});
        //XCLog.i(XCConstant.TAG_DB, "deleteFour()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int deleteFive() {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(mOperatorTableName, null, null);
        //XCLog.i(XCConstant.TAG_DB, "deleteFive()-->" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int updateOne(SampleDbModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(model);
        int rows = db.update(mOperatorTableName, values, UNIQUE_ID + " = 123", null);
        //XCLog.i(XCConstant.TAG_DB,"updateOne()更新了" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int updateTwo(SampleDbModel model, String value1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(model);
        int rows = db.update(mOperatorTableName, values, UNIQUE_ID + " = ?", new String[]{value1});
        //XCLog.i(XCConstant.TAG_DB,"updateTwo()更新了" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized int updateThree(SampleDbModel model, String value1) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValue(model);
        int rows = db.update(mOperatorTableName, values, NAME + " = ?", new String[]{value1});
        //XCLog.i(XCConstant.TAG_DB,"updateThree()更新了" + rows + "行");
        db.close();
        return rows;
    }

    public synchronized List<SampleDbModel> queryOne() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, GENDER + " = 0", null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryTwo() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, GENDER + " = 1", null, null, null, _ID + SORT_DESC, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryThree(String value1) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, NAME + " = ?", new String[]{value1}, null, null, _ID + SORT_ASC, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryFour() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, SCORE + " > 123456", null, null, null, _ID + SORT_ASC, "1");
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryFive(String value1) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, SCORE + " = ?", new String[]{value1}, null, null, null, "1");
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> querySix(String value1) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, SCORE + " >= ?", new String[]{value1}, null, null, _ID + SORT_ASC, "0,10");
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> querySeven(String value1) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, SCORE + " <= ?", new String[]{value1}, null, null, NAME + SORT_ASC, "10,30");
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryEight() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, SCORE + " <= 90", null, null, null, NAME + SORT_DESC, "5,-1");
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryNight() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, AGE + " <= 20 and " + SCORE + " > 70", null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryTen() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> queryEleven() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, AGE + SORT_DESC, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> query12() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> query13() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> query14() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }

    public synchronized List<SampleDbModel> query15() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(mOperatorTableName, null, null, null, null, null, null, null);
        List<SampleDbModel> beans = new ArrayList<SampleDbModel>();
        while (c.moveToNext()) {
            SampleDbModel bean = createModel(c);
            beans.add(bean);
        }
        c.close();
        db.close();
        return beans;
    }
}