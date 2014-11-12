package com.xmht.lock.core.view.time;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.data.time.observe.UpdateLevel;
import com.xmht.lock.core.debug.LOG;
import com.xmht.lock.core.utils.Utils;
import com.xmht.lockair.R;

public class TimeDateWidget1 extends TimeDateWidget {
    private TextView weekTV;
    private TextView dateTV;
    private TextView timeHM;
    
    public TimeDateWidget1(Context context) {
        this(context, null);
    }

    public TimeDateWidget1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimeDateWidget1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_time_date_1, this);
        weekTV = (TextView) findViewById(R.id.week);
        dateTV = (TextView) findViewById(R.id.date);
        timeHM = (TextView) findViewById(R.id.time_h_m);
    }
    
    @Override
    protected void setFont() {
        Utils.setFontToView(weekTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(dateTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(timeHM, "fonts/Helvetica-Light.ttf");
    }
    
    @Override
    public void onUpdate(UpdateLevel level) {
        switch (level) {
            case YEAR:
            case MONTH:
            case WEEK:
                weekTV.setText(TimeFormatter.getWeek(false, false));
            case DAY:
                dateTV.setText(TimeFormatter.getDate(false, false, " "));
            case HOUR:
            case MINUTE:
            case SECOND:
                timeHM.setText(TimeFormatter.getTime(true, true, ":"));
                LOG.e("Time", TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }
    }
    
}
