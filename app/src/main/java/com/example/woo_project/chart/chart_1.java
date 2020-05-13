package com.example.woo_project.chart;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.woo_project.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.text.pdf.GrayColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class chart_1 extends AppCompatActivity implements OnChartValueSelectedListener
{

    private Typeface mTfRegular;
    private Typeface mTfLight;
    protected BarChart mChart;
    private HorizontalBarChart hBarChart;
    private LineChart lineChart;
    TextView select_date;
    String year,month;
    Integer i_year,i_month;
    Button date_back,date_after;

    AppCompatRadioButton rbLeft,rbRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);

        select_date = findViewById(R.id.select_date);
        date_back = findViewById(R.id.date_back);

        date_after = findViewById(R.id.date_after);
        year = select_date.getText().toString();
        year=year.substring(0,3);
        month = select_date.getText().toString();
        month = month.substring(4,5);

        Log.v("test","month: "+month);
        Log.v("test","year: "+year);

        date_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.v("test","i_month: "+i_month);
                if (Integer.valueOf(month)>1)
                {
                    i_month = Integer.valueOf(month)-2;
                    month = String.valueOf(i_month);

                }
                else if(Integer.valueOf(month)==1)
                {
                    i_month = 11;
                    month = String.valueOf(i_month);
                    i_year = Integer.valueOf(year)-1;
                    year = String.valueOf(i_year);
                }
                select_date.setText(year+"年"+month+"-"+(Integer.valueOf(month)+1)+"月");
            }
        });

        date_after.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Integer.valueOf(month)<10)
                {month=String.valueOf(Integer.valueOf(month)+2);}

                else //month==11
                {
                    month="1";
                    year=String.valueOf(Integer.valueOf(year)+1);
                    Log.v("test","month: "+month);
                    Log.v("test","year: "+year);
                }
                select_date.setText(year+"年"+month+"-"+(Integer.valueOf(month)+1)+"月");
            }

        });



        rbLeft = findViewById(R.id.rbLeft);
        rbRight = findViewById(R.id.rbRight);

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        mChart = findViewById(R.id.chart1);
        hBarChart = findViewById(R.id.hBarChart);
        lineChart = findViewById(R.id.lineChart);

        initBarChart();
        initHBarChart();
        initLineChart();
    }

    public  void onRatioButtonClicked(View view)
    {
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId())
        {
            case R.id.rbLeft:
                if (isSelected)
                {
                    rbLeft.setTextColor(Color.WHITE);
                    rbRight.setTextColor(Color.GRAY);
                }
                break;

            case R.id.rbRight:
                if (isSelected){
                    rbLeft.setTextColor(Color.GRAY);
                    rbRight.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    /**
     * 初始化柱形圖控制元件屬性
     */
    private void initBarChart() {
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawBarShadow(false); //沒陰影
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false); // X,Y軸同時縮放，false則X,Y軸單獨縮放,預設false
        mChart.setDrawGridBackground(false);





//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
        setBarChartData();
        //自定義座標軸介面卡，配置在X軸，xAxis.setValueFormatter(xAxisFormatter);
       // IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();
        XAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();
        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X軸標籤顯示位置(預設顯示在上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.setGranularity(1f);// 設定X軸值之間最小距離。正常放大到一定地步，標籤變為小數值，到一定地步，相鄰標籤都是一樣的。這裡是指定相鄰標籤間最小差，防止重複。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);//不顯示X軸的對應標籤 (預設顯示)
        xAxis.setSpaceMin(0.5f);//折線起點距離左側Y軸距離
        xAxis.setTextSize(10);
        xAxis.setAxisLineWidth(mChart.getWidth()); //設置此軸的坐標軸的寬度



        String[] xStrs = new String[]{"大王菜舖子","花蓮市農會","永豐餘" ,"壽豐農會","李秋涼","吳媽媽"};
        List<String> chartLabels = new ArrayList<>(Arrays.asList(xStrs));

        xAxis.setLabelCount(chartLabels.size());

        xAxis.setValueFormatter(new  IndexAxisValueFormatter(chartLabels));


        //獲取到圖形左邊的Y軸
        YAxis leftAxis = mChart.getAxisLeft();
    //    leftAxis.addLimitLine(limitLine);
        leftAxis.setLabelCount(8, false);
    //    leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(25f);// 設定最大值到圖示頂部的距離佔所有資料範圍的比例。預設10，y軸獨有
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);
        //leftAxis.setDrawGridLines(false);

        //獲取到圖形右邊的Y軸，並設定為不顯示
        mChart.getAxisRight().setEnabled(false);


        //圖例設定,即 圖表下方標籤說明
        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f);//標籤圖案大小
        legend.setFormLineWidth(10f);
        legend.setFormToTextSpace(5f);
        legend.setTextSize(12f);
        legend.setXEntrySpace(5f);

        //如果點選柱形圖，會彈出pop提示框.XYMarkerView為自定義彈出框
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() { }

    /**
     *柱形圖數值設定
     */
    private void setBarChartData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //在這裡設定自己的資料來源,BarEntry 只接收float的引數，
        //圖形橫縱座標預設為float形式，如果想展示文字形式，需要自定義介面卡，
        yVals1.add(new BarEntry(0, 23f));
        yVals1.add(new BarEntry(1f, 65f));
        yVals1.add(new BarEntry(2f, 182f));
        yVals1.add(new BarEntry(3f, 120f));
        yVals1.add(new BarEntry(4f, 10f));
        yVals1.add(new BarEntry(5f, 5f));

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            set1.setColor(Color.rgb(242, 247, 158));
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "百合花苞");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.JOYFUL_COLORS);//柱狀圖條狀顏色
            set1.setValueTextColor(Color.BLUE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.8f);

            mChart.setData(data);

        }
    }


    /**初始化水平柱形圖圖控制元件屬性*/
    private void initHBarChart() {
        hBarChart.setOnChartValueSelectedListener(this);
        hBarChart.setDrawBarShadow(false);
        hBarChart.setDrawValueAboveBar(true);
        hBarChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        hBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        hBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        hBarChart.setDrawGridBackground(false);

        //自定義座標軸介面卡，設定在X軸
        DecimalFormatter formatter = new DecimalFormatter();
        XAxis xl = hBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(mTfLight);
        xl.setLabelRotationAngle(-45f);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);
