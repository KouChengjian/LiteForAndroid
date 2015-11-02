package com.kcj.peninkframe.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kcj.peninkframe.R;
import com.kcj.peninkframe.http.CompositeBasedModel.ApiResult;
import com.kcj.peninkframe.http.CompositeBasedModel.UserModel;
import com.kcj.peninkframe.http.ExtendBasedModel.User;
import com.kcj.peninkframe.http.RequestParams.BaiDuSearch;
import com.kcj.peninkframe.utils.Log;
import com.litesuits.http.LiteHttpClient;
import com.litesuits.http.async.HttpAsyncExecutor;
import com.litesuits.http.data.HttpStatus;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpClientException;
import com.litesuits.http.exception.HttpClientException.ClientException;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpNetException.NetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.exception.HttpServerException.ServerException;
import com.litesuits.http.parser.BinaryParser;
import com.litesuits.http.parser.BitmapParser;
import com.litesuits.http.parser.DataParser;
import com.litesuits.http.parser.FileParser;
import com.litesuits.http.request.Request;
import com.litesuits.http.request.content.ByteArrayBody;
import com.litesuits.http.request.content.FileBody;
import com.litesuits.http.request.content.InputStreamBody;
import com.litesuits.http.request.content.JsonBody;
import com.litesuits.http.request.content.MultipartBody;
import com.litesuits.http.request.content.SerializableBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.content.multi.BytesPart;
import com.litesuits.http.request.content.multi.FilePart;
import com.litesuits.http.request.content.multi.InputStreamPart;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.HttpMethod;
import com.litesuits.http.response.Response;
import com.litesuits.http.response.handler.HttpExceptionHandler;
import com.litesuits.http.response.handler.HttpModelHandler;
import com.litesuits.http.response.handler.HttpResponseHandler;
import com.litesuits.http.utils.AsyncExecutor;

/**
 * @ClassName: SampleHttpActivity
 * @Description: Http
 * @author: KCJ
 * @date:
 */
