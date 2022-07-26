package com.taogen.commons.network.vo;

import lombok.Data;

import java.io.InputStream;

/**
 * @author Taogen
 */
@Data
public class FormItem {
    private String name;
    private byte[] bytes;
    /**
     * TODO for big file of multipart form data
     */
    private InputStream inputStream;
    private String contentType;
    private String filename;
    private Boolean isFile;
    /**
     * when isFile=true, value is null
     */
    private String value;
}
