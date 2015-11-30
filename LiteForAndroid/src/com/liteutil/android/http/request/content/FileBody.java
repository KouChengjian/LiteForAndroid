package com.liteutil.android.http.request.content;


import java.io.File;

import com.liteutil.android.http.data.Consts;

/**
 * @author MaTianyu
 * @date 14-7-29
 */
public class FileBody extends HttpBody {
    public File file;

    public FileBody(File file) {
        this(file, Consts.MIME_TYPE_OCTET_STREAM);
    }

    public FileBody(File file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }
}
