
package com.xmht.lock.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xmht.lock.core.debug.LOG;
import com.xmht.lock.core.utils.Utils;
import com.xmht.lock.core.view.listener.UnlockListener;

public abstract class UnlockView extends View {
    private static int LONG_PRESS_TIME = 500;

    private final Handler handler = new Handler();
    private Runnable longPressed = new Runnable() {
        public void run() {
            LOG.e(this.getClass().getSimpleName());
            if (unlockListener != null) {
                unlockListener.onLongPress();
            }
        }
    };

    public UnlockView(Context context) {
        this(context, null);
    }

    public UnlockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                handler.postDelayed(longPressed, LONG_PRESS_TIME);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX() - downX;
                moveY = event.getRawY() - downY;
                if (moveX * moveX + moveY * moveY > Utils
                        .getDW(getContext()) * Utils.getDW(getContext()) * 0.01) {
                    handler.removeCallbacks(longPressed);
                }
                break;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(longPressed);
                break;
        }

        return true;
    }

    private UnlockListener unlockListener;

    public void setUnlockListener(UnlockListener listener) {
        unlockListener = listener;
    }

    protected void unlock() {
        if (unlockListener != null) {
            unlockListener.onUnlock();
        } else {
            reset();
        }
    }
    
    protected abstract void reset();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = Utils.getDW(getContext());
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = Utils.getDH(getContext()) / getDefaultHeightDenominator();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    protected int getDefaultHeightDenominator() {
        return 10;
    }
}
