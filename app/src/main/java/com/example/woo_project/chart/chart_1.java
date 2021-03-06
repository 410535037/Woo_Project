package com.example.woo_project.chart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class chart_1 extends Fragment implements OnChartValueSelectedListener
{

    private Typeface mTfRegular;
    private Typeface mTfLight;
    protected BarChart mChart;

    /*** 作物相關webservice ***/
    private List<String> corp_list = new ArrayList<>();
    private Spinner crop_name_spinner;


    /**廠商相關webservice**/
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

    private RadioButton[] radioButton=new RadioButton[2];
    private LinearLayout[] linearLayout=new LinearLayout[2];
    private RadioGroup radioGroup;

    private List<Float> yData = new ArrayList<>();
    private List<String> xData = new ArrayList<>();
    private PieChart pieChart;

    //圖表底下cardview功能
    private List<chart1_info_cardview> chart1_info_cardviewList;
    RecyclerView recyclerView;

    //總賣出公斤數
    private TextView chart_sales_all_num,chart_sales_vege;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.chart_view, container, false);

        crop_name_spinner = view.findViewById(R.id.crop_spinner);

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

        mChart = view.findViewById(R.id.chart_bar_block);
        mChart.setBackgroundColor(getResources().getColor(R.color.barchart_1));


        pieChart = (PieChart) view.findViewById(R.id.chart_pie_block);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(65f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(14);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);

        // 設置以百分比顯示，這裏是指將原來的0.x乘100，並不會加上“%”符號
        pieChart.setUsePercentValues(true);

        getCropData();

        //總賣出數量
        chart_sales_all_num = view.findViewById(R.id.chart_sales_all_num);
        chart_sales_vege = view.findViewById(R.id.chart_sales_vege);
        //圖表底下的recyclerview
        recyclerView =  view.findViewById(R.id.chart_recyclerview);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                chart_sales_vege.setText("青花椰");
                chart_sales_all_num.setText("50");
                //cardview 建立

                chart1_info_cardviewList = new ArrayList<>();
                //chart1_info_cardview(int id,String canopy,String harvest_date,float sales_num,String sales_date,float sales_price,String sales_vendor)
                chart1_info_cardviewList.add(new chart1_info_cardview(0,"A01","2020-08-22",
                        30.0,"2020-08-23",20.0,"壽豐農會"));
                chart1_info_cardviewList.add(new chart1_info_cardview(1,"B10","2020-08-23",
                        20.0,"2020-08-24",18.0,"吉安農會"));

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(new CardAdapter(getContext(), chart1_info_cardviewList));
            }

            @Override
            public void onNothingSelected() {}

        });

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
                        getCropData();
                        //changeradio(0);
                        break;
                    case R.id.rbRight:
                        Log.v("msg","rbright: "+R.id.rbRight);
                        getVendorData();
                        break;
                }
            }
        });


        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());
        mThreadHandler.post(getCrop_list);


        return view;
    }



    //抓作物list
    private Runnable getCrop_list= new Runnable() {
        @Override
        public void run() {
            corp_list = chart_webservice.crop_list();
            Log.v("test","getCrop "+corp_list);
            mThreadHandler.post(setCrop_list);
        }
    };
    //把作物list放進spinner
    private Runnable setCrop_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //給予對應item的資料
                    ArrayAdapter<String> crop_adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                            R.layout.chart_crop_dropdown_item,                            //選項資料內容
                            corp_list);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    crop_name_spinner.setAdapter(crop_adapter);
                }

            });
        }
    };

    /** 廠商相關webservice **/
    public void getVendorData()
    {
        final Runnable set_ship_to_vendor_num_kg = new Runnable()
        {
            @Override
            public void run ()
            {
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        if (!ship_to_vendor_num_kg.contains("錯誤")) {
                            xData.clear();
                            yData.clear();
                            String str1="";
                            for (int i = 0; i < ship_to_vendor_num_kg.size(); i++) {
                                //split_line 是Array
                                str1 = ship_to_vendor_num_kg.get(i);//list用"()" Array才用中括號
                                split_line = str1.split("%%");
                                xData.add(split_line[0]);
                                yData.add(Float.parseFloat(split_line[1]));
                            }
                            pieChart.setCenterText("廠商收購比例");
                            addDataSet();
                        }
                    }
                });
            }
        };

        Runnable get_ship_to_vendor_num_kg = new Runnable() {
            @Override
            public void run() {
                ship_to_vendor_num_kg = chart_webservice.ship_to_vendor_num_kg();

                mThreadHandler.post(set_ship_to_vendor_num_kg);
            }
        };

        mThreadHandler.post(get_ship_to_vendor_num_kg);
    }


    /** 作物相關webservice **/
    public void getCropData(){
        xData.clear();
        yData.clear();


        xData.add("青江");
        xData.add("小白");
        xData.add("青花椰");
        xData.add("白花椰");
        xData.add("小松");
        xData.add("奶白");
        xData.add("空心");
        xData.add("絲瓜");

        yData.add(70.0f);
        yData.add(30.0f);
        yData.add(52.76f);
        yData.add(44.32f);
        yData.add(46.01f);
        yData.add(16.89f);
        yData.add(23.9f);
        yData.add(14f);

        pieChart.setCenterText("所有作物收成比例");
        addDataSet();
    }




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

        float yData_all = 0f;   //yData加總
        for(int i = 0; i < yData.size(); i++){
                yData_all = yData_all + yData.get(i);
        }

        DecimalFormat df = new DecimalFormat("0.000"); //格式化小數，.後跟幾個零代表幾位小數
        float yData_percent = 0f;   //換算成百分比
        String df2str = "";  //DecimalFormat要轉回string

        List<Float> yData_under5 = new ArrayList<>();    //yData低於5%
        List<String> xData_under5 = new ArrayList<>();   //xData低於5%
        float yData_under5All = 0f;     //yData低於5%加總
        for(int i = 0; i < yData.size(); i++)
        {
            yData_percent = yData.get(i)/yData_all;
            df2str = df.format(yData_percent);//返回的是String型別

            if(Float.parseFloat(df2str)<0.05f)  //如果小於5%
            {
                yData_under5All = yData_under5All + Float.parseFloat(df2str);
                yData_under5.add(Float.parseFloat(df2str));
                xData_under5.add(xData.get(i));
            }
            else
            {
                Log.v("test","df2str : "+df2str);
                Log.v("test","xData :"+xData.get(i));
                yEntrys.add(new PieEntry(Float.parseFloat(df2str) , xData.get(i)));
            }

        }

        //如果有小於5%的資料則加入其他
        if(yData_under5All!=0f)
        {
            yEntrys.add(new PieEntry(yData_under5All , "其他"));
        }



        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"作物");
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
        //用百分比顯示

        //圓餅圖圖例
        //add legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setFormSize(10f);//標籤圖案大小
