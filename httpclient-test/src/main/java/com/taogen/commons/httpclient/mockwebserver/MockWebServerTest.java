package com.taogen.commons.httpclient.mockwebserver;

import com.taogen.commons.httpclient.vo.*;
import lombok.extern.log4j.Log4j2;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;


/**
 * @author Taogen
 */
@Log4j2
public class MockWebServerTest {
    public static final String RESPONSE_BODY_1 = "{\"id\": 1, \"name\": \"test\"}";

    public static final Integer SUCCESS_CODE = 200;

    public static void testRequestWithoutBody(MockWebServer mockWebServer,
                                              Function<String, HttpRequest> getHttpRequest,
                                              Function<HttpRequest, HttpResponse> getResponse) throws InterruptedException {
        MockWebServerUtils.enqueueMockedResponse(mockWebServer, RESPONSE_BODY_1.getBytes(StandardCharsets.UTF_8), "application/json");
        String url = MockWebServerUtils.getMockedUrlByUri(mockWebServer, "/testRequestWithoutBody_get");
        log.info("url: {}", url);
        HttpRequest httpRequest = getHttpRequest.apply(url);
        HttpResponse httpResponse = getResponse.apply(httpRequest);
        log.info("httpResponse: {}", httpResponse);
        // validate response
        assertEquals(SUCCESS_CODE, httpResponse.getStatusCode());
        assertEquals(RESPONSE_BODY_1, new String(httpResponse.getBody(), StandardCharsets.UTF_8));
        // validate request
        RecordedRequest request = mockWebServer.takeRequest();
        MockWebServerUtils.validateRequestWithQueryString(request, httpRequest);
    }

    public static void testPostWithJson(MockWebServer mockWebServer,
                                        Function<String, HttpRequestWithJson> getHttpRequest,
                                        Function<HttpRequestWithJson, HttpResponse> getResponse) throws InterruptedException {
        MockWebServerUtils.enqueueMockedResponse(mockWebServer, RESPONSE_BODY_1.getBytes(StandardCharsets.UTF_8), "application/json");
        String url = MockWebServerUtils.getMockedUrlByUri(mockWebServer, "/testRequestWithJson_post");
        log.info("url: {}", url);
        HttpRequestWithJson httpRequest = getHttpRequest.apply(url);
        HttpResponse httpResponse = getResponse.apply(httpRequest);
        log.info("httpResponse: {}", httpResponse);
        // validate response
        assertEquals(SUCCESS_CODE, httpResponse.getStatusCode());
        assertEquals(RESPONSE_BODY_1, new String(httpResponse.getBody(), StandardCharsets.UTF_8));
        // validate request
        RecordedRequest request = mockWebServer.takeRequest();
        MockWebServerUtils.validatePostWithJson(request, httpRequest);
    }

    public static void testPostWithUrlEncodedForm(MockWebServer mockWebServer,
                                                  Function<String, HttpRequestWithForm> getHttpRequest,
                                                  Function<HttpRequestWithForm, HttpResponse> getResponse) throws InterruptedException {
        MockWebServerUtils.enqueueMockedResponse(mockWebServer, RESPONSE_BODY_1.getBytes(StandardCharsets.UTF_8), "application/json");
        String url = MockWebServerUtils.getMockedUrlByUri(mockWebServer, "/testRequestWithUrlEncodedForm_post");
        log.info("url: {}", url);
        HttpRequestWithForm httpRequest = getHttpRequest.apply(url);
        HttpResponse httpResponse = getResponse.apply(httpRequest);
        log.info("httpResponse: {}", httpResponse);
        // validate response
        assertEquals(SUCCESS_CODE, httpResponse.getStatusCode());
        assertEquals(RESPONSE_BODY_1, new String(httpResponse.getBody(), StandardCharsets.UTF_8));
        // validate request
        RecordedRequest request = mockWebServer.takeRequest();
        MockWebServerUtils.validatePostWithUrlEncodedForm(request, httpRequest);
    }

    public static void testPostWithMultipartForm(MockWebServer mockWebServer,
                                                 Function<String, HttpRequestWithMultipart> getHttpRequest,
                                                 Function<HttpRequestWithMultipart, HttpResponse> getResponse) throws InterruptedException, IOException {
        MockWebServerUtils.enqueueMockedResponse(mockWebServer, RESPONSE_BODY_1.getBytes(StandardCharsets.UTF_8), "application/json");
        String url = MockWebServerUtils.getMockedUrlByUri(mockWebServer, "/testRequestWithMultipart_post");
        log.info("url: {}", url);
        HttpRequestWithMultipart httpRequest = getHttpRequest.apply(url);
        HttpResponse httpResponse = getResponse.apply(httpRequest);
        log.info("httpResponse: {}", httpResponse);
        // validate response
        assertEquals(SUCCESS_CODE, httpResponse.getStatusCode());
        assertEquals(RESPONSE_BODY_1, new String(httpResponse.getBody(), StandardCharsets.UTF_8));
        // validate request
        RecordedRequest request = mockWebServer.takeRequest();
        MockWebServerUtils.validatePostWithMultipartForm(request, httpRequest);
    }
}
