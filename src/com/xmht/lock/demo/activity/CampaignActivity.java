package com.xmht.lock.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xmht.lockair.R;

public class CampaignActivity extends Activity implements OnClickListener {
    private ImageView changeIV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        changeIV = (ImageView) findViewById(R.id.iv_change);
        changeIV.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        changeIV.startAnimation(anim);
    }
}
