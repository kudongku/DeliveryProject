package com.sparta.deliveryproject.exception;

import com.sparta.deliveryproject.responseDto.CommonResponseDto;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<CommonResponseDto> SQLIntegrityConstraintViolationExceptionHandler() {
        CommonResponseDto commonResponseDto = new CommonResponseDto(400, "중복된 값이 있습니다.");
        return new ResponseEntity<>(
                // HTTP body
                commonResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({PropertyValueException.class})
    public ResponseEntity<CommonResponseDto> PropertyValueExceptionHandler() {
        CommonResponseDto commonResponseDto = new CommonResponseDto(400, "누락된 값이 있습니다.");
        return new ResponseEntity<>(
                // HTTP body
                commonResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NotValidCategoryException.class})
    public ResponseEntity<CommonResponseDto> NotValidCategoryExceptionHandler(NotValidCategoryException e) {
        CommonResponseDto commonResponseDto = new CommonResponseDto(400, e.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                commonResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponseDto> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        CommonResponseDto commonResponseDto = new CommonResponseDto(400, e.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                commonResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<CommonResponseDto> NullPointerExceptionHandler(NullPointerException e) {
        CommonResponseDto commonResponseDto = new CommonResponseDto(400, e.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                commonResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({DuplicatedMenuException.class})
    public ResponseEntity<CommonResponseDto> DuplicatedMenuExceptionHandler(DuplicatedMenuException e) {
        CommonResponseDto commonResponseDto = new CommonResponseDto(400, e.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                commonResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
