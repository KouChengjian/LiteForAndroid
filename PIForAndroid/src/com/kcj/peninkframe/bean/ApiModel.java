package com.kcj.peninkframe.bean;

/**
 * @author MaTianyu @http://litesuits.com
 * @date 2015-10-03
 */
public class ApiModel<T> extends BaseModel {

    /**
     * 不变的部分：写在API基类中
     */
    private String api;
    private String v;

    protected int code;
    protected String message;

    /**
     * 变化的部分：使用泛型，数据类型的确认延迟到子类里。
     */
    protected T data;


    public String getApi() {
        return api;
    }

    public ApiModel setApi(String api) {
        this.api = api;
        return this;
    }

    public String getV() {
        return v;
    }

    public ApiModel setV(String v) {
        this.v = v;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ApiModel setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiModel setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ApiModel{" +
               "api='" + api + '\'' +
               ", v='" + v + '\'' +
               ", code=" + code +
               ", message='" + message + '\'' +
               ", data=" + data +
               "} ";
    }
}
