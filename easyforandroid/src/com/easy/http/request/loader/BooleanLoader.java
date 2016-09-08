package com.easy.http.request.loader;

import java.io.InputStream;


import com.easy.http.cache.DiskCacheEntity;
import com.easy.http.request.UriRequest;


public class BooleanLoader extends Loader<Boolean> {

	@Override
    public Loader<Boolean> newInstance() {
        return new BooleanLoader();
    }

	@Override
	public Boolean load(InputStream in) throws Throwable {
		return false;
	}

	@Override
	public Boolean load(UriRequest request) throws Throwable {
		request.sendRequest();
        return request.getResponseCode() < 300;
	}

	@Override
	public Boolean loadFromCache(DiskCacheEntity cacheEntity) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save2Cache(UriRequest request) {
		// TODO Auto-generated method stub
		
	}

}
