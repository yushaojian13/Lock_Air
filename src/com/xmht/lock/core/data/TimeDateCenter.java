package com.xmht.lock.core.data;

import android.content.Context;

import com.xmht.lock.core.view.TimeDateWidget;
import com.xmht.lock.core.view.widget.TimeDateWidget1;
import com.xmht.lock.core.view.widget.TimeDateWidget2;
import com.xmht.lock.core.view.widget.TimeDateWidget3;
import com.xmht.lock.core.view.widget.TimeDateWidget4;
import com.xmht.lock.core.view.widget.TimeDateWidget5;
import com.xmht.lock.core.view.widget.TimeDateWidget6;
import com.xmht.lock.core.view.widget.TimeDateWidget7;

public class TimeDateCenter {
    public static TimeDateWidget get(Context context, int style) {
        TimeDateWidget timeDateWidget = null;
        
        style = style + 1;
        if (style < 1) {
            style = 1;
        } else if (style > getCount()) {
            style = getCount();
        }
        
        switch (style) {
            case 1:
                timeDateWidget = new TimeDateWidget1(context);
                break;
            case 2:
                timeDateWidget = new TimeDateWidget2(context);
                break;
            case 3:
                timeDateWidget = new TimeDateWidget3(context);
                break;
            case 4:
                timeDateWidget = new TimeDateWidget4(context);
                break;
            case 5:
                timeDateWidget = new TimeDateWidget5(context);
                break;
            case 6:
                timeDateWidget = new TimeDateWidget6(context);
                break;
            case 7:
                timeDateWidget = new TimeDateWidget7(context);
                break;
        }
        return timeDateWidget;
    }
    
    public static int getCount() {
        return 7;
    }
}
