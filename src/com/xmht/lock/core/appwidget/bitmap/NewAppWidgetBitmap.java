
package com.xmht.lock.core.appwidget.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.format.Time;

import com.xmht.lock.core.appwidget.formatter.ITimeFormater;
import com.xmht.lock.core.appwidget.formatter.NewTimeFormatter;
import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.Utils;

public class NewAppWidgetBitmap implements AppWidgetBitmap {

    private Context context;
    private String font;
    private ITimeFormater timeFormater;

    public NewAppWidgetBitmap(Context context, Time time) {
        this.context = context;
        this.font = "fonts/CaviarDreams.ttf";
        this.timeFormater = new NewTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        long now = System.currentTimeMillis();
        String time = timeFormater.getTime();
        String am = timeFormater.getAmPm();
        String week = timeFormater.getWeek();
        String date = timeFormater.getDate();
        int space = 15;
        Typeface typeface = Utils.createTypeface(context, font);

        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        // paint.setShadowLayer(5, 5, 5, 0x55000000);

        paint.setTextSize(120);
        Rect timeRect = new Rect();
        paint.getTextBounds(time, 0, time.length(), timeRect);

        paint.setTextSize(30);
        Rect weekRect = new Rect();
        paint.getTextBounds(week, 0, week.length(), weekRect);

        paint.setTextSize(20);
        Rect amRect = new Rect();
        paint.getTextBounds(am, 0, am.length(), amRect);

        Bitmap bitmap = Bitmap.createBitmap(timeRect.width() + space + amRect.width(),
                weekRect.height() + space + timeRect.height(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0x6600ff00);

        paint.setTextSize(30);
        paint.setColor(Color.WHITE);
        canvas.drawText(week, -weekRect.left, -weekRect.top, paint);
        canvas.drawText(date, weekRect.width() + space - weekRect.left, -weekRect.top, paint);

        paint.setTextSize(120);
        paint.setColor(Color.WHITE);
        canvas.drawText(time, -timeRect.left, weekRect.height() + space - timeRect.top, paint);

        paint.setTextSize(20);
        paint.setColor(Color.WHITE);
        if (timeFormater.isAm()) {
            canvas.drawText(am, timeRect.width() + space - amRect.left, weekRect.height() + space
                    - amRect.top, paint);
        } else {
            canvas.drawText(am, timeRect.width() + space - amRect.left, weekRect.height() + space - timeRect.top, paint);
        }

        LOG.v("create bitmap uses " + (System.currentTimeMillis() - now) + " millis");
        return bitmap;
    }

}
