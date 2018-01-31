package com.gigi.httpclient.api;

/**
 * Created by coco on 24-Jan-18.
 */
public interface Client {

    Response sendRequest(Request request);
    void closeSession(String sessionId);

    /**
     * @param args : args[0]: url - REQUIRED, options[1]: method - optional
     * */
    Request createRequest(String... args);
}
