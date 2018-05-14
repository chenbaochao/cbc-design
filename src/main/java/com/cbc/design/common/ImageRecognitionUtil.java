package com.cbc.design.common;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.cbc.design.common.Bean.Baidu;

import java.io.File;
import java.util.HashMap;

/**
 * Created by cbc on 2018/4/3.
 */

public class ImageRecognitionUtil {

    private ImageRecognitionUtil(){

    }

    private static class Lazy {
        private static final ImageRecognitionUtil imageRecognitionUtil = new ImageRecognitionUtil();
    }
    public static ImageRecognitionUtil getInstance(){return Lazy.imageRecognitionUtil;};

    private static final String APP_ID = "11044298";
    private static final String API_KEY = "bHqftk9R7NKEs9umPahOAox7";
    private static final String SECRET_KEY = "qdTANsdSvELXGuavLXS1GxXC1DZtcWzQ";

    public boolean checkPornograp(String path){
        // 初始化一个AipImageClassifyClient
        AipContentCensor client = new AipContentCensor(APP_ID,API_KEY,SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        Baidu baidu;
        try {

            org.json.JSONObject res = client.antiPorn(path);
            baidu = JSONObject.parseObject(res.toString(2), Baidu.class);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return "正常".equals(baidu.getConclusion());
    }

}
