package com.bcx.plat.core.utils;

import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

import com.bcx.plat.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 返回结果 Create By HCL at 2017/7/31
 */public class ServiceResult<T> implements Serializable {

  private static ResourceBundleMessageSource messageSource;

  private static final long serialVersionUID = 812376774103405857L;
  private int state = STATUS_SUCCESS;
  private String message;
  private T content;
  private Map additional;

  public ServiceResult() {
  }

  public ServiceResult(T content) {
    this.content = content;
  }

  public ServiceResult(T content,int state) {
    this.state = state;
    this.content = content;
  }

  public ServiceResult(T content,String message) {
    this.content = content;
    this.message = message;
  }

  public ServiceResult(T content,int state, String message) {
    this.state = state;
    this.message = message;
    this.content = content;
  }

  public ServiceResult(int state, String message, T content, Map additional) {
    this.state = state;
    this.message = message;
    this.content = content;
    this.additional = additional;
  }

  public Map getAdditional() {
    return additional;
  }
  public void setAdditional(Map additional) {
    this.additional = additional;
  }

  /**
   * 转换消息
   */
  public ServiceResult<T> convertMsg(Locale locale) {
    setMessage(initMS().getMessage(getMessage(), null, locale));
    return this;
  }

  /**
   * 初始化消息
   *
   * @return 返回
   */
  private ResourceBundleMessageSource initMS() {
    if (null == messageSource) {
      messageSource = SpringContextHolder.getBean("messageSource");
    }
    return messageSource;
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

  public T getContent() {
    return content;
  }
  public void setContent(T content) {
    this.content = content;
  }

  public static ServiceResult Msg(int status,String msg){
    return new ServiceResult(null,status,msg);
  }
}