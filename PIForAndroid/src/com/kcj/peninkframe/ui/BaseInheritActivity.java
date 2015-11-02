package com.kcj.peninkframe.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import butterknife.ButterKnife;

import com.kcj.peninkframe.utils.ActivityTack;
import com.kcj.peninkframe.utils.Toastor;

/**
 * @ClassName: BaseInheritActivity
 * @Description: all activity inherit base
 * @author: KCJ
 * @date: 
 */
public class BaseInheritActivity extends FragmentActivity{

	protected Context mContext;
	protected String TAG; // 打印的名称
	protected ActivityTack tack = ActivityTack.getInstanse();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		TAG = this.getClass().getSimpleName();
		tack.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		tack.removeActivity(this);
		super.onDestroy();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
	}
	
	@Override
	public void finish() {
		super.finish();
		tack.removeActivity(this);
	}
	
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}

	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	
	protected void startAnimActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toastor.ShowToast(text);
				}
			});
		}
	}

	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toastor.ShowToast(resId);
			}
		});
	}
}
