package com.bitech.joke.utils;

import android.util.Log;

/**
 * <p></p>
 * Created on 2016/4/5 14:16.
 *
 * @author Lucy
 */
public class Logger {
    private final static String tag = "";

    public static int logLevel = Log.VERBOSE;

    private static boolean logFlag = true;

    private static Logger logger = new Logger();

    public static Logger getLogger() {
        return logger;
    }

    public Logger() {

    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " ]";
        }

        return null;
    }

    public void i(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.INFO) {
            String name = getFunctionName();
            if (name != null) {
                Log.i(tag, name + " - " + str);
            } else {
                Log.i(tag, str.toString());
            }
        }
    }

    public void v(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.VERBOSE) {
            String name = getFunctionName();
            if (name != null) {
                Log.v(tag, name + " - " + str);
            } else {
                Log.v(tag, str.toString());
            }

        }
    }

    public void w(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.WARN) {
            String name = getFunctionName();

            if (name != null) {
                Log.w(tag, name + " - " + str);
            } else {
                Log.w(tag, str.toString());
            }

        }
    }

    public void e(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.ERROR) {

            String name = getFunctionName();
            if (name != null) {
                Log.e(tag, name + " - " + str);
            } else {
                Log.e(tag, str.toString());
            }
        }
    }

    public void w(Exception ex) {
        if (!logFlag)
            return;
        if (logLevel <= Log.WARN) {
            Log.w(tag, "error", ex);
        }
    }

    public void d(Object str) {
        if (!logFlag)
            return;
        if (logLevel <= Log.DEBUG) {
            String name = getFunctionName();
            if (name != null) {
                Log.d(tag, name + " - " + str);
            } else {
                Log.d(tag, str.toString());
            }

        }
    }
}
