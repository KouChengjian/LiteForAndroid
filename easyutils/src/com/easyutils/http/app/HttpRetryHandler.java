package com.easyutils.http.app;


import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashSet;

import org.json.JSONException;

import com.easyutils.http.HttpMethod;
import com.easyutils.http.ex.HttpException;
import com.easyutils.http.request.UriRequest;
import com.easyutils.http.task.Callback;
import com.easyutils.http.utils.HttpLog;

/**
 * Author: wyouflf
 * Time: 2014/05/30
 */
public class HttpRetryHandler {

    protected int maxRetryCount = 2;

    protected static HashSet<Class<?>> blackList = new HashSet<Class<?>>();

    static {
        blackList.add(HttpException.class);
        blackList.add(Callback.CancelledException.class);
        blackList.add(MalformedURLException.class);
        blackList.add(URISyntaxException.class);
        blackList.add(NoRouteToHostException.class);
        blackList.add(PortUnreachableException.class);
        blackList.add(ProtocolException.class);
        blackList.add(NullPointerException.class);
        blackList.add(FileNotFoundException.class);
        blackList.add(JSONException.class);
        blackList.add(UnknownHostException.class);
        blackList.add(IllegalArgumentException.class);
    }

    public HttpRetryHandler() {
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public boolean canRetry(UriRequest request, Throwable ex, int count) {

        HttpLog.w(ex.getMessage(), ex);

        if (count > maxRetryCount) {
        	HttpLog.w(request.toString());
        	HttpLog.w("The Max Retry times has been reached!");
            return false;
        }

        if (!HttpMethod.permitsRetry(request.getParams().getMethod())) {
        	HttpLog.w(request.toString());
        	HttpLog.w("The Request Method can not be retried.");
            return false;
        }

        if (blackList.contains(ex.getClass())) {
        	HttpLog.w(request.toString());
        	HttpLog.w("The Exception can not be retried.");
            return false;
        }

        return true;
    }
}
