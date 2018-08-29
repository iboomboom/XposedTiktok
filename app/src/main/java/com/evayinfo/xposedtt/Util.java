package com.evayinfo.xposedtt;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;

import com.evayinfo.grace.utils.FileUtils;
import com.evayinfo.grace.utils.SharedPreUtils;
import com.evayinfo.xposedtt.bean.CountryInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String sign(String content, String salt, String key) {
        String signBase = "20180824000198272" + content + salt + key;
        return MD5.md5(signBase);
    }

    public static String getNum19() {
        String numStr = "";
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        numStr = trandStr.toString().substring(0, 4);
        numStr = numStr + dataStr;
        return numStr;
    }

    public static CountryInfo getCountry() {


        String strFilename = getSDPath() +File.separator+"config";
        File configFile = new File(strFilename,"config.txt");

        if (!configFile.exists()) {
            saveCurrentCountryInfo("日本","jp","08014350231");
            return new CountryInfo("日本","jp","08014350231");
        }

        String json ="" ;

        try {
            //读取当前项目下的StringDemo.java文件
            FileReader fr = new FileReader(configFile);
            //一次读取一个字符数组
            char[] chs = new char[1024] ;
            int len = 0 ;
            while((len=fr.read(chs))!=-1) {
                json = json + new String(chs,0,len);
            }
            //释放资源
            fr.close();

            return new Gson().fromJson(json,CountryInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new CountryInfo("日本","jp","08014350231");
        }

    }


    public static boolean saveCurrentCountryInfo(String name, String area, String phone) {

        CountryInfo countryInfo = new CountryInfo(name,area,phone);
        Gson gson = new Gson();
        String info = gson.toJson(countryInfo);

        String strFilename = getSDPath() +File.separator+"config";
        File fileText = new File(strFilename);

        if (!fileText.exists()) {
            fileText.mkdir();
        }
        File configFile = new File(fileText,"config.txt");

        try {
            // 创建文件对象
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(configFile);

            // 写文件
            fileWriter.write(info);
            // 关闭
            fileWriter.close();

            return true;
        } catch (IOException e) {
            //
            e.printStackTrace();
            return false;
        }
    }


    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public static boolean checkApkExist(Context context){
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo("io.virtualapp",
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}

