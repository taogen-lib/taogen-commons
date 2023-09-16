package com.taogen.common.jsonparser.jackson.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonProperty(value = "birth_date")
    private Date birthDate;

    private List<Role> roles;
}
