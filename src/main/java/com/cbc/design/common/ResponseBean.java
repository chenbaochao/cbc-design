package com.cbc.design.common;

/**
 * Created by cbc on 2018/3/30.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean<T> {

    private Integer code;

    private String msg;

    private T data;


}