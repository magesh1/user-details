package com.example.user.details.utils.exception;

import com.example.user.details.dto.CustomErrResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericException {

    private final Logger logger = LoggerFactory.getLogger(GenericException.class);

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<CustomErrResponse> handleUserExists(UserAlreadyExists e) {
        CustomErrResponse error = new CustomErrResponse(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrResponse> handleUserNotFound(UserNotFoundException e) {
        CustomErrResponse error = new CustomErrResponse(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrResponse> handleGenericExpection(Exception e) {
        CustomErrResponse error = new CustomErrResponse("An unexpected error occurred.");
        logger.error("\n err: {}\n", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
