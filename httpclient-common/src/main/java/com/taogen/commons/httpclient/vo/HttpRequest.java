package com.taogen.commons.httpclient.vo;

import com.taogen.commons.httpclient.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Taogen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class HttpRequest {
    private String url;
    private HttpMethod method;
    private Map<String, List<Object>> headers;
    /**
     * URL query string
     */
    private Map<String, List<Object>> queryParams;

    public static Map<String, List<Object>> getBasicHeaders() {
        Map<String, List<Object>> headers = new HashMap<>();
        headers.put("Accept", Arrays.asList("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        headers.put("Accept-Encoding", Arrays.asList("gzip, deflate, br"));
        headers.put("Accept-Language", Arrays.asList("en-US,en,zh;q=0.5"));
        headers.put("User-Agent", Arrays.asList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
        return headers;
    }
}
