package com.taogen.commons.httpclient.mockwebserver;

import com.taogen.commons.collection.MultiValueMapUtils;
import com.taogen.commons.httpclient.enums.HttpMethod;
import com.taogen.commons.httpclient.vo.HttpRequest;
import com.taogen.commons.httpclient.vo.HttpRequestWithForm;
import com.taogen.commons.httpclient.vo.HttpRequestWithJson;
import com.taogen.commons.httpclient.vo.HttpRequestWithMultipart;
import com.taogen.commons.network.HttpRequestUtil;
import com.taogen.commons.network.vo.FormItem;
import lombok.extern.log4j.Log4j2;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taogen.commons.collection.MultiValueMapUtils.multiValueMapEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Taogen
 */
@Log4j2
public class MockWebServerUtils {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * @param mockWebServer
     * @param responseBody
     * @param contentType   For example, "application/json"
     */
    public static void enqueueMockedResponse(MockWebServer mockWebServer, byte[] responseBody, String contentType) {
        Buffer buffer = new Buffer();
        buffer.write(responseBody);
        // note: Content-Length has been automatically set in MockResponse
        MockResponse mockedResponse = new MockResponse()
                .setBody(buffer)
                .addHeader(CONTENT_TYPE, contentType);
        mockWebServer.enqueue(mockedResponse);
    }

    /**
     * @param mockWebServer
     * @param requestUri
     * @return http://127.0.0.1:{randomNumber}/
     */
    public static String getMockedUrlByUri(MockWebServer mockWebServer, String requestUri) {
        return mockWebServer.url(requestUri).toString();
    }

    /**
     * validate request
     * <p>
     * - url
     * - method
     * - headers
     * - query string params
     * - body
     *
     * @param mockedRealRequest
     * @param okHttpRequest
     */
    public static void validateRequestWithQueryString(RecordedRequest mockedRealRequest, HttpRequest okHttpRequest) {
        log.debug("mocked real request: {}", mockedRealRequest);
        String actualUrl = mockedRealRequest.getRequestUrl().toString();
        if (actualUrl.indexOf("?") >= 0) {
            actualUrl = actualUrl.substring(0, actualUrl.indexOf("?"));
        }
        // validate URL
        log.debug("to validate URL");
        assertEquals(okHttpRequest.getUrl(), actualUrl);
        // validate method
        log.debug("to validate method");
        assertEquals(okHttpRequest.getMethod().name(), mockedRealRequest.getMethod());
        // validate headers
        log.debug("to validate headers");
        log.debug("okHttpRequest header: {}", okHttpRequest.getHeaders());
        log.debug("mockedRealRequest header: {}", mockedRealRequest.getHeaders().toMultimap());
        assertTrue(MultiValueMapUtils.multiStringValueMapContains(mockedRealRequest.getHeaders().toMultimap(),
                MultiValueMapUtils.multiObjectValueMapToMultiStringValueMap(okHttpRequest.getHeaders())));
        // validate query string params
        log.debug("to validate query params");
        Map<String, List<Object>> actualQueryParams = getQueryParamMapByRecordedRequest(mockedRealRequest);
        log.debug("okHttpRequest query param: {}", okHttpRequest.getQueryParams());
        log.debug("mockedRealRequest query param: {}", actualQueryParams);
        assertTrue(multiValueMapEquals(okHttpRequest.getQueryParams(), actualQueryParams));
        // validate body
    }

    public static Map<String, List<Object>> getQueryParamMapByRecordedRequest(RecordedRequest recordedRequest) {
        HttpUrl requestUrl = recordedRequest.getRequestUrl();
        return requestUrl.queryParameterNames().stream()
                .collect(HashMap::new, (map, key) -> map.put(key, new ArrayList<>(requestUrl.queryParameterValues(key))), Map::putAll);
    }

    public static void validatePostWithJson(RecordedRequest mockedRealRequest,
                                            HttpRequestWithJson okHttpRequestWithJson) {
        validateRequestWithQueryString(mockedRealRequest, okHttpRequestWithJson);
        log.debug("to validate method");
        assertEquals(HttpMethod.POST.name(), mockedRealRequest.getMethod());
        log.debug("to validate content type");
        String contentType = mockedRealRequest.getHeader(CONTENT_TYPE);
        assertTrue(contentType.contains("application/json"));
        // validate body
        log.debug("to validate body");
        log.debug("okHttpRequestWithJson body: {}", okHttpRequestWithJson.getJson());
        String actualBody = mockedRealRequest.getBody().readUtf8();
        log.debug("mockedRealRequest body: {}", actualBody);
        assertEquals(okHttpRequestWithJson.getJson(), actualBody);
    }

    public static void validatePostWithUrlEncodedForm(RecordedRequest mockedRealRequest, HttpRequestWithForm okHttpRequestWithFormData) {
        validateRequestWithQueryString(mockedRealRequest, okHttpRequestWithFormData);
        log.debug("to validate method");
        assertEquals(HttpMethod.POST.name(), mockedRealRequest.getMethod());
        log.debug("to validate content type");
        String contentType = mockedRealRequest.getHeader(CONTENT_TYPE);
        log.debug("content type: {}", contentType);
        assertTrue(contentType.contains("application/x-www-form-urlencoded"));
        // validate body
        log.debug("to validate body");
        log.debug("okHttpRequestWithFormData formData: {}", okHttpRequestWithFormData.getFormData());
        String actualFormData = mockedRealRequest.getBody().readUtf8();
        log.debug("mockedRealRequest formData: {}", actualFormData);
        Map<String, List<Object>> mockedFormDataMap = HttpRequestUtil.queryStringToMultiValueMap(actualFormData);
        log.debug("mockedRealRequest formDataMap: {}", mockedFormDataMap);
        Map<String, List<Object>> requestFormDataMap = okHttpRequestWithFormData.getFormData();
        log.debug("okHttpRequestWithFormData formDataMap: {}", requestFormDataMap);
        assertTrue(MultiValueMapUtils.multiValueMapEquals(requestFormDataMap, mockedFormDataMap));
    }

    public static void validatePostWithMultipartForm(RecordedRequest mockedRealRequest,
                                                     HttpRequestWithMultipart okHttpRequestWithFormData) throws IOException {
        validateRequestWithQueryString(mockedRealRequest, okHttpRequestWithFormData);
        log.debug("to validate method");
        assertEquals(HttpMethod.POST.name(), mockedRealRequest.getMethod());
        log.debug("to validate content type");
        String contentType = mockedRealRequest.getHeader(CONTENT_TYPE);
        assertTrue(contentType.contains("multipart/form-data"));
        // validate body
        log.debug("to validate body");
        log.debug("okHttpRequestWithFormData formData: {}", okHttpRequestWithFormData.getFormData());
        byte[] mockedRequestBodyBytes = mockedRealRequest.getBody().readByteArray();
        String boundary = HttpRequestUtil.getBoundaryByContentType(contentType);
        byte[] httpRequestBody = HttpRequestUtil.multiValueMapToMultipartData(okHttpRequestWithFormData.getFormData(), boundary);
        List<FormItem> mockedFormItems = HttpRequestUtil.convertBytesToFormItems(mockedRequestBodyBytes, boundary);
        List<FormItem> httpRequestFormItems = HttpRequestUtil.convertBytesToFormItems(httpRequestBody, boundary);
        assertTrue(HttpRequestUtil.formItemsEqual(mockedFormItems, httpRequestFormItems));
    }

}
