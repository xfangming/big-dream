package org.dream.toolkit.bean;

public class APIResponse<T> {
    /**
     * 成功码.
     */
    public static final int SUCCESS_CODE = 200;
    /**
     * 错误码.
     */
    public static final int ERROR_CODE = 500;
    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = "操作成功";
    /**
     * 错误信息
     */
    public static final String ERROR_MESSAGE="操作失败";
    private int code;
    private T data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
