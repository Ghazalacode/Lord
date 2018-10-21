package com.example.hossam.lord.StatsActivity;

import android.content.Context;
import android.widget.TextView;

import com.example.hossam.lord.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    ArrayList<String> mDays ;
    public MyMarkerView(Context context, int layoutResource , ArrayList<String> mDays) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tv_chart_marker);
        this.mDays = mDays ;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {





            tvContent.setText(mDays.get((int) e.getX())+" ->\n "+e.getY());


        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}