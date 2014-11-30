
package com.xmht.lock.widget.bitmap;

import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.xmht.lock.widget.formatter.LayerTimeFormatter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

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

        float space = 15;

        if (showShadow) {
            paint.setShadowLayer(5, 5, 5, 0x55000000);
        }

        paint.setFakeBoldText(true);
        Rect hourRect = computeRect(paint, hour, hourSize);
        paint.setFakeBoldText(false);
        Rect minuteRect = computeRect(paint, minute, minuteSize);
        Rect weekRect = computeRect(paint, week, weekSize);
        Rect dateRect = computeRect(paint, date, dateSize);

        float bitmapWidth = Math.max(hourRect.width(), weekRect.width() + space + dateRect.width());
        bitmapWidth = Math.max(bitmapWidth, minuteRect.width());
        float bitmapHeight = hourRect.height() + space + minuteRect.height() + space
                + weekRect.height();

        Bitmap bitmap = Bitmap.createBitmap((int) bitmapWidth, (int) bitmapHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        float delta = (hourRect.width() - minuteRect.width()) / 2;
        float hourDelta = delta < 0 ? -delta : 0;
        float minuteDelta = delta > 0 ? delta : 0;

        paint.setFakeBoldText(true);
        paint.setTextSize(hourSize);
        paint.setColor(primaryColor);
        canvas.drawText(hour, hourDelta - hourRect.left, -hourRect.top, paint);

        paint.setFakeBoldText(false);

        paint.setTextSize(minuteSize);
        paint.setColor(secondaryColor);
        canvas.drawText(minute, minuteDelta - minuteRect.left,
                hourRect.height() + space - minuteRect.top, paint);

        paint.setTextSize(weekSize);
        paint.setColor(secondaryColor);
        canvas.drawText(week, -weekRect.left, hourRect.height() + space + minuteRect.height()
                + space - weekRect.top, paint);

        paint.setTextSize(dateSize);
        paint.setColor(secondaryColor);
        canvas.drawText(date, weekRect.width() + space - dateRect.left, hourRect.height() + space
                + minuteRect.height()
                + space - dateRect.top, paint);
        return bitmap;
    }

}
