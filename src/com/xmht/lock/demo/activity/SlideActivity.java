
package com.xmht.lock.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.xmht.lockair.R;
import com.ysj.tools.debug.LOG;

public class SlideActivity extends Activity implements SlideListView.ScrollListener {
    private View headView;
    private SlideListView contentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        headView = findViewById(R.id.head);
        contentListView = (SlideListView) findViewById(R.id.content);
        contentListView.setScrollListener(this);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("List " + i);
        }
        contentListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        LOG.v("getHeight: " + headView.getHeight());
        LOG.v("getMeasuredHeight " + headView.getMeasuredHeight());
        LOG.v("y: " + headView.getY());
    }

    @Override
    public void onScrollY(float y) {
//        headView.getLayoutParams().
        float ty = headView.getTranslationY() + y;
        if (ty > 0) {
            ty = 0;
        }
        headView.setTranslationY(y);
    }
}
