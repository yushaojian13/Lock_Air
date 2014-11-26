
package com.xmht.lock.core.appwidget.bitmap;

import com.xmht.lock.core.appwidget.formatter.DigitalTimeFormatter;
import com.xmht.lock.core.appwidget.formatter.ITimeFormater;
import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.Utils;
import com.xmht.lockair.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.text.format.Time;

public class DigitalAppWidgetBitmap implements AppWidgetBitmap {

    private Context context;
    private String font;
    private Time time;
    private ITimeFormater timeFormater;

    public DigitalAppWidgetBitmap(Context context, Time time) {
        this.context = context;
        this.font = "fonts/UnidreamLED.ttf";
        this.time = time;
        this.timeFormater = new DigitalTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        String text = timeFormater.getTime();

        int space = 15;

        Typeface typeface = Utils.createTypeface(context, font);

        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        // paint.setShadowLayer(5, 5, 5, 0x55000000);

        paint.setTextSize(150);
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);

        paint.setTextSize(30);
        String[] weekArray = context.getResources().getStringArray(R.array.array_week_short);
        Rect[] weekRects = new Rect[weekArray.length];
        int weekTotalWidth = 0;
        int weekHeight = 0;
        int weekTop = 0;
        for (int i = 0; i < weekArray.length; i++) {
            weekRects[i] = new Rect();
            paint.getTextBounds(weekArray[i], 0, weekArray[i].length(), weekRects[i]);
            weekTotalWidth += weekRects[i].width();
            if (weekRects[i].height() > weekHeight) {
                weekHeight = weekRects[i].height();
            }

            if (weekRects[i].top < weekTop) {
                weekTop = weekRects[i].top;
            }
        }

        LOG.e("weekRect = " + weekTotalWidth + " " + weekHeight);
        LOG.e("timeRect = " + bound.width() + " " + bound.height());

        float delta = (bound.width() - weekTotalWidth) / 6;

        Bitmap bitmap = Bitmap.createBitmap(bound.width(), bound.height() + weekHeight + space,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0x6600ff00);

        paint.setTextSize(30);
        int week = (time.weekDay - 1 + weekArray.length) % weekArray.length;
        for (int i = 0; i < weekArray.length; i++) {
            int offset = 0;
            for (int j = 0; j < i; j++) {
                offset += weekRects[j].width();
            }
            if (i == week) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.GRAY);
            }
            canvas.drawText(weekArray[i], -bound.left + delta * i + offset, -weekTop, paint);
        }

        paint.setTextSize(150);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, -bound.left, -weekTop - bound.top + space, paint);

        return bitmap;
    }
}
