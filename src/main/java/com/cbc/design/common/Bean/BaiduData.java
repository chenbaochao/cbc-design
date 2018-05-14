package com.cbc.design.common.Bean;

import lombok.Data;

import java.util.Map;

/**
 * @DESCRIPTION :百度色情图像识别pojo
 * @AUTHOR : cbc
 * @TIME : 2018/4/03  14:01
 */
@Data
public class BaiduData {
    private String msg;
    private double probability;
    private int type;
    private String class_name;
    private BaiduDataStars[] stars;
    private String face_token;
    private Face location;
}
