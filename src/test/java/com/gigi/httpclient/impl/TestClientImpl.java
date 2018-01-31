package com.gigi.httpclient.impl;

import com.gigi.httpclient.api.Client;
import com.gigi.httpclient.api.Request;
import com.gigi.httpclient.api.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by coco on 24-Jan-18.
 */

public class TestClientImpl {

    Client client = new ClientImpl();

    @Ignore
    @Test
    public void testSendRequest() {

        Client client = new ClientImpl();
        Request request = client.createRequest("https://d.flashscore.ro/x/feed/f_1_0_2_ro_1").openSession();
        Map<String, String> headers = new HashMap<>();
        headers.put("accept","*/*");
        headers.put("accept-encoding","gzip, deflate, br");
        headers.put("accept-language","*");
        //headers.put("cookie","_ga=GA1.2.199044695.1514806275; _gid=GA1.2.1413539345.1516650223; _dc_gtm_UA-207011-12=1");
        headers.put("referer","https://d.flashscore.ro/x/feed/proxy-local");
        headers.put("user-agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        headers.put("x-fsign","SW9D1eZo");
        headers.put("x-geoip","1");
        headers.put("x-gir","https://www.flashscore.ro/");
        headers.put("x-referer","GET");
        headers.put("x-requested-with","XMLHttpRequest");

        request.setHeaders(headers);
        Response response = client.sendRequest(request);

        if(response != null) {
            System.out.println("Headers: ");
            response.getHeaders().forEach((key,value) -> {
                System.out.println("Key: " + key + " Value: " + value);
            });
            System.out.println("+++++++++++++++++++++++++++++++++Body: ");
            System.out.println(response.getPayload());
        }
    }
}
