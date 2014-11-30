
package com.xmht.lock.widget.bitmap;

import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.xmht.lock.widget.formatter.NewTimeFormatter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

public class NewAppWidgetBitmap extends AppWidgetBitmap {

    public NewAppWidgetBitmap(Context context, Time time) {
        super(context, time);
    }

    @Override
    public ITimeFormatter getTimeFormatter() {
        return new NewTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        Paint paint = initPaint();

        String time = timeFormatter.getTime();
        String am = timeFormatter.getAmPm();
        String week = timeFormatter.getWeek();
        String date = timeFormatter.getDate();

        float timeSize = 160 * scaleFactor;
        float weekSize = 40 * scaleFactor;
        float amSize = 25 * scaleFactor;

        Rect timeRect = computeRect(paint, time, timeSize);
        Rect weekRect = computeRect(paint, week, weekSize);
        Rect amRect = computeRect(paint, am, amSize);

        int space = 15;
        Bitmap bitmap = Bitmap.createBitmap(timeRect.width() + space + amRect.width(),
                weekRect.height() + space + timeRect.height(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (showShadow) {
            paint.setShadowLayer(2.5f, 2.5f, 2.5f, 0x55000000);
        }
        paint.setTextSize(weekSize);
        paint.setColor(primaryColor);
        canvas.drawText(week, -weekRect.left, -weekRect.top, paint);
        canvas.drawText(date, weekRect.width() + space - weekRect.left, -weekRect.top, paint);

        if (showShadow) {
            paint.setShadowLayer(4.5f, 4.5f, 4.5f, 0x55000000);
        }
        paint.setTextSize(timeSize);
        paint.setColor(primaryColor);
        canvas.drawText(time, -timeRect.left, weekRect.height() + space - timeRect.top, paint);

        if (showShadow) {
            paint.setShadowLayer(1.5f, 1.5f, 1.5f, 0x55000000);
        }
        paint.setTextSize(amSize);
        paint.setColor(secondaryColor);
        if (timeFormatter.isAm()) {
            canvas.drawText(am, timeRect.width() + space - amRect.left, weekRect.height() + space
                    - amRect.top, paint);
        } else {
            canvas.drawText(am, timeRect.width() + space - amRect.left, weekRect.height() + space
                    - timeRect.top, paint);
        }

        return bitmap;
    }

}
