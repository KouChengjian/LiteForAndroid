package com.easy.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.text.TextUtils;

import com.easy.http.app.KeyValue;
import com.easy.http.body.BaseRequestBody;


/**
 * 请求的基础参数
 */
public abstract class BaseParams {

	private String charset = "UTF-8";
    private HttpMethod method;  // 请求方式 get post
    private String bodyContent; // 
    private boolean multipart = false; // 是否强制使用multipart表单
    private boolean asJsonContent = false; // 用json形式的bodyParams上传
    private BaseRequestBody requestBody; // 生成的表单

    private final List<Header> headers = new ArrayList<Header>(); // 头文件
    private final List<KeyValue> queryStringParams = new ArrayList<KeyValue>();
    private final List<KeyValue> bodyParams = new ArrayList<KeyValue>(); // body内容
    private final List<KeyValue> fileParams = new ArrayList<KeyValue>(); // body文件
	
    public void setCharset(String charset) {
        if (!TextUtils.isEmpty(charset)) {
            this.charset = charset;
        }
    }

    public String getCharset() {
        return charset;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }
    
    public boolean isMultipart() {
        return multipart;
    }

    public void setMultipart(boolean multipart) {
        this.multipart = multipart;
    }

    /**
     * 以json形式提交body参数
     */
    public boolean isAsJsonContent() {
        return asJsonContent;
    }

    /**
     * 以json形式提交body参数
     */
    public void setAsJsonContent(boolean asJsonContent) {
        this.asJsonContent = asJsonContent;
    }
    
    /**
     * 覆盖header
     *
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        Header header = new Header(name, value, true);
        Iterator<Header> it = headers.iterator();
        while (it.hasNext()) {
            KeyValue kv = it.next();
            if (name.equals(kv.key)) {
                it.remove();
            }
        }
        this.headers.add(header);
    }

    /**
     * 添加header
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        this.headers.add(new Header(name, value, false));
    }
    
    /**
     * 添加参数至Query String
     *
     * @param name
     * @param value
     */
    public void addQueryStringParameter(String name, String value) {
        if (!TextUtils.isEmpty(name)) {
            this.queryStringParams.add(new KeyValue(name, value));
        }
    }
    
    
    
    
    
    
    public final static class Header extends KeyValue {

        public final boolean setHeader;

        public Header(String key, String value, boolean setHeader) {
            super(key, value);
            this.setHeader = setHeader;
        }
    }
}
