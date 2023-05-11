package com.consert.showmetheconsert.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.exception
 * @since : 2023/05/11
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    log.error("An error occurred: " + e.getMessage());
    return new ResponseEntity<>("An error occurred: " + e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleResourceNotFoundException(RuntimeException e) {
    log.error("An error occurred: " + e.getMessage());
    return new ResponseEntity<>("An error occurred: " + e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}