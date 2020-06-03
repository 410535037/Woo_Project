package com.example.woo_project.chart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.woo_project.R;
import com.example.woo_project.home.canopy_CardAdapter;
import com.example.woo_project.home.home2_plant_img_cardview;
import com.example.woo_project.home.home2_webservice;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class chart_1 extends Fragment implements OnChartValueSelectedListener
{

    private Typeface mTfRegular;
    private Typeface mTfLight;
    protected BarChart mChart;


    private List<String> ship_to_vendor_num_kg = new ArrayList<>();
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    String[] split_line={}; //將傳回值分割
    float kg_num=0;



    private TextView select_date;
    private String year,month;
    private Integer i_year,i_month;
    private Button date_back,date_after;

    private RadioButton[] radioButton=new RadioButton[3];
    private LinearLayout[] linearLayout=new LinearLayout[3];
    private RadioGroup radioGroup;

    private float[] yData = {25.3f, 10.6f, 52.76f, 44.32f, 46.01f, 16.89f, 23.9f,14f};
    private String[] xData = {"青江", "小白" , "青花椰" , "白花椰", "小松", "奶白", "空心","絲瓜"};
    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.chart_view, container, false);


        select_date = view.findViewById(R.id.select_date);
        date_back = view.findViewById(R.id.date_back);

        date_after = view.findViewById(R.id.date_after);
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
                if (Integer.parseInt(month)>1)
                {
                    i_month = Integer.parseInt(month)-2;
                    month = String.valueOf(i_month);
                }
                else if(Integer.parseInt(month)==1)
                {
                    i_month = 11;
                    month = String.valueOf(i_month);
                    i_year = Integer.parseInt(year)-1;
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
                if (Integer.parseInt(month)<10)
                {month=String.valueOf(Integer.parseInt(month)+2);}

                else //month==11
                {
                    month="1";
                    year=String.valueOf(Integer.parseInt(year)+1);
                    Log.v("test","month: "+month);
                    Log.v("test","year: "+year);
                }
                select_date.setText(year+"年"+month+"-"+(Integer.parseInt(month)+1)+"月");
            }
        });

        radioGroup=(RadioGroup)view.findViewById(R.id.radioGroup);
        radioButton[0]=(RadioButton)view.findViewById(R.id.rbLeft);
        radioButton[1]=(RadioButton)view.findViewById(R.id.rbRight);
        linearLayout[0]= (LinearLayout) view.findViewById(R.id.chart_layout_pie);
        linearLayout[1]= (LinearLayout) view.findViewById(R.id.chart_layout_bar);

