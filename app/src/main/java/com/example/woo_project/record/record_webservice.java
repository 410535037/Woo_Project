package com.example.woo_project.record;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class record_webservice
{
    //以下字串必須完全正確，不能有空白!! (之前為了一個空白卡了兩小時...)
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
    private static final String URL = "http://134.208.97.191:8080/Woo_WebService.asmx";     //WebService的網址
    //private static final String URL = "http://192.168.43.42/ws_test1/webservice.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/VegeInfo_WS";          //命名空間+要用的函數名稱
    private static final String METHOD_NAME = "VegeInfo_WS";   //函數名稱


    public static List<List<String>> record_select_list_canopy()
    {
        String SOAP_ACTION = "http://tempuri.org/record_select_list_canopy";          //命名空間+要用的函數名稱
        String METHOD_NAME = "record_select_list_canopy";   //函數名稱
        List<List<String>> result = new ArrayList<>();
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
            Log.v("test","有進WS");

            SoapObject obj1 = (SoapObject) envelope.getResponse();
            Log.v("test","obj1: "+obj1);
            Log.v("test","obj1: "+obj1.getProperty(0));
            for(int i=0; i<obj1.getPropertyCount(); i++)
            {
                String getString= obj1.getProperty(i).toString();
                Log.v("test","getString: "+getString);
                getString = getString.replace(" ","").replace("string=","");
                getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
                Log.v("test"," getString2: "+ getString);
                List<String> x = Arrays.asList(getString.split(";"));
                result.add(x);
            }
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    public static List<List<String>> record_select_list_plant()
    {
        String SOAP_ACTION = "http://tempuri.org/record_select_list_plant";          //命名空間+要用的函數名稱
        String METHOD_NAME = "record_select_list_plant";   //函數名稱
        List<List<String>> result = new ArrayList<>();
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            //request.addProperty("","");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","有進WS");
            // 獲取回傳數據
            SoapObject obj1 = (SoapObject) envelope.getResponse();
            Log.v("test","obj1: "+obj1);
            Log.v("test","obj1: "+obj1.getProperty(0));
            for(int i=0; i<obj1.getPropertyCount(); i++)
            {
                String getString= obj1.getProperty(i).toString();
                Log.v("test","getString: "+getString);
                getString = getString.replace(" ","").replace("string=","");
                getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
                Log.v("test"," getString2: "+ getString);
                List<String> x = Arrays.asList(getString.split(";"));
                result.add(x);
            }
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }


    public static String record_traceability(String area, String canopy,String table)
    {
        String SOAP_ACTION = "http://tempuri.org/record_traceability";          //命名空間+要用的函數名稱
        String METHOD_NAME = "record_traceability";   //函數名稱
        String result="";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("area",area);
            request.addProperty("canopy",canopy);
            request.addProperty("table",table);

            
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","有進WS");
            // 獲取回傳數據
            SoapObject obj1 = (SoapObject) envelope.getResponse();
            result = obj1.getProperty(0).toString();
            Log.v("test","obj1: "+obj1);
            Log.v("test","obj1: "+obj1.getProperty(0));
            Log.v("test","result: "+result);

            
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }

    }

}

