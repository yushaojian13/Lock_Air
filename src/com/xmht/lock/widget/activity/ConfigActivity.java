
package com.xmht.lock.widget.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.xmht.lock.widget.service.AppWidgetService;
import com.xmht.lockair.R;
import com.ysj.tools.debug.LOG;

public class ConfigActivity extends Activity implements OnCheckedChangeListener, OnClickListener {
    private RadioGroup styleRG;
    private RadioGroup primaryColorRG;
    private RadioGroup secondaryColorRG;
    private RadioGroup fontRG;
    private ToggleButton shadowToggleButton;
    private ToggleButton scaleToggleButton;
    private Button enableBtn;

    private List<String> widgetStyles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_clock_config);

        widgetStyles = new ArrayList<String>();
        widgetStyles.add(getString(R.string.widget_style_digit));
        widgetStyles.add(getString(R.string.widget_style_new));
        widgetStyles.add(getString(R.string.widget_style_cicle));
        widgetStyles.add(getString(R.string.widget_style_layer));

        styleRG = (RadioGroup) findViewById(R.id.rg_widget_style);
        primaryColorRG = (RadioGroup) findViewById(R.id.rg_widget_primary_color);
        secondaryColorRG = (RadioGroup) findViewById(R.id.rg_widget_secondary_color);
        fontRG = (RadioGroup) findViewById(R.id.rg_widget_font);

        styleRG.setOnCheckedChangeListener(this);
        primaryColorRG.setOnCheckedChangeListener(this);
        secondaryColorRG.setOnCheckedChangeListener(this);
        fontRG.setOnCheckedChangeListener(this);

        shadowToggleButton = (ToggleButton) findViewById(R.id.tg_shadow);
        scaleToggleButton = (ToggleButton) findViewById(R.id.tg_scale);
        enableBtn = (Button) findViewById(R.id.btn_enable);

        shadowToggleButton.setOnClickListener(this);
        scaleToggleButton.setOnClickListener(this);
        enableBtn.setOnClickListener(this);
    }

    private String style = null;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_widget_style:
                onStyleChanged(checkedId);
                break;
            case R.id.rg_widget_primary_color:
                onPrimaryColorChanged(checkedId);
                break;
            case R.id.rg_widget_secondary_color:
                onSecondaryColorChanged(checkedId);
                break;
            case R.id.rg_widget_font:
                onFontChanged(checkedId);
                break;
        }
    }

    private void onStyleChanged(int checkedId) {
        LOG.e(checkedId);
        style = widgetStyles.get((checkedId - 1) % widgetStyles.size());
        Toast.makeText(this, "check " + style, Toast.LENGTH_SHORT).show();
    }

    private int primaryColor = 0;

    private void onPrimaryColorChanged(int checkedId) {
        checkedId = checkedId - 4;
        checkedId = checkedId % 12;
        LOG.e(checkedId);
        switch (checkedId) {
            case 1:
                primaryColor = 0xffffffff;
                break;
            case 2:
                primaryColor = 0xffff0000;
                break;
        }
        Toast.makeText(this, "check " + String.format("%x", primaryColor), Toast.LENGTH_SHORT).show();
    }

    private int secondaryColor = 0;

    private void onSecondaryColorChanged(int checkedId) {
        checkedId = checkedId - 6;
        checkedId = checkedId % 12;
        LOG.e(checkedId);
        switch (checkedId) {
            case 1:
                secondaryColor = 0;
                break;
            case 2:
                secondaryColor = 0xffffffff;
                break;
            case 3:
                secondaryColor = 0xff00ff00;
                break;
        }
        Toast.makeText(this, "check " + String.format("%x", secondaryColor), Toast.LENGTH_SHORT).show();
    }

    private String font = null;

    private void onFontChanged(int checkedId) {
        checkedId = checkedId - 9;
        checkedId = checkedId % 12;
        LOG.e(checkedId);
        switch (checkedId) {
            case 1:
                font = null;
                break;
            case 2:
                font = "fonts/UnidreamLED.ttf";
                break;
            case 3:
                font = "fonts/CaviarDreams.ttf";
                break;
        }
        Toast.makeText(this, "check " + font, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tg_shadow:
                onShadowClick();
                break;
            case R.id.tg_scale:
                onScaleClick();
                break;
            case R.id.btn_enable:
                onEnableClick();
                break;
            default:
                break;
        }
    }
  
    private void onEnableClick() {
        Intent intent = new Intent(this, AppWidgetService.class);
        intent.putExtra(AppWidgetService.EXTRA_WIDGET_STYLE, style);
        intent.putExtra(AppWidgetService.EXTRA_WIDGET_SHADOW, shodowOn);
        intent.putExtra(AppWidgetService.EXTRA_WIDGET_FONT, font);
        intent.putExtra(AppWidgetService.EXTRA_WIDGET_SCALE, scale);
        intent.putExtra(AppWidgetService.EXTRA_WIDGET_COLORS, new int[] {
                primaryColor, secondaryColor
        });
        startService(intent);
        finish();
    }

    private boolean shodowOn;

    private void onShadowClick() {
        shodowOn = shadowToggleButton.isChecked();
        Toast.makeText(this, "shadow " + shodowOn, Toast.LENGTH_SHORT).show();
    }
    
    private float scale = 1.0f;
    
    private void onScaleClick() {
        boolean checked = scaleToggleButton.isChecked();
        if (checked) {
            scale = 1.5f;
        } else {
            scale = 1.0f;
        }
        Toast.makeText(this, "scale " + scale, Toast.LENGTH_SHORT).show();
    }

}
