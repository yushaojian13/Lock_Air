package com.xmht.lock.core.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xmht.lock.core.data.time.TimeLevel;
import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.view.TimeDateWidget;
import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.Utils;
import com.xmht.lockair.R;

public class TimeDateWidget7  extends TimeDateWidget {
    private TextView monthTV;
    private TextView dayTV;
    private TextView hmTV;
    
    public TimeDateWidget7(Context context) {
        this(context, null);
    }

    public TimeDateWidget7(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimeDateWidget7(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_time_date_7, this);
        monthTV = (TextView) findViewById(R.id.month);
        dayTV = (TextView) findViewById(R.id.day);
        hmTV = (TextView) findViewById(R.id.time_h_m);
    }

    @Override
    protected void setFont() {
        Utils.setFontToView(monthTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(dayTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(hmTV, "fonts/Helvetica-Light.ttf");
    }
    
    @Override
    public void onTimeChanged(TimeLevel level) {
        switch (level) {
            case YEAR:
            case MONTH:
                monthTV.setText(TimeFormatter.getMonth(false, false));
            case WEEK:
            case DAY:
                dayTV.setText(TimeFormatter.getDay(true));
            case HOUR:
            case MINUTE:
                hmTV.setText(TimeFormatter.getTime(true, false, ":"));
            case SECOND:
                LOG.v("Time", TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }
    }
    
}
