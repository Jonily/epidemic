package com.tcloudsoft.auth.provider.ex;

import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.ex.TcloudException;


public class TcmsAuthException extends TcloudException {


  private static final long serialVersionUID = 1L;

  public TcmsAuthException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage);
  }

  public TcmsAuthException(ResponseCodeEnum error) {
    super(error.getMessage());
  }

  public TcmsAuthException(ResponseCodeEnum error, Object... params) {
    super(error.name(), String.format(error.getMessage(), params));
  }

}
