package com.taogen.commons.httpclient;

import com.taogen.commons.httpclient.enums.HttpMethod;
import com.taogen.commons.httpclient.vo.HttpRequest;
import com.taogen.commons.httpclient.vo.HttpResponse;
import com.taogen.commons.httpclient.vo.*;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Taogen
 */
public class ApacheHttpClientUtil {

    public static HttpResponse requestWithoutBody(HttpRequest request) throws IOException, URISyntaxException, ParseException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(request.getUrl());
            addQueryStringParams(builder, request.getQueryParams());
            ClassicHttpRequest httpRequest = getHttpRequest(request.getMethod(), builder.build());
            addHeaders(httpRequest, request.getHeaders());
            try (CloseableHttpResponse httpResponse = httpclient.execute(httpRequest)) {
                HttpResponse apacheResponse = getApacheResponse(httpResponse);
                EntityUtils.consume(httpResponse.getEntity());
                return apacheResponse;
            }
        }
    }

    public static HttpResponse requestWithJson(HttpRequestWithJson httpRequest) throws IOException, URISyntaxException, ParseException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(httpRequest.getUrl());
            addQueryStringParams(builder, httpRequest.getQueryParams());
            ClassicHttpRequest classicHttpRequest = getHttpRequest(
                    httpRequest.getMethod(), builder.build());
            addHeaders(classicHttpRequest, httpRequest.getHeaders());
            classicHttpRequest.setEntity(new StringEntity(httpRequest.getJson(), ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse httpResponse = httpclient.execute(classicHttpRequest)) {
                HttpResponse apacheResponse = getApacheResponse(httpResponse);
                EntityUtils.consume(httpResponse.getEntity());
                return apacheResponse;
            }
        }
    }

    public static HttpResponse requestWithForm(HttpRequestWithForm httpRequest) throws IOException, URISyntaxException, ParseException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(httpRequest.getUrl());
            addQueryStringParams(builder, httpRequest.getQueryParams());
            ClassicHttpRequest classicHttpRequest = getHttpRequest(httpRequest.getMethod(), builder.build());
            addHeaders(classicHttpRequest, httpRequest.getHeaders());
            classicHttpRequest.setEntity(new UrlEncodedFormEntity(convertMultiValueMapToNameValuePairList(httpRequest.getFormData())));
            try (CloseableHttpResponse httpResponse = httpclient.execute(classicHttpRequest)) {
                HttpResponse apacheResponse = getApacheResponse(httpResponse);
                EntityUtils.consume(httpResponse.getEntity());
                return apacheResponse;
            }
        }
    }

    public static HttpResponse requestWithMultipart(HttpRequestWithMultipart httpRequest) throws IOException, URISyntaxException, ParseException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder builder = new URIBuilder(httpRequest.getUrl());
            addQueryStringParams(builder, httpRequest.getQueryParams());
            ClassicHttpRequest classicHttpRequest = getHttpRequest(httpRequest.getMethod(), builder.build());
            addHeaders(classicHttpRequest, httpRequest.getHeaders());
            classicHttpRequest.setEntity(getMultipartEntity(httpRequest.getFormData()));
            try (CloseableHttpResponse httpResponse = httpclient.execute(classicHttpRequest)) {
                HttpResponse apacheResponse = getApacheResponse(httpResponse);
                EntityUtils.consume(httpResponse.getEntity());
                return apacheResponse;
            }
        }
    }

    private static ClassicHttpRequest getHttpRequest(HttpMethod method, URI build) {
        if (method == HttpMethod.GET) {
            return new HttpGet(build);
        } else if (method == HttpMethod.POST) {
            return new HttpPost(build);
        } else if (method == HttpMethod.PUT) {
            return new HttpPut(build);
        } else if (method == HttpMethod.DELETE) {
            return new HttpDelete(build);
        }
        return null;
    }

    private static HttpEntity getMultipartEntity(Map<String, List<Object>> formData) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (formData != null) {
            for (Map.Entry<String, List<Object>> entry : formData.entrySet()) {
                List<Object> values = entry.getValue();
                if (values != null) {
                    for (Object value : values) {
                        if (value instanceof File) {
                            File file = (File) value;
                            multipartEntityBuilder.addPart(entry.getKey(), new FileBody(file));
                        } else {
                            multipartEntityBuilder.addTextBody(entry.getKey(), value.toString());
                        }
                    }
                }
            }
        }
        return multipartEntityBuilder.build();
    }

    private static List<NameValuePair> convertMultiValueMapToNameValuePairList(Map<String, List<Object>> formData) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (formData != null) {
            for (Map.Entry<String, List<Object>> entry : formData.entrySet()) {
                if (entry.getValue() != null) {
                    for (Object value : entry.getValue()) {
                        nameValuePairs.add(new BasicNameValuePair(entry.getKey(), Objects.toString(value)));
                    }
                }
            }
        }
        return nameValuePairs;
    }

    private static HttpResponse getApacheResponse(CloseableHttpResponse closeableHttpResponse) throws IOException, ParseException, ParseException {
        HttpEntity entity = closeableHttpResponse.getEntity();
        HttpResponse response = new HttpResponse();
        response.setStatusCode(closeableHttpResponse.getCode());
        response.setHeaders(convertApacheHeadersToMap(closeableHttpResponse.getHeaders()));
        response.setBody(EntityUtils.toByteArray(entity));
        return response;
    }

    private static Map<String, List<Object>> convertApacheHeadersToMap(Header[] headers) {
        Map<String, List<Object>> map = new LinkedHashMap<>();
        if (headers != null) {
            for (Header header : headers) {
                if (map.containsKey(header.getName())) {
                    map.get(header.getName()).add(header.getValue());
                } else {
                    List<Object> values = new ArrayList<>();
                    values.add(header.getValue());
                    map.put(header.getName(), values);
                }
            }
        }
        return null;
    }

    private static void addHeaders(ClassicHttpRequest classicHttpRequest,
                                   Map<String, List<Object>> headers) {
        if (headers != null) {
            for (String key : headers.keySet()) {
                headers.get(key).forEach(value ->
                        classicHttpRequest.addHeader(key, value));
            }
        }
    }

    private static void addQueryStringParams(URIBuilder builder, Map<String, List<Object>> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                params.get(key).forEach(value ->
                        builder.addParameter(key, Objects.toString(value)));
            }
        }
    }
}
