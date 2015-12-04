package com.liteutil.example.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.liteutil.LiteHttp;
import com.liteutil.exception.HttpException;
import com.liteutil.http.listener.Callback;
import com.liteutil.http.request.RequestParams;

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
        Callback.Cancelable cancelable = LiteHttp.http().get(params,
        		/**
                 * 1. callback的泛型:
                 * callback参数默认支持的泛型类型参见{@link org.xutils.http.loader.LoaderFactory},
                 * 例如: 指定泛型为File则可实现文件下载, 使用params.setSaveFilePath(path)指定文件保存的全路径.
                 * 默认支持断点续传(采用了文件锁和尾端校验续传文件的一致性).
                 * 其他常用类型可以自己在LoaderFactory中注册,
                 * 也可以使用{@link org.xutils.http.annotation.HttpResponse}
                 * 将注解HttpResponse加到自定义返回值类型上, 实现自定义ResponseParser接口来统一转换.
                 * 如果返回值是json形式, 那么利用第三方的json工具将十分容易定义自己的ResponseParser.
                 * 如示例代码{@link org.xutils.sample.http.BaiduResponse}, 可直接使用BaiduResponse作为
                 * callback的泛型.
                 *
                 * 2. callback的组合:
                 * 可以用基类或接口组合个种类的Callback, 见{@link org.xutils.common.Callback}.
                 * 例如:
                 * a. 组合使用CacheCallback将使请求检测缓存或将结果存入缓存(仅GET请求生效).
                 * b. 组合使用PrepareCallback的prepare方法将为callback提供一次后台执行耗时任务的机会,
                 * 然后将结果给onCache或onSuccess.
                 * c. 组合使用ProgressCallback将提供进度回调.
                 * ...(可参考{@link org.xutils.image.ImageLoader}
                 * 或 示例代码中的 {@link org.xutils.sample.download.DownloadCallback})
                 *
                 * 3. 请求过程拦截或记录日志: 参考 {@link org.xutils.http.app.RequestTracker}
                 *
                 * 4. 请求Header获取: 参考 {@link org.xutils.http.app.InterceptRequestListener}
                 *
                 * 5. 其他(线程池, 超时, 重定向, 重试, 代理等): 参考 {@link org.xutils.http.RequestParams}
                 *
                 **/
        		new Callback.CommonCallback<String>(){

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
//        cancelable.cancel();
	}
	
	
}
