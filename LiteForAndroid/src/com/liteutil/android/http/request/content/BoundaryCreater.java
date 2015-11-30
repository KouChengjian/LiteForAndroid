package com.liteutil.android.http.request.content;


import java.nio.charset.Charset;
import java.util.Random;

import com.liteutil.android.http.data.Consts;
import com.liteutil.android.util.StringCodingUtils;

/**
 * @author MaTianyu
 * @date 2014-08-06
 */
public class BoundaryCreater {
    public static final Charset charset         = Charset.forName(Consts.DEFAULT_CHARSET);
    private final static char[]  MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String boundary;
    private byte[] boundaryLine;
    private byte[] boundaryEnd;

    public BoundaryCreater() {
        final StringBuilder buf = new StringBuilder();
        final Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        boundary = buf.toString();
        boundaryLine =  StringCodingUtils.getBytes("--" + boundary + "\r\n", charset);
        boundaryEnd = StringCodingUtils.getBytes("--" + boundary + "--\r\n", charset);
    }

    public String getBoundary() {
        return boundary;
    }

    public byte[] getBoundaryLine() {
        return boundaryLine;
    }

    public byte[] getBoundaryEnd() {
        return boundaryEnd;
    }
}
