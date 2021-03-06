package com.easyutils.http.loader;


import java.io.InputStream;
import java.util.Date;

import android.text.TextUtils;

import com.easyutils.http.ProgressHandler;
import com.easyutils.http.RequestParams;
import com.easyutils.http.cache.DiskCacheEntity;
import com.easyutils.http.cache.LruDiskCache;
import com.easyutils.http.request.UriRequest;

public abstract class Loader<T> {

    protected RequestParams params;
    protected ProgressHandler progressHandler;

    public void setParams(final RequestParams params) {
        this.params = params;
    }

    public void setProgressHandler(final ProgressHandler callbackHandler) {
        this.progressHandler = callbackHandler;
    }

    protected void saveStringCache(UriRequest request, String resultStr) {
        if (!TextUtils.isEmpty(resultStr)) {
            DiskCacheEntity entity = new DiskCacheEntity();
            entity.setKey(request.getCacheKey());
            entity.setLastAccess(System.currentTimeMillis());
            entity.setEtag(request.getETag());
            entity.setExpires(request.getExpiration());
            entity.setLastModify(new Date(request.getLastModified()));
            entity.setTextContent(resultStr);
            LruDiskCache.getDiskCache(request.getParams().getCacheDirName()).put(entity);
        }
    }

    public abstract Loader<T> newInstance(); // 实例

    public abstract T load(final InputStream in) throws Throwable; // read after send content 

    public abstract T load(final UriRequest request) throws Throwable; // send content

    public abstract T loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable;

    public abstract void save2Cache(final UriRequest request);
}
