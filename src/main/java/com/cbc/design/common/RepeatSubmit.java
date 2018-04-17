package com.cbc.design.common;

import org.apache.commons.lang3.StringUtils;

/**
 *  重复提交
 * Created by cbc on 2018/3/28.
 */
public class RepeatSubmit {

    public static boolean isRepeatSubmit(String user_token,String client_token){
        if(StringUtils.isAnyBlank(user_token)) {
            return true;
        }
        if(!user_token.equals(client_token)){
            return true;
        }
        return false;
    }
}
