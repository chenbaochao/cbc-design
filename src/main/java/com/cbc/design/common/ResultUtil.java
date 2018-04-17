package com.cbc.design.common;

import org.springframework.http.HttpStatus;

/**
 * Created by cbc on 2018/3/30.
 */
public class ResultUtil {

    public static ResponseBean success(Object o){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(200);
        responseBean.setMsg("成功！");
        responseBean.setData(o);
        return responseBean;
    }

    public static ResponseBean success(){
        return success(null);
    };

    public static ResponseBean error(Integer code,String msg){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(code);
        responseBean.setMsg(msg);
        return responseBean;
    }
}
