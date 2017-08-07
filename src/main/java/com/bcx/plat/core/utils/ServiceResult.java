package com.bcx.plat.core.utils;

import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

import java.io.Serializable;
import java.util.Map;

/**
 * Service 返回的结果
 *
 * Create By HCL at 2017/7/31
 */
public class ServiceResult<T> implements Serializable {

  private static final long serialVersionUID = 812376774103405857L;

  private int state = STATUS_SUCCESS;
  private String message = "";
  private T data;
  private Map extra;

  /**
   * 空的构造方法，供 json 转换时使用
   *
   * Create By HCL at 2017/8/7
   */
  public ServiceResult() {
  }

  /**
   * 全参数构造方法
   *
   * @param state 状态
   * @param message 消息
   * @param data 数据信息
   */
  public ServiceResult(int state, String message, T data) {
    this.state = state;
    this.message = message;
    this.data = data;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Map getExtra() {
    return extra;
  }

  public void setExtra(Map extra) {
    this.extra = extra;
  }
}