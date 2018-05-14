package com.cbc.design.common;

/**
 * Created by cbc on 2018/3/30.
 */
public enum ResultEnum {

    UNKONW_ERROR(-1, "未知错误"),
    REPEAT_SUBMIT(1,"请不要重复提交表单！"),
    SUCCESS(200, "成功"),
    IMAGE_FAIL(2,"图片传输失败！"),
    IMAGE_PORN(3,"上传图片涉黄！"),
    IMAGE_THUMBNAILS_FAIL(4,"图片压缩失败！"),

    //用户  6--
    USERNAME_ALREADY_EXISTED(601,"用户名已经存在！"),
    PHONE_ALREADY_EXISTED(602,"手机号已经存在！"),
    EMAIL_ALREADY_EXISTED(603,"邮箱已经存在！"),
    EMAIL_NOT_ALLOWED(604,"请输入合法的邮箱！"),
    PHONE_NOT_ALLOWED(605,"请输入合法的手机号!"),


    //说说   7--
    CONTEXT_NOT_EMPTY(701,"说说类容不能为空！"),
    TITLE_NOT_EMPTY(702,"说说标题不能为空!"),
    CONTEXT_LENGTH(703,"说说长度不能超过122个汉字！"),
    TITLE_LENGTH(704,"说说名称不能超过122个汉字!"),

    //人脸 8--
    FACE_FAIL(801,"录入人脸信息失败，请重新录入！"),
    FACE_UNKNOW(802,"请把摄像头对准你的脸部！");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}