package com.cbc.design.common.Bean;

import lombok.Data;

/**
 * Created by cbc on 2018/4/3.
 */
@Data
public class Baidu {
    private String log_id;
    private String error_code;
    private String error_msg;
    private String conclusion;
    private BaiduData result;
    private BaiduData[] data;
    private String confidence_coefficient;
    private int result_num;
    private String cached;
    private String timestamp;
}
