package com.easy.http.body;

import java.io.IOException;
import java.io.OutputStream;

public interface BaseRequestBody {

    long getContentLength();

    void setContentType(String contentType);

    String getContentType();

    void writeTo(OutputStream out) throws IOException;
}