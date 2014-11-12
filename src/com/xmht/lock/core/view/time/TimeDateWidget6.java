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

public class TimeDateWidget6  extends TimeDateWidget {
    private TextView weekTV;
    private TextView hmTV;
    
    public TimeDateWidget6(Context context) {
        this(context, null);
    }

    public TimeDateWidget6(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimeDateWidget6(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_time_date_6, this);
        weekTV = (TextView) findViewById(R.id.week);
        hmTV = (TextView) findViewById(R.id.time_h_m);
    }

    @Override
    protected void setFont() {
        Utils.setFontToView(weekTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(hmTV, "fonts/Helvetica-Light.ttf");
    }
    
    @Override
    public void onUpdate(UpdateLevel level) {
        switch (level) {
            case YEAR:
            case MONTH:
            case WEEK:
                weekTV.setText(TimeFormatter.getWeek(true, false));
            case DAY:
            case HOUR:
            case MINUTE:
                hmTV.setText(TimeFormatter.getTime(true, false, ":"));
            case SECOND:
                LOG.e("Time", TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }
    }
    
}
