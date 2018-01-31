package com.gigi.httpclient.api;

import java.util.Map;

/**
 * Created by coco on 24-Jan-18.
 */
public interface Request {

    String getUrl();

    String getSessionId();

    Map<String, String> getHeaders();

    String getRequestMethod();

    Request openSession();

    void setHeaders(Map<String, String> headers);
}
