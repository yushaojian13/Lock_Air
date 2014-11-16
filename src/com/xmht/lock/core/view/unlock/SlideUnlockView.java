
package com.xmht.lock.core.view.unlock;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.xmht.lock.core.view.UnlockView;
import com.xmht.lock.core.view.anim.AnimationBundle;
import com.xmht.lock.core.view.anim.Tweener;

public class SlideUnlockView extends UnlockView {

    private Paint paint;

    private static final int ANIMATION_DURATION = 100;
    private AnimationBundle animationBundle = new AnimationBundle();

    public SlideUnlockView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(getWidth() * 0.05f, getHeight() * 0.45f, getWidth() * 0.95f,
                getHeight() * 0.55f, paint);
        paint.setColor(Color.DKGRAY);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(offsetX, 0);
        canvas.drawRect(getWidth() * 0.05f, getHeight() * 0.45f, getWidth() * 0.15f,
                getHeight() * 0.55f, paint);
        canvas.restore();
    }

    private float offsetX;
    private float downX = 0f;
    private float moveX = 0f;

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

    private void reset() {
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
