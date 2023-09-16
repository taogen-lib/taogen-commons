package com.taogen.commons.httpclient.vo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author Taogen
 */
@Data
@ToString(callSuper = true)
//@AllArgsConstructor
//@NoArgsConstructor
@SuperBuilder
public class HttpRequestWithJson extends HttpRequest {
    private String json;
}
