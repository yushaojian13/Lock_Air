
package com.xmht.lock.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lock.debug.LOG;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LOG.e(action);
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            onScreenOff(context);
        } else if (action.equals(Intent.ACTION_USER_PRESENT)
                || action.equals(Intent.ACTION_SCREEN_ON)) {
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            onScreenOff(context);
        }
    }

    private void onScreenOff(Context context) {
        Intent newIntent = new Intent(context, LockActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }

}
