package com.liteutil.example.ui;

import java.util.ArrayList;
import java.util.List;















import com.liteutil.android.LiteHttp;


import com.liteutil.android.exception.HttpException;
import com.liteutil.android.http.listener.Callback;
import com.liteutil.android.http.request.params.RequestParams;
import com.liteutil.example.bean.BaiduParams;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @ClassName: SampleHttpActivity
 * @Description: http请求
 * @author: KCJ
 * @date: 2015-11-28
 */
public class SampleHttpActivity extends Activity implements OnItemClickListener{

	private ListView listView;
	private Activity activity;
	
	
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
	}
	
	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("0. 请求");
//		data.add("0. 快速配置");
//		data.add("1. 异步请求");
//		data.add("2. 同步请求");
//		data.add("3. 简单同步请求");
//		data.add("4. 抛出异常请求");
//		data.add("9. 文件上传");
//		data.add("10. 文件/图片下载");
//		data.add("11. 禁止一些网络访问");
//		data.add("20. 多缓存机制");
//		data.add("21. 回调机制");
		return data;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		clickTestItem(pos);
	}
	
	private void clickTestItem(final int which) {
		switch (which) {
		case 0:
			onTest1Click();
			break;

		default:
			break;
		}
	}
	
	/**
     * 自定义实体参数类请参考:
     * 请求注解 {@link org.xutils.http.annotation.HttpRequest}
     * 请求注解处理模板接口 {@link org.xutils.http.app.ParamsBuilder}
     *
     * 需要自定义类型作为callback的泛型时, 参考:
     * 响应注解 {@link org.xutils.http.annotation.HttpResponse}
     * 响应注解处理模板接口 {@link org.xutils.http.app.ResponseParser}
     *
     * 示例: 查看 org.xutils.sample.http 包里的代码
     */
	public void onTest1Click(){
//		BaiduParams params = new BaiduParams();
//      params.wd = "xUtils";
		RequestParams params = new RequestParams("https://www.baidu.com");
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        // params.setMultipart(true);
        // 上传文件方式 1
        // params.uploadFile = new File("/sdcard/test.txt");
        // 上传文件方式 2
        // params.addBodyParameter("uploadFile", new File("/sdcard/test.txt"));
        Callback.Cancelable cancelable = LiteHttp.http().get(params,new Callback.CommonCallback<String>(){

			@Override
			public void onSuccess(String result) {
				Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(activity, "cancelled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
				
			}
        });
        
        cancelable.cancel();
	}
	
	
}
