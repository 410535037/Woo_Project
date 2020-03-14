package com.example.woo_project.record;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class getTV_HtoV
{
    public static String getTextHtoV(String strText){
        String strResult = "";
        String br = "\n";      //斷行標記，這裏可改用逗號或分號等字符
        List<String> strArr = new ArrayList<>();
        int substring_num = 0;
        strText = "●"+strText;
        for(int i = 0; i < strText.length()/10+1;i++)
        {
            if(substring_num+10 < strText.length())
                strArr.add(strText.substring(substring_num,substring_num+10));
            else
                strArr.add(strText.substring(substring_num));
            substring_num = substring_num+10;
        }
        substring_num = 0;
        //String strArr[] = strText.split("A");
        int nMaxLen = 5;      //各行最多字符數
        int nLines = strArr.size();    //總共的行數
        char charArr[][] = new char[nLines][];    //字符陳列（即二維數組）
        for (int i = 0; i < nLines; i++) {
            String str = strArr.get(i);
            int n = str.length();
            Log.v("test",i + "......"+str);
            //以最長的行的字符數（即原列數）作爲目標陳列的行數
            if (n > nMaxLen) nMaxLen = n;
            charArr[i] = strArr.get(i).toCharArray();
        }

        //行列轉換
        for (int i = 0; i < nMaxLen; i++) {
            for (int j = 0; j < nLines; j++) {
                //若短句字符已“用完”則以空格代替
                char c = i < charArr[j].length ? charArr[j][i] : '　';
                strResult +=String.valueOf(c);

                //兩列文字之間加空格，不需要的話請註釋掉下面一行
                if (j < nLines - 1) strResult += " ";  //★
            }
            strResult += br;   //添加換行符
        }
        Log.v("test",strResult);
        return strResult;
    }
}
