package com.easyutils.http.ex;

public class HttpRedirectException extends HttpException {
    private static final long serialVersionUID = 1L;

    public HttpRedirectException(int code, String detailMessage, String result) {
        super(code, detailMessage);
        this.setResult(result);
    }
}
