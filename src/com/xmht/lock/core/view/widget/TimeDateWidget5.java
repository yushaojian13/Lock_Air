package com.xmht.lock.core.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xmht.lock.core.data.time.TimeLevel;
import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.view.TimeDateWidget;
import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.Utils;
import com.xmht.lockair.R;

public class TimeDateWidget5  extends TimeDateWidget {
    private TextView hTV;
    private TextView mTV;
    private TextView weekTV;
    private TextView dateTV;
    
    public TimeDateWidget5(Context context) {
        this(context, null);
    }

    public TimeDateWidget5(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimeDateWidget5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_time_date_5, this);
        hTV = (TextView) findViewById(R.id.hour);
        mTV = (TextView) findViewById(R.id.minute);
        weekTV = (TextView) findViewById(R.id.week);
        dateTV = (TextView) findViewById(R.id.date);
    }

    @Override
    protected void setFont() {
        Utils.setFontToView(hTV, "fonts/Helvetica-Light.ttf", Typeface.BOLD);
        Utils.setFontToView(mTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(weekTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(dateTV, "fonts/Helvetica-Light.ttf");
    }
    
    @Override
    public void onTimeChanged(TimeLevel level) {
        switch (level) {
            case YEAR:
            case MONTH:
            case WEEK:
                weekTV.setText(TimeFormatter.getWeek(false, false));
            case DAY:
                dateTV.setText(TimeFormatter.getDate(false, false, " "));
            case HOUR:
                hTV.setText(TimeFormatter.getHour(true));
            case MINUTE:
                mTV.setText(TimeFormatter.getMinute());
            case SECOND:
                LOG.v("Time", TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }
    }
    
}
