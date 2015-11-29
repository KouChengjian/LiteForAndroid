package com.liteutil.android.http.impl.apache;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import com.liteutil.android.http.LiteHttp;


@SuppressWarnings("deprecation")
public class ApacheHttpClient extends LiteHttp{

	private static String TAG = ApacheHttpClient.class.getSimpleName();
    private DefaultHttpClient mHttpClient;
    private HttpContext mHttpContext;
    public static final int DEFAULT_KEEP_LIVE = 30000;
    public static final int DEFAULT_MAX_CONN_PER_ROUT = 128;
    public static final int DEFAULT_MAX_CONN_TOTAL = 256;
    public static final boolean TCP_NO_DELAY = true;
    private HttpRetryHandler retryHandler;
	
}
