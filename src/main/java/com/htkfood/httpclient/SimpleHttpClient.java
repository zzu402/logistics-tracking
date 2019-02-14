package com.htkfood.httpclient;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class SimpleHttpClient {
    
    private static HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };
    
    
    private static X509TrustManager defaultTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    public static byte[] get(String url, Map<String, String> requestHeaders, Map<String, List<String>> responseHeaders) throws RuntimeException {
        return request(url, "GET", requestHeaders, responseHeaders, null);
    }

    public static void syncGet(String url, Map<String, String> requestHeaders, ResponseHandler responseHandler){
        request(url, "GET", requestHeaders, null, 30000, 30000, responseHandler);
    }

    public static void asyncGet(String url, Map<String, String> requestHeaders, ResponseHandler responseHandler) {
        asyncRequest(url, "GET", requestHeaders, null,responseHandler);
    }

    public static String get(String url, Map<String, String> requestHeaders) throws RuntimeException {
        byte[] resultBytes = request(url, "GET", requestHeaders, null, null);
        try {
            return new String(resultBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("get 失败", e);
        }
    }

    public static byte[] post(String url, Map<String, String> requestHeaders, Map<String, List<String>> responseHeaders, byte[] content) throws RuntimeException {
        return request(url, "POST", requestHeaders, responseHeaders, content);
    }

    public static void syncPost(String url, Map<String, String> requestHeaders, byte[] content, ResponseHandler responseHandler) {
        request(url, "POST", requestHeaders, content, 30000, 30000, responseHandler);
    }

    public static void asyncPost(String url, Map<String, String> requestHeaders, byte[] content, ResponseHandler responseHandler) {
        asyncRequest(url, "POST", requestHeaders, content,responseHandler);
    }

    public static String formPost(String url, Map<String, String> requestHeaders, Map<String, Object> request) throws RuntimeException {
        String data = formData(request);
        Map<String, String> headers = new LinkedHashMap<>();
        if (null != requestHeaders)
            headers.putAll(requestHeaders);
        if (!headers.containsKey("Content-Type")) {
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        }
        try {
            byte[] content = data.getBytes("UTF-8");
            headers.put("Content-Length",Integer.toString(content.length));
            byte[] resultBytes = post(url, headers, null, content);
            return new String(resultBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("form post 失败", e);
        }
    }

    public static void asyncFormPost(String url, Map<String, String> requestHeaders, Map<String, Object> request, ResponseHandler responseHandler) throws RuntimeException {
        String data = formData(request);
        Map<String, String> headers = new LinkedHashMap<>();
        if (null != requestHeaders)
            headers.putAll(requestHeaders);
        if (!headers.containsKey("Content-Type")) {
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        }
        try {
            byte[] content = data.getBytes("UTF-8");
            headers.put("Content-Length",Integer.toString(content.length));
            asyncPost(url, headers, content, responseHandler);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("form post 失败", e);
        }
    }

    public static String jsonPost(String url, Map<String, String> requestHeaders, Map<String, Object> request) throws RuntimeException {
        String data = JsonMapper.nonEmptyMapper().toJson(request);
        Map<String, String> headers = new LinkedHashMap<>();
        if (null != requestHeaders)
            headers.putAll(requestHeaders);
        if (!headers.containsKey("Content-Type")) {
            headers.put("Content-Type", "application/json; charset=utf-8");
        }
        try {
            byte[] content = data.getBytes("UTF-8");
            headers.put("Content-Length",Integer.toString(content.length));
            byte[] resultBytes = post(url, headers, null, content);
            return new String(resultBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("json post 失败", e);
        }
    }

    public static void asyncJsonPost(String url, Map<String, String> requestHeaders, Map<String, Object> request, ResponseHandler responseHandler) throws RuntimeException {
        String data = JsonMapper.nonEmptyMapper().toJson(request);
        Map<String, String> headers = new LinkedHashMap<>();
        if (null != requestHeaders)
            headers.putAll(requestHeaders);
        if (!headers.containsKey("Content-Type")) {
            headers.put("Content-Type", "application/json; charset=utf-8");
        }
        try {
            byte[] content = data.getBytes("UTF-8");
            headers.put("Content-Length",Integer.toString(content.length));
            asyncPost(url, headers, content, responseHandler);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("json post 失败", e);
        }
    }

    public static void asyncRequest(String url, String method, Map<String, String> requestHeaders, byte[] content, final ResponseHandler responseHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                request(url, method, requestHeaders, content, 30000, 30000, responseHandler);
            }
        }).start();
    }

    public static byte[] request(String url, String method, Map<String, String> requestHeaders, Map<String, List<String>> responseHeaders, byte[] content) throws RuntimeException {
        DefaultResponseHandler responseHandler = new DefaultResponseHandler();
        request(url, method, requestHeaders, content, 30000, 30000, responseHandler);
        if(null != responseHandler.getThrowable()){
            throw new RuntimeException(String.format("[%s]请求[%s]失败", method, url), responseHandler.getThrowable());
        }
        int responseCode = responseHandler.getResponseCode();
        if ((200 != responseCode && 301 != responseCode && 302 != responseCode)) {
            String response = "";
            if(null != responseHandler.getResponse()){
                try {
                    response = new String(responseHandler.getResponse(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }
            }
            throw new RuntimeException("code : " + responseCode + ", message : " + responseHandler.getResponseMessage() + ", body : " + response,null);
        }else{
            if(null != responseHeaders && null != responseHandler.getResponseHeaders())
                responseHeaders.putAll(responseHandler.getResponseHeaders());
            return responseHandler.getResponse();
        }
    }

    public static String formData(Map<String, Object> data) throws RuntimeException {
        return formData(data, "utf-8");
    }

    public static String formData(Map<String, Object> data, String charset) throws RuntimeException {
        Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();
        StringBuilder builder = new StringBuilder();
        try {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                if (builder.length() > 0)
                    builder.append("&");
                builder.append(URLEncoder.encode(key, charset));
                builder.append("=");
                if (null != value)
                    builder.append(URLEncoder.encode(value.toString(), charset));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("构造表单数据失败", e);
        }
        return builder.toString();
    }

    /**
     * @param commonData
     * @param fileMap
     * @param boundary   BOUNDARY = "----------" + System.currentTimeMillis();
     * @return
     */
    public static byte[] formData(Map<String, Object> commonData, Map<String, FileEntry> fileMap, String boundary) throws RuntimeException {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            if (null != commonData) {
                Iterator<Map.Entry<String, Object>> iterator = commonData.entrySet().iterator();
                StringBuilder builder = new StringBuilder();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    String key = next.getKey();
                    Object value = next.getValue();

                    builder.append("--").append(boundary).append("\r\n");
                    builder.append(String.format("Content-Disposition: form-data; name=\"%s\"\r\n\r\n", URLEncoder.encode(key, "UTF-8")));
                    if (null == value)
                        builder.append("");
                    else
                        builder.append(URLEncoder.encode(value.toString(), "UTF-8"));
                    builder.append("\r\n");
                }
                output.write(builder.toString().getBytes("UTF-8"));
            }
            if (null != fileMap) {
                Iterator<Map.Entry<String, FileEntry>> iterator = fileMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, FileEntry> entry = iterator.next();
                    String key = entry.getKey();
                    FileEntry fileEntry = entry.getValue();

                    String filename = fileEntry.getFileName();
                    String contentType = new MimetypesFileTypeMap().getContentType(filename);
                    if (filename.endsWith(".png")) {
                        contentType = "image/png";
                    } else if (filename.endsWith(".jpg")) {
                        contentType = "image/jpeg";
                    } else if (filename.endsWith(".mp4")) {
                        contentType = "video/mpeg4";
                    }
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuilder builder = new StringBuilder();
                    builder.append("--").append(boundary).append("\r\n");
                    byte[] content = fileEntry.getContent();
                    builder.append(String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"; filelength=\"%s\"\r\n", key, filename, content.length));
                    builder.append(String.format("Content-Type: %s\r\n\r\n", contentType));

                    output.write(builder.toString().getBytes("UTF-8"));
                    output.write(content);
                    output.write("\r\n".getBytes("UTF-8"));
                }
            }
            output.write((String.format("--%s--\r\n", boundary)).getBytes("utf-8"));
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("构造表单数据失败", e);
        }
    }

    public static void request(String url, String method, Map<String, String> requestHeaders, byte[] content, int connectionTimeout, int readTimeout, ResponseHandler responseHandler) {
        HttpURLConnection connection = null;
        try {
            URL tUrl = new URL(url);
            if ("https".equalsIgnoreCase(tUrl.toURI().getScheme())) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = new TrustManager[]{defaultTrustManager};
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, tm, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                HttpsURLConnection httpsConnection = (HttpsURLConnection) tUrl.openConnection();
                httpsConnection.setSSLSocketFactory(sslSocketFactory);
                connection = httpsConnection;
            } else {
                connection = (HttpURLConnection) tUrl.openConnection();
            }
            request(connection, method, requestHeaders, content, connectionTimeout, readTimeout, responseHandler);
        } catch (Exception e) {
            if(null != responseHandler){
                responseHandler.onFailure(null,-1, null,null, e);
            }
        } finally {
            if (null != connection)
                connection.disconnect();
        }
    }

    private static void request(HttpURLConnection connection, String method, Map<String, String> requestHeaders, byte[] content, int connectionTimeout, int readTimeout, ResponseHandler responseHandler) {
        byte[] result = null;
        int responseCode = -1;
        String responseMessage = null;
        Map<String, List<String>> responseHeaders = null;
        try {
            //连接主机的超时时间（单位：毫秒）
            System.setProperty("sun.net.client.defaultConnectTimeout", Integer.toString(connectionTimeout));
            //从主机读取数据的超时时间（单位：毫秒）
            System.setProperty("sun.net.client.defaultReadTimeout", Integer.toString(readTimeout));
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);

            boolean output = false;
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                output = true;
            }
            connection.setInstanceFollowRedirects(false);//不自动跳转[如果没有设置，则302会自动跳转到location]

            //设置是否向httpUrlConnection输出，如果是post请求，参数要放在 http正文内，因此需要设为true, 默认情况下是false;
            connection.setDoOutput(output);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            connection.setDoInput(true);
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            if(null != requestHeaders){
                Iterator<Map.Entry<String, String>> iterator = requestHeaders.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    connection.setRequestProperty(key, value);
                }
            }
            connection.setRequestMethod(method);
            //连接
            connection.connect();
            // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
            // 所以在开发中不调用上述的connect()也可以)。
            if (output && null != content && content.length > 0) {
                OutputStream outputStream = null;
                try {
                    outputStream = connection.getOutputStream();
                    outputStream.write(content);
                    outputStream.flush();
                } finally {
                    try {
                        if (null != outputStream)
                            outputStream.close();
                    } catch (Exception e) {
                    }
                }
            }

            responseCode = connection.getResponseCode();
            responseMessage = connection.getResponseMessage();
            responseHeaders = connection.getHeaderFields();
            String encoding = connection.getContentEncoding();
            InputStream inputStream = connection.getErrorStream();
            if (null == inputStream)
                inputStream = connection.getInputStream();
            if (null != encoding && "gzip".equalsIgnoreCase(encoding) && null != inputStream)
                inputStream = new GZIPInputStream(inputStream);
            if (null != inputStream)
                result = IOUtil.toByteArray(inputStream);
            if(null != responseHandler){
                responseHandler.onSuccess(result,responseCode, responseMessage, responseHeaders);
            }
        } catch (Exception e) {
            if(null != responseHandler){
                responseHandler.onFailure(result,responseCode, responseMessage, responseHeaders, e);
            }
        }
    }
}
