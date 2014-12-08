
package com.xmht.lock.core.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.xmht.lock.core.service.LockService;
import com.xmht.lock.core.view.SlideLayout;
import com.xmht.lockair.R;
import com.ysj.tools.debug.LOG;

public class LockActivity extends Activity {
    private SlideLayout slideLayout;
    private ExitReceiver exitReceiver;

    public static final String ACTION_UNLOCK = "com.xmht.lock.air.unlock";
    public static final String ACTION_EXIT = "com.xmht.lock.air.exit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        slideLayout = (SlideLayout) findViewById(R.id.root);
        exitReceiver = new ExitReceiver();
        startService(new Intent(this, LockService.class));
    }

    @Override
    protected void onStart() {
        LOG.v("");
        super.onStart();
        slideLayout.onStart();
        registerExitReceiver();
    }

    private void registerExitReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UNLOCK);
        intentFilter.addAction(ACTION_EXIT);
        registerReceiver(exitReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        LOG.v("");
        super.onStop();
        slideLayout.onStop();
        unregisterReceiver(exitReceiver);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void exit() {
        finish();
        stopService(new Intent(this, LockService.class));
    }

    private class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_UNLOCK.equals(action)) {
                finish();
            } else if (ACTION_EXIT.equals(action)) {
                exit();
            }
        }
    }
}
