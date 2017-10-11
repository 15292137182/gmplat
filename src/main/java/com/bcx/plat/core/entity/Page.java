package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/11  Wen TieHu Create </pre>
 */
public class Page extends BaseEntity<Page>{

  private String pageNumber;//页面编号

  private String pageName;//页面名称

  private String pageUrl;//页面url

  private String grantAuth;//是否授权

  private String belongModule;//所属模块

  private String remarks;//备注

  public String getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(String pageNumber) {
    this.pageNumber = pageNumber;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public String getPageUrl() {
    return pageUrl;
  }

  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  public String getGrantAuth() {
    return grantAuth;
  }

  public void setGrantAuth(String grantAuth) {
    this.grantAuth = grantAuth;
  }

  public String getBelongModule() {
    return belongModule;
  }

  public void setBelongModule(String belongModule) {
    this.belongModule = belongModule;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
