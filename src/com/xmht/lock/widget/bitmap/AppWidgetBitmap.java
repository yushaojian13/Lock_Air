
package com.xmht.lock.widget.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.text.format.Time;

import com.xmht.lock.LockAirAppication;
import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.ysj.tools.utils.Fonts;
import com.ysj.tools.utils.SPHelper;

public abstract class AppWidgetBitmap {
    protected boolean showShadow;
    protected int primaryColor;
    protected int secondaryColor;
    protected String font;
    protected float scaleFactor;
    
    protected static final String SP_WIDGET_SHADOW = "widget_shadow"; 
    protected static final String SP_WIDGET_COLOR1 = "widget_color1"; 
    protected static final String SP_WIDGET_COLOR2 = "widget_color2"; 
    protected static final String SP_WIDGET_FONT = "widget_font"; 
    protected static final String SP_WIDGET_SCALE = "widget_scale"; 

    protected Context context;
    protected Time time;
    protected ITimeFormatter timeFormatter;
    
    private SPHelper spHelper;

    public AppWidgetBitmap(Context context, Time time) {
        spHelper = new SPHelper(context, LockAirAppication.SP_TAG);
        showShadow = spHelper.get(SP_WIDGET_SHADOW, false);
        scaleFactor = spHelper.get(SP_WIDGET_SCALE, 2.0f);
        primaryColor = spHelper.get(SP_WIDGET_COLOR1, Color.WHITE);
        secondaryColor = spHelper.get(SP_WIDGET_COLOR2, Color.WHITE);
        font = spHelper.get(SP_WIDGET_FONT, "fonts/Helvetica-Light.ttf");

        this.context = context;
        this.time = time;
        this.timeFormatter = getTimeFormatter();
    }

    public void setTime(Time time) {
        timeFormatter.setTime(time);
    }

    public void showShadow(boolean shadowOn) {
        showShadow = shadowOn;
        spHelper.put(SP_WIDGET_SHADOW, shadowOn);
    }

    public void setColors(int[] colors) {
        if (colors == null || colors.length == 0) {
            return;
        }

        if (colors[0] != 0) {
            primaryColor = colors[0];
            spHelper.put(SP_WIDGET_COLOR1, primaryColor);
        }
        if (colors.length > 1 && colors[1] != 0) {
            secondaryColor = colors[1];
            spHelper.put(SP_WIDGET_COLOR2, secondaryColor);
        }
    }

    public void setFont(String font) {
        if (font == null) {
            return;
        }

        this.font = font;
        spHelper.put(SP_WIDGET_FONT, font);
    }

    public void setScaleFactor(float factor) {
        scaleFactor = factor;
        spHelper.put(SP_WIDGET_SCALE, scaleFactor);
    }

    protected Rect computeRect(Paint paint, String text, float textSize) {
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
    
    protected Paint initPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        paint.setTypeface(Fonts.createTypeface(context, font, false));
        return paint;
    }

    public abstract ITimeFormatter getTimeFormatter();
    public abstract Bitmap create();
}
