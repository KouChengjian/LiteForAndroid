package com.easy.http.request;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.text.TextUtils;

import com.easy.bean.KeyValue;
import com.easy.http.RequestParams;


public class HttpRequest extends UriRequest{

	private String cacheKey = null;
    private boolean isLoading = false;
    private InputStream inputStream = null;
    private HttpURLConnection connection = null;
    private int responseCode = 0;
    
    public HttpRequest(RequestParams params, Type loadType) throws Throwable {
        super(params, loadType);
    }
    
    @Override
    protected String buildQueryUrl(RequestParams params) {
        String uri = params.getUri();
        StringBuilder queryBuilder = new StringBuilder(uri);
        if (!uri.contains("?")) {
            queryBuilder.append("?");
        } else if (!uri.endsWith("?")) {
            queryBuilder.append("&");
        }
        List<KeyValue> queryParams = params.getQueryStringParams();
        if (queryParams != null) {
            for (KeyValue kv : queryParams) {
                String name = kv.key;
                String value = kv.getValueStr();
                if (!TextUtils.isEmpty(name) && value != null) {
                    queryBuilder.append(
                            Uri.encode(name, params.getCharset()))
                            .append("=")
                            .append(Uri.encode(value, params.getCharset()))
                            .append("&");
                }
            }
        }

        if (queryBuilder.charAt(queryBuilder.length() - 1) == '&') {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }

        if (queryBuilder.charAt(queryBuilder.length() - 1) == '?') {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }
        return queryBuilder.toString();
    }

	@Override
	public void sendRequest() throws Throwable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLoading() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCacheKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object loadResultFromCache() throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCacheHeader() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResponseCode() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getResponseMessage() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getExpiration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastModified() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getETag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResponseHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<String>> getResponseHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getHeaderFieldDate(String name, long defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}
}
