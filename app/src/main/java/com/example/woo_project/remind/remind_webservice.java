package com.example.woo_project.remind;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class remind_webservice {

    //以下字串必須完全正確，不能有空白!! (之前為了一個空白卡了兩小時...)
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
//    private static final String URL = "http://134.208.97.191:8080/Woo_WebService.asmx";     //WebService的網址
    //private static final String URL = "http://192.168.43.42/ws_test1/webservice.asmx";
    private static final String URL = "http://172.20.10.2:80/ws/WebService.asmx";     //WebService的網址
    private static final String SOAP_ACTION = "http://tempuri.org/VegeInfo_WS";          //命名空間+要用的函數名稱
    private static final String METHOD_NAME = "VegeInfo_WS";   //函數名稱

    public static String select_cal(String gmail,String vege_name,String date) {
        String SOAP_ACTION = "http://tempuri.org/select_cal";          //命名空間+要用的函數名稱
        String METHOD_NAME = "select_cal";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("gmail", gmail);
            request.addProperty("vege_name", vege_name);
            request.addProperty("vege_time", date);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String Insert_remind(String vege, String vege_unit,int vege_num,String weight_unit,int weight,int productivity,String area, int nursery_num,int grow_day,int nursery_day,String nursery,String grow,String havest,int harvest_weight,String user) {
        String SOAP_ACTION = "http://tempuri.org/Insert_remind";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Insert_remind";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vege", vege);
            request.addProperty("vege_unit", vege_unit);
            request.addProperty("vege_num", vege_num);
            request.addProperty("weight_unit", weight_unit);
            request.addProperty("weight", weight);
            request.addProperty("productivity", productivity);
            request.addProperty("area", area);
            request.addProperty("nursery_num", nursery_num);
            request.addProperty("grow_day", grow_day);
            request.addProperty("nursery_day", nursery_day);
            request.addProperty("nursery", nursery);
            request.addProperty("grow", grow);
            request.addProperty("havest", havest);
            request.addProperty("harvest_weight", harvest_weight);
            request.addProperty("user", user);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static String Select_remind_unit() {
        String SOAP_ACTION = "http://tempuri.org/Select_remind_unit";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_remind_unit";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static String Select_remind_productivity(String vege_name) {
        String SOAP_ACTION = "http://tempuri.org/Select_remind_productivity";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_remind_productivity";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vege_name", vege_name);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static String Select_remind_data(String user_name) {
        String SOAP_ACTION = "http://tempuri.org/Select_remind_data";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_remind_productivity";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user_name", user_name);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

}
