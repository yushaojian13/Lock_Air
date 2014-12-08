
package com.xmht.lock.widget.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.xmht.lock.widget.formatter.LayerTimeFormatter;
import com.ysj.tools.debug.LOG;

public class LayerAppWidgetBitmap extends AppWidgetBitmap {

    public LayerAppWidgetBitmap(Context context, Time time) {
        super(context, time);
    }

    @Override
    public ITimeFormatter getTimeFormatter() {
        return new LayerTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        Paint paint = initPaint();

        String hour = timeFormatter.getHour();
        String minute = timeFormatter.getMinute();
        String week = timeFormatter.getWeek();
        String date = timeFormatter.getDate();

        float hourSize = 160 * scaleFactor;
        float minuteSize = 160 * scaleFactor;
        float weekSize = 25 * scaleFactor;
        float dateSize = 25 * scaleFactor;
        float space = 15 * scaleFactor;

        paint.setFakeBoldText(true);
        Rect hourRect = computeRect(paint, hour, hourSize);
        paint.setFakeBoldText(false);
        Rect minuteRect = computeRect(paint, minute, minuteSize);
        Rect weekRect = computeRect(paint, week, weekSize);
        Rect dateRect = computeRect(paint, date, dateSize);

        float bw1 = hourRect.width();
        float bw2 = weekRect.width();
        float bw3 = weekRect.width() + space + dateRect.width();

        int bitmapWidth = (int) (Math.max(Math.max(bw1, bw2), bw3) * 1.1f);
        int bitmapHeight = (int) ((hourRect.height() + space + minuteRect.height() + space
                + weekRect.height()) * 1.1f);

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        float delta = (hourRect.width() - minuteRect.width()) / 2;
        float hourDelta = delta < 0 ? -delta : 0;
        float minuteDelta = delta > 0 ? delta : 0;

        if (showShadow) {
            paint.setShadowLayer(5, 5, 5, 0x55000000);
        }
        paint.setFakeBoldText(true);
        paint.setTextSize(hourSize);
        paint.setColor(primaryColor);
        float hourX = -hourRect.left + hourDelta + bitmap.getWidth() * 0.05f;
        float hourY = -hourRect.top + bitmap.getHeight() * 0.05f;
        canvas.drawText(hour, hourX, hourY, paint);

        paint.setFakeBoldText(false);

        paint.setTextSize(minuteSize);
        paint.setColor(secondaryColor);
        float minuteX = minuteDelta - minuteRect.left + bitmap.getWidth() * 0.05f;
        float minuteY = hourRect.height() + space - minuteRect.top + bitmap.getHeight() * 0.05f;
        canvas.drawText(minute, minuteX, minuteY, paint);

        if (showShadow) {
            paint.setShadowLayer(1, 1, 1, 0x55000000);
        }
        paint.setTextSize(weekSize);
        paint.setColor(secondaryColor);
        float weekX = -weekRect.left + bitmap.getWidth() * 0.05f;
        float weekY = hourRect.height() + space + minuteRect.height() + space - weekRect.top
                + bitmap.getHeight() * 0.05f;
        canvas.drawText(week, weekX, weekY, paint);

        paint.setTextSize(dateSize);
        paint.setColor(secondaryColor);
        float dateX = weekRect.width() + space - dateRect.left + bitmap.getWidth() * 0.05f;
        float dateY = hourRect.height() + space + minuteRect.height() + space - dateRect.top
                + bitmap.getHeight() * 0.05f;
        canvas.drawText(date, dateX, dateY, paint);
        LOG.v("");
        return bitmap;
    }

}
