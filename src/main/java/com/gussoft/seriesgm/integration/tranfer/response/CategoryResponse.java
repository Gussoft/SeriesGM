package com.gussoft.seriesgm.integration.tranfer.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryResponse implements Serializable {

    @JsonProperty("id_category")
    private Long idCategory;

    @JsonProperty("name")
    private String name;

    @JsonProperty("created_at")
    private String createdAt;

}
// @JsonInclude(Include.NON_NULL)