package com.example.woo_project.chart;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class chart_webservice
{
    //以下字串必須完全正確，不能有空白!! (之前為了一個空白卡了兩小時...)
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
    private static final String URL = "http://134.208.97.191:8080/Woo_WebService.asmx";     //WebService的網址
    //private static final String URL = "http://192.168.43.42/ws_test1/webservice.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/VegeInfo_WS";          //命名空間+要用的函數名稱
    private static final String METHOD_NAME = "VegeInfo_WS";   //函數名稱

    public static List <String> ship_to_vendor_num_kg()
    {
        String SOAP_ACTION = "http://tempuri.org/ship_to_vendor_num_kg";          //命名空間+要用的函數名稱
        String METHOD_NAME = "ship_to_vendor_num_kg";   //函數名稱
        List<String> result = new ArrayList <>();
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

}

