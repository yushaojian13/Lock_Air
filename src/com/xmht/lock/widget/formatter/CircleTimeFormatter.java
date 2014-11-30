
package com.xmht.lock.widget.formatter;

import android.content.Context;
import android.text.format.Time;

import com.xmht.lockair.R;

public class CircleTimeFormatter extends TwoDigitsTimeFormatter {
    public CircleTimeFormatter(Context context, Time time) {
        super(context, time);
    }

    @Override
    public String getDate() {
        return getMonth() + "/" + getDay();
    }

    @Override
    public String getWeek() {
        String[] weekArray = context.getResources().getStringArray(R.array.array_week_normal);
        return weekArray[time.weekDay];
    }

}
