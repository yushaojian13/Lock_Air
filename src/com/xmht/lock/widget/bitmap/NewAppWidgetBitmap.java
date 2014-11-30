
package com.xmht.lock.widget.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.xmht.lock.widget.formatter.NewTimeFormatter;

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

        float timeSize = 120 * scaleFactor;
        float weekSize = 35 * scaleFactor;
        float dateSize = 35 * scaleFactor;
        float amSize = 25 * scaleFactor;
        float space = 15 * scaleFactor;

        Rect timeRect = computeRect(paint, time, timeSize);
        Rect weekRect = computeRect(paint, week, weekSize);
        Rect dateRect = computeRect(paint, date, dateSize);
        Rect amRect = computeRect(paint, am, amSize);

        float bw1 = weekRect.width() + space + dateRect.width();
        float bw2 = timeRect.width() + space + amRect.width();

        int bitmapWidth = (int) (Math.max(bw1, bw2) * 1.1f);
        int bitmapHeight = (int) ((weekRect.height() + space + timeRect.height()) * 1.1f);

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        if (showShadow) {
            paint.setShadowLayer(2.5f, 2.5f, 2.5f, 0x55000000);
        }
        paint.setTextSize(weekSize);
        paint.setColor(primaryColor);
        float weekX = -weekRect.left + bitmap.getWidth() * 0.05f;
        float weekY = -weekRect.top + bitmap.getHeight() * 0.05f;
        canvas.drawText(week, weekX, weekY, paint);
        float dateX = weekRect.width() + space - dateRect.left + bitmap.getWidth() * 0.05f;
        float dateY = -dateRect.top + bitmap.getHeight() * 0.05f;
        canvas.drawText(date, dateX, dateY, paint);

        if (showShadow) {
            paint.setShadowLayer(4.5f, 4.5f, 4.5f, 0x55000000);
        }
        paint.setTextSize(timeSize);
        paint.setColor(primaryColor);
        float timeX = -timeRect.left + bitmap.getWidth() * 0.05f;
        float timeY = weekRect.height() + space - timeRect.top + bitmap.getHeight() * 0.05f;
        canvas.drawText(time, timeX, timeY, paint);

        if (showShadow) {
            paint.setShadowLayer(1.5f, 1.5f, 1.5f, 0x55000000);
        }
        paint.setTextSize(amSize);
        paint.setColor(secondaryColor);
        float amX = timeRect.width() + space - amRect.left + bitmap.getWidth() * 0.05f;
        float amY;
        if (timeFormatter.isAm()) {
            amY = weekRect.height() + space - amRect.top + bitmap.getHeight() * 0.05f;
            canvas.drawText(am, amX, amY, paint);
        } else {
            amY = weekRect.height() + space - timeRect.top + bitmap.getHeight() * 0.05f;
            canvas.drawText(am, amX, amY, paint);
        }

        return bitmap;
    }

}
