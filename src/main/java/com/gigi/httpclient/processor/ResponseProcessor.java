package com.gigi.httpclient.processor;

import com.gigi.httpclient.api.Response;
import com.gigi.httpclient.impl.ResponseImpl;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by coco on 24-Jan-18.
 */
public class ResponseProcessor {

    private static final String ENCODING = "Content-Encoding";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String GZIP = "gzip";
    private static final String CHARSET = "UTF-8";


    public Response processResponse(InputStream responseInputStream, Map<String, List<String>> responseHeaders) throws Exception {

        Response response = new ResponseImpl();

        String payload;

        if(responseHeaders.containsKey(ENCODING) && responseHeaders.get(ENCODING).size() >0 && responseHeaders.get(ENCODING).get(0).equalsIgnoreCase(GZIP)) {

            String charset = CHARSET;

        //todo: deal with different charset
        //            if(responseHeaders.get(CONTENT_TYPE) != null ) {
        //
        //            }

            payload = decompressGzipResponse(responseInputStream, charset);
        } else {
            payload = buildPlainTextPayload(responseInputStream);
        }

        response.setHeaders(responseHeaders);
        response.setPayLoad(payload);

        return response;
    }

    private String decompressGzipResponse(InputStream responseInputStream, String charset) throws IOException {

        GZIPInputStream gis = new GZIPInputStream(responseInputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, charset));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        gis.close();
        return sb.toString();
    }

    private String buildPlainTextPayload(InputStream responseInputStream) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(responseInputStream));
        String inputLine;
        StringBuilder resp = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            resp.append(inputLine);
        }
        in.close();

        return resp.toString();
    }
}
