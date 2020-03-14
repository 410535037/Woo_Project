package com.example.woo_project.calendar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;

import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;

import com.example.woo_project.webservice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import androidx.appcompat.app.AlertDialog;
import cz.msebera.android.httpclient.Header;

public class calendar_memo extends AppCompatActivity {

    EditText memo;
    TextView date_tv;
    String date;
    GlobalVariable action_item_value,action_item_value2;
    Button cancel;
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8;
    String message;
    String s="",decide_action,decide_action2;
    ImageButton calendar_picture_select;
    ImageView calendar_picture_view;
    ProgressDialog mLoadingDialog;
    private final int REQUEST_PICK_IMAGE = 1;
    URL url;
    String url_String="",vege,gmail,plant_id;
    Bitmap myBitmap;

    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    String line,bbb;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.calendar_memo);

        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");

        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();

        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        memo = findViewById(R.id.edit_message1);
        date_tv = findViewById(R.id.date_tv);
        cancel = findViewById(R.id.cancel);

        try
        {
            url = new URL("");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mLoadingDialog = new ProgressDialog(calendar_memo.this);
        calendar_picture_view = findViewById(R.id.calendar_picture_view);

        calendar_picture_select = findViewById(R.id.calendar_picture_select);
        calendar_picture_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInsertImage();
            }
        });

        cb1=findViewById(R.id.cb1);
        cb2=findViewById(R.id.cb2);
        cb3=findViewById(R.id.cb3);
        cb4=findViewById(R.id.cb4);
        cb5=findViewById(R.id.cb5);
        cb6=findViewById(R.id.cb6);
        cb7=findViewById(R.id.cb7);
        cb8=findViewById(R.id.cb8);



        action_item_value= (GlobalVariable)getApplicationContext();
        action_item_value2= (GlobalVariable)getApplicationContext();
        action_item_value2.setAction_item(action_item_value.getAction_item());
        vege=action_item_value.getSelect_vege_name();
        gmail=action_item_value.getUser_gmail();

        //cb1到cb8是活動被勾選時會有顏色變化
        cb1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb1.isChecked()){
                    cb1.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb1.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb2.isChecked()){
                    cb2.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb2.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb3.isChecked()){
                    cb3.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb3.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb4.isChecked()){
                    cb4.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb4.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb5.isChecked()){
                    cb5.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb5.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb6.isChecked()){
                    cb6.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb6.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb7.isChecked()){
                    cb7.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb7.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });
        cb8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(cb8.isChecked()){
                    cb8.setTextColor(getResources().getColor(R.color.Green1));
                }else{
                    cb8.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });


        Intent intent2 = getIntent();
        //把傳送進來的String型別的date的值賦給新的變數
        date = intent2.getStringExtra("EXTRA_DATE");
        decide_action = intent2.getStringExtra("decide_edit");
        plant_id = intent2.getStringExtra("plant_id");
        //在最上面textview中顯示出日期
        date_tv.setText(date);


        Log.v("test",action_item_value.getAction_item());
        Intent intent = getIntent();
        //把傳送進來的String型別的Message的值賦給新的變數message
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        decide_action2 = intent.getStringExtra("decide_update");
        //在textview中顯示出來message
        memo.setText(message);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendar_memo.this,calendar.class);
                startActivity(intent);
                calendar_memo.this.finish();
            }
        });
    }
    public void sendMessage(View view)
    {
        //String s="";
        if(cb1.isChecked()){
            s += "播種  ";
        }
        if(cb2.isChecked()){
            s += "育苗  ";
        }
        if(cb3.isChecked()){
            s += "定植  ";
        }
        if(cb4.isChecked()){
            s += "澆水  ";
        }
        if(cb5.isChecked()){
            s += "施肥  ";
        }
        if(cb6.isChecked()){
            s += "鋤草  ";
        }
        if(cb7.isChecked()){
            s += "除病蟲  ";
        }
        if(cb8.isChecked()){
            s += "收成  ";
        }
        intent = new Intent(calendar_memo.this,calendar.class);


        message = memo.getText().toString();

        if(url_String.equals(""))
        {
            intent.putExtra("EXTRA_URL","");
        }
        else
        {
            intent.putExtra("EXTRA_URL",url_String);
            Log.v("test","url.toString: "+url_String);
        }

        //請經紀人指派工作名稱 r，給工人做
        mThreadHandler.post(r1);

    }

    //工作名稱 r1 的工作內容

    private Runnable r1=new Runnable () {

        public void run() {
            line = webservice.insert_cal_vege(vege,date,s,message,url_String,gmail,plant_id);
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r2);

        }

    };
    //工作名稱 r2 的工作內容
    private Runnable r2=new Runnable () {

        public void run() {

            //給message起一個名字，並傳給另一個activity
            intent.putExtra("EXTRA_MESSAGE",message);
            intent.putExtra("EXTRA_MESSAGE2",s);
            //啟動意圖
            Log.v("test1","line是: "+line);
            startActivity(intent);
            calendar_memo.this.finish();
        }

    };

    private Runnable r3=new Runnable () {

        public void run() {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r4);

        }

    };

    private Runnable r4=new Runnable () {

        public void run() {

            calendar_picture_view.setImageBitmap(myBitmap);
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除工人上的工作
        if (mThreadHandler != null) {
            mThreadHandler.removeCallbacks(r1);
            mThreadHandler.removeCallbacks(r3);
        }
        //解聘工人 (關閉Thread)
        if (mThread != null) {
            mThread.quit();
        }
    }


    private void textInsertImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { return; }

        switch(requestCode){
            case REQUEST_PICK_IMAGE:
                getSelectImage(data); //處理選取圖片的程式 寫在後面
                break;
        }
    }

    //選擇了要插入的圖片後，在onActivityResult會執行這個
    private void getSelectImage(Intent data){

        //從 onActivityResult 傳入的data中，取得圖檔路徑
        Uri selectedImage = null;
        selectedImage = data.getData();

        ContentResolver cr = this.getContentResolver(); //抽象資料的接口
        Bitmap bitmap = null;
        try {
            assert selectedImage != null;
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(selectedImage));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



//        Log.v("test","imagePath: "+imagePath);
//        //使用圖檔路徑產生調整過大小的Bitmap
//        Bitmap bitmap = getResizedBitmap(imagePath); //程式寫在後面

        //將 Bitmap 轉為 base64 字串
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        String imageBase64 = Base64.encodeToString(bitmapData, Base64.DEFAULT);
        //將圖檔上傳至 Imgur，將取得的圖檔網址插入文字輸入框
        imgurUpload(imageBase64); //程式寫在後面
    }

    //    當圖檔非常大時 (寬度>MAX_WIDTH*2)
    //    先用方法一產生不那麼大的檔 (寬度<=MAX_WIDTH*2)
    //    再用方法二縮至指定的大小 (寬度<=MAX_WIDTH)

    private Bitmap getResizedBitmap(String imagePath) {
        final int MAX_WIDTH = 1024; // 新圖的寬要小於等於這個值

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //只讀取寬度和高度
        BitmapFactory.decodeFile(imagePath, options);
        int width = options.outWidth, height = options.outHeight;
        Log.v("test","照片的 Width: "+width+"    Height: "+height);
        // 求出要縮小的 scale 值，必需是2的次方，ex: 1,2,4,8,16...
        int scale = 1;
        while(width > MAX_WIDTH*2){
            width /= 2;
            height /= 2;
            scale *= 2;
        }

        // 使用 scale 值產生縮小的圖檔
        BitmapFactory.Options scaledOptions = new BitmapFactory.Options();
        scaledOptions.inSampleSize = scale;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imagePath, scaledOptions);

        float resize = 1; //縮小值 resize 可為任意小數
        if(width>MAX_WIDTH){
            resize = ((float) MAX_WIDTH) / width;
        }

        Matrix matrix = new Matrix(); // 產生縮圖需要的參數 matrix
        matrix.postScale(resize, resize); // 設定寬與高的縮放比例

        // 產生縮小後的圖
        return Bitmap.createBitmap(scaledBitmap, 0, 0, width, height, matrix, true);
    }

    private void imgurUpload(final String image){ //插入圖片
        String urlString = "https://imgur-apiv3.p.mashape.com/3/image/";
        String mashapeKey = "c36b64ce86msh2cf38451f64a923p16a49bjsn3f0ccc47d896"; //設定自己的 Mashape Key
        String clientId = "ca76d0896a29008"; //設定自己的 Clinet ID
        String titleString = ""; //設定圖片的標題
        showLoadingDialog("載入中...");

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Mashape-Key", mashapeKey);
        client.addHeader("Authorization", "Client-ID "+clientId);
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestParams params = new RequestParams();
        params.put("title", titleString);
        params.put("image", image);
        client.post(urlString, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dismissLoadingDialog();
                Log.v("test", "Sucess response: "+response.toString());
                if (!response.optBoolean("success") || !response.has("data")) {
                    Log.v("test", "Sucess2 response: "+response.toString());
                    return;
                }
                JSONObject data = response.optJSONObject("data");
                //Log.d("editor","link: "+data.optString("link"));
                url_String = data.optString("link","");
                int width = data.optInt("width",0);
                int height = data.optInt("height",0);

                Log.v("test","link: "+url_String);
                try {
                    url = new URL(url_String);
                    mThreadHandler.post(r3);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {
                dismissLoadingDialog();
                Log.v("test", "Error response: "+error.toString());
                if (error.has("data")) {
                    JSONObject data = error.optJSONObject("data");
                    AlertDialog dialog = new AlertDialog.Builder(calendar_memo.this)
                            .setTitle("Error: " + statusCode + " " + e.getMessage())
                            .setMessage(data.optString("error",""))
                            .setPositiveButton("確定", null)
                            .create();
                    dialog.show();
                }
            }
        });
    }

    private void showLoadingDialog(String message){
        mLoadingDialog.setMessage(message);
        if(mLoadingDialog==null){
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setMessage(message);
        }
        mLoadingDialog.show();
    }

    private void dismissLoadingDialog(){
        if(mLoadingDialog!=null) {
            mLoadingDialog.dismiss();
        }
    }

}
