
package com.xmht.lock.demo.activity;

import com.xmht.lock.demo.view.ImageCreator;
import com.xmht.lockair.R;

import android.app.Activity;
import android.widget.ImageView;

public class DemoActivity extends Activity {
    private ImageView demoIV;
    
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        demoIV = (ImageView) findViewById(R.id.iv_demo);
        
        ImageCreator imageCreator = new ImageCreator(this, "01:23", 16);
        demoIV.setImageDrawable(imageCreator.createDrawable());
    };
}
