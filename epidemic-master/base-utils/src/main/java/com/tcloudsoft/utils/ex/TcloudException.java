package com.tcloudsoft.utils.ex;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TcloudException extends Exception {

  private static final long serialVersionUID = 1L;

  private String errorCode;
  private String errorMessage;

  public TcloudException(String errorCode, String errorMessage) {
    super();
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public TcloudException(String errorMessage) {
    super();
    this.errorCode = "500";
    this.errorMessage = errorMessage;
  }

  public TcloudException(String errorCode, String errorMessage, Exception e) {
    super(e);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
