package com.cbc.design.common.exception;

import com.cbc.design.common.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by cbc on 2018/3/30.
 */

public class RepeatSubmitException extends RuntimeException{

    @Getter
    @Setter
    private Integer code;

    public RepeatSubmitException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public RepeatSubmitException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

}
