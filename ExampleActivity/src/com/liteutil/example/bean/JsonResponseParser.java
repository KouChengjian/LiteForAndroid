package com.liteutil.example.bean;

import java.lang.reflect.Type;

import com.liteutil.android.http.request.UriRequest;
import com.liteutil.android.http.response.ResponseParser;

/**
 * Created by wyouflf on 15/11/5.
 */
public class JsonResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // custom check ?
        // check header ?
    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO: json to java bean
        return null;
    }
}
