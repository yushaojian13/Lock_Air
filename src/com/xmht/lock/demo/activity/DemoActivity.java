
package com.xmht.lock.demo.activity;

import android.app.Activity;
import android.content.Intent;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lockair.R;
import com.ysj.tools.view.PullView;
import com.ysj.tools.view.PullView.OnReleaseListener;

public class DemoActivity extends Activity implements OnReleaseListener {
//    private ImageView demoIV;
    private PullView pullView;
    
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        pullView = (PullView) findViewById(R.id.pv_demo);
        pullView.setOnReleaseListener(this);
        
//        demoIV = (ImageView) findViewById(R.id.iv_demo);
//        ImageCreator imageCreator = new ImageCreator(this, "01:23", Displays.dip2px(this, 16), Color.BLACK, "fonts/Helvetica-Light.ttf");
//        demoIV.setImageDrawable(imageCreator.createDrawable());
    };

    @Override
    public void onRelease() {
        Intent newIntent = new Intent(this, LockActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(newIntent);
    }
}
