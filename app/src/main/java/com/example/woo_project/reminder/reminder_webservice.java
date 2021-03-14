package com.example.woo_project.reminder;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class reminder_webservice {

    //以下字串必須完全正確，不能有空白!! (之前為了一個空白卡了兩小時...)
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
    private static final String URL = "http://134.208.97.191:8080/reminder_WebService.asmx";     //WebService的網址


    public static String Insert_reminder_vegetable_setting(int variety_of_vege, String vege,String vendor,String reminder_text,int seedling_num,String seedling_unit,int harvest_num, String seedling_day, String planting_day, String harvest_day,int days_of_seedling,int days_of_planting, String real_seedling_day, Boolean do_seedling, Boolean do_planting, Boolean do_harvest, String user) {
        String SOAP_ACTION = "http://tempuri.org/Insert_reminder_vegetable_setting";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Insert_reminder_vegetable_setting";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("variety_of_vege", variety_of_vege);
            request.addProperty("vege", vege);
            request.addProperty("vendor", vendor);
            request.addProperty("reminder_text", reminder_text);
            request.addProperty("seedling_num", seedling_num);
            request.addProperty("seedling_unit", seedling_unit);
            request.addProperty("harvest_num", harvest_num);
            request.addProperty("seedling_day", seedling_day);
            request.addProperty("planting_day", planting_day);
            request.addProperty("harvest_day", harvest_day);
            request.addProperty("days_of_seedling", days_of_seedling);
            request.addProperty("days_of_planting", days_of_planting);
            request.addProperty("real_seedling_day", real_seedling_day);
            request.addProperty("do_seedling", do_seedling);
            request.addProperty("do_planting", do_planting);
            request.addProperty("do_harvest", do_harvest);
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

    public static String Update_reminder_vegetable_setting(String vege,String vendor,String reminder_text,int seedling_num,String seedling_unit,int harvest_num, String seedling_day, String planting_day, String harvest_day,int days_of_seedling,int days_of_planting, String real_seedling_day, Boolean do_seedling, Boolean do_planting, Boolean do_harvest, String user, String seedling_id) {
        String SOAP_ACTION = "http://tempuri.org/Update_reminder_vegetable_setting";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Update_reminder_vegetable_setting";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vege", vege);
            request.addProperty("vendor", vendor);
            request.addProperty("reminder_text", reminder_text);
            request.addProperty("seedling_num", seedling_num);
            request.addProperty("seedling_unit", seedling_unit);
            request.addProperty("harvest_num", harvest_num);
            request.addProperty("seedling_day", seedling_day);
            request.addProperty("planting_day", planting_day);
            request.addProperty("harvest_day", harvest_day);
            request.addProperty("days_of_seedling", days_of_seedling);
            request.addProperty("days_of_planting", days_of_planting);
            request.addProperty("real_seedling_day", real_seedling_day);
            request.addProperty("do_seedling", do_seedling);
            request.addProperty("do_planting", do_planting);
            request.addProperty("do_harvest", do_harvest);
            request.addProperty("user", user);
            request.addProperty("seedling_id",seedling_id);


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
            Log.v("edit","Update_reminder 的result: "+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }



    public static String Select_reminder_vegetable_growing_seedling_day(String user) {
        String SOAP_ACTION = "http://tempuri.org/Select_reminder_vegetable_growing_seedling_day";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_reminder_vegetable_growing_seedling_day";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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

    public static String Select_reminder_vegetable_harvest_day(String user) {
        String SOAP_ACTION = "http://tempuri.org/Select_reminder_vegetable_harvest_day";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_reminder_vegetable_harvest_day";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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

    public static String Select_reminder_vegetable_planting_day(String user) {
        String SOAP_ACTION = "http://tempuri.org/Select_reminder_vegetable_planting_day";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_reminder_vegetable_planting_day";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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

    public static String Insert_reminder_vegetable_planting_day(String vege, String planting_day, String greenhouse,String num,String num_unit, String user) {
        String SOAP_ACTION = "http://tempuri.org/Insert_reminder_vegetable_planting_day";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Insert_reminder_vegetable_planting_day";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vege", vege);
            request.addProperty("planting_day", planting_day);
            request.addProperty("greenhouse", greenhouse);
            request.addProperty("harvest_weight", num);
            request.addProperty("harvest_weight_unit", num_unit);
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

    public static String Insert_greenhouse_and_planting_num(String vege, int variety_of_vege, int seedling_num, String seedling_unit, String vendor, String reminder_text, int harvest_num, String seedling_day, String planting_day, String harvest_day,int days_of_seedling,int days_of_planting, String real_seedling_day,String greenhouse, Boolean do_seedling, Boolean do_planting, Boolean do_harvest, String user) {
        //String
        String SOAP_ACTION = "http://tempuri.org/Insert_greenhouse_and_planting_num";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Insert_greenhouse_and_planting_num";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("vege", vege); //作物名稱
            request.addProperty("variety_of_vege", variety_of_vege); //作物品種
            request.addProperty("seedling_num", seedling_num);  //育苗數量
            request.addProperty("seedling_unit", seedling_unit);  //育苗數量單位
            request.addProperty("vendor", vendor);  //出貨廠商
            request.addProperty("reminder_text", reminder_text); //備註
            request.addProperty("harvest_num", harvest_num); //預計收成數量
            request.addProperty("seedling_day", seedling_day); //預計育苗日
            request.addProperty("planting_day", planting_day); //預計定植日
            request.addProperty("harvest_day", harvest_day);  //預計收成日
            request.addProperty("days_of_seedling", days_of_seedling); //育苗天數
            request.addProperty("days_of_planting", days_of_planting); //定植天數
            request.addProperty("real_seedling_day", real_seedling_day); //實際育苗日

            request.addProperty("greenhouse", greenhouse); //定植棚架********在資料庫的育苗要多新增一個棚架位置

            request.addProperty("do_seedling", do_seedling); //是否完成育苗
            request.addProperty("do_planting", do_planting); //是否完成定植
            request.addProperty("do_harvest", do_harvest);  //是否完成收成
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

    public static String Select_reminder_day(String user,String day1,String day2) {
        String SOAP_ACTION = "http://tempuri.org/Select_reminder_day";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Select_reminder_day";   //函數名稱

        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user", user);
            request.addProperty("day1", day1);
            request.addProperty("day2", day2);

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


    //抓取作物提醒設定育苗的數量單位清單(ex: 盤、株)
    public static List<String> Unit_number_list()
    {
        String SOAP_ACTION = "http://tempuri.org/Unit_number_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "Unit_number_list";   //函數名稱
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

    //抓取育苗CardView所需資料
    public static List<List<String>> reminder_seedling_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_seedling_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_seedling_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //確認育苗CardView
    public static boolean reminder_seedling_data_list_checkornot(String user, String seedling_id, boolean checkornot)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_seedling_data_list_checkornot";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_seedling_data_list_checkornot";   //函數名稱
        boolean result = false ;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user",user);
            request.addProperty("seedling_id",seedling_id);
            request.addProperty("checkornot",checkornot);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","有進WS");
            // 獲取回傳數據
            SoapPrimitive obj1 = (SoapPrimitive) envelope.getResponse();
            Log.v("test","obj1: "+obj1.getValue());
            result = Boolean.parseBoolean(String.valueOf(obj1.getValue()));
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //確認育苗CardView
    public static boolean reminder_seedling_delete(int user, int id)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_seedling_delete";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_seedling_delete";   //函數名稱
        boolean result = false ;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user",user);
            request.addProperty("id",id);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","有進WS");
            // 獲取回傳數據
            SoapPrimitive obj1 = (SoapPrimitive) envelope.getResponse();
            Log.v("test","obj1: "+obj1.getValue());
            result = Boolean.parseBoolean(String.valueOf(obj1.getValue()));
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }


    private static final String planting_TAG="planting";
    //抓取定植CardView所需資料
    public static List<List<String>> reminder_planting_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_planting_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_planting_data_list";   //函數名稱
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
            Log.v(planting_TAG,"有進WS");
            // 獲取回傳數據
            SoapObject obj1 = (SoapObject) envelope.getResponse();
            Log.v(planting_TAG,"obj1: "+obj1);
            Log.v(planting_TAG,"obj1: "+obj1.getProperty(0));
            for(int i=0; i<obj1.getPropertyCount(); i++)
            {
                String getString= obj1.getProperty(i).toString();
                Log.v(planting_TAG,"getString: "+getString);
                getString = getString.replace(" ","").replace("string=","");
                getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
                Log.v(planting_TAG," getString2: "+ getString);
                List<String> x = Arrays.asList(getString.split(";"));
                result.add(x);
            }
            Log.v(planting_TAG,"result: "+result);
            return result;
        } catch (Exception e) {

            Log.v(planting_TAG,"e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //抓取收成CardView所需資料
    public static List<List<String>> reminder_harvest_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_harvest_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_harvest_data_list";   //函數名稱
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
            Log.v(planting_TAG,"有進WS");
            // 獲取回傳數據
            SoapObject obj1 = (SoapObject) envelope.getResponse();
            Log.v(planting_TAG,"obj1: "+obj1);
            Log.v(planting_TAG,"obj1: "+obj1.getProperty(0));
            for(int i=0; i<obj1.getPropertyCount(); i++)
            {
                String getString= obj1.getProperty(i).toString();
                Log.v(planting_TAG,"getString: "+getString);
                getString = getString.replace(" ","").replace("string=","");
                getString = getString.substring(getString.indexOf("{")+1,getString.indexOf("}"));
                Log.v(planting_TAG," getString2: "+ getString);
                List<String> x = Arrays.asList(getString.split(";"));
                result.add(x);
            }
            Log.v(planting_TAG,"result: "+result);
            return result;
        } catch (Exception e) {

            Log.v(planting_TAG,"e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //抓取出貨廠商清單(ex:農會、大王菜舖子...)
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

    public static List<List<String>> reminder_vege_id()
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_vege_id";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_vege_id";   //函數名稱
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

    //今日--育苗CardView所需資料
    public static List<List<String>> reminder_today_seedling_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_today_seedling_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_today_seedling_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //當週--育苗CardView所需資料
    public static List<List<String>> reminder_thisweek_seedling_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_thisweek_seedling_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_thisweek_seedling_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //下週--育苗CardView所需資料
    public static List<List<String>> reminder_nextweek_seedling_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_nextweek_seedling_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_nextweek_seedling_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }



    //今日--定植CardView所需資料
    public static List<List<String>> reminder_today_planting_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_today_planting_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_today_planting_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //當週--定植CardView所需資料
    public static List<List<String>> reminder_thisweek_planting_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_thisweek_planting_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_thisweek_planting_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //下週--定植CardView所需資料
    public static List<List<String>> reminder_nextweek_planting_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_nextweek_planting_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_nextweek_planting_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }


    //今日--收成CardView所需資料
    public static List<List<String>> reminder_today_harvest_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_today_harvest_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_today_harvest_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //當週--收成CardView所需資料
    public static List<List<String>> reminder_thisweek_harvest_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_thisweek_harvest_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_thisweek_harvest_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

    //下週--收成CardView所需資料
    public static List<List<String>> reminder_nextweek_harvest_data_list(String user)
    {
        String SOAP_ACTION = "http://tempuri.org/reminder_nextweek_harvest_data_list";          //命名空間+要用的函數名稱
        String METHOD_NAME = "reminder_nextweek_harvest_data_list";   //函數名稱
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
            Log.v("test","result: "+result);
            return result;
        } catch (Exception e) {

            Log.v("test","e的錯誤訊息 : "+e.toString());
            return result;
        }
    }

}
