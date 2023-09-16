package com.taogen.commons.httpclient.springresttemplate;

import lombok.extern.log4j.Log4j2;
import okhttp3.mockwebserver.MockWebServer;

import java.util.*;

/**
 * @author Taogen
 */
@Log4j2
public class BaseTest {
    public static final String RANDOM_TOKEN = "123456";

    public static final String CHINESE_TEST = "中文测试";
    public static final String APP_HEADER_KEY = "my-app-id";
    public static final String APP_HEADER_VALUE = "java-http-clients";
    protected static MockWebServer mockWebServer;

    protected Map<String, List<Object>> getBasicHeaders() {
        Map<String, List<Object>> headers = new LinkedHashMap<>();
        headers.put(APP_HEADER_KEY, new ArrayList<>(Arrays.asList(APP_HEADER_VALUE)));
        return headers;
    }

}
