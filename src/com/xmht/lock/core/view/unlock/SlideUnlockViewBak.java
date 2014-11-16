
package com.xmht.lock.core.view.unlock;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.xmht.lock.core.view.UnlockView;
import com.xmht.lock.core.view.anim.AnimationBundle;
import com.xmht.lock.core.view.anim.Tweener;
import com.xmht.lock.core.view.common.TargetDrawable;
import com.xmht.lockair.R;

public class SlideUnlockViewBak extends UnlockView {

    private Paint paint;
    private TargetDrawable bgDrawable;
    private TargetDrawable slideDrawable;

    private static final int ANIMATION_DURATION = 100;
    private AnimationBundle animationBundle = new AnimationBundle();

    public SlideUnlockViewBak(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        bgDrawable = new TargetDrawable();
        slideDrawable = new TargetDrawable();
        bgDrawable.setDrawable(getResources(), R.drawable.slide_unlock_bg);
        slideDrawable.setDrawable(getResources(), R.drawable.slide_unlock_touch_bg);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void setBg(Drawable drawable) {
        bgDrawable.setDrawable(drawable);
    }

    public void setSlide(Drawable drawable) {
        slideDrawable.setDrawable(drawable);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        float scale = (float) getWidth() / (float) bgDrawable.getWidth();
        bgDrawable.setScaleX(scale);
        bgDrawable.setPositionX(getWidth() * 0.5f);
        bgDrawable.setPositionY(getHeight() * 0.5f);
        slideDrawable.setPositionX(slideDrawable.getWidth() * 0.5f);
        slideDrawable.setPositionY(getHeight() * 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bgDrawable.draw(canvas);
        slideDrawable.draw(canvas);
    }

    private boolean move = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < slideDrawable.getWidth()) {
                    move = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (move) {
                    float x = event.getX();
                    slideDrawable.setX(x);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                move = false;
                reset();
                if (event.getX() + slideDrawable.getWidth() > getWidth()) {
                    unlock();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        animationBundle.cancel();
        animationBundle.add(Tweener.to(slideDrawable, ANIMATION_DURATION,
                "ease", new LinearInterpolator(),
                "X", new float[] {
                        slideDrawable.getX(), 0f
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
