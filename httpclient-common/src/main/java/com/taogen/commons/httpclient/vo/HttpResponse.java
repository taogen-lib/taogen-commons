package com.taogen.commons.httpclient.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Taogen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    private Integer statusCode;
    private Map<String, List<Object>> headers;
    /**
     * byte[] array for text response body or binary response body
     */
    private byte[] body;
    private String bodyString;

    public HttpResponse(Integer statusCode, Map<String, List<Object>> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public String getBodyString(Charset charset) {
        if (this.body == null) {
            return null;
        }
        String bodyString = new String(body, charset);
        if (this.bodyString == null) {
            this.bodyString = bodyString;
        }
        return bodyString;
    }
}
