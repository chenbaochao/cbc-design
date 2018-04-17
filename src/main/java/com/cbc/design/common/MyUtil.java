package com.cbc.design.common;

import lombok.Data;
import lombok.Getter;
import net.coobird.thumbnailator.Thumbnails;

import java.util.Random;

/**
 * Created by cbc on 2018/3/28.
 */

public class MyUtil {

    /**
     * 静态内部类懒汉式单例模式（保证延迟加载，并且线程安全）
     */

    private MyUtil() {
    }


    private static class Lazy {
        private static final MyUtil myUtil = new MyUtil();
    }

    public static MyUtil getInstance() {
        return Lazy.myUtil;
    }


    private final static String txt = "这是我的毕设，请不要乱来！！！";

    public String getTxt() {
        return txt;
    }

    //随机字符串
    private final static String CBC = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    /**
     * 获得随机生成的字符串
     *
     * @return 获得随机生成的字符串
     */
    public String getRandom() {
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        char[] now = CBC.toCharArray();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(now[random.nextInt(CBC.length())]);
        }
        return stringBuffer.toString();
    }



    /**
     * 将图片生成对应的缩略图
     *
     * @param path 传尽量图片的路径
     * @param save 保存图片的路径
     * @return 成功-true
     */
    public boolean thumbnails(String path, String save) {
        try {
            Thumbnails.of(path).size(150, 150).toFile(save);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
