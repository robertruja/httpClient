package com.gigi.httpclient.impl;


import com.gigi.httpclient.api.Client;
import com.gigi.httpclient.api.Request;
import com.gigi.httpclient.api.Response;
import com.gigi.httpclient.model.Session;
import com.gigi.httpclient.processor.ResponseProcessor;
import org.crumbs.core.logging.Logger;

import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientImpl implements Client {

    private Map<String, Session> openSessions = new HashMap<>();
    private Logger LOG = Logger.getLogger(this.getClass());
    private ResponseProcessor processor = new ResponseProcessor();

    @Override
    public Response sendRequest(Request request) {
        Response response = null;
        try {

            String url = processUrl(request.getUrl());
            if(request.getSessionId() != null) {
                openSessions.put(request.getSessionId(), new Session(request.getSessionId()));
            }

            LOG.info("Sending request to " + url);

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            populateRequest(request, connection);

            int responseCode = connection.getResponseCode();
            response = processor.processResponse(connection.getInputStream(), connection.getHeaderFields());
            response.setResponseCode(responseCode);
            // set session info
            if(isOpenSession(request)) {
                Session session = openSessions.get(request.getSessionId());
                session.setLastResponseHeaders(connection.getHeaderFields());
                response.setSessionId(request.getSessionId());
            }

        } catch (Exception ex) {
            LOG.error("Exception occured while trying to request url: " + request.getUrl(), ex);
        }
        return response;
    }

    private String processUrl(String reqUrl) {
        if(!reqUrl.startsWith("http://") && !reqUrl.startsWith("https://")) {
                reqUrl = "http://" + reqUrl;
        }
        return reqUrl;
    }

    @Override
    public void closeSession(String sessionId) {
        openSessions.remove(sessionId);
    }

    @Override
    public Request createRequest(String... args) {
        Request request;
        String url = args[0];
        if(url != null) {
            String method;
            if (args.length > 1 && (method = args[1]) != null && (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("POST"))) {
                request = new RequestImpl(url, method);
            } else {
                request = new RequestImpl(url);
            }
        } else {
            throw new RuntimeException("Cannot create request with null url");
        }
        return request;
    }

    private void populateRequest(Request request, HttpURLConnection connection) throws Exception {

        if(isOpenSession(request)) {
            Map<String, List<String>> headers = openSessions.get(request.getSessionId()).getLastResponseHeaders();
            if(headers != null) {
                List<String> cookies = headers.get("set-cookie");
                String cookie = "";
                for(String cook: cookies) {
                    cookie = cookie + cook + ";";
                }
                connection.setRequestProperty("cookie",cookie);
            } {
                LOG.warn("No headers found for session id: " + request.getSessionId());
            }
        }
        connection.setRequestMethod(request.getRequestMethod());
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        Map<String, String> headers = request.getHeaders();
        if(headers != null) {
            headers.forEach((k,v) -> {
                connection.setRequestProperty(k,v);
            });
        }
    }

    private boolean isOpenSession(Request request) {
        return request.getSessionId() != null && openSessions.containsKey(request.getSessionId());
    }
}
