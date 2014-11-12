
package com.xmht.lock.core.view.unlock;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xmht.lock.core.utils.Utils;
import com.xmht.lock.core.view.anim.AnimationBundle;
import com.xmht.lock.core.view.anim.Tweener;
import com.xmht.lock.core.view.common.TargetDrawable;
import com.xmht.lockair.R;

public class RainUnlockView extends View {
    private TargetDrawable arrowDrawable;
    private Paint paint;
    private String text;
    private float textSize;
    private int textColor;
    private float textPaddingLeft;
    private float waterMarkHeight;
    private float waterMarkWidth;
    private int waterMarkColor;

    private static final int ANIMATION_DURATION = 100;
    private AnimationBundle animationBundle = new AnimationBundle();

    private float scaleX;
    private float scaleY;

    public RainUnlockView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);

        scaleX = getResources().getDisplayMetrics().widthPixels / 720f;
        scaleY = getResources().getDisplayMetrics().heightPixels / 1280f;

        textSize = Utils.dip2px(getContext(), 18);
        textPaddingLeft = Utils.dip2px(getContext(), 25);
        textColor = 0xFF363636;
        text = context.getResources().getString(R.string.unlock);

        waterMarkHeight = 98 * scaleY;
        waterMarkWidth = 490 * scaleX;
        waterMarkColor = 0x66FFFFFF;

        setBackgroundColor(Color.TRANSPARENT);
    }
    
    public void setDrawable(Drawable drawable) {
        arrowDrawable = new TargetDrawable();
        arrowDrawable.setDrawable(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWaterMark(canvas);
        canvas.translate(offsetX, 0);
        drawText(canvas, text);
        drawTarget(canvas);
    }

    private void drawText(Canvas canvas, String text) {
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(textPaddingLeft, waterMarkHeight / 2 - (paint.descent() - paint.ascent())
                * 0.5f);
        canvas.drawText(text, 0, -paint.ascent() * 1.1f, paint);
        canvas.restore();
    }

    private void drawWaterMark(Canvas canvas) {
        paint.setColor(waterMarkColor);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(-waterMarkHeight / 2, 0);
        RectF rectF = new RectF(0, 0, waterMarkWidth + waterMarkHeight / 2 + offsetX,
                waterMarkHeight);
        canvas.drawRoundRect(rectF, waterMarkHeight / 2, waterMarkHeight / 2, paint);
        canvas.restore();
    }

    private void drawTarget(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(textPaddingLeft + waterMarkHeight + paint.measureText(text),
                waterMarkHeight / 2);
        canvas.scale(scaleX, scaleX);
        arrowDrawable.draw(canvas);
        canvas.restore();
    }

    public void setFont(String path) {
        try {
            paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), path));
        } catch (Exception e) {
            return;
        }
        invalidate();
    }

    private float offsetX;
    private float downX = 0f;
    private float moveX = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() > waterMarkHeight) {
                    break;
                }
                moveX = event.getX();
                offsetX = (moveX > downX) ? (moveX - downX) : 0;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (waterMarkWidth + offsetX > getWidth()) {
                    if (unlockListener != null) {
                        unlockListener.onUnlock();
                    }
                }
                reset();
                return true;
        }
        
        return super.onTouchEvent(event);
    }

    public void setTranslationX(float translationX) {
        offsetX = translationX;
    }

    private void reset() {
        animationBundle.cancel();
        animationBundle.add(Tweener.to(this, ANIMATION_DURATION,
                "ease", new LinearInterpolator(),
                "translationX", new float[] {
                        offsetX, 0f
                },
                "onUpdate", mUpdateListener
                ));

        animationBundle.start();
    }

    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    };

    private UnlockListener unlockListener;

    public void setUnlockListener(UnlockListener listener) {
        unlockListener = listener;
    }

    public interface UnlockListener {
        public void onUnlock();
    }

}
