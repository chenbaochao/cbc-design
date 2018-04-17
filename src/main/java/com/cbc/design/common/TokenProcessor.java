package com.cbc.design.common;


import io.netty.handler.codec.base64.Base64Encoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * Created by cbc on 2018/3/28.
 */

public class TokenProcessor {

    /**
     * 饿汉式单例模式
     */

    private TokenProcessor() {
    }

    private static final TokenProcessor instance = new TokenProcessor();

    public static TokenProcessor getInstance() {
        return instance;
    }


    /**
     *  生成token
     */
    public String generateToken(){
        String token = System.currentTimeMillis() + new Random().nextInt(999999999)+"";
        //数据指纹   128位长   16个字节  md5
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());
            //base64编码--任意二进制编码明文字符
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean saveToken(HttpServletRequest request, String name, String value) {
        request.getSession().setAttribute(name,value);
        return true;
    }

    public boolean deleteToken(HttpServletRequest request, String name) {
        request.getSession().removeAttribute(name);
        return true;
    }

    public String getToken(HttpServletRequest request, String name) {
        return (String) request.getSession().getAttribute(name);
    }
}
