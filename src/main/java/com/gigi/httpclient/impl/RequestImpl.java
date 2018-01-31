package com.gigi.httpclient.impl;

import com.gigi.httpclient.api.Request;

import java.util.Map;
import java.util.UUID;

public class RequestImpl implements Request {

    private String sessionId;
    private String url;
    private String method = "GET";
    private Map<String,String> headers;

    RequestImpl(String url) {
        this.url = url;
    }

    RequestImpl(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getRequestMethod() {
        return method;
    }

    @Override
    public Request openSession() {
        sessionId = UUID.randomUUID().toString();
        return this;
    }

    @Override
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
