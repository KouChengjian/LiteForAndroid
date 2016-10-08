package com.easyutils.http.body;

import java.io.IOException;
import java.io.OutputStream;

public interface BaseRequestBody {

    long getContentLength(); // 内容长度

    void setContentType(String contentType); // 设置内容类型

    String getContentType(); // 获取内容类型

    void writeTo(OutputStream out) throws IOException; // 写入
}
