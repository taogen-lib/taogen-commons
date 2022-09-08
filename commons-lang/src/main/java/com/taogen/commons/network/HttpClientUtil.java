package com.taogen.commons.network;

import com.taogen.commons.collection.MultiValueMapUtils;
import com.taogen.commons.network.vo.*;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @author Taogen
 */
public class HttpClientUtil {
    public static String MULTIPART_BODY_BOUNDARY = new BigInteger(256, new Random()).toString();

    public static HttpResponse requestWithoutBody(HttpRequest request) throws IOException {
        URL urlObj = getURL(request.getUrl(), request.getQueryParams());
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        urlConnection.setRequestMethod(request.getMethod().name());
        addHeaders(urlConnection, request.getHeaders());
        try {
            return getResponse(urlConnection);
        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL getURL(String url, Map<String, List<Object>> queryParams) throws MalformedURLException {
        StringBuilder realUrl = new StringBuilder()
                .append(url);
        if (MultiValueMapUtils.isNotEmpty(queryParams)) {
            realUrl.append("?")
                    .append(HttpRequestUtil.multiValueMapToQueryString(queryParams))
                    .toString();
        }
        return new URL(realUrl.toString());
    }

    private static void addHeaders(HttpURLConnection urlConnection, Map<String, List<Object>> headers) {
        if (headers != null) {
            for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
                String key = entry.getKey();
                List<Object> values = entry.getValue();
                if (values != null) {
                    for (Object value : values) {
                        urlConnection.addRequestProperty(key, Objects.toString(value));
                    }
                }
            }
        }
    }

    private static HttpResponse getResponse(HttpURLConnection urlConnection) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        httpResponse.setBodyString(convertStreamToString(in));
        httpResponse.setBody(httpResponse.getBodyString().getBytes(StandardCharsets.UTF_8));
        httpResponse.setStatusCode(urlConnection.getResponseCode());
        httpResponse.setHeaders(MultiValueMapUtils.multiStringValueMapToMultiObjectValueMap(urlConnection.getHeaderFields()));
        return httpResponse;
    }

    private static String convertStreamToString(InputStream in) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        }
    }


    public static HttpResponse requestWithJson(HttpRequestWithJson request) throws IOException {
        URL urlObj = getURL(request.getUrl(), request.getQueryParams());
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        urlConnection.setRequestMethod(request.getMethod().name());
        addHeaders(urlConnection, request.getHeaders());
        urlConnection.setRequestProperty("Content-Type", "application/json");
        // Connection Will Be Used to Send Content
        urlConnection.setDoOutput(true);
        byte[] postData = request.getJson().getBytes(StandardCharsets.UTF_8);
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length));
        OutputStream out = urlConnection.getOutputStream();
        out.write(postData);
        try {
            return getResponse(urlConnection);
        } finally {
            urlConnection.disconnect();
        }
    }

    public static HttpResponse requestWithUrlEncodedForm(HttpRequestWithForm request) throws IOException {
        URL urlObj = getURL(request.getUrl(), request.getQueryParams());
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        urlConnection.setRequestMethod(request.getMethod().name());
        addHeaders(urlConnection, request.getHeaders());
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setDoOutput(true);
        byte[] postData = HttpRequestUtil.multiValueMapToQueryString(request.getFormData())
                .getBytes(StandardCharsets.UTF_8);
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length));
        OutputStream out = urlConnection.getOutputStream();
        out.write(postData);
        try {
            return getResponse(urlConnection);
        } finally {
            urlConnection.disconnect();
        }
    }

    public static HttpResponse requestWithFormData(HttpRequestWithMultipart request) throws IOException {
        URL urlObj = getURL(request.getUrl(), request.getQueryParams());
        HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
        urlConnection.setRequestMethod(request.getMethod().name());
        addHeaders(urlConnection, request.getHeaders());
        urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + MULTIPART_BODY_BOUNDARY);
        urlConnection.setDoOutput(true);
        byte[] postData = HttpRequestUtil.multiValueMapToMultipartData(request.getFormData(), MULTIPART_BODY_BOUNDARY);
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length));
        OutputStream out = urlConnection.getOutputStream();
        out.write(postData);
        try {
            return getResponse(urlConnection);
        } finally {
            urlConnection.disconnect();
        }
    }

}