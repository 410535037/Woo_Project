package com.example.woo_project.shipping;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.woo_project.R;


public class shipping_add_data extends AppCompatActivity
{
    AppCompatButton add_vendor,shipping_add_vendor_name_bt;

    private RadioButton[] radioButton=new RadioButton[2];
    private LinearLayout[] linearLayout=new LinearLayout[2];
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.shipping_add_data);
        add_vendor=(AppCompatButton)findViewById(R.id.add_vendor);
        add_vendor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                set_dialog();
            }
        });

        linearLayout[0]= (LinearLayout) findViewById(R.id.shipping_layout_left);
        linearLayout[1]= (LinearLayout) findViewById(R.id.shipping_layout_right);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioButton[0]=(RadioButton)findViewById(R.id.rbLeft);
        radioButton[1]=(RadioButton)findViewById(R.id.rbRight);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                Log.v("msg",checkedId+"checkedId");
                switch(checkedId)
                {
                    case R.id.rbLeft:
                        Log.e("msg",R.id.rbLeft+"R.id.radioButton1");
                        changeradio(0);
                        break;
                    case R.id.rbRight:
                        Log.e("msg",R.id.rbRight+"R.id.radioButton2");
                        changeradio(1);
                        break;

                }
            }});



}
    int old=0;
    public void changeradio(int i)
    {
        Log.v("msg",i+"");
        linearLayout[old].setVisibility(View.VISIBLE);
        linearLayout[i].setVisibility(View.INVISIBLE);
        old=i;
    }

    //顯示Dialog
    private void set_dialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.shipping_add_vendor_dialog, null);
        shipping_add_vendor_name_bt = mView.findViewById(R.id.shipping_add_vendor_name_bt);

        //篩選
        AppCompatButton shipping_add_vendor_name_bt = mView.findViewById(R.id.shipping_add_vendor_name_bt);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        shipping_add_vendor_name_bt.setOnClickListener(new View.OnClickListener()
        {
            //webservice部分可參考record.java
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });



//        // 調整Dialog從哪開始
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
//
//        // 去除四角黑色背景
//        dialogWindow.setBackgroundDrawable(new BitmapDrawable());
//
//        /* 將Dialog用螢幕大小百分比方式設置 */
//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 取得螢幕寬和高
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 取得對話框目前數值
//        p.height = (int) (d.getHeight() * 0.4); // 高度設為螢幕的0.4
//        p.width = (int) (d.getWidth() * 0.8);  // 寬度設為螢幕的0.8
//        dialogWindow.setAttributes(p);

    }
}