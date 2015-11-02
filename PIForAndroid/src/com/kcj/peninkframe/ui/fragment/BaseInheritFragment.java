package com.kcj.peninkframe.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;

import com.kcj.peninkframe.utils.Toastor;

/**
 * @ClassName: BaseInheritFragment
 * @Description: all fragment inherit base
 * @author: KCJ
 * @date: 
 */
public class BaseInheritFragment extends Fragment {
	
	protected Context mContext;
	protected String TAG; // 打印的名称
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = this.getClass().getSimpleName();
		mContext = getActivity();
	}

	public void setContentView(View view) {
		ButterKnife.inject(this, view);
	}
	
	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}
	
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	
	public void startAnimActivity(Class<?> cla) {
		getActivity().startActivity(new Intent(getActivity(), cla));
	}
	
	protected void startAnimActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
	public void ShowToast(String text) {
		Toastor.ShowToast(text);
	}

	public void ShowToast(int text) {
		Toastor.ShowToast(text);
	}
	
}
