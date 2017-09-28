package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 数据库表信息 Create By HCL at 2017/7/31
 */
public class DBTableColumn extends BaseEntity<DBTableColumn> {

  private String relateTableRowId;//关联表rowId
  private String columnEname;//字段英文名
  private String columnCname;//字段中文名
  private String desp;//说明

  /**
   * 重载构建 - 创建信息方法，加入rowId
   */
  @Override
  public DBTableColumn buildCreateInfo() {
    return super.buildCreateInfo();
  }

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getRelateTableRowId() {
    return relateTableRowId;
  }

  public void setRelateTableRowId(String relateTableRowId) {
    this.relateTableRowId = relateTableRowId;
  }

  public String getColumnEname() {
    return columnEname;
  }

  public void setColumnEname(String columnEname) {
    this.columnEname = columnEname;
  }

  public String getColumnCname() {
    return columnCname;
  }

  public void setColumnCname(String columnCname) {
    this.columnCname = columnCname;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }
}