package com.example.task3jpamanytomany.exception.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
     String code;
     String message;

     public ErrorResponse(String code, String message) {
          this.code = code;
          this.message = message;
     }
}