//        mTfRegular = Typeface.createFromAsset( getActivity().getAssets(), "OpenSans-Regular.ttf");
//        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        mChart = view.findViewById(R.id.chart_bar_block);
        mChart.setBackgroundColor(getResources().getColor(R.color.barchart_1));
        //initBarChart();

        pieChart = (PieChart) view.findViewById(R.id.chart_pie_block);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(65f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("所有作物收成比例");
        pieChart.setCenterTextSize(14);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
//                String employee = xData[pos1 + 1];
            }

            @Override
            public void onNothingSelected() {}

        });

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");

        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                Log.v("msg",checkedId+"checkedId");
                switch(checkedId)
                {
                    case R.id.rbLeft:
                        Log.v("msg","R.id.rbLeft: " +R.id.rbLeft);
                        changeradio(0);
                        break;
                    case R.id.rbRight:
                        Log.v("msg","rbright: "+R.id.rbRight);
                        mThreadHandler.post(get_ship_to_vendor_num_kg);
                        Log.v("msg","get_ship_to_vendor_num_kg: "+ship_to_vendor_num_kg);
                        //changeradio(1);
                        break;
                }
            }
        });



        return view;
    }

    public Runnable get_ship_to_vendor_num_kg = new Runnable() {
        @Override
        public void run() {
            ship_to_vendor_num_kg = chart_webservice.ship_to_vendor_num_kg();
            mThreadHandler.post(set_ship_to_vendor_num_kg);
        }
    };

    String str1;
    List<String> vendor_x ;
    List<String> vendor_y ;
    public Runnable set_ship_to_vendor_num_kg = new Runnable()
    {
        @Override
        public void run()
        {
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                @Override
                public void run()
                {
                    if(!ship_to_vendor_num_kg.contains("錯誤"))
                    {
                        vendor_x = new ArrayList<>();
                        vendor_y = new ArrayList<>();
                        for(int i = 0 ; i < ship_to_vendor_num_kg.size() ; i++)
                        {
                            //split_line 是Array
                            str1 = ship_to_vendor_num_kg.get(i);//list用"()" Array才用中括號
                            split_line = str1.split("%%");
                            vendor_x.add(split_line[0]);
                            vendor_y.add(split_line[1]);
                        }
                        Log.v("test","vendor_x "+vendor_x);
                        Log.v("test","vendor_y "+vendor_y);
                        initBarChart();
                        changeradio(1);
                    }
                }
            });
        }
    };




    /**
     *  切換圖表的控制
     **/
    int old=0;
    public void changeradio(int i)
    {
        Log.v("msg",i+"");
        linearLayout[old].setVisibility(View.INVISIBLE);
        linearLayout[i].setVisibility(View.VISIBLE);
        old=i;
    }


    /**
     * 圓餅圖控制元件屬性
     */
    public void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "作物");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(14);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.argb(255,255,173,173));
        colors.add(Color.argb(255,255,214,165));
        colors.add(Color.argb(255,253,255,182));
        colors.add(Color.argb(255,202,255,191));
        colors.add(Color.argb(255,155,246,255));
        colors.add(Color.argb(255,160,196,255));
        colors.add(Color.argb(255,189,178,255));
        colors.add(Color.argb(255,255,198,255));
        colors.add(Color.argb(255,255,255,252));
        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f);//標籤圖案大小
        legend.setFormToTextSpace(5f);
        legend.setTextSize(12f);
        //legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    /**
     * 柱形圖初始化+控制元件屬性
     */
    public void initBarChart() {
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

        mChart.setBackgroundColor(Color.rgb(123,123,123));
        mChart.setLogEnabled(true);
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
        xAxis.setTextSize(9);
        xAxis.setAxisLineWidth(mChart.getWidth()); //設置此軸的坐標軸的寬度

        List<String> chartLabels = new ArrayList<>(vendor_x);//把vendor_x的值加入到x軸資料
        Log.v("test","chartLabels:"+ chartLabels);
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
        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);

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
    public void setBarChartData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //在這裡設定自己的資料來源,BarEntry 只接收float的引數，
        //圖形橫縱座標預設為float形式，如果想展示文字形式，需要自定義介面卡，

        for(int i = 0 ; i < ship_to_vendor_num_kg.size() ; i++)
        {
            yVals1.add(new BarEntry(i,Float.parseFloat(vendor_y.get(i))));
            Log.v("test","柱形圖數值設定ship_to_vendor_num_kg:"+ship_to_vendor_num_kg);
        }
//        yVals1.add(new BarEntry(0, 23f));
//        yVals1.add(new BarEntry(1f, 65f));
//        yVals1.add(new BarEntry(2f, 182f));

        BarDataSet barDataSet;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0)
        {
//            barDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
//            barDataSet.setValues(yVals1);
//            barDataSet.setColor(Color.rgb(242, 247, 158));
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
        else
        {
            barDataSet = new BarDataSet(yVals1, "百合花苞");

            //add colors to dataset
            ArrayList<Integer> colors = new ArrayList<>();
//            colors.add(Color.argb(255,255,173,173));
//            colors.add(Color.argb(255,255,214,165));
//            colors.add(Color.argb(255,253,255,182));
//            colors.add(Color.argb(255,202,255,191));
//            colors.add(Color.argb(255,155,246,255));
//            colors.add(Color.argb(255,160,196,255));
//            colors.add(Color.argb(255,189,178,255));
//            colors.add(Color.argb(255,255,198,255));
//            colors.add(Color.argb(255,255,255,252));
            colors.add(getResources().getColor(R.color.barchart_1));
            colors.add(getResources().getColor(R.color.barchart_2));
            colors.add(getResources().getColor(R.color.barchart_3));
            colors.add(getResources().getColor(R.color.barchart_4));
            colors.add(getResources().getColor(R.color.barchart_5));
            colors.add(getResources().getColor(R.color.barchart_6));
            colors.add(getResources().getColor(R.color.barchart_7));
            colors.add(getResources().getColor(R.color.barchart_8));
            colors.add(getResources().getColor(R.color.barchart_9));


            barDataSet.setColors(colors);
            barDataSet.setValueTextColor(getResources().getColor(R.color.barchart_9));
            barDataSet.setDrawIcons(false);

            BarData data = new BarData(barDataSet);
            data.setValueTextSize(10f);
            data.setBarWidth(0.8f);

            mChart.setData(data);

        }
    }
}
