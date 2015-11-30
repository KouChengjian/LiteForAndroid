package com.liteutil.android.http.request;

import com.liteutil.android.http.parser.DataParser;
import com.liteutil.android.http.parser.StringParser;
import com.liteutil.android.http.request.param.HttpParamModel;

/**
 * @author MaTianyu
 * @date 2015-04-18
 */
public class StringRequest extends AbstractRequest<String> {

    public StringRequest(String url) {
        super(url);
    }

    public StringRequest(HttpParamModel model) {
        super(model);
    }

    public StringRequest(String url, HttpParamModel model) {
        super(url, model);
    }

    @Override
    public DataParser<String> createDataParser() {
        return new StringParser();
    }

}
