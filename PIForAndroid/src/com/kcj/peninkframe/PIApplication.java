package com.kcj.peninkframe;

import android.app.Application;

import com.kcj.peninkframe.utils.Toastor;

//                                         _oo0oo_
//                                        o8888888o
//                                        88" . "88
//                                        (| -_- |)
//                                        0\  =  /0
//                                      ___/'---'\___
//                                    .' \\\|     |// '.
//                                   / \\\|||  :  |||// \\
//                                  / _ ||||| -:- |||||- \\
//                                  | |  \\\\  -  /// |   |
//                                  | \_|  ''\---/''  |_/ |
//                                  \  .-\__  '-'  __/-.  /
//                                 ___'. .'  /--.--\  '. .'___
//                              ."" '<  '.___\_<|>_/___.' >'  "".
//                             | | : '-  \'.;'\ _ /';.'/ - ' : | |
//                             \  \ '_.   \_ __\ /__ _/   .-' /  /
//                         ====='-.____'.___ \_____/___.-'____.-'=====
//                                           '=---='
//
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//佛祖保佑          永无BUG       镇类之宝

/**

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
