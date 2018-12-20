package com.example.yuekaomoni01.bean;

public class AddCarBean {

    /**
     * msg : 加购成功
     * code : 0
     */

    private String msg;
    private String code;
    private final String SUCCESS_CODE="0";
    public boolean isSuccess(){

        return code.equals(SUCCESS_CODE);
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
