package com.cbc.design.common.exception;

import com.cbc.design.common.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by cbc on 2018/4/1.
 */
public class ValidationException extends RuntimeException {

    @Getter
    @Setter
    private Integer code;

    public ValidationException(ResultEnum resultEmum){
        super(resultEmum.getMsg());
        this.code = resultEmum.getCode();
    }

    public ValidationException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
