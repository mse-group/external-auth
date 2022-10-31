package com.mse.externalauth.model;

/**
 * @author special.fy
 */
public final class RestResult<T> {

    private int status=200;

    private String message;

    private T Data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public static <T> RestResult<T> failResult(int httpStatusCode, String message) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setStatus(httpStatusCode);
        restResult.setMessage(message);
        return restResult;
    }

    public static <T> RestResult<T> success(String message) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setMessage(message);
        return restResult;
    }

    public static <T> RestResult<T> successResult(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setData(data);
        return restResult;
    }

    public static <T> RestResult<T> successResult(T data, String message) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setData(data);
        restResult.setMessage(message);
        return restResult;
    }
}
