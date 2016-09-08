package com.easy.http.body;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


import android.text.TextUtils;

public class StringBody implements BaseRequestBody {
	private byte[] content;
	private String contentType;
	private String charset = "UTF-8";

	public StringBody(String str, String charset)
			throws UnsupportedEncodingException {
		if (!TextUtils.isEmpty(charset)) {
			this.charset = charset;
		}
		this.content = str.getBytes(this.charset);
	}

	@Override
	public long getContentLength() {
		return content.length;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getContentType() {
		return TextUtils.isEmpty(contentType) ? "application/json;charset="
				+ charset : contentType;
	}

	@Override
	public void writeTo(OutputStream out) throws IOException {
		out.write(content);
		out.flush();
	}
}
