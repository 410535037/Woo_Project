package com.example.woo_project.record;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.woo_project.GlobalVariable;
import com.example.woo_project.QRCode.Constant;
import com.example.woo_project.R;
import com.example.woo_project.home.home2;
import com.example.woo_project.webservice;
import com.google.android.material.chip.ChipGroup;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.itextpdf.text.xml.xmp.XmpWriter.UTF8;

public class record extends Fragment
{
    private boolean fg = false;
    int num = 0,id=2;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    private AutoCompleteTextView record_search;
    private String record_search_string="",record_month_select_string="",record_month_select_info="can't not found";


    private List<record_Cardview> cardviewList;
    private Spinner mSpinner2;
    private GlobalVariable record_name;

    //篩選的物件
    private Spinner record_canopy_sp,record_canopy_area_sp,record_plant_kind_sp,reocrd_plant_name_sp;

    //生產履歷的物件
    private Spinner record_traceability_area_sp, record_traceability_canopy_sp ;
    private List<List<String>> record_traceability_list = new ArrayList<>();
    private String[] traceability_str_split;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       // getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.activity_record, container, false);

        // 申請文件讀取權限
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申請權限
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission
                    .WRITE_EXTERNAL_STORAGE))
            {
                Toast.makeText(getContext(), "請到設定開啟使用文件權限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);

        }


        //生產履歷button
        Button traceability = view.findViewById(R.id.record_traceability);
        traceability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTraceability();
            }
        });

        record_name = (GlobalVariable) getActivity().getApplicationContext();
        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        //宣告特約工人
        HandlerThread mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        record_search = view.findViewById(R.id.search_record);
        record_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //點擊後抓searchview的文字並跳轉到作物資訊
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("test","record TextView:"+record_search.getText());

                Intent x=new Intent(getContext(),record_Information2.class);
                startActivity(x);
            }
        });

        //cardview 建立

        cardviewList = new ArrayList<>();
        cardviewList.add(new record_Cardview(0,"非十字花科 皇宮菜","20/03/11"));
        cardviewList.add(new record_Cardview(1,"非十字花科 皇宮菜","20/03/11"));
        RecyclerView recyclerView =  view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new record.CardAdapter(getContext(), cardviewList));

        //Dialog 建立
        ImageButton showDialog = view.findViewById(R.id.Select_item);
        showDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setSelect_dialog();
            }
        });

        Button showDialog2 = view.findViewById(R.id.record_table);
        showDialog2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setRecord_dialog();
            }
        });
        return view;
    }

    //生產履歷參數
    private String area_str,canopy_str,table_str="無";
    String area, canopy, table;
    //生產履歷操作
    private void getTraceability()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.record_traceability_dialog, null);
        record_traceability_area_sp = mView.findViewById(R.id.record_traceability_area_sp);
        record_traceability_canopy_sp = mView.findViewById(R.id.record_traceability_canopy_sp);
        Button record_traceability_commit = mView.findViewById(R.id.record_traceability_commit);
        ChipGroup traceability_ChipGroup = mView.findViewById(R.id.traceability_ChipGroup);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();




        record_traceability_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_str = record_traceability_area_sp.getSelectedItem().toString();
                canopy_str = record_traceability_canopy_sp.getSelectedItem().toString();
                Log.v("test","area_str: " + area_str + "    canopy_str: "+canopy_str);
                load_Traceability_table();

                dialog.dismiss();
            }
        });



        traceability_ChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                String test="";
                switch(checkedId){
                    case R.id.traceability_chip1:
                        test = "全部!";
                        table_str = "全部";
                        break;
                    case R.id.traceability_chip2:
                        test = " 防治資材! ";
                        table_str = "防治資材使用紀錄表";
                        break;
                    case R.id.traceability_chip3:
                        test = " 肥料施用! ";
                        table_str = "肥料施用表";
                        break;
                    case R.id.traceability_chip4:
                        test = " 工作紀錄! ";
                        table_str = "工作紀錄表";
                        break;
                    default:
                        break;
                }
                Toast.makeText(getContext(), test, Toast.LENGTH_SHORT).show();
            }
        });



        // 調整Dialog從哪開始
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
        //p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.65);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);
        load_Traceability_sp();     //從資料庫抓棚架list



    }
    String traceability_str;
    Runnable get_table;
    private void load_Traceability_table()
    {

        get_table = new Runnable() {
            @Override
            public void run() {

                Document doc = new Document(PageSize.A4);//创建一个document对象
                FileOutputStream fos,fos2;
                try
                {
                    //抓系統時間當黨名
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                    String time = formatter.format(curDate);

                    fos = new FileOutputStream(new File(getActivity().getFilesDir(),"/"+time+".pdf")); //pdf_address为Pdf文件保存到sd卡的路径
                    Log.v("test","路徑: "+fos.toString());
                    PdfWriter.getInstance(doc, fos);
                    doc.open();
                    PdfPTable table = new PdfPTable(9);  //建立一個只有兩個欄位的表格
//                    table.setWidthPercentage(100f);    //設定表格寬
//                    table.setWidths(new float[]{0.20f, 0.90f});    //課定這兩個欄位的比例大小

                    PdfPCell cell = new PdfPCell();   //建立一個儲存格
                    //透過 Paragraph 物件增加元素及指定編碼, 也可以直接存入字串
                    Log.v("test","traceability_str_split.length : "+traceability_str_split.length);
                    for(int i =0;i<traceability_str_split.length;i++)
                    {
                        cell.addElement(new Paragraph(traceability_str_split[i],setChineseFont()));
                        table.addCell(cell);
                        cell = new PdfPCell();

                    }



//                    Log.v("test","traceability_str: "+traceability_str);

                    doc.add(table);
                    // 一定要记得关闭document对象
                    doc.close();
                    fos.flush();
                    fos.close();
                    mThreadHandler.sendEmptyMessage(123);//操作完毕后进行提醒



                    //存檔
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.addCategory("android.intent.category.DEFAULT");


                    String[] files =  getActivity().fileList();

                    for(String file : files){
                        Log.e("test","file is :"+ getActivity().getFilesDir().getAbsolutePath()+file);
                    }
                    String path =  getActivity().getFilesDir().getAbsolutePath()+"/"+time+".pdf";///data/data/包名/cache

                    File file = new File(path);
                    Uri pdfUri;
                    pdfUri = FileProvider.getUriForFile(
                            getContext(),
                            "com.example.woo_project.provider", //(use your app signature + ".provider" )
                            file);

                    //pdfUri = Uri.fromFile(file);
                    intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
                    intent.setType("application/pdf");

                    try {
                        record.this.startActivity(Intent.createChooser(intent, file.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };


        Runnable set_table = new Runnable() {
            @Override
            public void run() {

                Log.v("test","area: " + area_str + "    canopy: "+ canopy_str + "   table: "+table_str);
                traceability_str = record_webservice.record_traceability(area_str,canopy_str,table_str);
                traceability_str_split = traceability_str.split("zzz");
                //Toast.makeText(record.this, traceability_str, Toast.LENGTH_SHORT).show();
                mThreadHandler.post(get_table);
            }
        };

        mThreadHandler.post(set_table);

    }

    public Font setChineseFont() {

        Font fontChinese = null;
        try {
            //字型 標楷體
            BaseFont chinese = BaseFont.createFont("assets/kaiu.ttf"
                    , BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//設置中文字型

            fontChinese = new Font(chinese, 12, Font.NORMAL);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }




    //跑生產履歷棚架資料
    private void load_Traceability_sp()
    {
        final Runnable set_Traceability = new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        List<String> canopy_area = new ArrayList<>();
                        //棚架
                        for (int i = 0; i < record_traceability_list.size(); i++) {
                            canopy_area.add(record_traceability_list.get(i).get(0));
                            Log.v("test", "record_select_list_canopy:  " + record_traceability_list.get(i).get(0));
                        }
                        ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(getContext(),
                                R.layout.record_select_dropdown_item,                            //選項資料內容
                                canopy_area);   //自訂getView()介面格式(Spinner介面未展開時的View)
                        record_traceability_area_sp.setAdapter(area_adapter);
                        record_traceability_area_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //選中該區域的棚架list
                                List<String> record_plant_canopy = new ArrayList<>();
                                for (int i = 1; i < record_traceability_list.get(position).size(); i++) {
                                    record_plant_canopy.add(record_traceability_list.get(position).get(i));
                                }

                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                                        R.layout.record_select_dropdown_item,                            //選項資料內容
                                        record_plant_canopy);   //自訂getView()介面格式(Spinner介面未展開時的View)
                                record_traceability_canopy_sp.setAdapter(adapter1);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                });

            }
        };

        Runnable get_Traceability = new Runnable() {
            @Override
            public void run() {
                record_traceability_list = record_webservice.record_select_list_canopy();
                mThreadHandler.post(set_Traceability);
            }
        };
        mThreadHandler.post(get_Traceability);
    }



    //篩選操作
    private void setSelect_dialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.record_select, null);
        record_canopy_area_sp = mView.findViewById(R.id.record_canopy_area_sp);
        record_canopy_sp = mView.findViewById(R.id.record_canopy_sp);
        reocrd_plant_name_sp = mView.findViewById(R.id.reocrd_plant_name_sp);
        record_plant_kind_sp = mView.findViewById(R.id.record_plant_kind_sp);

        //篩選
        Button select_item = mView.findViewById(R.id.record_select_item);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_month_select_string = mSpinner2.getSelectedItem().toString();

                if(!record_month_select_string.equals("月份…"))
                {
                    record_month_select_string = record_month_select_string.replace("月", "");
                    Log.v("test", "record month:  " + record_month_select_string);
                    //請經紀人指派工作名稱 r，給工人做
                    //mThreadHandler.post(record_month_select_r1);
                }
                dialog.dismiss();
            }
        });



        // 調整Dialog從哪開始
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
        // p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.75);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);

        mThreadHandler.post(getRecord_select_list);
    }

    //篩選操作
    private void setRecord_dialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.record_table, null);
        Button one,two,three;
        one = mView.findViewById(R.id.add1);
        two = mView.findViewById(R.id.add2);
        three = mView.findViewById(R.id.add3);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        // 調整Dialog從哪開始
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
        // p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.75);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);
    }


    //從資料庫抓棚架list和作物list
    List<List<String>> record_select_list_canopy = new ArrayList<>();
    List<List<String>> record_select_list_plant = new ArrayList<>();
    Runnable getRecord_select_list = new Runnable() {
        @Override
        public void run() {
            record_select_list_canopy = record_webservice.record_select_list_canopy();
            record_select_list_plant = record_webservice.record_select_list_plant();
            mThreadHandler.post(setRecord_select_list);
        }
    };

    Runnable setRecord_select_list = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    List<String> canopy_area = new ArrayList<>();
                    List<String> plant_kind = new ArrayList<>();

                    //棚架
                    for(int i = 0 ; i <record_select_list_canopy.size();i++)
                    {
                        canopy_area.add(record_select_list_canopy.get(i).get(0));
                        Log.v("test","record_select_list_canopy:  "+record_select_list_canopy.get(i).get(0));
                        Log.v("test","record_select_list_canopy1:  "+record_select_list_canopy.get(i));
                        //record_select_list_canopy.get(i).remove(0);
                    }
                    ArrayAdapter<String> area_adapter = new ArrayAdapter<String>(getContext(),
                            R.layout.record_select_dropdown_item,                            //選項資料內容
                            canopy_area);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    record_canopy_area_sp.setAdapter(area_adapter);

                    record_canopy_area_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //選中該區域的棚架list
                            List<String> record_plant_canopy = new ArrayList<>();
                            for(int i = 1; i < record_select_list_canopy.get(position).size();i++)
                            {
                                record_plant_canopy.add(record_select_list_canopy.get(position).get(i));
                            }


                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                                    R.layout.record_select_dropdown_item,                            //選項資料內容
                                    record_plant_canopy);   //自訂getView()介面格式(Spinner介面未展開時的View)
                            record_canopy_sp.setAdapter(adapter1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                    //作物
                    for(int i = 0 ; i <record_select_list_plant.size();i++)
                    {
                        plant_kind.add(record_select_list_plant.get(i).get(0));
                        Log.v("test","record_select_list_plant:  "+record_select_list_plant.get(i).get(0));
                        Log.v("test","record_select_list_plant1:  "+record_select_list_plant.get(i));
                        //record_select_list_canopy.get(i).remove(0);
                    }

                    //作物種類
                    ArrayAdapter<String> plant_adapter = new ArrayAdapter<String>(getContext(),
                            R.layout.record_select_dropdown_item,                            //選項資料內容
                            plant_kind);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    record_plant_kind_sp.setAdapter(plant_adapter);

                    //作物名稱
                    record_plant_kind_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //選中作物類別的作物list
                            List<String> record_plant_name = new ArrayList<>();
                            for(int i = 1; i < record_select_list_plant.get(position).size();i++)
                            {
                                record_plant_name.add(record_select_list_plant.get(position).get(i));
                            }


                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                                    R.layout.record_select_dropdown_item,                            //選項資料內容
                                    record_plant_name);   //自訂getView()介面格式(Spinner介面未展開時的View)
                            reocrd_plant_name_sp.setAdapter(adapter1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                }
            });

        }
    };


    /**
    Runnable record_search_r1 = new Runnable() {
        @Override
        public void run() {
            record_search_string = webservice.Record_list(record_name.getUser_gmail());
            mThreadHandler.post(record_search_r2);

        }
    };

    Runnable record_search_r2 = new Runnable() {
        @Override
        public void run()
    {
            new Handler(Looper.getMainLooper()).post(new Runnable()
        {
                @Override
                public void run() {
                    String[] all ,split,split_record_search_img;
                    List<String> split_record_search = new ArrayList<>();
                    List<String> split_record_search_date = new ArrayList<>();
                    List<String> split_record_search_id = new ArrayList<>();
                    if (!record_search_string.equals("can't not found"))
                    {
                        all = record_search_string.split("分開");
                        split = all[0].split("%");
                        split_record_search_img = all[1].split("切");
                        int index=0;
                        if(split.length < 3)
                        {
                            split_record_search_date.add(split[index]);
                            split_record_search.add(split[index+1]);
                            split_record_search_id.add("0");
                        }
                        else
                        {
                            for(int i = 0;i < split.length/3;i++)
                            {
                                split_record_search_date.add(split[index]);
                                split_record_search.add(split[index+1]);
                                split_record_search_id.add(split[index+2]);
                                index = index + 3;
                            }
                        }



                        //搜尋bar list view
                        Log.v("test","record的紀錄: "+ split_record_search.get(0));
                        record_search.setAdapter(new ArrayAdapter<>(record.this,
                                android.R.layout.simple_list_item_1, split_record_search));

                    int size1 = cardviewList.size();
                    for(int i=0;i < size1;i++)
                    {
                        Log.v("test","cardviewList.size(): "+cardviewList.size());
                        cardviewList.remove(0);
                    }

                    for(int num = 0; num < split_record_search.size();num++)
                    {
                        cardviewList.add(new record_Cardview(split_record_search_id.get(num),split_record_search.get(num),split_record_search_date.get(num), split_record_search_img[num]));
                        //cardviewList.add(new record_Cardview(String.valueOf(num),split_record_search.get(num),split_record_search_date.get(num), split_record_search_img[num]));
                    }

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new record.CardAdapter(record.this, cardviewList));
                    }
                }
            });
        }
    };


    Runnable  record_month_select_r1 = new Runnable() {
        @Override
        public void run() {
            record_month_select_info = webservice.Record_Select_month(record_month_select_string);
            mThreadHandler.post(record_month_select_r2);
        }
    };

    Runnable  record_month_select_r2 = new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable(){
                @Override
                public void run() {
                    if (!record_month_select_info.equals("can't not found"))
                    {

                        split_line3 = record_month_select_info.split("切"); //切割作物名和圖片編碼
                        split_record_vege = split_line3[0].split("%");   //切割各作物
                        split_record_date = split_line3[1].split("%");   //時間
                        split_record_img = split_line3[2].split("圖");  //切割各圖片編碼
                        split_record_id_string = split_line3[3].split("%");



                        int size1 = cardviewList.size();
                        for(int i=0;i < size1;i++)
                        {
                            Log.v("test","cardviewList.size(): "+cardviewList.size());
                            cardviewList.remove(0);
                        }

                        for(int num = 0; num < split_record_vege.length;num++)
                        {
                            cardviewList.add(new record_Cardview(split_record_id_string[num],split_record_vege[num],split_record_date[num], split_record_img[num]));
                        }

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new record.CardAdapter(record.this, cardviewList));
                    }
                    else
                    {
                        Toast.makeText(record.this,"查無資料!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(record_search_r1 );
            mThreadHandler.removeCallbacks(record_month_select_r1 );

        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }

    }
**/


    private class CardAdapter  extends  RecyclerView.Adapter<CardAdapter.ViewHolder>
    {
        private Context context;
        public List<record_Cardview> cardviewList;

        CardAdapter(Context context, List<record_Cardview> cardviewList) {
            this.context = context;
            this.cardviewList = cardviewList;
        }

        @Override
        public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.record_cardview,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
            final record_Cardview cardview = cardviewList.get(i);
            viewHolder.tx1.setText(String.valueOf(cardview.getName()));
            //Glide.with(record.this).load(cardview.getImage()).into(viewHolder.plantId);
            viewHolder.tx2.setText(String.valueOf((cardview.getTime())));
            Log.v("test","cardview.getName():  "+cardview.getName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

 //                   addItem(cardviewList.size());
                    //record_name.setRecord_vege_name(cardview.getName());

                    Intent intent = new Intent(getContext(), record_Information.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardviewList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
                    ImageView plantId;
                    TextView tx1,tx2;

                    ViewHolder(View itemView){
                        super(itemView);
                        plantId = (ImageView) itemView.findViewById(R.id.plantId);
                        tx1 = (TextView) itemView.findViewById(R.id.tx1);
                        tx2 = (TextView) itemView.findViewById(R.id.tx2);
                    }
        }

        public  void addItem(int i){
            fg = true;
//            num = cardviewList.size()-1;
//            //add(位置,資料)
//            cardviewList.add(i, new record_Cardview(id,"小白菜", R.drawable.icon201));
//            id=id+1;
//            notifyItemInserted(i);
        }
    }

}
