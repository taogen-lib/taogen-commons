package com.taogen.commons.jsonparser.orgjson.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author Taogen
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String name;

    private Integer age;

    private Date birthDate;

    private List<Role> roles;
}
