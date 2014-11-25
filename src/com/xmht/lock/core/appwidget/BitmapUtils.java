
package com.xmht.lock.core.appwidget;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.Utils;
import com.xmht.lockair.R;

public class BitmapUtils {

    /**
     * call in a work thread
     */
    public static Bitmap createBitmap(Context context, String text, String font) {
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
        String[] weekArray = context.getResources().getStringArray(R.array.array_week);
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
        for (int i = 0; i < weekArray.length; i++) {
            int offset = 0;
            for (int j = 0; j < i; j++) {
                offset += weekRects[j].width();
            }
            if (i == 1) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.GRAY);
            }
            canvas.drawText(weekArray[i], -bound.left + delta * i + offset, -weekTop, paint);
        }
        
        paint.setTextSize(150);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, -bound.left,  -weekTop -bound.top + space, paint);

        return bitmap;
    }

    /**
     * call in a work thread
     */
    public static void saveNextBitmap(Context context, Bitmap bitmap, String fileName) {
        OutputStream os = null;
        try {
            os = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(CompressFormat.PNG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * call in a work thread
     */
    public static Bitmap loadCurBitmap(Context context, String fileName) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}
