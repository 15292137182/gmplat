package com.bcx.plat.core.validate;

/**
 * 错误信息容器
 *
 * Create By HCL at 2017/9/21
 */
public class Error {

  private String fieldName;
  private String errorMessage;

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

}