package com.htkfood.httpclient;

import java.util.List;
import java.util.Map;

public interface ResponseHandler {
    void onSuccess(byte[] response, int responseCode, String responseMessage, Map<String, List<String>> responseHeaders);
    void onFailure(byte[] response, int responseCode, String responseMessage, Map<String, List<String>> responseHeaders, Throwable throwable);
}
