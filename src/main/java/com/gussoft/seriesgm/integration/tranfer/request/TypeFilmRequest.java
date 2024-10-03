package com.gussoft.seriesgm.integration.tranfer.request;

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
public class TypeFilmRequest implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

}
