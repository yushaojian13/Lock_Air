
package com.xmht.lock.core.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lock.core.debug.LOG;
import com.xmht.lock.core.view.listener.UnlockListener;

public class UnlockView extends View {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.postDelayed(longPressed, LONG_PRESS_TIME);
                break;
            case MotionEvent.ACTION_MOVE:
                // _handler.removeCallbacks(_longPressed);
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
        Intent intent = new Intent(LockActivity.ACTION_UNLOCK);
        getContext().sendBroadcast(intent);
    }
}
