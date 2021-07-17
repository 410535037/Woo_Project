package com.example.woo_project.shipping;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class shipping_webservice
{
    //以下字串必須完全正確，不能有空白!! (之前為了一個空白卡了兩小時...)
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
    private static final String URL = "http://134.208.97.191:8080/goods_WebService.asmx";     //WebService的網址
    //private static final String URL = "http://192.168.43.42/ws_test1/webservice.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/VegeInfo_WS";          //命名空間+要用的函數名稱
    private static final String METHOD_NAME = "VegeInfo_WS";   //函數名稱

    //庫存統計
    public static List<List<String>> inventory_sum(int user)
    {
        String SOAP_ACTION = "http://tempuri.org/Inventory_sum";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Inventory_sum";   //函數名稱
        List<List<String>> result = new ArrayList<>();
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user",user);

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
            Log.v("test2","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test2","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //庫存詳細資訊
    public static List<List<String>> Inventory(int user, String plant_name, String plant_img, String ship_vendor,String canopy)
    {
        String SOAP_ACTION = "http://tempuri.org/Inventory";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Inventory";   //函數名稱
        List<List<String>> result = new ArrayList<>();
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user",user);
            request.addProperty("plant_name",plant_name);
            request.addProperty("plant_img",plant_img);
            request.addProperty("ship_vendor",ship_vendor);
            request.addProperty("canopy",canopy);

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
            Log.v("test2","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test2","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }
    //庫存cardview有哪些cnaopy
    public static List<String> Inventory_canopy_list(int user, String plant_name, String plant_img, String ship_vendor)
    {
        String SOAP_ACTION = "http://tempuri.org/Inventory_canopy_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Inventory_canopy_list";   //函數名稱
        List<String> result = new ArrayList<>();
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user",user);
            request.addProperty("plant_name",plant_name);
            request.addProperty("plant_img",plant_img);
            request.addProperty("ship_vendor",ship_vendor);
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
            Log.v("test"," getString1: "+ getString);
            getString = getString.replace(" ","").replace("string=","");
            getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
            Log.v("test"," getString2: "+ getString);
            result = Arrays.asList(getString.split(";"));
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test"," getString2777777: "+ e);
            return result;
        }
    }

    //存貨到出貨
    public static String Go_Ship(int user,String ship_date,String ship_vendor,String old_ship_vendor,String ship_price, Boolean ship_status, String plant_name, String plant_img)
    {
        String SOAP_ACTION = "http://tempuri.org/Go_Ship";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Go_Ship";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user",user);
            request.addProperty("ship_date",ship_date);
            request.addProperty("ship_vendor",ship_vendor);
            request.addProperty("old_ship_vendor",old_ship_vendor);

            Log.v("test2","vendor: "+ship_vendor);
            Log.v("test2","old vendor: "+old_ship_vendor);

            request.addProperty("ship_price",ship_price);
            request.addProperty("ship_status",ship_status);
            request.addProperty("plant_name",plant_name);
            request.addProperty("plant_img",plant_img);

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
            Log.v("test2","Go_Ship的result: "+result);

            return result;
        } catch (Exception e) {
            Log.v("test2","e的錯誤訊息 : "+e.toString());
            return "NO";
        }
    }













    public static String add_shipping_data(String vege_name_tx,String vendor_name_tx,String sale_num_str,String set_date_str,boolean ship_status,String total_earnings_str)
    {
        String SOAP_ACTION = "http://tempuri.org/add_shipping_data";          //命名空間+要用的函數名稱
        String METHOD_NAME = "add_shipping_data";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vege_name",vege_name_tx);
            request.addProperty("vendor_name",vendor_name_tx);
            request.addProperty("num_kg",sale_num_str);
            request.addProperty("shipping_date",set_date_str);
            request.addProperty("ship_status",ship_status);
            request.addProperty("total_earnings_str",total_earnings_str);

            Log.v("test","RRRRR_add_vendor_name"+vege_name_tx);
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

            Log.v("test","add_shipping_data ws的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static List<List<String>> instock_data_list()
    {
        String SOAP_ACTION = "http://tempuri.org/instock_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "instock_data_list";   //函數名稱
        List<List<String>> result = new ArrayList<>();
        //必須用try catch包著
        try
        {
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
        }
        catch (Exception e) {
//            result.add(e.toString());
            Log.v("test"," getString: "+ e);
            return result;
        }
    }


    public static List<String> crop_list()
    {
        String SOAP_ACTION = "http://tempuri.org/crop_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "crop_list";   //函數名稱
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
            Log.v("test"," getString0012: "+ getString);
            getString = getString.replace(" ","").replace("string=","");
            getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
            Log.v("test"," getString2: "+ getString);
            result = Arrays.asList(getString.split(";"));
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
//            result.add(e.toString());
            Log.v("test"," getString: "+ e);
            return result;
        }
    }


    public static List<String> vendor_list()
    {
        String SOAP_ACTION = "http://tempuri.org/vendor_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "vendor_list";   //函數名稱
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
            Log.v("test"," getString0012: "+ getString);
            getString = getString.replace(" ","").replace("string=","");
            getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
            Log.v("test"," getString2: "+ getString);
            result = Arrays.asList(getString.split(";"));
            Log.v("test","ws的result: "+result);
            return result;
        } catch (Exception e) {
//            result.add(e.toString());
            Log.v("test"," getString: "+ e);
            return result;
        }
    }


    public static String add_vendor_name(String vendor_name)
    {
        String SOAP_ACTION = "http://tempuri.org/add_vendor_name";          //命名空間+要用的函數名稱
        String METHOD_NAME = "add_vendor_name";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vendor_name",vendor_name);
            Log.v("test","RRRRR_add_vendor_name"+vendor_name);
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
