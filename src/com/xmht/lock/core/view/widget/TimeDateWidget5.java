
package com.xmht.lock.core.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.data.time.observe.TimeLevel;
import com.xmht.lock.core.debug.LOG;
import com.xmht.lock.core.utils.Utils;
import com.xmht.lock.core.view.TimeDateWidget;
import com.xmht.lockair.R;

public class TimeDateWidget5 extends TimeDateWidget {
    private TextView dateTV;
    private TextView weekTV;
    private TextView hmTV;
    private TextView amTV;

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
        hmTV = (TextView) findViewById(R.id.time_h_m);
        amTV = (TextView) findViewById(R.id.time_am_pm);
        weekTV = (TextView) findViewById(R.id.week);
        dateTV = (TextView) findViewById(R.id.date);
    }

    @Override
    protected void setFont() {
        Utils.setFontToView(dateTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(weekTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(hmTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(amTV, "fonts/Helvetica-Light.ttf");
    }

    @Override
    public void onTimeChanged(TimeLevel level) {
        switch (level) {
            case YEAR:
            case MONTH:
            case WEEK:
                weekTV.setText(TimeFormatter.getWeek(true, false));
            case DAY:
                dateTV.setText(TimeFormatter.getDate(false, false, " "));
            case HOUR:
                amTV.setText(TimeFormatter.getAM(true));
            case MINUTE:
                hmTV.setText(TimeFormatter.getTime(false, false, ":"));
            case SECOND:
                LOG.v("Time", TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }
    }

}
