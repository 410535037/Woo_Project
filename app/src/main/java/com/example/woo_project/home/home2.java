package com.example.woo_project.home;
import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.example.woo_project.DownloadImageTask;
import com.example.woo_project.GlobalVariable;
import com.example.woo_project.QRCode.Constant;
import com.example.woo_project.R;

import com.example.woo_project.record.record;
import com.example.woo_project.user_setting.user_setting;
import com.example.woo_project.webservice;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class home2 extends Fragment implements ViewPager.OnPageChangeListener{

    private BottomSheetDialog bottomSheetDialog;
    private AppCompatImageButton add_canopy_area;

    GlobalVariable GV; //首頁作物照片(暫時)
    int cardview_id;

    List<home2_plant_img_cardview> canopy_cardviewList;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;

    private Spinner canopy_area;
    private ProgressDialog mLoadingDialog;
    private String test="";
    private home2_canopy_A  home2_canopy_A = new home2_canopy_A();
    private List<String> canopyarea_list = new ArrayList<>();

    //使用者圖片
    private String user_img_link="",account="";
    private DownloadImageTask downloadImageTask;

    ViewPager home2_viewpager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_home2, container, false);


        canopy_area = view.findViewById(R.id.canopy_area);
        GV = (GlobalVariable) Objects.requireNonNull(getActivity()).getApplicationContext();
        account = GV.getUser_gmail();


        mLoadingDialog = new ProgressDialog(getContext());
//        showLoadingDialog("載入中...");
        add_canopy_area = view.findViewById(R.id.add_canopy_area);
        add_canopy_area.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add_canopy_or_canopyArea();
            }
        } );




        //QRCode
        Button qrcode = view.findViewById(R.id.goto_qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQrCode();
            }
        });



        canopy_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
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


        ImageView setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getContext(), user_setting.class);
                startActivity(a);

            }
        });
        //更換首頁圖片
        downloadImageTask = new DownloadImageTask(setting);
        mThreadHandler.post(download_user_img_r1);



        return view;
    }

    private Runnable download_user_img_r1 = new Runnable()
    {
        @Override
        public void run()
        {
            user_img_link = webservice.user_img_down(account);
            Log.v("test","account : "+account);
            if(user_img_link.contains("http"))
            {
                mThreadHandler.post(user_img_r2);
            }

        }
    };

    private Runnable user_img_r2 = new Runnable()
    {
        @Override
        public void run() {
            downloadImageTask.execute(user_img_link);
        }
    };


    //抓區域list
    private Runnable getCanopyarea_list= new Runnable() {
        @Override
        public void run() {
            canopyarea_list = home2_webservice.canopyarea_list();
            mThreadHandler.post(setCanopyarea_list);
        }
    };
    //把區域list放進spinner
    private Runnable setCanopyarea_list= new Runnable() {
        @Override
        public void run() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //給予對應item的資料
                    ArrayAdapter<String> canopyarea_adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),
                            R.layout.home2_canopyarea_dropdown_item,                            //選項資料內容
                            canopyarea_list);   //自訂getView()介面格式(Spinner介面未展開時的View)
                    canopy_area.setAdapter(canopyarea_adapter);
                    //Toast.makeText(home2.this,"刪除成功", Toast.LENGTH_SHORT).show();
                }

            });
        }
    };

    //棚架fragment刷新
    private void selected_canopyarea() {

        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Log.v("test", "canopy_area.getSelectedItem():  " + canopy_area.getSelectedItem() + "         " + canopy_area.getSelectedItemId());
        home2_canopy_A = new home2_canopy_A();
        home2_canopy_A.setViewpager_id((int) canopy_area.getSelectedItemId(), canopy_area.getSelectedItem().toString().substring(0, 1));
        fragmentTransaction.add(R.id.canopy_fg, home2_canopy_A).commit();
    }


    //移除棚架fragment
    private void remove_selected_canopyarea(){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(home2_canopy_A).commit();
    }


    public void showDialog(View view)
    {
        bottomSheetDialog.show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) { switch (position) { } }

    @Override
    public void onPageScrollStateChanged(int state) {}

    //加棚架or區域
    private void add_canopy_or_canopyArea(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
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
        WindowManager m = Objects.requireNonNull(getActivity()).getWindowManager();
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
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申請權限
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission
                    .CAMERA)) {
                Toast.makeText(getContext(), "請到設定開啟使用相機權限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申請文件讀取權限
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申請權限
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(Objects.requireNonNull(getActivity()), "請到設定開啟使用文件權限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 掃描
        Intent intent = new Intent(Objects.requireNonNull(getActivity()), CaptureActivity.class);
        //startActivityForResult(intent, Constant.REQ_QR_CODE);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("test","resultCode內容 : "+resultCode);
        //掃描结果回傳
        if (requestCode == Constant.REQ_QR_CODE && resultCode == getActivity().RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //掃描訊息
            Toast.makeText(getContext(),"QRCode內容 : "+scanResult,Toast.LENGTH_SHORT).show();
            Log.v("test","QRCode內容 : "+scanResult);
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
                    Toast.makeText( Objects.requireNonNull(getActivity()), "請到設定開啟使用相機權限", Toast.LENGTH_LONG).show();
                }
                break;
            case Constant.REQ_PERM_EXTERNAL_STORAGE:
                // 文件讀寫權限申請
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 取得權限
                    startQrCode();
                } else {
                    // 被禁止授權
                    Toast.makeText(Objects.requireNonNull(getActivity()), "請到設定開啟使用文件權限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }



    private void showLoadingDialog(String message){
        message = "載入中...";
        mLoadingDialog.setMessage(message);
        if(mLoadingDialog==null){
            mLoadingDialog = new ProgressDialog(getContext());
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
