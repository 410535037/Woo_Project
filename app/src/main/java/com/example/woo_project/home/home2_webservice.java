package com.example.woo_project.home;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class home2_webservice
{
    //以下字串必須完全正確，不能有空白!! (之前為了一個空白卡了兩小時...)
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
    private static final String URL = "http://134.208.97.191:8080/Woo_WebService.asmx";     //WebService的網址
    //private static final String URL = "http://192.168.43.42/ws_test1/webservice.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/VegeInfo_WS";          //命名空間+要用的函數名稱
    private static final String METHOD_NAME = "VegeInfo_WS";   //函數名稱

    public static List<String> canopy_list(int viewpager_id)
    {
        String SOAP_ACTION = "http://tempuri.org/canopy_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "canopy_list";   //函數名稱
        List<String> result = new ArrayList<>();
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("viewpager_id",viewpager_id);
            Log.v("test","viewpager_id: "+viewpager_id);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","有進WS");
            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果

            String getString=object.getProperty(0).toString();
            Log.v("test"," getString: "+ getString);
            getString = getString.replace(" ","").replace("string=","");
            getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
            Log.v("test"," getString2: "+ getString);
            result = Arrays.asList(getString.split(";"));
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
//            result.add(e.toString());
            return result;
        }
    }

    public static List<String> canopyarea_list()
    {
        String SOAP_ACTION = "http://tempuri.org/canopyarea_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "canopyarea_list";   //函數名稱
        List<String> result = new ArrayList<>();
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
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果

            String getString=object.getProperty(0).toString();
            Log.v("test"," getString7777: "+ getString);
            getString = getString.replace(" ","").replace("string=","");
            getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
            Log.v("test"," getString2: "+ getString);
            result = Arrays.asList(getString.split(";"));
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
//            result.add(e.toString());
            Log.v("test"," getString2777777: "+ e);
            return result;
        }
    }

    public static String home_add_canopy_or_area(String area,String canopy, int mode)
    {
        String SOAP_ACTION = "http://tempuri.org/home_add_canopy_or_area";          //命名空間+要用的函數名稱
        String METHOD_NAME = "home_add_canopy_or_area";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("area",area);
            request.addProperty("canopy",canopy);
            request.addProperty("mode",mode);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","有進WS");
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
