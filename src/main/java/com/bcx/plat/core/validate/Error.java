package com.bcx.plat.core.validate;

/**
 * 错误的封装类
 * <p>
 * 主要包括错误字段、错误信息、错误信息 Key
 * <p>
 * Create By HCL at 2017/9/18
 */
public class Error {

  private String field;
  private String messageKey;
  private String message;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(String messageKey) {
    this.messageKey = messageKey;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}