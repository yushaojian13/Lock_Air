
package com.xmht.lock.core.debug;

import java.util.regex.Pattern;

import android.util.Log;

public class LOG {

    private static String TAG = "LockAir";
    private static boolean isLog = false;
    public static boolean isFormat = true;

    private static String format(Object msg) {
        if (isFormat) {
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[4];// maybe this number needs to be
                                                // corrected
            String methodName = e.getMethodName();
            String className = e.getClassName();
            String[] splits =className.split(Pattern.quote("."));
            return String.format("%s %s ==> %s", splits[splits.length-1], methodName, msg);
        }
        return msg.toString();
    }
    
    public static void enableLog(boolean flag){
    	isLog = flag;
    }
    
    public static boolean isLog(){
    	return isLog;
    }

    /**
     * see d2
     * 
     * @param msg
     */
    public static void d(Object msg) {
        if (isLog) {
            Log.d(TAG, format(msg));
        }
    }

    /**
     * see e2
     * 
     * @param msg
     */
    public static void e(Object msg) {
        if (isLog) {
            Log.e(TAG, format(msg));
        }
    }

    /**
     * see v2
     * 
     * @param msg
     */
    public static void v(Object msg) {
        if (isLog) {
            Log.v(TAG, format(msg));
        }
    }

    private static int LOG_LEVEL = 0;
    private static final int VERBOSE = 5;
    private static final int DEBUG = 4;
    private static final int INFO = 3;
    private static final int WARN = 2;
    private static final int ERROR = 1;
    private static final String DEFAULT_TAG = "XMLib";

    // static {
    // if (BuildConfig.DEBUG) {
    // LOG_LEVEL = 6;
    // } else {
    // LOG_LEVEL = 0;
    // }
    // }

    public static void v(String tag, Object msg) {
        if (LOG_LEVEL > VERBOSE) {
            Log.v(tag, format(msg));
        }
    }

    public static void d(String tag, Object msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(tag, format(msg));
        }
    }

    public static void i(String tag, Object msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(tag, format(msg));
        }
    }

    public static void w(String tag, Object msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(tag, format(msg));
        }
    }

    public static void e(String tag, Object msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(tag, format(msg));
        }
    }

    public static void v2(Object msg) {
        if (LOG_LEVEL > VERBOSE) {
            Log.v(DEFAULT_TAG, format(msg));
        }
    }

    public static void d2(Object msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(DEFAULT_TAG, format(msg));
        }
    }

    public static void i(Object msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(DEFAULT_TAG, format(msg));
        }
    }

    public static void w(Object msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(DEFAULT_TAG, format(msg));
        }
    }

    public static void e2(Object msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(DEFAULT_TAG, format(msg));
        }
    }

}
