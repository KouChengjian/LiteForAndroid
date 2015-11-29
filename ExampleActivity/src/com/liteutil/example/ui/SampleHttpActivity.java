package com.liteutil.example.ui;

import java.util.ArrayList;
import java.util.List;

import com.liteutil.android.http.LiteHttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @ClassName: SampleHttpActivity
 * @Description: http请求
 * @author: KCJ
 * @date: 2015-11-28
 */
public class SampleHttpActivity extends Activity implements OnItemClickListener{

	private ListView listView;
	private LiteHttp liteHttp;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);
		listView.setOnItemClickListener(this);
		
//		liteHttp = LiteHttp.newApacheHttpClient(null);
	}
	
	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("0. 快速配置");
		data.add("1. 异步请求");
		data.add("2. 同步请求");
		data.add("3. 简单同步请求");
		data.add("4. 抛出异常请求");
		data.add("9. 文件上传");
		data.add("10. 文件/图片下载");
		data.add("11. 禁止一些网络访问");
		data.add("20. 多缓存机制");
		data.add("21. 回调机制");
		return data;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

}
