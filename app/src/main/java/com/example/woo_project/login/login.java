package com.example.woo_project.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.woo_project.GlobalVariable;
import com.example.woo_project.R;

import com.example.woo_project.webservice;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{
    /***web service相關***/
    //找到UI工人的經紀人，這樣才能派遣工作  (找到顯示畫面的UI Thread上的Handler)
    private Handler mUI_Handler = new Handler();
    //宣告特約工人的經紀人
    private  Handler mThreadHandler;
    //宣告特約工人
    private HandlerThread mThread;
    GlobalVariable user;

    GlobalVariable gl;
    TextView loginsub;
    Animation smalltobig, stb2;
    String name,email,line;

    //
    Button SignIn;
    Button SignOut;
    public GoogleApiClient googleApiClient;
    public static final int GoogleSignInRequestCode=9001;
    public TextView Name,Email;
    private RelativeLayout Prof_Section;
    public FirebaseAnalytics mFirebaseAnalytics;//try
    public FirebaseAuth mAuth;//try

    //

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        user = (GlobalVariable)getApplicationContext();


        gl= (GlobalVariable)getApplicationContext();
        /*** web service相關 ***/
        //聘請一個特約工人，有其經紀人派遣其工人做事 (另起一個有Handler的Thread)
        mThread = new HandlerThread("");
        //讓Worker待命，等待其工作 (開啟Thread)
        mThread.start();
        //找到特約工人的經紀人，這樣才能派遣工作 (找到Thread上的Handler)
        mThreadHandler=new Handler(mThread.getLooper());

        Log.v("test", "start");

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        stb2 = AnimationUtils.loadAnimation(this, R.anim.stb2);

        loginsub =  findViewById(R.id.loginsub);

//        Email=(TextView)findViewById(R.id.email);
//        SignOut=(Button)findViewById(R.id.bn_logout);
//        Name=(TextView)findViewById(R.id.name);
        SignIn=findViewById(R.id.bn_login);
        
        SignIn.setOnClickListener(this);
        //SignOut.setOnClickListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);//try
        // 設定 FirebaseAuth 介面
        mAuth = FirebaseAuth.getInstance();//try
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestId().requestProfile().build();


        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        loginsub.setTranslationY(400);
        SignIn.setTranslationY(400);

        SignIn.setAlpha(0);
        loginsub.setAlpha(0);

        SignIn.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        loginsub.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
    }

    /***web srevice***/
    //工作名稱 r1 的工作內容
    private Runnable r1=new Runnable ()
    {
        public void run()
        {
            line = webservice.Login_Getgmail(email);//傳回執行完的結果
            //請經紀人指派工作名稱 r，給工人做
            mUI_Handler.post(r2);
        }
    };

    /***web srevice***/
    //工作名稱 r2 的工作內容

    private Runnable r2=new Runnable ()
    {
        public void run()
        {

            if (!line.equals(""))
            {
                Log.v("test","gmail成功傳到資料庫");

            }
            else
            {
                Log.v("test","錯誤");
            }
        }
    };


    //
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {

            case R.id.bn_login:
                signIn();break;
//            case R.id.bn_logout:
//                signOut();break;
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {  }
    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }


    public void signIn() {
        //signInButton 按下時，啟動登入頁面
        Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(googleSignInIntent, GoogleSignInRequestCode);

    }

//    public void signOut()
//    {   Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>()
//    {
//        @Override
//        public void onResult(@NonNull Status status)
//        { updateUI(false); }
//
//    }); }

    private  void handleResult(GoogleSignInResult result)
    {
        Log.v("test","result.isSuccess() value:"+result.isSuccess());
        if (result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();


            email = account.getEmail();//你要用的email在這裡啦
            user.setUser_gmail(email);
            Log.v("test",""+email);
            if(!email.equals("")){mThreadHandler.post(r1);Log.v("test", "email有抓到值");}
            else {Log.v("test", "email沒有值");}

//            if(!img_url.equals("")){mThreadHandler.post(r11);Log.v("test", "照片有抓到值");}
//            else {Log.v("test", "照片欄沒有值");}
            updateUI(true);
            Log.v("test", "true");
        }
        else{
            updateUI(false);
            Log.v("test", "false");
        }
    }

    private  void updateUI(boolean isLogin)
    {
        Log.v("test", "enter updateui");
        Log.v("test", "isLogin value: "+isLogin);
        if(isLogin)
        {
//          Prof_Section.setVisibility(View.VISIBLE);顯示Gmail帳號及暱稱
//          SignIn.setVisibility(View.GONE);

            //以下為將資料/數據值傳遞到下一個java檔
            //new一個intent物件，並指定Activity切換的class

//            Intent intent = new Intent();
//            intent.setClass(login.this, login2.class);
//
//            //new一個Bundle物件，並將要傳遞的資料傳入
//            Bundle bundle = new Bundle();
//            bundle.putString("name", name);
//            //將Bundle物件assign給intent
//            intent.putExtras(bundle);
//
//            //切換Activity
//            startActivity(intent);

            Toast.makeText(login.this, "帳號連結成功", Toast.LENGTH_SHORT).show();

            Intent a = new Intent( login.this,login2.class);
            a.putExtra("giveemail",email);
            startActivity(a);
            Log.v("test", "成功");
            login.this.finish();
        }
        else
        {
//            Prof_Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
            Log.v("test","失敗");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GoogleSignInRequestCode)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
            Log.v("test","result: "+result.toString());
        }
    }
    //API end
}

