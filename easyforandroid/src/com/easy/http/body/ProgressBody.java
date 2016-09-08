package com.easy.http.body;

import com.easy.http.ProgressHandler;


public interface ProgressBody extends BaseRequestBody {
    void setProgressHandler(ProgressHandler progressHandler);
}

