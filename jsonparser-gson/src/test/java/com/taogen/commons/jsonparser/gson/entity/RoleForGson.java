package com.taogen.commons.jsonparser.gson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Taogen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleForGson {
    private Long id;
    private String name;
}
