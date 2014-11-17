package com.xmht.lock.core.view.chooser;

import com.xmht.lockair.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class UnlockChooser extends RelativeLayout {
    public UnlockChooser(Context context) {
        this(context, null);
    }

    public UnlockChooser(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public UnlockChooser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }
    
    private void setView() {
        LayoutInflater.from(getContext()).inflate(R.layout.chooser_unlock, this);
    }
}
