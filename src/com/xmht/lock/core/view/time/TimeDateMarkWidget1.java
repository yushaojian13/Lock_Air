
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

public class TimeDateMarkWidget1 extends TimeDateWidget {
    private TextView dayTV;
    private TextView monthTV;
    private TextView weekTV;
    private TextView timeHM;

    public TimeDateMarkWidget1(Context context) {
        this(context, null);
    }

    public TimeDateMarkWidget1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeDateMarkWidget1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_time_date_mark_1, this);
        dayTV = (TextView) findViewById(R.id.day);
        monthTV = (TextView) findViewById(R.id.month);
        weekTV = (TextView) findViewById(R.id.week);
        timeHM = (TextView) findViewById(R.id.time_h_m);
    }

    @Override
    protected void setFont() {
        Utils.setFontToView(dayTV, "fonts/Helvetica_LT_27_Ultra_Light_Condensed.ttf");
        Utils.setFontToView(monthTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(weekTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(timeHM, "fonts/Helvetica-Light.ttf");
    }

    @Override
    public void onUpdate(UpdateLevel level) {
        switch (level) {
            case YEAR:
            case MONTH:
                monthTV.setText(TimeFormatter.getMonth(false, false));
            case WEEK:
                weekTV.setText(TimeFormatter.getWeek(false, false));
            case DAY:
                dayTV.setText(TimeFormatter.getDay(true));
            case HOUR:
            case MINUTE:
            case SECOND:
                timeHM.setText(TimeFormatter.getTime(true, false, ":"));
                LOG.e(TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }
    }
}
