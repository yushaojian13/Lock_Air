
package com.xmht.lock.core.view.unlock;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.xmht.lock.core.view.UnlockView;
import com.xmht.lockair.R;
import com.ysj.tools.anim.AnimationBundle;
import com.ysj.tools.anim.Tweener;
import com.ysj.tools.utils.Displays;

public class RainUnlockView extends UnlockView {
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

    private static final int MARK_COLOR = 0x66FFFFFF;

    private static final int TEXT_COLOR = 0xFF363636;

    public RainUnlockView(Context context) {
        this(context, null);
    }
    
    public RainUnlockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainUnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textSize = Displays.dip2px(getContext(), 18);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        
        textPaddingLeft = Displays.dip2px(getContext(), 25);
        textColor = TEXT_COLOR;
        text = context.getResources().getString(R.string.unlock);
        
        waterMarkColor = MARK_COLOR;
        
        setBackgroundColor(Color.TRANSPARENT);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        waterMarkHeight = getHeight();
        waterMarkWidth = getWidth() * 0.7f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWaterMark(canvas);
        drawText(canvas);
        drawTarget(canvas);
    }

    private void drawWaterMark(Canvas canvas) {
        paint.setColor(waterMarkColor);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        float tX = -waterMarkHeight / 2;
        float tY = 0;
        canvas.translate(tX, tY);
        RectF rectF = new RectF(0, 0, waterMarkWidth + waterMarkHeight / 2 + offsetX,
                waterMarkHeight);
        canvas.drawRoundRect(rectF, waterMarkHeight / 2, waterMarkHeight / 2, paint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        paint.setColor(textColor);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        float tX = textPaddingLeft + offsetX;
        float tY = waterMarkHeight / 2 - (paint.descent() - paint.ascent()) * 0.5f;
        canvas.translate(tX, tY);
        canvas.drawText(text, 0, -paint.ascent() * 1.05f, paint);
        canvas.restore();
    }

    private void drawTarget(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        float tX = textPaddingLeft + waterMarkHeight / 2 + paint.measureText(text) + offsetX;
        float tY = waterMarkHeight / 2;
        canvas.translate(tX, tY);
        float textHeight = paint.descent() - paint.ascent();
        double angle = Math.PI * 100 / 180;
        float deltaX = (float) (textHeight * 0.45f * Math.cos(angle * 0.5));
        float deltaY = (float) (textHeight * 0.45f * Math.sin(angle * 0.5));
        paint.setStrokeWidth(2.5f);
        canvas.drawLine(0, -deltaY, deltaX, 0, paint);
        canvas.drawLine(deltaX, 0, 0, +deltaY, paint);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() > waterMarkHeight) {
                    break;
                }
                moveX = event.getX();
                offsetX = (moveX > downX) ? (moveX - downX) : 0;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (waterMarkWidth + offsetX > getWidth()) {
                    unlock();
                } else {
                    reset();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setWaterMarkX(float translationX) {
        offsetX = translationX;
    }

    @Override
    protected void reset() {
        animationBundle.cancel();
        animationBundle.add(Tweener.to(this, ANIMATION_DURATION,
                "ease", new LinearInterpolator(),
                "waterMarkX", new float[] {
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
    
    protected int getDefaultHeightDenominator() {
        return 13;
    };

}
