package com.liteutil.example.ui;

import java.util.ArrayList;
import java.util.List;


import java.util.concurrent.FutureTask;

import com.liteutil.android.http.response.Response;
import com.liteutil.android.http.HttpConfig;
import com.liteutil.android.http.LiteHttp;
import com.liteutil.android.http.exception.HttpException;
import com.liteutil.android.http.listener.HttpListener;
import com.liteutil.android.http.request.StringRequest;
import com.liteutil.android.http.request.param.HttpMethods;
import com.liteutil.android.util.HttpUtil;

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
	
	private boolean needRestore;
	
	public static final String url = "http://baidu.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);
		listView.setOnItemClickListener(this);
		
		activity = this;
		liteHttp = LiteHttp.newApacheHttpClient(null);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		clickTestItem(pos);
	}
	
	private void initLiteHttp() {
		if (liteHttp == null) {
            HttpConfig config = new HttpConfig(activity) // configuration quickly
                    .setDebugged(true)                   // log output when debugged
                    .setDetectNetwork(true)              // detect network before connect
                    .setDoStatistics(true)               // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                    .setTimeOut(10000, 10000);             // connect and socket timeout: 10s
            liteHttp = LiteHttp.newApacheHttpClient(config);
        } else {
            liteHttp.getConfig()                        // configuration directly
                    .setDebugged(true)                  // log output when debugged
                    .setDetectNetwork(true)             // detect network before connect
                    .setDoStatistics(true)              // statistics of time and traffic
                    .setUserAgent("Mozilla/5.0 (...)")  // set custom User-Agent
                    .setTimeOut(10000, 10000);            // connect and socket timeout: 10s
        }
    }

	private void clickTestItem(final int which) {
		if (needRestore) {
		    liteHttp.getConfig().restoreToDefault();
		    needRestore = false;
		}
		switch (which) {
		case 0:
			initLiteHttp();
			HttpUtil.showTips(activity, "LiteHttp2.0", "Init Config Success!");
			break;
		case 1:
			// 1. Asynchronous Request 同步请求
			// 1.1 init request 初始化请求
			final StringRequest request = new StringRequest(url).setHttpListener(new HttpListener<String>() {
				@Override
				public void onSuccess(String s,Response<String> response) {
					HttpUtil.showTips(activity, "String", s);
					response.printInfo();
				}

				@Override
				public void onFailure(HttpException e,Response<String> response) {
					HttpUtil.showTips(activity, "HttpException",e.toString());
				}
			});
			// 1.2 execute async, nothing returned.
			liteHttp.executeAsync(request);
			// 1.3 perform async, future task returned.
//			FutureTask<String> task = liteHttp.performAsync(request);// 可以取消
//			task.cancel(false);
			break;

		}
	}
	
	
	
	
}
