package com.easy.common.task;

import com.easy.common.listener.Callback;
import com.easy.http.ProgressHandler;


public class HttpTask<ResultType> extends BaseTask<ResultType> implements ProgressHandler {

	
	
//	public HttpTask(RequestParams params, Callback.Cancelable cancelHandler, Callback.CommonCallback<ResultType> callback) {
//        super(cancelHandler);
//
//	}

	@Override
	protected ResultType doBackground() throws Throwable {
		if (this.isCancelled()) {
            throw new Callback.CancelledException("cancelled before request");
        }
		return null;
	}
	
	@Override
	protected void onWaiting() {
		super.onWaiting();
	}
	
	@Override
	protected void onStarted() {
		super.onStarted();
	}

	@Override
	protected void onSuccess(ResultType result) {
		
	}

	@Override
	protected void onError(Throwable ex, boolean isCallbackError) {
		
	}
	
	@Override
	protected void onCancelled(Callback.CancelledException cex) {
		super.onCancelled(cex);
	}

	@Override
	protected void onFinished() {
		super.onFinished();
	}

	
	@Override
	public boolean updateProgress(long total, long current,boolean forceUpdateUI) {
		return false;
	}
}
