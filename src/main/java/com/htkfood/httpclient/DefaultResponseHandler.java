package com.htkfood.httpclient;
import java.util.List;
import java.util.Map;

public class DefaultResponseHandler implements ResponseHandler {
    private Map<String, List<String>> responseHeaders;
    private byte[] response;
    private String responseMessage;
    private int responseCode = -1;
    private Throwable throwable;
    @Override
    public void onSuccess(byte[] response, int responseCode, String responseMessage, Map<String, List<String>> responseHeaders) {
        this.response=response;
        this.responseCode=responseCode;
        this.responseMessage=responseMessage;
        this.responseHeaders=responseHeaders;
    }

    @Override
    public void onFailure(byte[] response, int responseCode, String responseMessage, Map<String, List<String>> responseHeaders, Throwable throwable) {
        this.response=response;
        this.responseCode=responseCode;
        this.responseMessage=responseMessage;
        this.responseHeaders=responseHeaders;
        this.throwable = throwable;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public byte[] getResponse() {
        return response;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}