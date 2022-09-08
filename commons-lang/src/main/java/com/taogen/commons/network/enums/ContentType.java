package com.taogen.commons.network.enums;

/**
 * @author Taogen
 */
public enum ContentType {
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContentType fromValue(String value) {
        for (ContentType contentType : values()) {
            if (contentType.getValue().equals(value)) {
                return contentType;
            }
        }
        throw new IllegalArgumentException("Unsupported content type: " + value);
    }
}
