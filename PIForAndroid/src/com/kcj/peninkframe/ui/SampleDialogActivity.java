package com.kcj.peninkframe.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FlipEnter.FlipVerticalEnter;
import com.flyco.animation.ZoomExit.ZoomOutExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.MaterialTipDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.dialog.widget.NormalTipDialog;
import com.kcj.peninkframe.R;
import com.kcj.peninkframe.views.CustomBaseDialog;
import com.kcj.peninkframe.views.DiaogAnimChoose;
import com.kcj.peninkframe.views.IOSTaoBaoDialog;
import com.kcj.peninkframe.views.ShareBottomDialog;
import com.kcj.peninkframe.views.ShareTopDialog;

/**
 * @ClassName: SampleDialogActivity
 * @Description: 各式的对话框
 * @author: KCJ
 * @date:
 */
public class SampleDialogActivity extends BaseSwipeBackActivity implements
		OnItemClickListener {

	private ListView listView;
	Context context;

	private BaseAnimatorSet bas_in;
	private BaseAnimatorSet bas_out;

	private ArrayList<DialogMenuItem> testItems = new ArrayList<>();
	private String[] stringItems = { "收藏", "下载", "分享", "删除", "歌手", "专辑" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);
		listView.setOnItemClickListener(this);

		context = this;
		bas_in = new FlipVerticalEnter();
		bas_out = new ZoomOutExit();

		testItems.add(new DialogMenuItem("收藏", R.mipmap.ic_winstyle_favor));
		testItems.add(new DialogMenuItem("下载", R.mipmap.ic_winstyle_download));
		testItems.add(new DialogMenuItem("分享", R.mipmap.ic_winstyle_share));
		testItems.add(new DialogMenuItem("删除", R.mipmap.ic_winstyle_delete));
		testItems.add(new DialogMenuItem("歌手", R.mipmap.ic_winstyle_artist));
		testItems.add(new DialogMenuItem("专辑", R.mipmap.ic_winstyle_album));
	}

	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("1.1 NormalDialog Default(two btns)");
		data.add("1.2 NormalDialog StyleTwo");
		data.add("1.3 NormalDialog Custom Attr");
		data.add("1.4 NormalDialog(one btn)");
		data.add("1.5 NormalDialog(three btns)");
		data.add("1.6 MaterialDialogDefault Default(two btns)");
		data.add("1.7 MaterialDialogDefault(one btn)");
		data.add("1.8 MaterialDialogDefault(three btns)");
		data.add("1.9 NormalListDialog");
		data.add("1.10 NormalListDialog Custom Attr");
		data.add("1.11 NormalListDialog No Title");
		data.add("1.12 NormalListDialog DataSource String[]");
		data.add("1.13 NormalListDialog DataSource Adapter");
		data.add("1.14 ActionSheetDialog");
		data.add("1.14 ActionSheetDialog NoTitle");

		data.add("2.1 Custome Dialog extends BaseDialog");
		data.add("2.2 Custome Dialog extends BottomBaseDialog");
		data.add("2.3 Custome Dialog extends TopBaseDialog");

		data.add("3.1 Show Anim");
		data.add("3.2 Dismiss Anim");

		data.add("4.1 Custom Anim like ios taobao");
		return data;
	}

	public void setBasIn(BaseAnimatorSet bas_in) {
		this.bas_in = bas_in;
	}

	public void setBasOut(BaseAnimatorSet bas_out) {
		this.bas_out = bas_out;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		switch (pos) {
		case 0: // 1.1~1.14
			NormalDialogStyleOne();
			break;
		case 1:
			NormalDialogStyleTwo();
			break;
		case 2:
			NormalDialogCustomAttr();
			break;
		case 3:
			NormalDialogOneBtn();
			break;
		case 4:
			NormalDialoThreeBtn();
			break;
		case 5:
			MaterialDialogDefault();
			break;
		case 6:
			MaterialDialogThreeBtns();
			break;
		case 7:
			MaterialDialogOneBtn();
			break;
		case 8:
			NormalListDialog();
			break;
		case 9:
			NormalListDialogCustomAttr();
			break;
		case 10:
			NormalListDialogNoTitle();
			break;
		case 11:
			NormalListDialogStringArr();
			break;
		case 12:
			NormalListDialogAdapter();
			break;
		case 13:
			ActionSheetDialog();
			break;
		case 14:
			ActionSheetDialogNoTitle();
			break;

		case 15:
			final CustomBaseDialog dialog = new CustomBaseDialog(context);
			dialog.show();
			break;
		case 16:
			final ShareBottomDialog dialog1 = new ShareBottomDialog(context,
					listView);
			dialog1.showAnim(bas_in)//
					.show();//
			// .show(0, 100);
			break;
		case 17:
			final ShareTopDialog dialog2 = new ShareTopDialog(context);
			dialog2.showAnim(bas_in)//
					.show();
			// .show(0, 100);
			break;

		case 18:
			DiaogAnimChoose.showAnim(context);
			break;
		case 19:
			DiaogAnimChoose.dissmissAnim(context);
			break;

		case 20:
			final IOSTaoBaoDialog dialog5 = new IOSTaoBaoDialog(context,
					(View) listView.getParent());
			dialog5.show();
			// .show(0, 100);
			break;
		}
	}

	private void NormalDialogStyleOne() {
		final NormalDialog dialog = new NormalDialog(context);
		dialog.title("haha").content("是否确定退出程序?")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();
		dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				ShowToast("left");
				dialog.dismiss();

			}
		});
		dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				ShowToast("right");
				dialog.dismiss();
			}
		});
	}

	private void NormalDialogStyleTwo() {
		final NormalDialog dialog = new NormalDialog(context);
		dialog.content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
				.style(NormalDialog.STYLE_TWO)//
				.titleTextSize(23)//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

		dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				ShowToast("left");
				dialog.dismiss();

			}
		});
		dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				ShowToast("right");
				dialog.dismiss();
			}
		});

	}

	private void NormalDialogCustomAttr() {
		final NormalDialog dialog = new NormalDialog(context);
		dialog.isTitleShow(false)
				//
				.bgColor(Color.parseColor("#383838"))
				//
				.cornerRadius(5)
				//
				.content("是否确定退出程序?")
				//
				.contentGravity(Gravity.CENTER)
				//
				.contentTextColor(Color.parseColor("#ffffff"))
				//
				.dividerColor(Color.parseColor("#222222"))
				//
				.btnTextSize(15.5f, 15.5f)
				//
				.btnTextColor(Color.parseColor("#ffffff"),
						Color.parseColor("#ffffff"))//
				// .btnPressColor(Color.parseColor("#2B2B2B"))//
				.widthScale(0.85f)//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

		dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				ShowToast("left");
				dialog.dismiss();

			}
		});
		dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				ShowToast("right");
				dialog.dismiss();
			}
		});
	}

	private void NormalDialogOneBtn() {
		final NormalTipDialog dialog = new NormalTipDialog(context);
		dialog.content("你今天的抢购名额已用完~")//
				// .btnNum(1)
				.style(NormalDialog.STYLE_ONE).btnText("继续逛逛")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

		dialog.setOnBtnClickL(new OnBtnClickL() {

			@Override
			public void onBtnClick() {
				ShowToast("left");
				dialog.dismiss();

			}

		});
	}

	private void NormalDialoThreeBtn() {
		final NormalDialog dialog = new NormalDialog(context);
		dialog.content("你今天的抢购名额已用完~")//
				.style(NormalDialog.STYLE_TWO)//
				// .btnNum(3)
				.btnText("确定", "继续逛逛")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

		dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				ShowToast("left");
				dialog.dismiss();

			}
		});
		dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				ShowToast("right");
				dialog.dismiss();
			}
		});
	}

	private void MaterialDialogDefault() {
		final MaterialDialog dialog = new MaterialDialog(context);
		dialog.content(
				"嗨！这是一个 MaterialDialogDefault. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。"
						+ "它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^")//
				.btnText("取消", "确定")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

		dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				ShowToast("left");
				dialog.dismiss();

			}
		});
		dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				ShowToast("right");
				dialog.dismiss();
			}
		});
	}

	private void MaterialDialogThreeBtns() {
		final MaterialDialog dialog = new MaterialDialog(context);
		dialog.isTitleShow(false)//
				.content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
				.btnText("取消", "知道了")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

	}

	private void MaterialDialogOneBtn() {
		final MaterialTipDialog dialog = new MaterialTipDialog(context);
		dialog//
		.content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
				.btnText("确定")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

	}

	private void NormalListDialog() {
		final NormalListDialog dialog = new NormalListDialog(context, testItems);
		dialog.title("请选择")//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();
		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(testItems.get(position).operName);
				dialog.dismiss();
			}
		});
	}

	private void NormalListDialogCustomAttr() {
		final NormalListDialog dialog = new NormalListDialog(context, testItems);
		dialog.title("请选择")//
				.titleTextSize_SP(18)//
				.titleBgColor(Color.parseColor("#409ED7"))//
				.itemPressColor(Color.parseColor("#85D3EF"))//
				.itemTextColor(Color.parseColor("#303030"))//
				.itemTextSize(14)//
				.cornerRadius(0)//
				.widthScale(0.8f)//
				.show(R.style.myDialogAnim);

		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(testItems.get(position).operName);
				dialog.dismiss();
			}
		});
	}

	private void NormalListDialogNoTitle() {
		final NormalListDialog dialog = new NormalListDialog(context, testItems);
		dialog.title("请选择")//
				.isTitleShow(false)//
				.itemPressColor(Color.parseColor("#85D3EF"))//
				.itemTextColor(Color.parseColor("#303030"))//
				.itemTextSize(15)//
				.cornerRadius(2)//
				.widthScale(0.75f)//
				.show();

		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(testItems.get(position).operName);
				dialog.dismiss();
			}
		});
	}

	private void NormalListDialogStringArr() {
		final NormalListDialog dialog = new NormalListDialog(context,
				stringItems);
		dialog.title("请选择")//
				.layoutAnimation(null).show(R.style.myDialogAnim);
		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(testItems.get(position).operName);
				dialog.dismiss();
			}
		});
	}

	private void NormalListDialogAdapter() {
		final NormalListDialog dialog = new NormalListDialog(context,
				new TestAdapter(context, testItems));
		dialog.title("请选择")//
				.show(R.style.myDialogAnim);
		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(testItems.get(position).operName);
				dialog.dismiss();
			}
		});
	}

	private void ActionSheetDialog() {
		final String[] stringItems = { "接收消息并提醒", "接收消息但不提醒", "收进群助手且不提醒",
				"屏蔽群消息" };
		final ActionSheetDialog dialog = new ActionSheetDialog(context,
				stringItems, null);
		dialog.title("选择群消息提醒方式\r\n(该群在电脑的设置:接收消息并提醒)")//
				.titleTextSize_SP(14.5f)//
				.show();

		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(stringItems[position]);
				dialog.dismiss();
			}
		});
	}

	private void ActionSheetDialogNoTitle() {
		final String[] stringItems = { "版本更新", "帮助与反馈", "退出QQ" };
		final ActionSheetDialog dialog = new ActionSheetDialog(context,
				stringItems, listView);
		dialog.isTitleShow(false).show();

		dialog.setOnOperItemClickL(new OnOperItemClickL() {
			@Override
			public void onOperItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(stringItems[position]);
				dialog.dismiss();
			}
		});
	}

	class TestAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<DialogMenuItem> customBaseItems;
		private DisplayMetrics dm;

		public TestAdapter(Context context,
				ArrayList<DialogMenuItem> customBaseItems) {
			this.context = context;
			this.customBaseItems = customBaseItems;

			dm = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
		}

		@Override
		public int getCount() {
			return customBaseItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final DialogMenuItem item = customBaseItems.get(position);

			LinearLayout ll_item = new LinearLayout(context);
			ll_item.setOrientation(LinearLayout.HORIZONTAL);
			ll_item.setGravity(Gravity.CENTER_VERTICAL);

			ImageView iv_item = new ImageView(context);
			iv_item.setPadding(0, 0, (int) (15 * dm.density), 0);
			ll_item.addView(iv_item);

			TextView tv_item = new TextView(context);
			tv_item.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			tv_item.setSingleLine(true);
			tv_item.setTextColor(Color.parseColor("#303030"));
			tv_item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

			ll_item.addView(tv_item);
			ll_item.setPadding(item.resId == 0 ? (int) (18 * dm.density)
					: (int) (16 * dm.density), (int) (10 * dm.density), 0,
					(int) (10 * dm.density));

			iv_item.setImageResource(item.resId);
			tv_item.setText(item.operName);
			iv_item.setVisibility(item.resId == 0 ? View.GONE : View.VISIBLE);

			return ll_item;
		}
	}

	@Override
	public void onBackPressed() {
		final NormalDialog dialog = new NormalDialog(context);
		dialog.content("亲,真的要走吗?再看会儿吧~(●—●)")
				//
				.style(NormalDialog.STYLE_TWO)
				//
				.titleTextSize(23)
				//
				.btnText("继续逛逛", "残忍退出")
				//
				.btnTextColor(Color.parseColor("#383838"),
						Color.parseColor("#D4D4D4"))//
				.btnTextSize(16f, 16f)//
				.showAnim(bas_in)//
				.dismissAnim(bas_out)//
				.show();

		dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {

			@Override
			public void onBtnLeftClick() {
				dialog.dismiss();
			}
		});

		dialog.setOnBtnRightClickL(new OnBtnRightClickL() {

			@Override
			public void onBtnRightClick() {
				dialog.superDismiss();
				finish();
			}
		});

	}
}
