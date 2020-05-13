package com.example.woo_project.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class DecimalFormatter extends ValueFormatter {
    private DecimalFormat format;

    public DecimalFormatter() {
        format = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return format.format(value) + "$";
    }

}