package com.taogen.commons.network;

import com.taogen.commons.io.FileUtils;
import com.taogen.commons.network.enums.HttpMethod;
import com.taogen.commons.network.vo.*;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;

class HttpClientUtilTest {

    protected static MockWebServer mockWebServer;

    public static final String RANDOM_TOKEN = "123456";

    public static final String CHINESE_TEST = "中文测试";
    public static final String APP_HEADER_KEY = "my-app-id";
    public static final String APP_HEADER_VALUE = "java-http-clients";

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
                HttpResponse httpResponse = HttpClientUtil.requestWithoutBody(httpRequest);
                return httpResponse;
            } catch (IOException e) {
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
                return HttpClientUtil.requestWithJson(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithJson(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithUrlEncodedForm() throws InterruptedException {
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
                return HttpClientUtil.requestWithUrlEncodedForm(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithUrlEncodedForm(mockWebServer, getHttpRequest, getResponse);

    }

    @Test
    void requestWithFormData() throws IOException, InterruptedException {
        Function<String, HttpRequestWithMultipart> getHttpRequest = url -> {
            Map<String, List<Object>> formData = new HashMap<>();
            formData.put("id", Arrays.asList(1));
            formData.put("name", Arrays.asList("test", "test2"));
            try {
                formData.put("file", Arrays.asList(
                        new File(FileUtils.getFilePathByFileClassPath("static/test.jpg")),
                        new File(FileUtils.getFilePathByFileClassPath("static/test.txt"))));
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
                return HttpClientUtil.requestWithFormData(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithMultipartForm(mockWebServer, getHttpRequest, getResponse);

    }

    protected Map<String, List<Object>> getBasicHeaders() {
        Map<String, List<Object>> headers = new LinkedHashMap<>();
        headers.put(APP_HEADER_KEY, new ArrayList<>(Arrays.asList(APP_HEADER_VALUE)));
        return headers;
    }

}