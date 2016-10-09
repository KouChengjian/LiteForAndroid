package com.easyutils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import android.content.Context;

import com.easyutils.http.HttpManagerImpl;
import com.easyutils.http.task.TaskController;
import com.easyutils.http.task.TaskControllerImpl;

public final class Easy {

	private static Context mContext;
	private static boolean debug = EasyConfig.debug;
    private static TaskController taskController;
    private static HttpManagerImpl httpManagerImpl;  
	
	static {
		// 默认信任所有https域名
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}
	
	public static void init(Context context){
		mContext = context;
		taskController = TaskControllerImpl.registerInstance();
		httpManagerImpl = HttpManagerImpl.registerInstance(); 
	}
	
	public static boolean isDebug() {
		return debug;
	}

	public static Context getContext() {
		return mContext;
	}
	
	public static TaskController task() {
		if (taskController == null) {
			taskController = TaskControllerImpl.registerInstance();
		}
		return taskController;
	}

	public static HttpManagerImpl http() {
		if (httpManagerImpl == null) {
			httpManagerImpl = HttpManagerImpl.registerInstance();
		}
		return httpManagerImpl;
	}
}
