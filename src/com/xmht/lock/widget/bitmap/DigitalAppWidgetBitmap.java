
package com.xmht.lock.widget.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;

import com.xmht.lock.widget.formatter.DigitalTimeFormatter;
import com.xmht.lock.widget.formatter.ITimeFormatter;
import com.xmht.lockair.R;

public class DigitalAppWidgetBitmap extends AppWidgetBitmap {

    public DigitalAppWidgetBitmap(Context context, Time time) {
        super(context, time);
    }

    @Override
    public ITimeFormatter getTimeFormatter() {
        return new DigitalTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        Paint paint = initPaint();

        String timeText = timeFormatter.getTime();
        String[] weekArray = context.getResources().getStringArray(R.array.array_week_short);

        float timeSize = 150 * scaleFactor;
        float weekSize = 30 * scaleFactor;
        float space = 15 * scaleFactor;

        Rect timeRect = computeRect(paint, timeText, timeSize);
        Rect[] weekRects = new Rect[weekArray.length];
        int weekTotalWidth = 0;
        int weekMaxHeight = 0;
        int weekMaxTop = 0;
        for (int i = 0; i < weekArray.length; i++) {
            weekRects[i] = computeRect(paint, weekArray[i], weekSize);
            weekTotalWidth += weekRects[i].width();
            if (weekRects[i].height() > weekMaxHeight) {
                weekMaxHeight = weekRects[i].height();
            }
            if (weekRects[i].top < weekMaxTop) {
                weekMaxTop = weekRects[i].top;
            }
        }

        float dw1 = timeRect.width();
        float dw2 = weekTotalWidth + space * (weekArray.length - 1);

        int bitmapWidth = (int) (Math.max(dw1, dw2) * 1.1f);
        int bitmapHeight = (int) ((timeRect.height() + space + weekMaxHeight) * 1.1f);

        float weekDelta = dw1 > dw2 ? (dw1 - dw2) / 6 : 0;
        float timeDelta = dw1 > dw2 ? 0 : (dw2 - dw1) * 0.5f;
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0x66ffffff);

        paint.setTextSize(weekSize);
        float weekX;
        float weekY;
        int week = (time.weekDay - 1 + weekArray.length) % weekArray.length;
        for (int i = 0; i < weekArray.length; i++) {
            int offset = 0;
            for (int j = 0; j < i; j++) {
                offset += weekRects[j].width();
            }
            if (i == week) {
                if (showShadow) {
                    paint.setShadowLayer(1.5f, 1.5f, 1.5f, 0x55000000);
                }
                paint.setColor(secondaryColor);
            } else {
                paint.setShadowLayer(0f, 0f, 0f, 0x55000000);
                paint.setColor(Color.GRAY);
            }
            weekX = bitmapWidth * 0.05f + -weekRects[i].left + (space + weekDelta) * i + offset;
            weekY = bitmap.getHeight() * 0.05f - weekRects[i].top
                    + (weekMaxHeight - weekRects[i].height()) * 0.5f;
            canvas.drawText(weekArray[i], weekX, weekY, paint);
        }

        if (showShadow) {
            paint.setShadowLayer(5f, 5f, 5f, 0x55000000);
        }
        paint.setTextSize(timeSize);
        paint.setColor(primaryColor);
        float timeX = bitmapWidth * 0.05f - timeRect.left + timeDelta;
        float timeY = bitmap.getHeight() * 0.05f - weekMaxTop + space - timeRect.top;
        canvas.drawText(timeText, timeX, timeY, paint);

        return bitmap;
    }
}
