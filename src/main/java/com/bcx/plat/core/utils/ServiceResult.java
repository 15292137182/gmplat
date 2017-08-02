package com.bcx.plat.core.utils;

import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.objToJson;

import com.bcx.plat.core.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 返回结果 Create By HCL at 2017/7/31
 */
public class ServiceResult<T extends BaseEntity> implements Serializable {

  private static ResourceBundleMessageSource messageSource;

  private static final long serialVersionUID = 812376774103405857L;
  private int state = STATUS_SUCCESS;
  private String message;
  private Map<String, Object> content = new HashMap<>();

  public ServiceResult() {

  }

  /**
   * 常用参数构造方法
   *
   * @param state 状态
   * @param message 消息
   * @param data 数据
   */
  public ServiceResult(int state, String message, Object data) {
    setState(state);
    setMessage(message);
    setData(data);
  }

  /**
   * 接受状态和消息的构造函数
   *
   * @param state 消息
   * @param message 消息
   */
  public ServiceResult(int state, String message) {
    setState(state);
    setMessage(message);
  }

  /**
   * 接受消息和对象的构造函数
   *
   * @param message 消息
   * @param data 数据
   */
  public ServiceResult(String message, Object data) {
    setState(STATUS_SUCCESS);
    setData(data);
    setMessage(message);
  }

  /**
   * 接受 data 的构造方法
   *
   * @param data 数据
   */
  public ServiceResult(Object data) {
    setState(STATUS_SUCCESS);
    setData(data);
    setMessage("");
  }

  /**
   * 设置页面信息
   *
   * @param pageNum 当前页面
   * @param pageSize 页面大小
   * @param pages 总页数
   * @param total 总条数
   * @return 返回自身
   */
  public ServiceResult<T> setPageInfo(int pageNum, int pageSize, int pages, int total) {
    getContent().put("pageNum", pageNum);
    getContent().put("pageSize", pageSize);
    getContent().put("pages", pages);
    getContent().put("total", total);
    return this;
  }

  /**
   * 设置页面信息
   *
   * @param pageInfo 查询的页面信息
   * @return 返回自身
   */
  public ServiceResult<T> setPageInfo(PageInfo<T> pageInfo) {
    getContent().put("pageNum", pageInfo.getPageNum());
    getContent().put("pageSize", pageInfo.getPageSize());
    getContent().put("pages", pageInfo.getPages());
    getContent().put("total", pageInfo.getTotal());
    setData(pageInfo.getList());
    return this;
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

  public Map<String, Object> getContent() {
    return content;
  }

  public void setContent(Map<String, Object> content) {
    this.content = content;
  }

  public void setData(Object data) {
    getContent().put("data", data);
  }

  @JsonIgnore
  public Object getData() {
    return getContent().get("data");
  }
}
