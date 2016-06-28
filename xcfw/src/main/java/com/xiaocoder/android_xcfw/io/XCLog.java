package com.xiaocoder.android_xcfw.io;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xiaocoder.android_xcfw.application.XCConstant;
import com.xiaocoder.android_xcfw.util.UtilString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author xiaocoder
 * @email fengjingyu@foxmail.com
 * @description 1 可以控制频率的吐司
 * 2 输出log到控制台
 * 3 输出log到文件
 * 4 日志的清空，日志的大小控制
 * <p/>
 * 使用前初始化initLog方法
 */
public class XCLog {
    /**
     * 毫秒
     */
    public static final int TOAST_TIME_GAP = 2000;
    /**
     * 缓存文件达到70M就会清空
     */
    public static final long LOG_FILE_LIMIT_SIZE = 73400320;
    /**
     * 记录上一次土司的时间
     */
    public static long lastToastTime;
    /**
     * log文件 = dirDame(目录) + logFileName（文件名）
     */
    public static File file;
    /**
     * 存储日志文件的文件夹 “aa/bb”
     */
    public static String dirDame;
    /**
     * 日志文件名，可以加文件类型后缀
     * "123.txt"
     */
    public static String logFileName;

    /**
     * 以下值需要初始化
     */
    public static Context context;
    /**
     * 调试土司是否显示
     */
    public static boolean isDtoast = false;
    /**
     * i类型的日志是否打印到日志文件中
     */
    public static boolean isPrintlog = false;
    /**
     * 日志是否输出到控制台
     */
    public static boolean isOutput = true;
    /**
     * 有时候控制台可能打印json不全，比如json太长的时候，可以调用tempPrint方法，打印到本地查看
     */
    public static String tempFileName = "temp_file.txt";
    /**
     * 编码
     */
    public static String encoding;

    /**
     * @param context       上下文
     * @param is_dtoast     调试土司是否显示
     * @param is_output     是否输出到控制台
     * @param is_printlog   i日志是否打印到日志文件
     * @param dir_name      日志文件件
     * @param log_file_name 日志文件面
     * @param encoding      编码
     */
    public static void initXCLog(Context context, boolean is_dtoast, boolean is_output, boolean is_printlog,
                                 String dir_name, String log_file_name, String encoding) {

        XCLog.context = context;
        XCLog.dirDame = UtilString.isAvaliable(dir_name) ? dir_name : "logDir_" + System.currentTimeMillis();
        XCLog.logFileName = UtilString.isAvaliable(log_file_name) ? log_file_name : "log_file.txt";
        XCLog.encoding = UtilString.isAvaliable(encoding) ? encoding : "utf-8";
        XCLog.isDtoast = is_dtoast;
        XCLog.isOutput = is_output;
        XCLog.isPrintlog = is_printlog;
    }

    /**
     * 防止点击频繁, 不断的弹出
     */
    public static void longToast(Object msg) {
        longToast(false, msg);
    }

    /**
     * 防止点击频繁, 不断的弹出
     */
    public static void longToast(boolean showImmediately, Object msg) {
        toast(showImmediately, msg, Toast.LENGTH_LONG);
    }

    /**
     * 防止点击频繁, 不断的弹出
     */
    public static void shortToast(Object msg) {
        shortToast(false, msg);
    }

