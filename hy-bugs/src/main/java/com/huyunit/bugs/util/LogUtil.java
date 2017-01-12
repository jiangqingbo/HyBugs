package com.huyunit.bugs.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 打印log日志信息
 * author: bobo
 * create time: 2017/1/12 16:16
 * Email: jqbo84@163.com
 */
public class LogUtil {
    //是否需要打印Log日志信息
    public static boolean isDebug = true;
    public static String customTagPrefix = "Debug";
    private static final int MAX_LENGTH = 8000;
    private static final String EMPTY = "====>Empty<====";

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            d(content.substring(0, content.length() / 2));
            d(content.substring(content.length() / 2, content.length()));
        } else {
            Log.d(tag, content);
        }
    }

    public static void d(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            d(content.substring(0, content.length() / 2), tr);
            d(content.substring(content.length() / 2, content.length()), tr);
        } else {
            Log.d(tag, content, tr);
        }
    }

    public static void e(String content) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            e(content.substring(0, content.length() / 2));
            e(content.substring(content.length() / 2, content.length()));
        } else {
            Log.e(tag, content);
        }
    }

    public static void e(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            e(content.substring(0, content.length() / 2), tr);
            e(content.substring(content.length() / 2, content.length()), tr);
        } else {
            Log.e(tag, content, tr);
        }
    }

    public static void i(String content) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            i(content.substring(0, content.length() / 2));
            i(content.substring(content.length() / 2, content.length()));
        } else {
            Log.i(tag, content);
        }
    }

    public static void i(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            i(content.substring(0, content.length() / 2), tr);
            i(content.substring(content.length() / 2, content.length()), tr);
        } else {
            Log.i(tag, content, tr);
        }
    }

    public static void v(String content) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            v(content.substring(0, content.length() / 2));
            v(content.substring(content.length() / 2, content.length()));
        } else {
            Log.v(tag, content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            v(content.substring(0, content.length() / 2), tr);
            v(content.substring(content.length() / 2, content.length()), tr);
        } else {
            Log.v(tag, content, tr);
        }
    }

    public static void w(String content) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            w(content.substring(0, content.length() / 2));
            w(content.substring(content.length() / 2, content.length()));
        } else {
            Log.w(tag, content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            w(content.substring(0, content.length() / 2), tr);
            w(content.substring(content.length() / 2, content.length()), tr);
        } else {
            Log.w(tag, content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        Log.w(tag, tr);
    }

    public static void wtf(String content) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            wtf(content.substring(0, content.length() / 2));
            wtf(content.substring(content.length() / 2, content.length()));
        } else {
            Log.wtf(tag, content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        if (TextUtils.isEmpty(content)) content = EMPTY;
        if (content.length() > MAX_LENGTH) {
            wtf(content.substring(0, content.length() / 2), tr);
            wtf(content.substring(content.length() / 2, content.length()), tr);
        } else {
            Log.wtf(tag, content, tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (!isDebug) return;
        String tag = generateTag();
        Log.wtf(tag, tr);
    }

    private static long lastTime;

    public static void t(String msg) {
        if (!isDebug) return;
        long current = System.currentTimeMillis();
        e(msg + ":" + (current - lastTime));
        lastTime = current;
    }

}
