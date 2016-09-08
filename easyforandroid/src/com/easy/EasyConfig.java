package com.easy;

import android.content.Context;

public class EasyConfig {
	
	private static Context mContext;
	private static boolean debug = true;
	
	public static void init(Context context){
		mContext = context;
	}
	
	public static boolean isDebug() {
		return debug;
	}

	public static Context getContext() {
		return mContext;
	}
}
