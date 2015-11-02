package com.kcj.peninkframe;

import com.kcj.peninkframe.utils.Toastor;

import android.app.Application;

/**
 * @ClassName: 
 * @Description: 
 * @author: 
 * @date: 
 */
public class PIApplication extends Application{

public static PIApplication mInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		new	Toastor(this);
	}
	
	public static PIApplication getInstance() {
		return mInstance;
	}
	
}
