package com.taogen.commons.network.vo;

import lombok.Data;

/**
 * @author Taogen
 */
@Data
public class FormItem {
    private String name;
    private byte[] bytes;
    private String contentType;
    private String filename;
    private Boolean isFile;
    /**
     * when isFile=true, value is null
     */
    private String value;
}
