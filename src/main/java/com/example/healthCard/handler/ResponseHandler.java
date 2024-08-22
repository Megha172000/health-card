package com.example.healthCard.handler;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class ResponseHandler {
    Object body;
    int code;
    Pageable pageable;
    int totalPages;

    public static ResponseEntity<ResponseHandler> getSuccessResponse(Object body){
        ResponseHandler responseHandler = ResponseHandler.builder()
                .code(1000)
                .body(body)
                .build();
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseHandler> getSuccessResponse(ResponseHandler responseHandler){
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseHandler> getErrorResponse(HttpStatus httpStatus, Object body){
        ResponseHandler responseHandler = ResponseHandler.builder()
                .code(getInternalStatusCode(httpStatus))
                .body(body)
                .build();
        return new ResponseEntity<>(responseHandler, httpStatus);
    }

    private static int getInternalStatusCode(HttpStatus httpStatus){
        switch (httpStatus){
            case OK -> {
                return 1000;
            }
            case NOT_FOUND -> {
                return 1400;
            }
            case BAD_REQUEST -> {
                return 1200;
            }
            case CONFLICT -> {
                return 1100;
            }
            default -> {
                return 1500;
            }
        }
    }
}
