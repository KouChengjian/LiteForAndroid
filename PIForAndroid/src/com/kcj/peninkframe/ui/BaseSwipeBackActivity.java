package com.kcj.peninkframe.ui;

import android.os.Bundle;
import android.view.View;

import com.kcj.peninkframe.views.swipeback.SwipeBackActivityBase;
import com.kcj.peninkframe.views.swipeback.SwipeBackActivityHelper;
import com.kcj.peninkframe.views.swipeback.SwipeBackLayout;
import com.kcj.peninkframe.views.swipeback.SwipeBackUtils;

/**
 * @ClassName: BaseSwipeBackActivity
 * @Description: 滑动退出
 * @author: KCJ
 * @date: 
 */
abstract public class BaseSwipeBackActivity extends BaseInheritActivity implements SwipeBackActivityBase{

	private SwipeBackActivityHelper mHelper;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }
	
	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
	    if (v == null && mHelper != null)
	        return mHelper.findViewById(id);
	    return v;
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }
	
	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
	}

}
