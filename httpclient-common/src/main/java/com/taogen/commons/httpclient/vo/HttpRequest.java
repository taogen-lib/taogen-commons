package com.taogen.commons.httpclient.vo;

import com.taogen.commons.httpclient.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
}
