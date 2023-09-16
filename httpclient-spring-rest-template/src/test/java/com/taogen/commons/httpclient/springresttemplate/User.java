package com.taogen.commons.httpclient.springresttemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Taogen
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
}
