package com.liteutil.android.http.listener;

import com.liteutil.android.http.request.UriRequest;
import com.liteutil.android.http.request.params.RequestParams;

/**
 * Created by wyouflf on 15/11/12.
 * 请求重定向控制接口
 */
public interface RedirectHandler {

    /**
     * 根据请求信息返回自定义重定向的请求参数
     *
     * @param request
     * @return 返回不为null时进行重定向
     */
    RequestParams getRedirectParams(UriRequest request);
}
