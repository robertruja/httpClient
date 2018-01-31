package com.gigi.httpclient.impl;

import com.gigi.httpclient.api.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by coco on 13-Jan-18.
 */
public class ResponseImpl implements Response {

    private String payload;
    private int responseCode = 500;
    private String sessionId;

    private Map<String, List<String>> headers;

    public void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }

    @Override
    public void setPayLoad(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String setSessionId(String sessionId) {
        return sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}
