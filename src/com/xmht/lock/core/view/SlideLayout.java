
package com.xmht.lock.core.view;

import android.annotation.SuppressLint;
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
import com.xmht.lock.core.data.WidgetCenter;
import com.xmht.lock.core.data.UnlockCenter;
import com.xmht.lock.core.data.WallpaperCenter;
import com.xmht.lock.core.view.listener.SwipeListener;
import com.xmht.lock.core.view.listener.UnlockListener;
import com.xmht.lock.utils.SPHelper;
import com.xmht.lock.utils.Utils;
import com.xmht.lockair.R;

public class SlideLayout extends Widget implements SwipeListener, UnlockListener {
    private int screenHeight;
    
    private int wallpaperIndex;
    private int widgetIndex;
    private int unlockIndex;
    
    private ImageView wallpaperIV;
    private TimeDateWidget timeView;
    private UnlockView unlockView;

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
        unlockIndex = SPHelper.get("unlock", 0);

        screenHeight = Utils.getDH(getContext());
        
        addWallpaper();
        addTimeView();
        addUnlockView();
    }

    private void addWallpaper() {
        wallpaperIV = new ImageView(getContext());
        wallpaperIV.setScaleType(ScaleType.CENTER_CROP);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        wallpaperIV.setLayoutParams(lp);
        changeWallpaper();
        addView(wallpaperIV);
    }

    private void changeWallpaper() {
        wallpaperIV.setImageResource(WallpaperCenter.getRes(wallpaperIndex));
    }

    private void addTimeView() {
        if (timeView != null) {
            timeView.onStop();
            removeView(timeView);
        }

        timeView = WidgetCenter.get(getContext(), widgetIndex);
        timeView.setHorizontalSlideListner(this);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.topMargin = screenHeight / 20;
        timeView.setLayoutParams(lp);
        timeView.onStart();
        addView(timeView);
    }

    private void addUnlockView() {
        if (unlockView != null) {
            removeView(unlockView);
            unlockView.setUnlockListener(null);
        }

        unlockView = UnlockCenter.get(getContext(), unlockIndex);
        unlockView.setUnlockListener(this);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = screenHeight / 6;
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        unlockView.setLayoutParams(lp);
        addView(unlockView);
    }

    float downX = 0;
    float downY = 0;
    float moveX;
    float moveY;
    float upX;
    float upY;

    @SuppressLint("ClickableViewAccessibility")
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
                    wallpaperIndex = wallpaperIndex % WallpaperCenter.getCount();
                    changeWallpaper();
                } else if (upX - downX > getWidth() * 0.2f) {
                    wallpaperIndex--;
                    wallpaperIndex = (wallpaperIndex + WallpaperCenter.getCount()) % WallpaperCenter.getCount();
                    changeWallpaper();
                }
                break;
        }

        return true;
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
    public void onStart() {
        timeView.onStart();
    }

    @Override
    public void onStop() {
        timeView.onStop();
        SPHelper.set("wallpaper", wallpaperIndex);
        SPHelper.set("time", widgetIndex);
        SPHelper.set("unlock", unlockIndex);
    }
    
    @Override
    public void leftSwipe() {
        widgetIndex--;
        widgetIndex = (widgetIndex + WidgetCenter.getCount()) % WidgetCenter.getCount();
        addTimeView();
    }

    @Override
    public void rightSwipe() {
        widgetIndex++;
        widgetIndex = widgetIndex % WidgetCenter.getCount();
        addTimeView();
    }

    @Override
    public void onUnlock() {
        Intent intent = new Intent(LockActivity.ACTION_UNLOCK);
        getContext().sendBroadcast(intent);        
    }
    
    @Override
    public void onLongPress() {
        showUnlockChooserDialog();
    }
    
    private void showUnlockChooserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.select_unlock);
//        builder.setView(new UnlockChooser(getContext()));
        builder.setItems(R.array.array_unlock, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == unlockIndex) {
                    return;
                }

                switch (which) {
                    case 0:
                        unlockIndex = 0;
                        break;
                    case 1:
                        unlockIndex = 1;
                        break;
                    default:
                        break;
                }
                addUnlockView();
            }
        });
        builder.create().show();
    }
}
