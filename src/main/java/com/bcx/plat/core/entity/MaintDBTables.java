package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by Went on 2017/7/31. 维护数据库表实体类
 */
public class MaintDBTables extends BaseEntity<MaintDBTables> implements Serializable {

  //id
  private String tableSchema;//表schema
  private String tableEname;//表英文名
  private String tableCname;//表中文名
  private String desp;//说明

  /**
   * 构建 - 创建信息
   *
   * @return 返回自身
   */
  @Override
  public MaintDBTables buildCreateInfo() {
    return super.buildCreateInfo();
  }

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getTableSchema() {
    return tableSchema;
  }

  public void setTableSchema(String tableSchema) {
    this.tableSchema = tableSchema;
  }

  public String getTableEname() {
    return tableEname;
  }

  public void setTableEname(String tableEname) {
    this.tableEname = tableEname;
  }

  public String getTableCname() {
    return tableCname;
  }

  public void setTableCname(String tableCname) {
    this.tableCname = tableCname;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }
}
