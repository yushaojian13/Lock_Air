
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

import com.xmht.lock.core.appwidget.formatter.CircleTimeFormatter;
import com.xmht.lock.core.appwidget.formatter.ITimeFormater;
import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.Utils;

public class CircleAppWidgetBitmap implements AppWidgetBitmap {

    private Context context;
    private String font;
    private Time mTime;
    private ITimeFormater timeFormater;
    private float scaleFactor = 1.5f;

    public CircleAppWidgetBitmap(Context context, Time time) {
        this.context = context;
        this.mTime = new Time(time);
        this.font = "fonts/CaviarDreams.ttf";
        this.timeFormater = new CircleTimeFormatter(context, time);
    }

    @Override
    public Bitmap create() {
        long now = System.currentTimeMillis();
        String time = timeFormater.getTime();
        String week = timeFormater.getWeek();
        String date = timeFormater.getDate();
        int space = 15;
        Typeface typeface = Utils.createTypeface(context, font);
        LOG.v("create typeface uses " + (System.currentTimeMillis() - now) + " millis");

        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        // paint.setShadowLayer(5, 5, 5, 0x55000000);

        paint.setTextSize(70 * scaleFactor);
        Rect timeRect = new Rect();
        paint.getTextBounds(time, 0, time.length(), timeRect);

        paint.setTextSize(50 * scaleFactor);
        Rect weekRect = new Rect();
        paint.getTextBounds(week, 0, week.length(), weekRect);
        
        paint.setTextSize(30 * scaleFactor);
        Rect dateRect = new Rect();
        paint.getTextBounds(date, 0, date.length(), dateRect);
        
        float circleRadius = timeRect.width() * 0.65f;

        Bitmap bitmap = Bitmap.createBitmap((int)circleRadius * 4,
                (int)circleRadius * 2 + space + dateRect.height(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0x6600ff00);
        
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(0xff3a98a4);
        float circleX = bitmap.getWidth() / 2;
        canvas.drawCircle(circleX, circleRadius, circleRadius, paint);
        canvas.drawLine(0, circleRadius * 2, bitmap.getWidth(), circleRadius * 2, paint);
        
        paint.setStyle(Paint.Style.FILL);
        
        paint.setTextSize(70 * scaleFactor);
        paint.setColor(Color.WHITE);
        float timeYOffset = (circleRadius * 2 - timeRect.height() - weekRect.height()) / 2;
        canvas.drawText(time, -timeRect.left + (bitmap.getWidth() - timeRect.width()) / 2, - timeRect.top + timeYOffset, paint);

        paint.setTextSize(50 * scaleFactor);
        paint.setColor(Color.WHITE);
        canvas.drawText(week, -weekRect.left + (bitmap.getWidth() - weekRect.width()) / 2, timeRect.height() - weekRect.top + timeYOffset, paint);
        
        paint.setTextSize(30 * scaleFactor);
        paint.setColor(Color.WHITE);
        canvas.drawText(date, -dateRect.left + (bitmap.getWidth() - dateRect.width()) / 2, bitmap.getHeight() - dateRect.height() - dateRect.top, paint);
        
        mTime.monthDay -= 1;
        date = timeFormater.getDate();
        canvas.drawText(date, -dateRect.left, bitmap.getHeight() - dateRect.height() - dateRect.top, paint);
        
        mTime.monthDay += 2;
        date = timeFormater.getDate();
        canvas.drawText(date, -dateRect.left + bitmap.getWidth() - dateRect.width(), bitmap.getHeight() - dateRect.height() - dateRect.top, paint);
        
        LOG.v("create " + timeFormater.getYear() + "/" + timeFormater.getDate()+ " " + timeFormater.getTime() + ":" + timeFormater.getSecond() + " bitmap uses " + (System.currentTimeMillis() - now) + " millis");
        return bitmap;
    }

}
