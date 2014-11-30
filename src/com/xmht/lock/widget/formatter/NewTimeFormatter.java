
package com.xmht.lock.widget.formatter;

import android.content.Context;
import android.text.format.Time;

import com.xmht.lockair.R;

public class NewTimeFormatter extends TwoDigitsTimeFormatter {

    public NewTimeFormatter(Context context, Time time) {
        super(context, time);
    }

    @Override
    public String getDate() {
        return getYear() + "." + getMonth() + "." + getDay();
    }

    @Override
    public String getWeek() {
        String[] weekArray = context.getResources().getStringArray(R.array.array_week_full);
        return weekArray[time.weekDay];
    }

}
