package com.gussoft.seriesgm.integration.expose;

import com.gussoft.seriesgm.core.exception.ApiException;
import com.gussoft.seriesgm.core.exception.NotFoundException;
import com.gussoft.seriesgm.integration.tranfer.response.MessageResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@ControllerAdvice(annotations = RestController.class)
public class ApiAdvice {

    @ExceptionHandler(value = ApiException.class)
    public Mono<ResponseEntity<MessageResponse<Void>>> getException(ApiException exception) {

        MessageResponse<Void> msg = MessageResponse.<Void>builder()
                .message(exception.getMessage())
                .build();

        return Mono.just(new ResponseEntity<>(msg, exception.getHttpStatus()));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public Mono<ResponseEntity<MessageResponse<Void>>> getNotFoundException(NotFoundException exception) {

        MessageResponse<Void> msg = MessageResponse.<Void>builder()
                .message(exception.getMessage())
                .build();

        return Mono.just(new ResponseEntity<>(msg, HttpStatusCode.valueOf(500)));
    }

}
