package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * <p>Title: 菜单</p>
 * <p>Description: 菜单</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/10  Wen TieHu Create </pre>
 */
public class Menu extends BaseEntity<Menu> {
  private String parentNumber;//菜单父编号
  private String number;//菜单编号
  private String name;//菜单名称
  private String category;//菜单类别
  private String url;//菜单url
  private String sort;//菜单排序
  private String grantAuth;//是否授权
  private String avail;//是否可用
  private String icon;//菜单图标
  private String remarks;//备注

  public String getParentNumber() {
    return parentNumber;
  }

  public void setParentNumber(String parentNumber) {
    this.parentNumber = parentNumber;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getGrantAuth() {
    return grantAuth;
  }

  public void setGrantAuth(String grantAuth) {
    this.grantAuth = grantAuth;
  }

  public String getAvail() {
    return avail;
  }

  public void setAvail(String avail) {
    this.avail = avail;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
