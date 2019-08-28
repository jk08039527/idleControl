package com.jerry.baselib.common.okhttp.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import com.jerry.baselib.common.okhttp.request.PostRequest;
import com.jerry.baselib.common.okhttp.request.RequestCall;

public class PostBuilder extends BaseOkHttpRequestBuilder {

    private List<FileInput> files = new ArrayList<>();
    private String body;

    @Override
    public RequestCall build() {
        if (TextUtils.isEmpty(body)) {
            return new PostRequest(url, tag, params, headers, files).build();
        }
        return new PostRequest(url, body).build();
    }

    public PostBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    public static class FileInput {

        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }
    }

    public PostBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PostBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public PostBuilder body(String body) {
        this.body = body;
        return this;
    }
}
