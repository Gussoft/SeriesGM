package com.gussoft.seriesgm.integration.tranfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericResponse<E> {

    public GenericResponse(String message) {
        this.responseMessage = message;
    }

    public GenericResponse(String message, E data) {
        this.responseMessage = message;
        this.data = data;
    }

    @JsonProperty("message")
    private String responseMessage;

    @JsonInclude(value = Include.NON_NULL)
    private E data;

}