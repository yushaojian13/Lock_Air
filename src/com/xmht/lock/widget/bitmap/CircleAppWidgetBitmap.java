
package com.xmht.lock.widget.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import com.xmht.lock.widget.formatter.CircleTimeFormatter;
import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.ysj.tools.debug.LOG;

public class CircleAppWidgetBitmap extends AppWidgetBitmap {

    public CircleAppWidgetBitmap(Context ctx, Time time) {
        super(ctx, time);
    }

    @Override
    public ITimeFormatter getTimeFormatter() {
        return new CircleTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        Paint paint = initPaint();

        String timeText = timeFormatter.getTime();
        String weekText = timeFormatter.getWeek();
        String dateText = timeFormatter.getDate();

        float timeSize = 70 * scaleFactor;
        float weekSize = 50 * scaleFactor;
        float dateSize = 30 * scaleFactor;
        float ringWidth = 4 * scaleFactor;
        float lineWidth = 1.5f * scaleFactor;
        float space = 15 * scaleFactor;

        Rect timeRect = computeRect(paint, timeText, timeSize);
        Rect weekRect = computeRect(paint, weekText, weekSize);
        Rect dateRect = computeRect(paint, dateText, dateSize);

        float cr1 = timeRect.width();
        float cr2 = weekRect.width();
        float cr3 = timeRect.height() + space + weekRect.height();
        float circleRadius = Math.max(Math.max(cr1, cr2), cr3) * 0.75f;
        
        float bw1 = dateRect.width() * 3f + space * 2;
        float bw2 = circleRadius * 3f;
        float bh1 = circleRadius * 2f + space + dateRect.height();

        int bitmapWidth = (int) (Math.max(bw1, bw2) * 1.1f);
        int bitmapHeight = (int) (bh1 * 1.1f);

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(0xff3a98a4);
        float circleX = bitmap.getWidth() / 2;
        float circleY = circleRadius + ringWidth + bitmap.getHeight() * 0.05f;
        canvas.drawCircle(circleX, circleY, circleRadius, paint);

        paint.setStrokeWidth(lineWidth);
        float lineStartX = bitmap.getWidth() * 0.05f;
        float lineStartY = circleRadius * 2 + ringWidth + lineWidth + bitmap.getHeight() * 0.05f;
        float lineEndX = bitmap.getWidth() * 0.95f;
        float lineEndY = lineStartY;
        canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paint);

        paint.setStyle(Paint.Style.FILL);

        if (showShadow) {
            paint.setShadowLayer(5, 5, 5, 0x55000000);
        }
        paint.setTextSize(timeSize);
        paint.setColor(primaryColor);
        float timeYOffset = (circleRadius * 2 - timeRect.height() - weekRect.height()) / 2;
        float timeX = -timeRect.left + (bitmap.getWidth() - timeRect.width()) / 2;
        float timeY = -timeRect.top - space + timeYOffset + bitmap.getHeight() * 0.05f + ringWidth;
        canvas.drawText(timeText, timeX, timeY, paint);

        if (showShadow) {
            paint.setShadowLayer(3, 3, 3, 0x55000000);
        }
        paint.setTextSize(weekSize);
        paint.setColor(secondaryColor);
        float weekX = -weekRect.left + (bitmap.getWidth() - weekRect.width()) / 2;
        float weekY = timeRect.height() - weekRect.top + space + timeYOffset + bitmap.getHeight() * 0.05f
                + ringWidth;
        canvas.drawText(weekText, weekX, weekY, paint);

        if (showShadow) {
            paint.setShadowLayer(1.5f, 1.5f, 1.5f, 0x55000000);
        }
        paint.setTextSize(dateSize);
        paint.setColor(secondaryColor);
        float dateX = -dateRect.left + (bitmap.getWidth() - dateRect.width()) / 2;
        float dateY = bitmap.getHeight() * 0.95f - dateRect.height() - dateRect.top;
        canvas.drawText(dateText, dateX, dateY, paint);

        paint.setShadowLayer(0, 0, 0, 0x55000000);
        Time tmpTime = new Time();

        tmpTime.set(time.toMillis(false) - 24 * 60 * 60 * 1000);
        timeFormatter.setTime(tmpTime);
        dateText = timeFormatter.getDate();
        paint.setColor(Color.GRAY);
        dateX = -dateRect.left + bitmap.getWidth() * 0.05f;
        dateY = bitmap.getHeight() * 0.95f - dateRect.height() - dateRect.top;
        canvas.drawText(dateText, dateX, dateY, paint);

        tmpTime.set(time.toMillis(false) + 24 * 60 * 60 * 1000);
        timeFormatter.setTime(tmpTime);
        dateText = timeFormatter.getDate();
        paint.setColor(Color.GRAY);
        dateX = -dateRect.left + bitmap.getWidth() * 0.95f - dateRect.width();
        dateY = bitmap.getHeight() * 0.95f - dateRect.height() - dateRect.top;
        canvas.drawText(dateText, dateX, dateY, paint);

        timeFormatter.setTime(time);
        LOG.v("");
        return bitmap;
    }
}
