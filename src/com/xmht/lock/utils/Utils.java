
package com.xmht.lock.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Utils {
    public static int getDW(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getDH(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
    
    public static float dip2px(Context context, float dp) {
        return dp * getDensity(context);
    }
    
    private final static HashMap<String, SoftReference<Typeface>> TYPEFACE_CACHE = new HashMap<String, SoftReference<Typeface>>();

    public static Typeface createTypeface(Context context, String fontPath, boolean cache) {
        if (TYPEFACE_CACHE.containsKey(fontPath)) {
            return TYPEFACE_CACHE.get(fontPath).get();
        }
        Scheme scheme = Scheme.ofUri(fontPath);
        Typeface tf = null;
        if (scheme == Scheme.FILE) {
            try {
                tf = Typeface.createFromFile(fontPath.replace("file://", ""));
            } catch (Exception e) {
            }
        } else if (scheme == Scheme.ASSETS) {
            fontPath = fontPath.replaceFirst("assets://", "");
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            } catch (Exception e) {
            }
        } else {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            } catch (Exception e) {
            }
        }
        if (tf != null && cache) {
            TYPEFACE_CACHE.put(fontPath, new SoftReference<Typeface>(tf));
        }
        return tf;
    }
    
    public static void setFontToView(TextView textView, String fontPath) {
        setFontToView(textView, fontPath, Typeface.NORMAL);
    }
    
    public static void setFontToView(TextView textView, String fontPath, int typefaceStyle) {
        Typeface tf = createTypeface(textView.getContext(), fontPath, true);
        textView.setTypeface(tf, typefaceStyle);
    }
}
