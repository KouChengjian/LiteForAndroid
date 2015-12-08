package com.liteutil.example.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.liteutil.http.*;
import com.liteutil.example.DownloadService;
import com.liteutil.example.http.BaiduParams;
import com.liteutil.example.http.download.DownloadActivity;
import com.liteutil.exception.DbException;
import com.liteutil.exception.HttpException;
import com.liteutil.http.listener.Callback;
import com.liteutil.http.listener.Callback.CommonCallback;
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
		data.add("1. 请求");
		data.add("2. 上传");
		data.add("3. 下载界面");
		data.add("4. 下载");
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
		case 1:
			try {
				onTest2Click();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			onTest4Click();
			break;
		case 3:
			onTest3Click();
			break;
		case 5:
			try {
				onTest5Click();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
//        cancelable.cancel(); // 取消s
        // 如果需要记录请求的日志, 可使用RequestTracker接口(优先级依次降低, 找到一个实现后会忽略后面的):
        // 1. 自定义Callback同时实现RequestTracker接口;
        // 2. 自定义ResponseParser同时实现RequestTracker接口;
        // 3. 在LoaderFactory注册.
	}
	
	/**
	 * 上传多文件示例
	 * @throws FileNotFoundException
	 */
	
	private void onTest2Click() throws FileNotFoundException {
		RequestParams params = new RequestParams("http://192.168.0.13:8080/upload");
        // 加到url里的参数, http://xxxx/s?wd=xUtils
        params.addQueryStringParameter("wd", "xUtils");
        // 添加到请求body体的参数, 只有POST, PUT, PATCH, DELETE请求支持.
        // params.addBodyParameter("wd", "xUtils");
        // 使用multipart表单上传文件
        params.setMultipart(true);
        params.addBodyParameter(
                "file",
                new File("/sdcard/test.jpg"),
                null); // 如果文件没有扩展名, 最好设置contentType参数.
        params.addBodyParameter(
                "file2",
                new FileInputStream(new File("/sdcard/test2.jpg")),
                "image/jpeg",
                // 测试中文文件名
                "你+& \" 好.jpg"); // InputStream参数获取不到文件名, 最好设置, 除非服务端不关心这个参数.
        LiteHttp.http().post(params, new CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onCancelled(CancelledException cex) {
				Toast.makeText(activity, "cancelled", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFinished() {
				
			}
		});
	}
	
	/**
	 * 打开下载列表
	 * @throws FileNotFoundException
	 */
	private void onTest4Click(){
        startActivity(new Intent(activity, DownloadActivity.class));
    }
	
	// 添加到下载列表
    private void onTest3Click()   {
        for (int i = 0; i < 5; i++) {
            String url = "http://file.bmob.cn/M02/D8/C4/oYYBAFZi4RGAY665AABEZ2jphKw911.png";
            String label = i + "xUtils_" + System.nanoTime();
            try {
				DownloadService.getDownloadManager().startDownload(url, label,"/sdcard/xUtils/" + label + ".png", true, false, null);
			} catch (DbException e) {
				e.printStackTrace();
			}
        }
    }
    
    /**
     * 缓存示例, 更复杂的例子参考 {@link org.xutils.image.ImageLoader}
     */
    private void onTest5Click() throws FileNotFoundException {
    	BaiduParams params = new BaiduParams();
        params.wd = "xUtils";
        // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
        params.setCacheMaxAge(1000 * 60);
        Callback.Cancelable cancelable
        // 使用CacheCallback, xUtils将为该请求缓存数据.
        = LiteHttp.http().get(params, new Callback.CacheCallback<String>() {

        	private boolean hasError = false;
            private String result = null;
        	
			@Override
			public void onSuccess(String result) {
				// 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
                // 但是 onFinished 总会被调用.
                this.result = result;
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				hasError = true;
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
                if (!hasError && result != null) {
                    // 成功获取数据
                    Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                }
            }

			@Override
			public boolean onCache(String result) {
				// 得到缓存数据, 缓存过期后不会进入这个方法.
                // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
                //
                // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
                //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
                //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
                //
                // * 如果信任该缓存返回 true, 将不再请求网络;
                //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
                //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
                //
                this.result = result;
                return false; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
			}
        	
        });
    }
}
