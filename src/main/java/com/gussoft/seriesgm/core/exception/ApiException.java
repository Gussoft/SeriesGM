package com.gussoft.seriesgm.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 3840287382237745348L;

  private String message;
  private HttpStatus httpStatus;

  public ApiException(String message, HttpStatus httpStatus) {
    super(message);
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String toString() {
    return message;
  }

}

