package com.example.woo_project.user_setting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.woo_project.DownloadImageTask;
import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;

import com.example.woo_project.login.login;

import com.example.woo_project.user_setting.personal_help;
import com.example.woo_project.user_setting.personal_info;
import com.example.woo_project.webservice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;

public class user_setting extends AppCompatActivity
{
    GlobalVariable gl;
    public TextView user_name;
    Button model,level,personal_user_info,personal_article,personal_collect,personal_article_like,personal_help,personal_signout;
    ProgressDialog mLoadingDialog;
    private final int REQUEST_PICK_IMAGE = 1;
    DownloadImageTask downloadImageTask;
    ImageView user_img;
    GlobalVariable user;
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private android.os.Handler mUI_Handler = new android.os.Handler();
    //宣告特約工人的經紀人
    private Handler mThreadHandler;
    String user_img_link="",account="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.personal_page );

        user = (GlobalVariable)getApplicationContext();
        account = user.getUser_gmail();

        user_img = findViewById(R.id.user_img);
        mLoadingDialog = new ProgressDialog(user_setting.this);
        downloadImageTask = new DownloadImageTask(user_img);
        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        //宣告特約工人
        HandlerThread mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());
        mThreadHandler.post(download_user_img_r1);
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertImage();
            }
        });

//        Name=(TextView)findViewById(R.id.name);
//        Email=(TextView)findViewById(R.id.email);
//        //取得intent中的bundle物件
//        Bundle bundle01 =this.getIntent().getExtras();
//
//        String name = bundle01.getString("name");
//        String email = bundle01.getString("email");
//        Name.setText(name);
//        Email.setText(email);

        level=findViewById(R.id.level);
        model=findViewById(R.id.model);
        user_name=findViewById(R.id.personal_user_name);
        personal_user_info=findViewById(R.id.personal_user_info);
        personal_article=findViewById(R.id.personal_article);
        personal_collect=findViewById(R.id.personal_collect);
        personal_article_like=findViewById(R.id.personal_article_like);
        personal_help=findViewById(R.id.personal_help);
        personal_signout=findViewById(R.id.personal_signout);
        gl = (GlobalVariable)getApplicationContext();
        user_name.setText(gl.getUser_name());

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(user_setting.this);
                builder.setTitle("模式功能☆即將推出，敬請期待!");
                builder.setMessage("針對各模式推薦使用者適合的作物，首頁也能切換不同模式的模型!");
                builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(user_setting.this);
                builder.setTitle("等級功能☆即將推出，敬請期待!");
                builder.setMessage("透過操作APP中的各功能來提升經驗值，除了增加使用者的樂趣，也能換取商品的優惠!");
                builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        personal_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_setting.this, personal_info.class);
                startActivity(intent);
                user_setting.this.finish();
            }
        });
        personal_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(user_setting.this, my_post.class);
                //(intent);
                user_setting.this.finish();
            }
        });
      /**
        personal_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_setting.this, like_vege.class);
                startActivity(intent);
                user_setting.this.finish();
            }
        });
        personal_article_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_setting.this, like_post.class);
                startActivity(intent);
                user_setting.this.finish();
            }
        });

        **/
        personal_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_setting.this, personal_help.class);
                startActivity(intent);
                user_setting.this.finish();
            }
        });
        personal_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(user_setting.this, login.class);
                startActivity(x);
                user_setting.this.finish();
            }
        });
    }


    Runnable update_user_img_r1 = new Runnable() {
        @Override
        public void run() {
            webservice.user_img_update(user_img_link ,account);
            mThreadHandler.post(user_img_r2);
        }
    };

    Runnable download_user_img_r1 = new Runnable() {
        @Override
        public void run() {
            user_img_link = webservice.user_img_down(account);
            Log.v("test","account : "+account);
            if(user_img_link.contains("http"))
            {
                mThreadHandler.post(user_img_r2);
            }


        }
    };

    Runnable user_img_r2 = new Runnable() {
        @Override
        public void run() {
            downloadImageTask.execute(user_img_link);
        }
    };



    private void InsertImage(){
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
        //Log.d("editor",imageBase64);

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
        showLoadingDialog("上傳中...");

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
                if (!response.optBoolean("success") || !response.has("data")) {
                    Log.d("editor", "response: "+response.toString());
                    return;
                }
                JSONObject data = response.optJSONObject("data");
                //Log.d("editor","link: "+data.optString("link"));
                String link = data.optString("link","");
                int width = data.optInt("width",0);
                int height = data.optInt("height",0);
                //String bbcode = "[img="+width+"x"+height+"]"+link+"[/img]";
                //String bbcode ="["+link+"]";
                if(user_img_link.contains("http"))
                {
                    downloadImageTask = null;
                    downloadImageTask = new DownloadImageTask(user_img);
                }
                user_img_link = link;
                mThreadHandler.post(update_user_img_r1);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject error) {
                dismissLoadingDialog();
                //Log.d("editor","error: "+error.toString());
                if (error.has("data")) {
                    JSONObject data = error.optJSONObject("data");
                    AlertDialog dialog = new AlertDialog.Builder(user_setting.this)
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
        if(message==null){
            message = "載入中...";
        }
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
