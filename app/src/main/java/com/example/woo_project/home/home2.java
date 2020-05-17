package com.example.woo_project.home;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.QRCode.Constant;
import com.example.woo_project.R;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

public class home2 extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    BottomSheetDialog bottomSheetDialog;
    ImageButton record,calendar,setting,user;
    AppCompatImageButton add_canopy_area;

    String delete_cardview_id;
    GlobalVariable GV; //首頁作物照片(暫時)
    int cardview_id;

    List<home2_plant_img_cardview> canopy_cardviewList;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    String gmail;
    Spinner canopy_area;
    ProgressDialog mLoadingDialog;

    String test="";

    private home2_canopy_A  home2_canopy_A = new home2_canopy_A();
    private home2_canopy_B  home2_canopy_B = new home2_canopy_B();
    private home2_canopy_C  home2_canopy_C = new home2_canopy_C();
    private home2_canopy_North  home2_canopy_North = new home2_canopy_North();
    private home2_canopy_Middle  home2_canopy_Millde = new home2_canopy_Middle();

    List<String> canopyarea_list = new ArrayList<>();

    ViewPager home2_viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_home2 );


        canopy_area = findViewById(R.id.canopy_area);
        GV = (GlobalVariable) getApplicationContext();
        gmail = GV.getUser_gmail();


        mLoadingDialog = new ProgressDialog(home2.this);
//        showLoadingDialog("載入中...");
        add_canopy_area = findViewById(R.id.add_canopy_area);
        add_canopy_area.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add_canopy_or_canopyArea();
            }
        } );


        createBottomButton();

        //QRCode
        Button qrcode = findViewById(R.id.goto_qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQrCode();
            }
        });


//        home2_viewpager =  findViewById(R.id.home2_viewpager);
//        home2_viewpager.addOnPageChangeListener(this);
//        setupViewPager(home2_viewpager);


        canopy_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.findFragmentById(R.id.canopy_fg)==null)
                {
                    selected_canopyarea();
                }
                else
                {
                    remove_selected_canopyarea();
                    selected_canopyarea();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        mThreadHandler.post(getCanopyarea_list);






    }



    Runnable getCanopyarea_list= new Runnable() {
        @Override
        public void run() {
            canopyarea_list = home2_webservice.canopyarea_list();
            mThreadHandler.post(setCanopyarea_list);
        }
    };

    Runnable setCanopyarea_list= new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //給予對應item的資料
                    ArrayAdapter<String> canopyarea_adapter = new ArrayAdapter<String>(home2.this,
                            R.layout.home2_canopyarea_dropdown_item,                            //選項資料內容
                            canopyarea_list);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    canopy_area.setAdapter(canopyarea_adapter);
                    //Toast.makeText(home2.this,"刪除成功", Toast.LENGTH_SHORT).show();
                }

            });
        }
    };

    //棚架fragment刷新
    private void selected_canopyarea(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.v("test","canopy_area.getSelectedItem():  "+canopy_area.getSelectedItem()+"         "+canopy_area.getSelectedItemId());
        home2_canopy_A = new home2_canopy_A();
        home2_canopy_A.setViewpager_id((int) canopy_area.getSelectedItemId(),canopy_area.getSelectedItem().toString().substring(0,1));
        fragmentTransaction.add(R.id.canopy_fg, home2_canopy_A).commit();
    }
    //移除棚架fragment
    private void remove_selected_canopyarea(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(home2_canopy_A).commit();
    }


    //建立功能Button
    Button goto_home,goto_edit,goto_record,goto_chart,goto_transportation;
    private void createBottomButton()
    {
        goto_home = findViewById(R.id.goto_home);
        goto_edit = findViewById(R.id.goto_edit);
        goto_record = findViewById(R.id.goto_record);
        goto_chart = findViewById(R.id.goto_chart);
        goto_transportation = findViewById(R.id.goto_transportation);
        goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style2));
        goto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style2));
                goto_edit.setBackground(getResources().getDrawable(R.drawable.bottom_button_edit_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));

//                Intent intent = new Intent(home2.this, com.example.woo_project.record.record.class);
//                startActivity(intent);
            }
        });


        goto_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_edit.setBackground(getResources().getDrawable(R.drawable.bottom_button_edit_style2));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));

