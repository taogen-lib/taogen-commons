package com.taogen.common.jsonparser.jackson.entity;

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
public class Role {
    private Long id;
    private String name;
}
