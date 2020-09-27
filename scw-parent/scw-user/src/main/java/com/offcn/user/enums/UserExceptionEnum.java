package com.offcn.user.enums;


public enum UserExceptionEnum {
    // 两种情况
    // 账户已经存在
    LOGIN_EXIST(1,"账户已经存在"),
    // 账户被锁定
    LOGIN_LOCKED(2,"账户被锁定"),
    // 邮箱
    EMAIL_EXIST(3,"邮箱已经存在");
    private Integer code;
    private String msg;

    private UserExceptionEnum(Integer code, String msg) {
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


