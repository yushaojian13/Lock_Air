
package com.xmht.lock.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lock.core.debug.LOG;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LOG.e("LockAir", intent.getAction());
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            lock(context);
        } else if (action.equals(Intent.ACTION_USER_PRESENT)
                || action.equals(Intent.ACTION_SCREEN_ON)) {
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
        }
    }

    private void lock(Context context) {
        Intent newIntent = new Intent(context, LockActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }

}
