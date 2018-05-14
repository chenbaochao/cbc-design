package com.cbc.design.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;
import com.cbc.design.auth.domain.User;
import com.cbc.design.common.Bean.Baidu;
import com.cbc.design.common.Bean.BaiduData;
import com.cbc.design.common.exception.ValidationException;

import java.util.HashMap;

public class FaceRecognitionUtil {


    private static final String APP_ID ="11231588";

    private static final String API_KEY = "vc6jSnvEoO5vzSx3AyfP0xyl";

    private static final String SECRET_KEY = "ZYFdrNrSyd5NEj38AGr7VdymTWeqvzCz";


    public static Baidu addUserFace(User user,String img){
        AipFace client = new AipFace(APP_ID,API_KEY,SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        HashMap<String, String> options = new HashMap<>();

        String userInfo = JSONObject.toJSONString(user);
        options.put("user_info",userInfo);
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        String uid = user.getId().toString();
        Baidu baidu = null;
        try {
            org.json.JSONObject res = client.addUser(img,"BASE64","cbc_design",uid, options);
            baidu = JSONObject.parseObject(res.toString(2), Baidu.class);
        }catch (Exception e){
            e.printStackTrace();
            baidu = new Baidu();
        }
        return baidu;
    }

    public static Baidu faceRecognition(String img){
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        HashMap<String, String> options = new HashMap<>();
        options.put("quality_control","NORMAL");
        options.put("liveness_control","LOW");
        String group_id = "cbc_design";
        Baidu baidu;
        try {
            org.json.JSONObject res = client.search(img, "BASE64", group_id, options);
            baidu = JSONObject.parseObject(res.toString(2),Baidu.class);
        }catch (Exception e){
            e.printStackTrace();
            baidu = new Baidu();
        }
        return baidu;
    }
}
