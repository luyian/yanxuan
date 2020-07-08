package com.it.yanxuan.result;

/**
 * 封装执行结果的实体类
 * @author aaaa
 */
public class Result implements java.io.Serializable {
    private Boolean flag;
    private String message;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