public class SampleHttpActivity extends BaseSwipeBackActivity implements
		OnItemClickListener {

	private ListView listView;
	private LiteHttpClient client;
	private HttpAsyncExecutor asyncExcutor;
	
	private String url = "http://baidu.com";
	String imageUrl = "http://pic.yesky.com/imagelist/07/37/5146451_2754_1000x500.jpg";
	
	private String urlUser = "http://litesuits.com/mockdata/user";
    private String urlUserList = "http://litesuits.com/mockdata/user_list";
    private String localPath = "/HttpServer/PostReceiver";
    private String localHost = "http://10.0.1.32:8080";
    //private String localPath       = "/LiteHttpServer/ReceiveFile";
    //private String localHost       = "http://192.168.1.100:8080";
    private String urlLocalRequest = localHost + localPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);
		listView.setOnItemClickListener(this);

		client = LiteHttpClient.newApacheHttpClient(mContext);
		asyncExcutor = HttpAsyncExecutor.newInstance(client);
	}

	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("1. 基础get请求");
		data.add("2. 带参数Get请求");
		data.add("3. 基础head请求");
		data.add("4. 基础Post请求");
		data.add("5. 处理异常方案");
		data.add("6. get简化模式");
		data.add("7. post简化模式");
		data.add("8. Https 安全协议");
		data.add("9. 智能Json Model转化");
		data.add("10. Java Model方式添加请求参数");
		data.add("11. 上传文件");
		data.add("12. 获取字节");
		data.add("13. 下载图片");
		data.add("14. 下载文件");
		data.add("15. 自定义数据解析");
		data.add("16. 自动重定向");
		data.add("17. 自带异步执行器:获得响应");
		data.add("18. 自带异步执行器:获得对象");
		data.add("19. Thread方式开启异步");
		data.add("20. AsyncTask方式开启异步");
		data.add("21. HTTP加载取消");
		data.add("22. Post发送Model Entity");
		data.add("23. Post 参数测试");
		return data;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		 new Thread(getRunnable(pos)).start();
	}
	
	public Runnable getRunnable(final int pos){
		Runnable Runnable = new Runnable() {
			@Override
			public void run() {
				final int id = pos + 1;
				switch (id) {
				case 1: // 基础get请求
					makeBaseGetRequest();
					break;
				case 2: // 带参数Get请求
					makeParamteredGetRequest();
					break;
				case 3: // 基础head请求
					makeBaseHeadRequest();
					break;
				case 4: // 基础Post请求
					makePostRequest();
					break;
				case 5: // 处理异常方案
					makeRequestWithExceptionHandler();
					break;
				case 6: // get简化模式
					makeSimpleGetRequest();
					break;
				case 7: // post简化模式
					makeSimplePostRequest();
					break;
				case 8: // Https 安全协议
					makeHttpsRequest();
					break;
				case 9: // 智能Json Model转化
					makeIntelligentJsonModelMapingRequest();
					break;
				case 10: // Java Model方式添加请求参数
					makeJavaModeAsParamsRequest();
					break;
				case 11: // 上传文件
					makeUpLoadMultiBodyRequest();
					break;
				case 12: // 获取字节
					makeLoadBytesRequest();
					break;
				case 13: // 下载图片
					makeLoadBitmapRequest();
					break;
				case 14: // 下载图片
					makeLoadFileRequest();
					break;
				case 15: // 自定义数据解析
					makeCustomParserRequest();
					break;
				case 16: // 自动重定向
					makeAutoRedirectRequest();
					break;
				case 17: // 自带异步执行器:获得响应
					innerAsyncGetResponse();
					break;
				case 18: // 自带异步执行器:获得对象
					innerAsyncGetModel();
					break;
				case 19: // Thread方式开启异步
					useThreadExecute();
					break;
				case 20: // AsyncTask方式开启异步
					useAsyncExecute();
					break;
				case 21: // HTTP加载取消
					cancelHttpLoading();
					break;
				case 22: // Post发送Model Entity
					innerAsyncPostModel();
					break;
				case 23: // Post 参数测试
					innerAsyncPostParameters();
					break;
				}
			}
		};
		return Runnable;
	}

	private void makeBaseGetRequest() {

		String url = "http://huaban.com/boards/24782545/?idb3ivvm&limit=6&wfl=1";

		LiteHttpClient client = LiteHttpClient.newApacheHttpClient(mContext,
				"Mozilla/5.0");
		Response res = client.execute(new Request(url));
		String html = res.getString();
//		Log.e("makeBaseGetRequest", html);
		
		Document doc = Jsoup.parse(html);

//		Elements divs = doc.select("div.pin,.wfc,.wft"); // pin wfc wft
//		Elements divs = doc.select("script"); // script
//		Elements divs = doc.getElementsByTag("script");
//		Log.e("divs", divs.size() + "=======");
//		for (Element div : divs) {
//			Log.e("eles", div.toString()+"=======");
//
//		}
	}
	

	 
	

	private void makeParamteredGetRequest() {
		String url = "www.baidu.com";
		Request req = new Request(url).setMethod(HttpMethod.Get)
				.addUrlPrifix("http://").addUrlSuffix("/s")
				.addHeader("User-Agent", "Taobao Browser 1.0")
				.addUrlParam("wd", "你好 Lite").addUrlParam("bs", "大家好")
				.addUrlParam("inputT", "0");
		Response res = client.execute(req);
		printLog(res);
	}
	
	private void makeBaseHeadRequest() {
        Response res = client.execute(new Request(url).setMethod(HttpMethod.Head));
        printLog(res);
    }
	
	private void makePostRequest() {
        Request req = new Request("https://passport.csdn.net/account/login").setMethod(HttpMethod.Post);
        Response res = client.execute(req);
        printLog(res);
    }
	
	private void makeRequestWithExceptionHandler() {
        url = "http://h5.m.taobao.com/we/pc.htm";
        //			InputStream is = new

        String json = "{\"a\":1}";

        Request req = new Request(url).setMethod(HttpMethod.Trace);
        req.addHeader("Content-Type", "application/json");
        req.addUrlParam("", json);

        asyncExcutor.execute(req, new HttpResponseHandler() {

            @Override
            public void onSuccess(Response response, HttpStatus status, NameValuePair[] headers) {
                ShowToast("成功");
            }

            @Override
            public void onFailure(Response response, HttpException e) {

                new HttpExceptionHandler() {
                    @Override
                    protected void onClientException(HttpClientException e, ClientException type) {
                    	ShowToast("开发者可更新界面提示用户，原因：客户端有异常");
                    }

                    @Override
                    protected void onNetException(HttpNetException e, NetException type) {
                        if (type == NetException.NetworkError) {
                        	ShowToast("开发者可更新界面提示用户，原因：无可用网络");
                        } else if (type == NetException.UnReachable) {
                        	ShowToast("开发者可更新界面提示用户，原因：服务器不可访问(或网络不稳定)");
                        } else if (type == NetException.NetworkDisabled) {
                        	ShowToast("原因：该网络类型已被开发者设置禁用");
                        }
                    }

                    @Override
                    protected void onServerException(HttpServerException e, ServerException type, HttpStatus status) {
                    	ShowToast("开发者可更新界面提示用户，原因：服务暂时不可用");
                    }

                }.handleException(e);

                printLog(response);
            }
        });
    }
	
	/**
     * 第一步 调用get方法
     * 第二步 没了
     * 是的 没有第二步
     */
    private void makeSimpleGetRequest() {
        LiteHttpClient client = LiteHttpClient.newApacheHttpClient(mContext, "Mozilla/5.0");
        String s = client.get(url);
        Log.i(TAG, s);
    }
	
    private void makeSimplePostRequest() {
        byte[] bytes = client.post("https://passport.csdn.net/account/login", new BinaryParser());
        if (bytes != null) {
            Log.d(TAG, new String(bytes));
        }
    }
	
    private void makeHttpsRequest() {
        String s = client.get("https://www.alipay.com");
        Log.d(TAG, s);
    }
    
    /**
     * 通过String存储data对象，再转化为User模型
     */
    private void makeIntelligentJsonModelMapingRequest() {
        //		User user = client.get("", null, User.class);
        asyncExcutor.execute(new AsyncExecutor.Worker<Response>() {

            @Override
            public Response doInBackground() {
                return client.execute(new Request(urlUser));
            }

            @Override
            public void onPostExecute(Response res) {
                //以组合的方式组织model并解析
                ApiResult api = res.getObject(ApiResult.class);
                if (api != null) {
                    UserModel user1 = api.getData(UserModel.class);
                    Log.i(TAG, "user1: " + user1);
                    ShowToast("user1: " + user1);
                }
                //以继承的方式组织model并解析
                User user2 = res.getObject(User.class);
                Log.i(TAG, "user2: " + user2);
                ShowToast("user2: " + user2);
                // user1 和 user2 是一样的对象，只是实现、组织起来的的方式不同。
                printLog(res);
            }
        });

    }
	
    private void makeJavaModeAsParamsRequest() {
        BaiDuSearch model = new BaiDuSearch();
        Request req = new Request(url).addUrlSuffix("/s").setParamModel(model);
        Response res = client.execute(req);
        printLog(res);

        //		Man man = new Man("jame",18);
        //		Response resonse = client.execute(new Request("http://a.com",man));
        //build as http://a.com?name=jame&age=18
    }
	
    /**
     * 多文件上传
     */
    private Response makeUpLoadMultiBodyRequest() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("sdcard/alog.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //View v;v.setBackground();
        String url = urlLocalRequest;
        Request req = new Request(url);

        MultipartBody body = new MultipartBody();
        body.addPart(new StringPart("key1", "hello"));
        body.addPart(new StringPart("key2", "很高兴见到你", "utf-8", null));
        body.addPart(new BytesPart("key3", new byte[]{1, 2, 3}));
        body.addPart(new FilePart("pic", new File("sdcard/apic.png"), "image/jpeg"));
        body.addPart(new FilePart("song", new File("sdcard/asong.mp3"), "audio/x-mpeg"));
        body.addPart(new InputStreamPart("alog", fis, "alog.xml", "text/xml"));
        req.setMethod(HttpMethod.Post).setHttpBody(body);
        Response res = client.execute(req);
        res.printInfo();
        //System.out.println("response string : " + res.getString());
        return res;
    }
	
    private void makeLoadBytesRequest() {
        byte[] bytes = client.execute(url, new BinaryParser(), HttpMethod.Get);
        if (bytes != null) {
            Log.d(TAG, "bytes length is : " + bytes.length);
        }
    }
	
    private void makeLoadBitmapRequest() {
        final Response res = client.execute(new Request("http://img.hb.aicdn.com/7ef856d3ed723a39b54baceb5469e3f95580c03022ec8f-rbcIf8_fw236").setDataParser(new BitmapParser()));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new HttpResponseHandler() {
                    @Override
                    public void onSuccess(Response response, HttpStatus status, NameValuePair[] headers) {
                        addImageViewToBottom(response.getBitmap());
                    }

                    @Override
                    public void onFailure(Response response, HttpException exception) {
                    	ShowToast("加载图片失败");
                    }
                }.handleResponse(res);
            }
        });
    }
	
    private void makeLoadFileRequest() {
        File file = client.execute(imageUrl, new FileParser("sdcard/lite.jpg"), HttpMethod.Get);
    }
	
    private void makeCustomParserRequest() {
        String customString = client.execute(url, new DataParser<String>() {
            @Override
            protected String parseData(InputStream stream, long totalLength, String charSet) throws IOException {
                Reader reader = new InputStreamReader(stream, charSet);
                CharArrayBuffer buffer = new CharArrayBuffer((int) totalLength);
                try {
                    char[] tmp = new char[buffSize];
                    int l;
                    //判断线程有没有被结束，以及时停止读数据，节省流量。
                    while (!Thread.currentThread().isInterrupted() && (l = reader.read(tmp)) != -1) {
                        buffer.append(tmp, 0, l);
                        //统计数据，不加此方法则数据统计不完整。
                        readLength += l;
                    }
                } finally {
                    reader.close();
                }
                return "自定义啊： 加前缀 " + buffer.toString() + "自定义哈，加后缀。";
            }
        }, HttpMethod.Get);
        Log.d(TAG, customString);
    }
	
    private void makeAutoRedirectRequest() {
        String url = "http://www.baidu.com/link?url=Lqc3GptP8u05JCRDsk0jqsAvIZh9WdtO_RkXYMYRQEm";
        String s = client.get(url);
        Log.d(TAG, s);
    }
	
    private void innerAsyncGetResponse() {

        HttpAsyncExecutor asyncExcutor = HttpAsyncExecutor.newInstance(client);

        asyncExcutor.execute(new Request(url), new HttpResponseHandler() {
            @Override
            protected void onSuccess(Response res, HttpStatus status, NameValuePair[] headers) {
                printLog(res);
                ShowToast(res.getString());
            }

            @Override
            protected void onFailure(Response res, HttpException e) {
            	ShowToast("e: " + e);
            }
        });
    }
	
    private void innerAsyncGetModel() {
        asyncExcutor.execute(new Request(urlUser), new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
            	ShowToast("User String: " + data);
                printLog(res);
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
            	ShowToast("e: " + e);
            }

        });

        asyncExcutor.execute(new Request(urlUser), new HttpModelHandler<User>() {
            @Override
            protected void onSuccess(User data, Response res) {
                Log.i(TAG, "User: " + data);
                ShowToast("User: " + data);
                printLog(res);
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
            	ShowToast("e: " + e);
            }

        });

        asyncExcutor.execute(new Request(urlUserList), new HttpModelHandler<ArrayList<User>>() {
            @Override
            protected void onSuccess(ArrayList<User> data, Response res) {
                Log.i(TAG, "User List: " + data);
                ShowToast("User List: " + data);
                printLog(res);
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
            	ShowToast("e: " + e);
            }

        });

    }
	
    private void useThreadExecute() {
        new Thread() {
            @Override
            public void run() {
                final Response res = client.execute(new Request(url));
                // must post to  UI thread by yourself
                runOnUiThread(new Runnable() {
                    public void run() {

                        new HttpResponseHandler() {
                            @Override
                            protected void onSuccess(Response res, HttpStatus status, NameValuePair[] headers) {
                                printLog(res);
                                ShowToast("Thread Execute : " + res.getString());
                            }

                            @Override
                            protected void onFailure(Response res, HttpException e) {
                            	ShowToast("e: " + e);
                            }
                        }.handleResponse(res);

                    }
                });

            }
        }.start();
    }
    
    private void useAsyncExecute() {
        AsyncTask<Void, Void, Response> task = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... params) {
                return client.execute(new Request(url));
            }

            @Override
            protected void onPostExecute(Response res) {
                printLog(res);
                ShowToast("AsyncTask Execute : " + res.getString());
            }
        };
        task.execute();
    }
    
    private void cancelHttpLoading() {
        FutureTask<Response> future = asyncExcutor.execute(new Request(urlUser), new HttpResponseHandler() {
            @Override
            protected void onSuccess(Response res, HttpStatus status, NameValuePair[] headers) {
                printLog(res);
                ShowToast(res.getString());
            }

            @Override
            protected void onFailure(Response res, HttpException e) {
            	ShowToast("e: " + e);
            }
        });
        SystemClock.sleep(200);
        future.cancel(true);
    }
    
    private void innerAsyncPostModel() {
        Request req = new Request(urlUser);
        req.setMethod(HttpMethod.Post);
        req.setHttpBody(new JsonBody(new BaiDuSearch()));
        asyncExcutor.execute(req, new HttpModelHandler<String>() {
            @Override
            protected void onSuccess(String data, Response res) {
            	ShowToast("User String: " + data);
                printLog(res);
            }

            @Override
            protected void onFailure(HttpException e, Response res) {
            	ShowToast("e: " + e);
            }

        });
    }
    
    private void innerAsyncPostParameters() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("POST BODY TEST");
        String[] array = getResources().getStringArray(R.array.http_test_post);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String url = urlLocalRequest;

                Request req = new Request(url);
                req.setMethod(HttpMethod.Post);

                switch (which) {
                    case 0:
                        testHttpPost();
                        //req.setHttpBody(new StringBody("hello"));
                        break;
                    case 1:
                        LinkedList<NameValuePair> pList = new LinkedList<NameValuePair>();
                        pList.add(new NameValuePair("key1", "value1"));
                        pList.add(new NameValuePair("key2", "value2"));
                        req.setHttpBody(new UrlEncodedFormBody(pList));
                        break;
                    case 2:
                        req.setHttpBody(new JsonBody(new BaiDuSearch()));
                        break;
                    case 3:

                        ArrayList<String> list = new ArrayList<String>();
                        list.add("a");
                        list.add("b");
                        list.add("c");
                        req.setHttpBody(new SerializableBody(list));
                        break;
                    case 4:
                        req.setHttpBody(new ByteArrayBody(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                                18, 127
                        }));
                        break;
                    case 5:
                        req.setHttpBody(new FileBody(new File("sdcard/alog.xml")));
                        break;
                    case 6:
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(new File("sdcard/alog.xml"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        req.setHttpBody(new InputStreamBody(fis));
                        break;
                    case 7:
                        asyncExcutor.execute(new AsyncExecutor.Worker<String>() {
                            @Override
                            protected String doInBackground() {
                                Response res = makeUpLoadMultiBodyRequest();
                                //printLog(res);
                                return "yes";
                            }

                            @Override
                            protected void onPostExecute(String data) {
                            	ShowToast("onSuccess : " + data);
                            }
                        });
                        break;
                }
                if (which != 0) {
                    asyncExcutor.execute(req, new HttpModelHandler<String>() {
                        @Override
                        protected void onSuccess(String data, Response res) {
                        	ShowToast("onSuccess : " + data);
                            printLog(res);
                        }

                        @Override
                        protected void onFailure(HttpException e, Response res) {
                        	ShowToast("onFailure: " + e);
                        }

                    });
                }
            }
        });
        //builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void addImageViewToBottom(final Bitmap bitmap) {
    	String dateTime = new Date(System.currentTimeMillis()).getTime() + "";// 获取当前时间

		File file1 = new File("/sdcard/KCJ/");
		if (!file1.exists()) {
			file1.mkdirs();
        }
		String files = "/sdcard/KCJ/"+ dateTime + ".jpg" ;
		File file = new File(files);
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 0, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ShowToast(file.getAbsolutePath());
//        if (bitmap != null) {
//            ImageView imageview = new ImageView(this);
////            imageview.setImageBitmap(bitmap);
////            mListview.addFooterView(imageview);
////            Toast.makeText(this, "Bitmap 加载完成，RowBytes：" + bitmap.getRowBytes() + ", ByteCount: " + bitmap.getByteCount(),
////                    Toast.LENGTH_LONG).show();
////            mListview.setSelection(mListview.getAdapter().getCount());
//        }
    }
    
    private void testHttpPost() {
        asyncExcutor.execute(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    //创建连接
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(urlLocalRequest);
                    //设置参数，仿html表单提交
                    List<BasicNameValuePair> temp = new ArrayList<BasicNameValuePair>();
                    temp.add(new BasicNameValuePair("key1", "value11"));
                    temp.add(new BasicNameValuePair("key2", "value222"));
                    post.setEntity(new UrlEncodedFormEntity(temp, HTTP.UTF_8));
                    //发送HttpPost请求，并返回HttpResponse对象
                    HttpResponse httpResponse = httpClient.execute(post);
                    // 判断请求响应状态码，状态码为200表示服务端成功响应了客户端的请求
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //获取返回结果
                        String result = EntityUtils.toString(httpResponse.getEntity());
                        Log.i(TAG, "Apache result: " + result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
    
	private void printLog(Response response) {
		if (response.getString() != null) {
			Log.i(TAG, "http result lengh : " + response.getString().length());
		}
		Log.v(TAG, "http result :\n " + response.getString());
		Log.d(TAG, response.toString());
	}
}
