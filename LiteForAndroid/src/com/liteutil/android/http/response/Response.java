package com.liteutil.android.http.response;

import com.liteutil.android.http.data.HttpStatus;
import com.liteutil.android.http.data.NameValuePair;
import com.liteutil.android.http.exception.HttpException;
import com.liteutil.android.http.request.AbstractRequest;

/**
 * User Facade
 * providing developers with easy access to the results of
 * {@link com.litesuits.http.LiteHttp#execute(com.litesuits.http.request.AbstractRequest)},
 * and with information of status,request,charset,etc.
 *
 * @author MaTianyu
 *         2014-1-1下午10:00:42
 */
public interface Response<T> {


    public NameValuePair[] getHeaders();

    public HttpStatus getHttpStatus();

    public T getResult();

    public <R extends AbstractRequest<T>> R getRequest();

    public long getReadedLength();

    public long getContentLength();

    public String getCharSet();

    public long getUseTime();

    public boolean isConnectSuccess();

    public int getRetryTimes();

    public int getRedirectTimes();

    public HttpException getException();

    public boolean isCacheHit();

    public String getRawString();

    public Response<T> setTag(Object tag);

    public Object getTag();

    String resToString();

    void printInfo();

}
