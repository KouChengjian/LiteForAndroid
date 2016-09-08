package com.easy.http;


public interface HttpManager {

    /**
     * �첽GET����
     */
//    <T> Callback.Cancelable get(RequestParams entity, Callback.CommonCallback<T> callback);

    /**
     * �첽POST����
     */
//    <T> Callback.Cancelable post(RequestParams entity, Callback.CommonCallback<T> callback);

    /**
     * �첽����
     */
//    <T> Callback.Cancelable request(HttpMethod method, RequestParams entity, Callback.CommonCallback<T> callback);


    /**
     * ͬ��GET����
     */
//    <T> T getSync(RequestParams entity, Class<T> resultType) throws Throwable;

    /**
     * ͬ��POST����
     */
//    <T> T postSync(RequestParams entity, Class<T> resultType) throws Throwable;

    /**
     * ͬ������
     */
//    <T> T requestSync(HttpMethod method, RequestParams entity, Class<T> resultType) throws Throwable;

    /**
     * ͬ������
     */
//    <T> T requestSync(HttpMethod method, RequestParams entity, Callback.TypedCallback<T> callback) throws Throwable;
}