//        legend.setFormToTextSpace(5f);
//        legend.setTextSize(12f);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//上边
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);


        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value) {
                //直接回傳value會多好幾位 所以要修改一下
                DecimalFormat df = new DecimalFormat("0.0"); //格式化小數，.後跟幾個零代表幾位小數
                String df2str = "";  //DecimalFormat要轉回string
                df2str = df.format(value);//返回的是String型別

                return super.getFormattedValue(Float.parseFloat(df2str))+" %";
            }
        });

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

        List<String> chartLabels = new ArrayList<>();//把vendor_x的值加入到x軸資料
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
           // yVals1.add(new BarEntry(i,Float.parseFloat(vendor_y.get(i))));
           // Log.v("test","柱形圖數值設定ship_to_vendor_num_kg:"+ship_to_vendor_num_kg);
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


    /**
     *  圖表底下的Cardview
     **/
    private class CardAdapter  extends  RecyclerView.Adapter<CardAdapter.ViewHolder>
    {
        private Context context;
        public List<chart1_info_cardview> cardviewList;

        CardAdapter(Context context, List<chart1_info_cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.chart_view_cardview,viewGroup,false);
            return new ViewHolder(view);
        }
        //String canopy,String harvest_date,float sales_num,String sales_date,float sales_price,String sales_vendor
        @Override
        public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
            final chart1_info_cardview cardview = cardviewList.get(i);
            viewHolder.canopy.setText(cardview.getCanopy());
            viewHolder.harvest_date.setText(cardview.getHarvest_date());
            viewHolder.sales_num.setText(String.valueOf(cardview.getSales_num()));
            viewHolder.sales_date.setText(cardview.getSales_date());
            viewHolder.sales_price.setText(String.valueOf(cardview.getSales_price()));
            viewHolder.sales_vendor.setText(cardview.getSales_vendor());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            int id;
            TextView canopy,harvest_date,sales_date,sales_vendor,sales_num,sales_price;

            ViewHolder(View itemView){
                super(itemView);
                canopy = itemView.findViewById(R.id.chart_canopy);
                harvest_date = itemView.findViewById(R.id.chart_harvest_date);
                sales_date = itemView.findViewById(R.id.chart_sales_date);
                sales_vendor = itemView.findViewById(R.id.chart_sales_vendor);
                sales_num = itemView.findViewById(R.id.chart_sales_num);
                sales_price = itemView.findViewById(R.id.chart_sales_price);
            }
        }
        public  void addItem(int i){

//            num = cardviewList.size()-1;
//            //add(位置,資料)
//            cardviewList.add(i, new record_Cardview(id,"小白菜", R.drawable.icon201));
//            id=id+1;
//            notifyItemInserted(i);
        }
    }



}
