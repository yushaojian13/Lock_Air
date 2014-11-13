
package com.xmht.lock.core.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lock.core.utils.SPHelper;
import com.xmht.lock.core.view.listener.SwipeListener;
import com.xmht.lock.core.view.unlock.RainUnlockView;
import com.xmht.lock.core.view.unlock.RainUnlockView.UnlockListener;
import com.xmht.lock.core.view.widget.TimeDateWidget1;
import com.xmht.lock.core.view.widget.TimeDateWidget4;
import com.xmht.lock.core.view.widget.TimeDateWidget5;
import com.xmht.lock.core.view.widget.TimeDateWidget6;
import com.xmht.lock.core.view.widget.TimeDateWidget7;
import com.xmht.lock.core.view.widget.TimeDateWidget8;
import com.xmht.lock.core.view.widget.TimeDateWidget9;
import com.xmht.lockair.R;

public class SlideLayout extends WidgetBase implements SwipeListener {
    int screenWidth;
    int screenHeight;

    int[] wallpapers;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setView() {
        wallpaperIndex = SPHelper.get("wallpaper", 0);
        widgetIndex = SPHelper.get("time", 0);

        wallpapers = new int[] {
                R.drawable.chunv, R.drawable.shuangzi, R.drawable.jiniu,
                R.drawable.baiyang, R.drawable.mojie,
                R.drawable.sheshou, R.drawable.shizi,
                R.drawable.shuangyu, R.drawable.shuiping, R.drawable.tianping,
                R.drawable.tianxie, R.drawable.juxie
        };
        timevViews = new TimeDateWidget[] {
                new TimeDateWidget1(getContext()), new TimeDateWidget4(getContext()),
                new TimeDateWidget5(getContext()), new TimeDateWidget6(getContext()),
                new TimeDateWidget7(getContext()), new TimeDateWidget8(getContext()),
                new TimeDateWidget9(getContext())
        };
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        addWallpaper();
        addTimeView();
        addUnlockView();
    }

    private ImageView wallpaperIV;

    private void addWallpaper() {
        wallpaperIV = new ImageView(getContext());
        wallpaperIV.setScaleType(ScaleType.CENTER_CROP);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        wallpaperIV.setLayoutParams(lp);
        changeWallpaper();
        addView(wallpaperIV);
    }

    TimeDateWidget timeView;
    TimeDateWidget[] timevViews;

    private void addTimeView() {
        if (timeView != null) {
            timeView.onStop();
            removeView(timeView);
        }

        timeView = timevViews[widgetIndex];
        timeView.setHorizontalSlideListner(this);
        timeView.onStart();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.topMargin = screenHeight / 20;
        timeView.setLayoutParams(lp);
        addView(timeView);
    }

    private void addUnlockView() {
        RainUnlockView rainUnlockView = new RainUnlockView(getContext());
        rainUnlockView.setDrawable(getResources().getDrawable(R.drawable.right_arrow));
        if (getContext() instanceof UnlockListener) {
            UnlockListener unlockListener = (UnlockListener) getContext();
            rainUnlockView.setUnlockListener(unlockListener);
        }
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, screenHeight / 10);
        lp.bottomMargin = screenHeight / 6;
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rainUnlockView.setLayoutParams(lp);
        addView(rainUnlockView);
    }

    float downX = 0;
    float downY = 0;
    float moveX;
    float moveY;
    float upX;
    float upY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX() - downX;
                moveY = event.getRawY() - downY;
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getRawX();
                upY = event.getRawY();

                if (downY > screenHeight * 0.95 && upY < screenHeight * 0.85) {
                    showMenuDialog();
                } else if (upX - downX < -getWidth() * 0.2f) {
                    wallpaperIndex++;
                    wallpaperIndex = wallpaperIndex % wallpapers.length;
                    changeWallpaper();
                } else if (upX - downX > getWidth() * 0.2f) {
                    wallpaperIndex--;
                    wallpaperIndex = (wallpaperIndex + wallpapers.length) % wallpapers.length;
                    changeWallpaper();
                }
                break;
        }

        return true;
    }

    private int widgetIndex;
    private int wallpaperIndex;

    private void changeWallpaper() {
        wallpaperIV.setImageResource(wallpapers[wallpaperIndex]);
        SPHelper.set("wallpaper", wallpaperIndex);
    }

    @Override
    public void onStart() {
        timeView.onStart();
    }

    @Override
    public void onStop() {
        timeView.onStop();
        SPHelper.set("time", widgetIndex);
    }

    private void showMenuDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.app_name)
                .setItems(R.array.menu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showAboutDialog();
                                break;
                            case 1:
                                Intent intent = new Intent(LockActivity.ACTION_EXIT);
                                getContext().sendBroadcast(intent);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.action_about).setMessage(R.string.about_us);
        builder.create().show();
    }

    @Override
    public void leftSwipe() {
        widgetIndex--;
        widgetIndex = (widgetIndex + timevViews.length) % timevViews.length;
        addTimeView();
    }

    @Override
    public void rightSwipe() {
        widgetIndex++;
        widgetIndex = widgetIndex % timevViews.length;
        addTimeView();
    }
}
