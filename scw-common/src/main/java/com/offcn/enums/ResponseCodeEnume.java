package com.offcn.enums;


public enum ResponseCodeEnume {

    SUCCESS(0,"操作成功"),
        FAIL(1,"系统错误"),
    NOTFOUND(2,"资源未找到"),
    NOTAUTHED(3,"没有权限"),
    PARAMERROR(4,"提交参数错误");

    private Integer code;
    private String msg;

    private ResponseCodeEnume(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
