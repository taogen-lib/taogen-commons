package com.taogen.commons.httpclient;

import com.taogen.commons.httpclient.enums.HttpMethod;
import com.taogen.commons.httpclient.mockwebserver.MockWebServerTest;
import com.taogen.commons.httpclient.vo.*;
import com.taogen.commons.io.FileUtils;
import lombok.extern.log4j.Log4j2;
import okhttp3.mockwebserver.MockWebServer;
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

@Log4j2
class OkHttpUtilTest extends BaseTest {

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
    void requestWithoutBody_get() throws IOException, InterruptedException {
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
                HttpResponse httpResponse = OkHttpUtil.requestWithoutBody(httpRequest);
                return httpResponse;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testRequestWithoutBody(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithJson() throws IOException, InterruptedException {
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
                return OkHttpUtil.requestWithJson(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithJson(mockWebServer, getHttpRequest, getResponse);
    }


    @Test
    void requestWithFormUrlEncoded() throws InterruptedException, IOException {
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
                return OkHttpUtil.requestWithFormUrlEncoded(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithUrlEncodedForm(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithFormData() throws InterruptedException, IOException, URISyntaxException {
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
                return OkHttpUtil.requestWithFormData(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithMultipartForm(mockWebServer, getHttpRequest, getResponse);
    }
}