    /**
     * 防止点击频繁, 不断的弹出
     */
    public static void shortToast(boolean showImmediately, Object msg) {
        toast(showImmediately, msg, Toast.LENGTH_SHORT);
    }


    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dShortToast(boolean showImmediately, Object msg) {
        if (isDtoast) {
            shortToast(showImmediately, msg);
        }
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dShortToast(Object msg) {
        dShortToast(false, msg);
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dLongToast(Object msg) {
        dLongToast(false, msg);
    }

    /**
     * 调试的toast , 上线前开关关闭
     */
    public static void dLongToast(boolean showImmediately, Object msg) {
        if (isDtoast) {
            longToast(showImmediately, msg);
        }
    }

    /**
     * @param isShowImmediately true为立即显示，不受时间的限制
     * @param msg               消息，可以object null
     * @param showtType         土司的类型 如 long short
     */
    private static void toast(boolean isShowImmediately, Object msg, int showtType) {

        if (context == null) {
            return;
        }

        if (isShowImmediately || System.currentTimeMillis() - lastToastTime > TOAST_TIME_GAP) {
            Toast.makeText(context, msg + "", showtType).show();
            lastToastTime = System.currentTimeMillis();
        }

    }

    /**
     * 以tag打印到控制台 和 文件
     * <p/>
     * 上线前is_output 与 is_printlog关闭
     */
    public static void i(Context context, Object msg) {
        if (isOutput) {
            Log.i(context.getClass().getSimpleName(), msg + "");
        }
        if (isPrintlog) {
            writeLog2File(context.getClass().getSimpleName() + "---" + msg, true);
        }
    }

    public static void i(String tag, Object msg) {
        if (isOutput) {
            Log.i(tag, msg + "");
        }
        if (isPrintlog) {
            writeLog2File(msg + "", true);
        }
    }

    public static void i(Object msg) {
        if (isOutput) {
            Log.i(XCConstant.TAG_SYSTEM_OUT, msg + "");
        }
        if (isPrintlog) {
            writeLog2File(msg + "", true);
        }
    }

    public static void itemp(Object msg) {
        i(XCConstant.TAG_TEMP, msg);
    }

    public static void itest(Object msg) {

        i(XCConstant.TAG_TEST, msg);
    }

    /**
     * e不管是否上线，都打印日志到本地，并输出到控制台
     * 注：e的日志颜色不同
     */
    public static void e(String hint) {
        Log.e(XCConstant.TAG_ALOG, hint);
        writeLog2File(hint, true);
    }

    public static void e(Context context, String hint) {
        Log.e(XCConstant.TAG_ALOG, context.getClass().getSimpleName() + "--" + hint);
        writeLog2File(context.getClass().getSimpleName() + "--" + hint, true);
    }

    public static void e(String hint, Exception e) {
        e.printStackTrace();
        Log.e(XCConstant.TAG_ALOG, hint + "--" + "Exception-->" + e.toString() + "--" + e.getMessage());
        writeLog2File("Exception-->" + hint + "-->" + e.toString() + "--" + e.getMessage(), true);
    }

    public static void e(Context context, String hint, Exception e) {
        e.printStackTrace();
        Log.e(XCConstant.TAG_ALOG, "Exception-->" + context.getClass().getSimpleName() + "--" + hint + "--" + e.toString() + "--" + e.getMessage());
        writeLog2File("Exception-->" + context.getClass().getSimpleName() + "--" + hint + "--" + e.toString() + "--" + e.getMessage(), true);
    }

    /**
     * 删除日志文件
     */
    public static synchronized void clearLog() {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    /**
     * 只在有sd卡的时候，才会打印日志
     */
    public static synchronized void writeLog2File(String content, boolean is_append) {

        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(content) || !XCIOAndroid.isSDcardExist()) {
            return;
        }

        RandomAccessFile raf = null;

        try {
            if (file == null || !file.exists()) {

                // sd中，在app_root文件夹下创建log文件
                file = XCIOAndroid.createFileInSDCard(dirDame, logFileName);

            }

            // 日志满了的处理
            logFull();

            // 已存在文件
            if (!is_append) {
                // 假如不允许追加写入，则删除 后 重新创建
                file.delete();
                file.createNewFile();
            }

            raf = new RandomAccessFile(file, "rw");
            long len = raf.length();
            raf.seek(len);
            raf.write((content + "-->" + DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG).format(new Date()) + "  end  " + System.getProperty("line.separator"))
                    .getBytes(encoding));

        } catch (Exception e) {
            e.printStackTrace();
            // 这里不要调用e(),可能相互调用，异常
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印到XCConfig.TEMP_PRINT_FILE文件中，会覆盖之前的打印信息
     * <p/>
     * 场景：如果json很长，有时控制台未必会全部打印出来，则可以去app的目录下找到这个临时文件查看
     */
    public synchronized static void tempPrint(String str) {

        if (context == null) {
            return;
        }

        if (isOutput) {
            FileOutputStream fos = null;
            try {
                // 在app_root目录下创建temp_print文件，如果没有sd卡，则写到内部存储中
                fos = new FileOutputStream(XCIOAndroid.createFileInAndroid(context, dirDame, tempFileName));
                fos.write(str.getBytes());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    } finally {
                        fos = null;
                    }
                }
            }
        }
    }

    public static void logFull() throws Exception {
        if (file != null && file.exists() && file.length() > LOG_FILE_LIMIT_SIZE) {
            file.delete();
            file.createNewFile();
        }
    }

    /**
     * 格式化打印json到控制台
     */
    public static void logFormatContent(boolean bindLineAndlLog, String tag, String hint, String content) {

        if (isOutput) {
            try {
                Log.i(tag, "－－－－－－－－－－－－－－－－－－  " + hint + " MSG BEGIN －－－－－－－－－－－－－－－－－－");

                if (content != null) {
                    if (bindLineAndlLog) {
                        String[] msgLines = content.split("\n");
                        for (String oneLine : msgLines) {
                            Log.i(tag, "| " + oneLine);
                        }
                    } else {
                        Log.i(tag, content);
                    }
                } else {
                    Log.i(tag, "传入XCLog.logFormatContent()的内容为空");
                }

                Log.i(tag, "－－－－－－－－－－－－－－－－－－  " + hint + "  MSG END －－－－－－－－－－－－－－－－－－");
            } catch (Exception ex) {
                Log.e(XCConstant.TAG_ALOG, "logFormatContent----" + content, ex);
            }
        }
    }

    /**
     * 格式化打印json到控制台
     */
    public static void logFormatContent(String tag, String hint, String content) {
        logFormatContent(false, tag, hint, content);
    }


    /**
     * 系统会返回一个数组，该数组的下标从本类(或方法)开始并逐步向上调用直至最终的类，
     * 如下标为0是 StackTraceElementTest类
     * 下标为1则为MainActivity(这个方法在MainActivity中被调用,如果再在本类中的方法又进行调用则进行+1)，下标为2则表示MainActivity的父类Activity
     *
     * @return 获取当前日志输出所在的信息的集合 集合内包含类名、方法名、行数
     */
    //TODO 未测试使用，待定
    private String[] getLogCurrentMsg() {
        String[] arr = new String[3];
        int level = 3;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        StackTraceElement element = stacks[level];
        arr[0] = element.getClassName();
        arr[1] = element.getMethodName();
        arr[2] = element.getLineNumber() + "";
        return arr;
    }

}