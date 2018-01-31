package com.gigi.httpclient.model;

import java.util.List;
import java.util.Map;

/**
 * Created by coco on 24-Jan-18.
 */
public class Session {

    private String sessionId;
    private Map<String, List<String>> lastResponseHeaders;

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setLastResponseHeaders(Map<String, List<String>> headerFields) {
        this.lastResponseHeaders = headerFields;
    }

    public Map<String, List<String>> getLastResponseHeaders() {
        return lastResponseHeaders;
    }
}
