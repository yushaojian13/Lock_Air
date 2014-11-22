package com.xmht.lock.core.data;

import android.content.Context;

import com.xmht.lock.core.view.UnlockView;
import com.xmht.lock.core.view.unlock.RainUnlockView;
import com.xmht.lock.core.view.unlock.SlideUnlockView;

public class UnlockCenter {
    public static UnlockView get(Context context, int style) {
        UnlockView unlockView = null;
        
        style = style + 1;
        if (style < 1) {
            style = 1;
        } else if (style > getCount()) {
            style = getCount();
        }
        
        switch (style) {
            case 1:
                unlockView = new SlideUnlockView(context);
                break;
            case 2:
                unlockView = new RainUnlockView(context);
                break;
        }
        return unlockView;
    }
    
    public static int getCount() {
        return 2;
    }
}
