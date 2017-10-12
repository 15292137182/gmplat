package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * <p>Title: FuncOperat</p>
 * <p>Description: 功能操作接口实体类</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/12  Wen TieHu Create </pre>
 */
public class FuncOperat extends BaseEntity<FuncOperat> {

  private String operatNumber;//操作编号

  private String operatName;//操作名称

  private String interceptUrl;//拦截url

  private String avail;//是否可用

  private String desc;//描述

  public String getOperatNumber() {
    return operatNumber;
  }

  public void setOperatNumber(String operatNumber) {
    this.operatNumber = operatNumber;
  }

  public String getOperatName() {
    return operatName;
  }

  public void setOperatName(String operatName) {
    this.operatName = operatName;
  }

  public String getInterceptUrl() {
    return interceptUrl;
  }

  public void setInterceptUrl(String interceptUrl) {
    this.interceptUrl = interceptUrl;
  }

  public String getAvail() {
    return avail;
  }

  public void setAvail(String avail) {
    this.avail = avail;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
