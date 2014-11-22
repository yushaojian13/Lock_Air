
package com.xmht.lock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xmht.lock.debug.LOG;

public class SPHelper {
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences("LockAir", Context.MODE_PRIVATE);
    }

    public static int get(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }
    
    public static void set(String key, int value) {
        sp.edit().putInt(key, value).commit();
        LOG.v("put " + key + ": " + value);
    }
}
