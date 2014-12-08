
package com.xmht.lock.demo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageView;

import com.xmht.lockair.R;
import com.ysj.tools.utils.Displays;
import com.ysj.tools.view.ImageCreator;

public class DemoActivity extends Activity {
    private ImageView demoIV;
    
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        demoIV = (ImageView) findViewById(R.id.iv_demo);
        
        ImageCreator imageCreator = new ImageCreator(this, "01:23", Displays.dip2px(this, 16), Color.BLACK, "fonts/Helvetica-Light.ttf");
        demoIV.setImageDrawable(imageCreator.createDrawable());
    };
}