//                Intent intent = new Intent(home2.this, com.example.woo_project.record.record.class);
//                startActivity(intent);
            }
        });


        goto_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_edit.setBackground(getResources().getDrawable(R.drawable.bottom_button_edit_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style2));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));

                Intent intent = new Intent(home2.this, com.example.woo_project.record.record.class);
                startActivity(intent);
            }
        });


        goto_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_edit.setBackground(getResources().getDrawable(R.drawable.bottom_button_edit_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style2));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style));

                Intent intent = new Intent(home2.this, com.example.woo_project.chart.chart_1.class);
                startActivity(intent);
            }
        });


        goto_transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_home.setBackground(getResources().getDrawable(R.drawable.bottom_button_home_style));
                goto_edit.setBackground(getResources().getDrawable(R.drawable.bottom_button_edit_style));
                goto_record.setBackground(getResources().getDrawable(R.drawable.bottom_button_record_style));
                goto_chart.setBackground(getResources().getDrawable(R.drawable.bottom_button_chart_style));
                goto_transportation.setBackground(getResources().getDrawable(R.drawable.bottom_button_transportation_style2));

//                Intent intent = new Intent(home2.this, com.example.woo_project.record.record.class);
//                startActivity(intent);
            }
        });

    }
    public void showDialog(View view)
    {
        bottomSheetDialog.show();
    }

    //viewpager函式
    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        home2_canopy_A.setViewpager_id(0,"A");
                        return home2_canopy_A;
                    case 1:
                        home2_canopy_B.setViewpager_id(1,"B");
                        return home2_canopy_B;
                    case 2:
                        home2_canopy_C.setViewpager_id(2,"C");
                        return home2_canopy_C;
                    case 3:
                        home2_canopy_North.setViewpager_id(3,"北");
                        return home2_canopy_North;
                    case 4:
                        home2_canopy_Millde.setViewpager_id(4,"中");
                        return home2_canopy_Millde;
                }



                return null;


            }
            @Override
            public int getCount() {
                return 5;
            }
            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
            }

        });


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
//            case 0:
//                canopy_area.("A區棚架");
//                break;
//            case 1:
//                canopy_area.setText("B區棚架");
//                break;
//            case 2:
//                canopy_area.setText("C區棚架");
//                break;
//            case 3:
//                canopy_area.setText("北區棚架");
//                break;
//            case 4:
//                canopy_area.setText("中區棚架");
//                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {}


    private void add_canopy_or_canopyArea(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(home2.this);
        View mView = getLayoutInflater().inflate(R.layout.home_add_canopy, null);
        //Dialog的標題
        mBuilder.setTitle("");

        Button add_canopy,add_area;
        add_canopy = mView.findViewById(R.id.add_canopy);
        add_area = mView.findViewById(R.id.add_area);

        mBuilder.setView(mView);
        final AlertDialog canopy_dialog = mBuilder.create();
        canopy_dialog.show();

        add_canopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThreadHandler.post(add_canopy_or_area);
                canopy_dialog.dismiss();
            }
        });

        add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canopy_dialog.dismiss();
            }
        });

        // 調整Dialog從哪開始
        Window dialogWindow = canopy_dialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.CENTER );

        // 去除四角黑色背景
        dialogWindow.setBackgroundDrawable(new BitmapDrawable());

        /* 將Dialog用螢幕大小百分比方式設置 */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
    //    p.height = (int) (d.getHeight() * 0.8); // 高度設為螢幕的0.8
        p.width = (int) (d.getWidth() * 0.75);  // 寬度設為螢幕的0.75
        dialogWindow.setAttributes(p);

    }

    private Runnable add_canopy_or_area = new Runnable() {
        @Override
        public void run() {
            test = home2_webservice.home_add_canopy_or_area("1","09",0);
            mThreadHandler.post(set_fragment);
        }
    };

    private Runnable set_fragment= new Runnable() {
        @Override
        public void run() {

            if(test.equals("ok"))
            {
                remove_selected_canopyarea();
                selected_canopyarea();
                Log.v("test","kkkkkk");
            }

        }
    };



    // 開始掃描
    private void startQrCode() {
        // 申請相機讀取權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申請權限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .CAMERA)) {
                Toast.makeText(this, "請到設定開啟使用相機權限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(home2.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申請文件讀取權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申請權限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "請到設定開啟使用文件權限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(home2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 掃描
        Intent intent = new Intent(home2.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //掃描结果回傳
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //掃描訊息
            Toast.makeText(home2.this,"QRCode內容 : "+scanResult,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 相機權限申請
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 取得授權
                    startQrCode();
                } else {
                    // 被禁止授權
                    Toast.makeText( home2.this, "請到設定開啟使用相機權限", Toast.LENGTH_LONG).show();
                }
                break;
            case Constant.REQ_PERM_EXTERNAL_STORAGE:
                // 文件讀寫權限申請
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 取得權限
                    startQrCode();
                } else {
                    // 被禁止授權
                    Toast.makeText(home2.this, "請到設定開啟使用文件權限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }





    private void showLoadingDialog(String message){
        message = "載入中...";
        mLoadingDialog.setMessage(message);
        if(mLoadingDialog==null){
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setMessage(message);
        }
        mLoadingDialog.show();
    }
    private void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
