package com.consert.showmetheconsert.exception;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.exception
 * @since : 2023/05/11
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
