
package com.xmht.lock.core.view.time;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmht.lock.core.data.info.InfoCenter.InfoType;
import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.data.time.observe.TimeLevel;
import com.xmht.lock.core.debug.LOG;
import com.xmht.lock.core.utils.Utils;
import com.xmht.lock.core.view.TimeDateInfoWidget;
import com.xmht.lockair.R;

public class TimeDateInfoWidget1 extends TimeDateInfoWidget {

    private TextView timeHM;
    private TextView weekTV;
    private TextView dateTV;

    private TextView batteryTV;
    private ImageView batteryTipIV;
    private ImageView netTipIV;
    private ImageView signalLevelIV;

    public TimeDateInfoWidget1(Context context) {
        this(context, null);
    }

    public TimeDateInfoWidget1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeDateInfoWidget1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_time_date_info_1, this);
        timeHM = (TextView) findViewById(R.id.time_h_m);
        weekTV = (TextView) findViewById(R.id.week);
        dateTV = (TextView) findViewById(R.id.date);

        batteryTipIV = (ImageView) findViewById(R.id.battery_tip);
        batteryTV = (TextView) findViewById(R.id.battery_tv);
        netTipIV = (ImageView) findViewById(R.id.net_tip);
        signalLevelIV = (ImageView) findViewById(R.id.signal_level);
    }

    @Override
    protected void setFont() {
        Utils.setFontToView(timeHM, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(weekTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(dateTV, "fonts/Helvetica-Light.ttf");
        Utils.setFontToView(batteryTV, "fonts/Helvetica-Light.ttf");
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
            case MINUTE:
            case SECOND:
                timeHM.setText(TimeFormatter.getTime(true, true, ":"));
                LOG.v("Time", TimeFormatter.getTime(true, true, ":"));
                break;
            default:
                break;
        }

    }

    @Override
    public void onInfoLevelChanged(InfoType infoType, int level) {
        switch (infoType) {
            case Wifi:
                if (level < 0) {
                    level = 0;
                } else if (level > wifiDrawable.length - 1) {
                    level = wifiDrawable.length - 1;
                }
                netTipIV.setImageResource(wifiDrawable[level]);
                break;
            case Battery:
                if (level < 0) {
                    level = 0;
                } else if (level > 100) {
                    level = 100;
                }
                batteryTV.setText(level + "%");
                batteryTipIV.setImageResource(batteryDrawable[level / 20]);
                break;
            case Signal:
                if (level < 0) {
                    level = 0;
                } else if (level > signalDrawable.length - 1) {
                    level = signalDrawable.length - 1;
                }
                signalLevelIV.setImageResource(signalDrawable[level]);
                break;

            default:
                break;
        }
    }

}
