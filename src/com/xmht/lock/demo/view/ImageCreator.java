package com.xmht.lock.demo.view;

import com.xmht.lock.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageCreator {
    private String text;
    private float textSize;
    
    private Rect textRect;
    private Paint paint;
    private Context context;
    
    public ImageCreator(Context context, String text, float textSize) {
        this.context = context;
        this.text = text;
        this.textSize = Utils.dip2px(context, textSize);
        
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Utils.createTypeface(context, "fonts/Helvetica-Light.ttf", false));
        
        computeRect();
    }

    private void computeRect() {
        paint.setTextSize(textSize);
        textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);
    }
    
    public void setText(String text) {
        this.text = text;
        computeRect();
    }
    
    public void setTextSize(float size) {
        this.textSize = Utils.dip2px(context, textSize);
        computeRect();
    }

    public Drawable createDrawable() {
        int bitmapWidth = textRect.width();
        int bitmapHeight = textRect.height();
        
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, -textRect.left, -textRect.top, paint);
        
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }
}
