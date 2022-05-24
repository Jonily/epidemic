package com.tcloudsoft.utils.response;

import com.tcloudsoft.utils.ex.ResponseCodeEnum;
import com.tcloudsoft.utils.ex.TcloudException;
import lombok.Data;

@Data
public class ResponseData<T> {

  private boolean success = true;
  private String code = "";
  private String codeMessage = "";
  private T data;

  public ResponseData() {
    super();
  }

  private ResponseData(String code, String codeMessage, boolean success, T data) {
    super();
    this.code = code;
    this.codeMessage = codeMessage;
    this.data = data;
    this.success = success;
  }


  private ResponseData(ResponseCodeEnum code, boolean success, T data) {
    super();
    this.code = code.name();
    this.codeMessage = code.getMessage();
    this.data = data;
    this.success = success;
  }


  /**
   * 返回成功方法
   * 
   * @param data 成功数据
   * @return 对象
   */
  public static <T> ResponseData<T> ok() {
    return new ResponseData<T>(ResponseCodeEnum.C000000, true, null);
  }

  /**
   * 返回成功方法
   * 
   * @param data 成功数据
   * @return 对象
   */
  public static <T> ResponseData<T> ok(T data) {
    return new ResponseData<T>(ResponseCodeEnum.C000000, true, data);
  }

  /**
   * 返回失败方法
   * 
   * @param e 失败异常
   * @return 对象
   */
  public static <T> ResponseData<T> fail(TcloudException e) {
    return new ResponseData<T>(e.getErrorCode(), e.getErrorMessage(), false, null);
  }

  /**
   * 返回失败方法
   * 
   * @param e 失败异常
   * @return 对象
   */
  public static <T> ResponseData<T> fail(String errorCode, String errorMessage) {
    return new ResponseData<T>(errorCode, errorMessage, false, null);
  }

  public static <T> ResponseData<T> fail(String errorMessage) {
    return new ResponseData<T>("500",errorMessage, false, null);
  }

  public static <T> ResponseData<T> fail(ResponseCodeEnum code) {
    return new ResponseData<T>(code.name(), code.getMessage(), false, null);
  }
}
