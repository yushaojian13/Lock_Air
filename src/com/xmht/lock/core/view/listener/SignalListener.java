
package com.xmht.lock.core.view.listener;

import com.xmht.lock.core.data.info.InfoCenter;
import com.xmht.lock.core.data.info.InfoCenter.InfoType;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

public class SignalListener extends PhoneStateListener
{
    private static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    private static final int SIGNAL_STRENGTH_POOR = 1;
    private static final int SIGNAL_STRENGTH_MODERATE = 2;
    private static final int SIGNAL_STRENGTH_GOOD = 3;
    private static final int SIGNAL_STRENGTH_GREAT = 4;
    
    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength)
    {
        super.onSignalStrengthsChanged(signalStrength);

        int level;
        int asu = signalStrength.getGsmSignalStrength();
        if (asu <= 2 || asu == 99) {
            level = SIGNAL_STRENGTH_NONE_OR_UNKNOWN;
        }
        else if (asu >= 12) {
            level = SIGNAL_STRENGTH_GREAT;
        }
        else if (asu >= 8) {
            level = SIGNAL_STRENGTH_GOOD;
        }
        else if (asu >= 5) {
            level = SIGNAL_STRENGTH_MODERATE;
        }
        else {
            level = SIGNAL_STRENGTH_POOR;
        }
        InfoCenter.set(InfoType.Signal, level);
    }

};
