package com.gigi.httpclient.api;

import java.util.List;
import java.util.Map;

/**
 * Created by coco on 24-Jan-18.
 */
public interface Response {
    
    void setHeaders(Map<String, List<String>> headerFields);

    Map<String, List<String>> getHeaders();

    void setResponseCode(int responseCode);

    int getResponseCode();

    void setPayLoad(String s);

    String getPayload();

    String setSessionId(String sessionId);

    String getSessionId();
}