//        xl.setAxisMinimum(0);
        xl.setValueFormatter(formatter);

        //對Y軸進行設定
        YAxis yl = hBarChart.getAxisLeft();
        yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        hBarChart.getAxisRight().setEnabled(false);

        //圖例設定
        Legend l = hBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        //右下方description label：設置圖表資訊
        Description description = lineChart.getDescription();
        description.setEnabled(false);//不顯示Description Label (預設顯示)
        description.setText("百合花包");//顯示文字名稱
        description.setTextSize(14);//字體大小
        description.setTextColor(getResources().getColor(R.color.colorAccent));//字體顏色
        description.setPosition(680, 80);//顯示位置座標 (預設右下方)

        setHBarChartData();
        hBarChart.setFitBars(true);
        hBarChart.animateY(2500);
    }


    /** 設定水平柱形圖資料的方法*/
    private void setHBarChartData() {
        //填充資料，在這裡換成自己的資料來源
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(0, 4));
        yVals1.add(new BarEntry(1, 2));
        yVals1.add(new BarEntry(2, 6));
        yVals1.add(new BarEntry(3, 1));
        BarDataSet set1;

        if (hBarChart.getData() != null &&
                hBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) hBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            hBarChart.getData().notifyDataChanged();
            hBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);//可以去掉，沒什麼用
            data.setBarWidth(0.5f);
            hBarChart.setData(data);
        }
    }


    /** 初始化折線圖控制元件屬性*/
    private void initLineChart() {
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);

        //自定義介面卡，適配於X軸
        ValueFormatter xAxisFormatter = new XAxisValueFormatter();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        //自定義介面卡，適配於Y軸
        ValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);//是否畫線

        lineChart.getAxisRight().setEnabled(false);

        setLineChartData();
    }


    /**設定折線圖的資料*/
    private void setLineChartData() {
        //填充資料，在這裡換成自己的資料來源
        List <Entry> valsComp1 = new ArrayList <>();
        List<Entry> valsComp2 = new ArrayList<>();

        valsComp1.add(new Entry(0, 2));
        valsComp1.add(new Entry(1, 4));
        valsComp1.add(new Entry(2, 0));
        valsComp1.add(new Entry(3, 2));

        valsComp2.add(new Entry(0, 2));
        valsComp2.add(new Entry(1, 0));
        valsComp2.add(new Entry(2, 4));
        valsComp2.add(new Entry(3, 2));

        //這裡，每重新new一個LineDataSet，相當於重新畫一組折線
        //每一個LineDataSet相當於一組折線。比如:這裡有兩個LineDataSet：setComp1，setComp2。
        //則在影象上會有兩條折線圖，分別表示公司1 和 公司2 的情況.還可以設定更多
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1 ");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(getResources().getColor(R.color.green));
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2 ");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setDrawCircles(true);
        setComp2.setColor(getResources().getColor(R.color.gray));
        setComp2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}
