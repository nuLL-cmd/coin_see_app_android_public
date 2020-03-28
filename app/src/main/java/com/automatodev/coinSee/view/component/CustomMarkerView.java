package com.automatodev.coinSee.view.component;

import android.app.Activity;
import android.widget.TextView;

import com.automatodev.coinSee.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;
import java.util.Locale;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent;


    public CustomMarkerView(Activity context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);

    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Locale locale = new Locale("pt","BR");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            tvContent.setText(""+format.format(e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}