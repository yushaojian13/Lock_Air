
package com.xmht.lock.core.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.xmht.lock.core.service.LockService;
import com.xmht.lock.core.view.common.SlideLayout;
import com.xmht.lock.core.view.unlock.RainUnlockView.UnlockListener;
import com.xmht.lockair.R;

public class LockActivity extends Activity implements UnlockListener {
    private SlideLayout slideLayout;
    
    private ExitReceiver exitReceiver;
    
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
        super.onStart();
        slideLayout.onStart();
        registerExitReceiver();
    }

    private void registerExitReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_EXIT);
        registerReceiver(exitReceiver, intentFilter);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        slideLayout.onStop();
        unregisterReceiver(exitReceiver);
    }

    @Override
    public void onUnlock() {
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void exit() {
        finish();
        stopService(new Intent(this, LockService.class));
    }
    
    private class ExitReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_EXIT.equals(action)) {
                exit();
            }
            
        }
    }
}
