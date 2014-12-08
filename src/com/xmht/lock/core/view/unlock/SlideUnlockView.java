
package com.xmht.lock.core.view.unlock;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.xmht.lock.core.view.UnlockView;
import com.ysj.tools.anim.AnimationBundle;
import com.ysj.tools.anim.Tweener;

public class SlideUnlockView extends UnlockView {

    private Paint paint;

    private static final int ANIMATION_DURATION = 100;
    private AnimationBundle animationBundle = new AnimationBundle();

    public SlideUnlockView(Context context) {
        this(context, null);
    }
    public SlideUnlockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideUnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0xffbdbdbd);
        canvas.drawRect(getWidth() * 0.05f, getHeight() * 0.45f, getWidth() * 0.95f,
                getHeight() * 0.55f, paint);
        paint.setColor(0xff4b4b4b);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(offsetX, 0);
        canvas.drawRect(getWidth() * 0.05f, getHeight() * 0.45f, getWidth() * 0.15f,
                getHeight() * 0.55f, paint);
        canvas.restore();
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
                moveX = event.getX();
                offsetX = (moveX > downX) ? (moveX - downX) : 0;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (offsetX > getWidth() * 0.8f) {
                    unlock();
                } else {
                    reset();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOffsetX(float x) {
        offsetX = x;
    }

    @Override
    protected void reset() {
        animationBundle.cancel();
        animationBundle.add(Tweener.to(this, ANIMATION_DURATION,
                "ease", new LinearInterpolator(),
                "offsetX", new float[] {
                        offsetX, 0
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
}
