package com.liteutil.android.http.impl.apache;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import com.liteutil.android.http.data.Consts;
import com.liteutil.android.http.exception.HttpClientException;
import com.liteutil.android.http.request.AbstractRequest;
import com.liteutil.android.http.request.content.ByteArrayBody;
import com.liteutil.android.http.request.content.FileBody;
import com.liteutil.android.http.request.content.HttpBody;
import com.liteutil.android.http.request.content.InputStreamBody;
import com.liteutil.android.http.request.content.MultipartBody;
import com.liteutil.android.http.request.content.StringBody;

/**
 * help us to build {@link org.apache.http.HttpEntity}
 *
 * @author MaTianyu
 *         2014-1-18上午1:41:41
 */
public class EntityBuilder {

    public static HttpEntity build(AbstractRequest req) throws HttpClientException {
        try {
            HttpBody body = req.getHttpBody();
            if (body != null) {
                req.addHeader(Consts.CONTENT_TYPE, body.getContentType());
                if (body instanceof StringBody) {
                    // StringBody JsonBody UrlEncodedFormBody
                    StringBody b = (StringBody) body;
                    return new StringEntity(b.string, b.charset);
                } else if (body instanceof ByteArrayBody) {
                    // ByteArrayBody SerializableBody
                    ByteArrayBody b = (ByteArrayBody) body;
                    return new ByteArrayEntity(b.bytes);
                } else if (body instanceof InputStreamBody) {
                    InputStreamBody b = (InputStreamBody) body;
                    return new InputStreamEntity(b.inputStream, b.inputStream.available(), req);
                } else if (body instanceof FileBody) {
                    FileBody b = (FileBody) body;
                    return new FileEntity(b.file, b.contentType, req);
                } else if (body instanceof MultipartBody) {
                    return new MultipartEntity((MultipartBody) body);
                } else {
                    throw new RuntimeException("Unpredictable Entity Body(非法实体)");
                }
            }
        } catch (Exception e) {
            throw new HttpClientException(e);
        }
        return null;
    }
}
