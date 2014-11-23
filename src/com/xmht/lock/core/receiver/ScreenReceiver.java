
package com.xmht.lock.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lock.core.service.LockService;
import com.xmht.lock.debug.LOG;

public class ScreenReceiver extends BroadcastReceiver {
    private static int count;

    @Override
    public void onReceive(Context context, Intent intent) {
        count++;
        LOG.e("instantiated " + count + " times");
        String action = intent.getAction();
        LOG.v(action);
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            onScreenOff(context);
        } else if (Intent.ACTION_USER_PRESENT.equals(action)
                || Intent.ACTION_SCREEN_ON.equals(action)) {
        } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            LockService.action(context, true, true);
        }
    }

    private void onScreenOff(Context context) {
        Intent newIntent = new Intent(context, LockActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }

}
