package com.taogen.commons.httpclient;

import com.taogen.commons.httpclient.enums.HttpMethod;
import com.taogen.commons.httpclient.mockwebserver.MockWebServerTest;
import com.taogen.commons.httpclient.vo.*;
import com.taogen.commons.io.FileUtils;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class ApacheHttpClientUtilTest extends BaseTest {
    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void requestWithoutBody() throws InterruptedException {
        Function<String, HttpRequest> getHttpRequest = url -> {
            Map<String, List<Object>> queryParams = new HashMap<>();
            queryParams.put("id", Arrays.asList(1));
            queryParams.put("name", Arrays.asList("test"));
            queryParams.put("testChinese", Arrays.asList(CHINESE_TEST));
            HttpRequest httpRequest = HttpRequest.builder()
                    .url(url)
                    .method(HttpMethod.GET)
                    .headers(getBasicHeaders())
                    .queryParams(queryParams)
                    .build();
            return httpRequest;
        };
        Function<HttpRequest, HttpResponse> getResponse = httpRequest -> {
            try {
                HttpResponse httpResponse = ApacheHttpClientUtil.requestWithoutBody(httpRequest);
                return httpResponse;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testRequestWithoutBody(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithJson() throws InterruptedException {
        Function<String, HttpRequestWithJson> getHttpRequest = url -> {
            String requestBody = "{\"id\": 1}";
            return HttpRequestWithJson.builder()
                    .url(url)
                    .method(HttpMethod.POST)
                    .headers(getBasicHeaders())
                    .json(requestBody)
                    .build();
        };

        Function<HttpRequestWithJson, HttpResponse> getResponse = httpRequest -> {
            try {
                return ApacheHttpClientUtil.requestWithJson(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithJson(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithForm() throws InterruptedException {
        Function<String, HttpRequestWithForm> getHttpRequest = url -> {
            Map<String, List<Object>> formData = new HashMap<>();
            formData.put("id", Arrays.asList(1));
            formData.put("name", Arrays.asList("test", "test2"));
            return HttpRequestWithForm.builder()
                    .url(url)
                    .method(HttpMethod.POST)
                    .headers(getBasicHeaders())
                    .formData(formData)
                    .build();
        };

        Function<HttpRequestWithForm, HttpResponse> getResponse = httpRequest -> {
            try {
                return ApacheHttpClientUtil.requestWithForm(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithUrlEncodedForm(mockWebServer, getHttpRequest, getResponse);

    }

    @Test
    void requestWithMultipart() throws IOException, InterruptedException {
        Function<String, HttpRequestWithMultipart> getHttpRequest = url -> {
            Map<String, List<Object>> formData = new HashMap<>();
            formData.put("id", Arrays.asList(1));
            formData.put("name", Arrays.asList("test", "test2"));
            try {
                formData.put("file", Arrays.asList(
                        new File(FileUtils.getFilePathByFileClassPath("test/test.jpg")),
                        new File(FileUtils.getFilePathByFileClassPath("test/test.txt"))));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return HttpRequestWithMultipart.builder()
                    .url(url)
                    .method(HttpMethod.POST)
                    .headers(getBasicHeaders())
                    .formData(formData)
                    .build();
        };

        Function<HttpRequestWithMultipart, HttpResponse> getResponse = httpRequest -> {
            try {
                return ApacheHttpClientUtil.requestWithMultipart(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithMultipartForm(mockWebServer, getHttpRequest, getResponse);
    }
}
