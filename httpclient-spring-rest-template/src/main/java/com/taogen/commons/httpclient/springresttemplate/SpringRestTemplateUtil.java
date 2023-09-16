package com.taogen.commons.httpclient.springresttemplate;

import com.taogen.commons.httpclient.vo.HttpRequest;
import com.taogen.commons.httpclient.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Taogen
 */
@Component
@RequiredArgsConstructor
public class SpringRestTemplateUtil {
    private final RestTemplate restTemplate;

    private static HttpMethod convertToSpringHttpMethod(com.taogen.commons.httpclient.enums.HttpMethod method) {
        if (com.taogen.commons.httpclient.enums.HttpMethod.GET.equals(method)) {
            return HttpMethod.GET;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.POST.equals(method)) {
            return HttpMethod.POST;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.PUT.equals(method)) {
            return HttpMethod.PUT;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.DELETE.equals(method)) {
            return HttpMethod.DELETE;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.HEAD.equals(method)) {
            return HttpMethod.HEAD;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.OPTIONS.equals(method)) {
            return HttpMethod.OPTIONS;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.PATCH.equals(method)) {
            return HttpMethod.PATCH;
        } else if (com.taogen.commons.httpclient.enums.HttpMethod.TRACE.equals(method)) {
            return HttpMethod.TRACE;
        } else {
            throw new IllegalArgumentException("Unsupported http method: " + method);
        }
    }

    private static MultiValueMap<String, String> convertMapToMultiStringValueMap(
            Map<String, List<Object>> map) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            for (Object value : entry.getValue()) {
                multiValueMap.add(entry.getKey(), Objects.toString(value));
            }
        }
        return multiValueMap;
    }

    private static MultiValueMap<String, Object> convertMapToMultiObjectValueMap(
            Map<String, List<Object>> map) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            for (Object value : entry.getValue()) {
                multiValueMap.add(entry.getKey(), Objects.toString(value));
            }
        }
        return multiValueMap;
    }

    private static HttpHeaders convertMapToHeaders(
            Map<String, List<Object>> map) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            for (Object value : entry.getValue()) {
                httpHeaders.add(entry.getKey(), Objects.toString(value));
            }
        }
        return httpHeaders;
    }

    public HttpResponse requestWithoutBody(HttpRequest request) {
        ResponseEntity<String> response = request(request.getUrl(),
                convertToSpringHttpMethod(request.getMethod()),
                convertMapToMultiStringValueMap(request.getQueryParams()),
                convertMapToHeaders(request.getHeaders()),
                null,
                String.class
        );
        return convertSpringResponseToHttpResponse(response);
    }

    public HttpResponse requestWithJson(HttpRequestWithJson request) throws IOException {
        if (request.getMethod() == com.taogen.commons.httpclient.enums.HttpMethod.GET) {
            throw new IllegalArgumentException("GET method can't use json body");
        }
        HttpHeaders httpHeaders = convertMapToHeaders(request.getHeaders());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = request(request.getUrl(),
                convertToSpringHttpMethod(request.getMethod()),
                convertMapToMultiStringValueMap(request.getQueryParams()),
                httpHeaders,
                request.getJson(),
                String.class
        );
        return convertSpringResponseToHttpResponse(response);
    }

    public HttpResponse requestWithFormUrlEncoded(HttpRequestWithForm request) throws IOException {
        if (request.getMethod() == com.taogen.commons.httpclient.enums.HttpMethod.GET) {
            throw new IllegalArgumentException("GET method can't use form data body");
        }
        HttpHeaders headers = convertMapToHeaders(request.getHeaders());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        ResponseEntity<String> response = request(request.getUrl(),
                convertToSpringHttpMethod(request.getMethod()),
                convertMapToMultiStringValueMap(request.getQueryParams()),
                headers,
                convertMapToMultiObjectValueMap(request.getFormData()),
                String.class
        );
        return convertSpringResponseToHttpResponse(response);
    }

    /**
     * @param request File is FileSystemResource object or File object
     * @return
     * @throws IOException
     */
    public HttpResponse requestWithFormData(HttpRequestWithMultipart request) throws IOException {
        if (request.getMethod() == com.taogen.commons.httpclient.enums.HttpMethod.GET) {
            throw new IllegalArgumentException("GET method can't use form data body");
        }
        HttpHeaders httpHeaders = convertMapToHeaders(request.getHeaders());
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        ResponseEntity<String> response = request(request.getUrl(),
                convertToSpringHttpMethod(request.getMethod()),
                convertMapToMultiStringValueMap(request.getQueryParams()),
                httpHeaders,
                convertMapToMultiObjectValueMap(request.getFormData()),
                String.class
        );
        return convertSpringResponseToHttpResponse(response);
    }

    private HttpResponse convertSpringResponseToHttpResponse(ResponseEntity<String> response) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatusCode(response.getStatusCode().value());
        httpResponse.setHeaders(convertSpringHeadersToMap(response.getHeaders()));
        httpResponse.setBody(response.getBody().getBytes(StandardCharsets.UTF_8));
        httpResponse.setBodyString(response.getBody());
        return httpResponse;
    }

    private Map<String, List<Object>> convertSpringHeadersToMap(HttpHeaders headers) {
        if (headers == null || headers.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, List<Object>> map = new LinkedHashMap();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (!map.containsKey(entry.getKey())) {
                map.put(entry.getKey(), new ArrayList<>());
            }
            map.get(entry.getKey()).addAll(entry.getValue());
        }
        return map;
    }

    /**
     * Get, post, put, delete
     *
     * @param url
     * @param method
     * @param params
     * @param headers
     * @param body    when request content-type is application/json, body is JSON string object.
     *                when request content-type is application/x-www-form-urlencoded, body is MultiValueMap<String, String> object.
     *                when request content-type is multipart/form-data, body is MultiValueMap<String, Object> object. The file value is FileSystemResource object.
     * @return String object or entity object
     */
    public <T> ResponseEntity<T> request(String url,
                                         HttpMethod method,
                                         MultiValueMap<String, String> params,
                                         HttpHeaders headers,
                                         Object body,
                                         Class<T> responseType) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(params);
        HttpEntity<Object> requestEntity =
                new HttpEntity<>(body, headers);
        return restTemplate.exchange(builder.build().toUriString(), method, requestEntity, responseType);
    }

    /**
     * Get object list
     *
     * @param url
     * @param params
     * @param headers
     * @param body
     * @param responseType
     * @param <T>
     * @return entity list or string list
     */
    public <T> ResponseEntity<List<T>> getObjectList(String url,
                                                     MultiValueMap<String, String> params,
                                                     MultiValueMap<String, String> headers,
                                                     Object body,
                                                     ParameterizedTypeReference<List<T>> responseType) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(params);
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, requestEntity, responseType);
    }
}
