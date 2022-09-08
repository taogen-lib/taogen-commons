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
public class HttpRequestWithForm extends HttpRequest {
    Map<String, List<Object>> formData;
}
