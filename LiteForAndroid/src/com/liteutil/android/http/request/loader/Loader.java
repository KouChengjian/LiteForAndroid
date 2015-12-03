package com.liteutil.android.http.request.loader;


import android.text.TextUtils;

import com.liteutil.android.bean.DiskCacheEntity;
import com.liteutil.android.bean.LruDiskCache;
import com.liteutil.android.http.request.RequestTracker;
import com.liteutil.android.http.request.UriRequest;
import com.liteutil.android.http.request.params.RequestParams;
import com.liteutil.android.http.task.ProgressHandler;

import java.io.InputStream;
import java.util.Date;

/**
 * @ClassName: Loader<T>
 * @Description: 加载基类
 * @author:
 * @date:
 */
public abstract class Loader<T> {

    protected RequestParams params;
    protected RequestTracker tracker;
    protected ProgressHandler progressHandler;

    public void setParams(final RequestParams params) {
        this.params = params;
    }

    public void setProgressHandler(final ProgressHandler callbackHandler) {
        this.progressHandler = callbackHandler;
    }

    public void setResponseTracker(RequestTracker tracker) {
        this.tracker = tracker;
    }

    public RequestTracker getResponseTracker() {
        return this.tracker;
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

    public abstract Loader<T> newInstance();

    public abstract T load(final InputStream in) throws Throwable;

    public abstract T load(final UriRequest request) throws Throwable;

    public abstract T loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable;

    public abstract void save2Cache(final UriRequest request);
}
