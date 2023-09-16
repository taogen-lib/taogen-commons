package com.taogen.commons.jsonparser.gson.entity;

import com.google.gson.annotations.SerializedName;
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
public class UserForGson {
    private Long id;

    private String name;

    private Integer age;

    /**
     * Change the field name: Using Gson @SerializedName.
     * Format date:
     * 1) Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
     * 2) Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, MyDateJsonSerializer);
     */
    @SerializedName(value = "birth_date")
    private Date birthDate;

    private List<RoleForGson> roles;
}
