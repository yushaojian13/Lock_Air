package com.xmht.lock.widget.formatter;

import com.xmht.lockair.R;

import android.content.Context;
import android.text.format.Time;

public class LayerTimeFormatter extends TwoDigitsTimeFormatter {

    public LayerTimeFormatter(Context context, Time time) {
        super(context, time);
    }

    @Override
    public String getDate() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMonth());
        sb.append(context.getString(R.string.month));
        sb.append(getDay());
        sb.append(context.getString(R.string.day));
        return  sb.toString();
    }

    @Override
    public String getWeek() {
        String[] weekArray = context.getResources().getStringArray(R.array.array_week_normal);
        return weekArray[time.weekDay];
    }

}
