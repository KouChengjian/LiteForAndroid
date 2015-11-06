package com.kcj.peninkframe.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;
import com.kcj.peninkframe.R;

public class CustomBaseDialog extends BaseDialog { // <CustomBaseDialog>

	private TextView tv_cancel;
    private TextView tv_exit;
	
	public CustomBaseDialog(Context context) {
		super(context);
	}

	@Override
	public View onCreateView() {
		widthScale(0.85f);
        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.dialog_custom_base, null);
        tv_cancel = (TextView)inflate.findViewById(R.id.tv_cancel);
        tv_exit = (TextView)inflate.findViewById(R.id.tv_exit);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
        return inflate;
	}

	@Override
	public boolean setUiBeforShow() {
		tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
		return false;
	}

}
