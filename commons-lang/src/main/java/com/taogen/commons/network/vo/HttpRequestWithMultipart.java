package com.taogen.commons.network.vo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Taogen
 */
@Data
@ToString(callSuper = true)
@SuperBuilder
public class HttpRequestWithMultipart extends HttpRequest {
    /**
     * File item use File class object
     */
    Map<String, List<Object>> formData;

}
