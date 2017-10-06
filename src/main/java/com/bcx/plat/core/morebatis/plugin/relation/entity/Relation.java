package com.bcx.plat.core.morebatis.plugin.relation.entity;

import com.bcx.plat.core.base.support.BeanInterface;
import java.util.List;

/**
 * 专门用来存放集合数据的表所对应的实体类
 */
public class Relation implements BeanInterface<Relation>{
  private String rowId;
  private String primaryTable;
  private String primaryRowId;
  private String name;
  private String value;
  private Integer sort;

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getPrimaryTable() {
    return primaryTable;
  }

  public void setPrimaryTable(String primaryTable) {
    this.primaryTable = primaryTable;
  }

  public String getPrimaryRowId() {
    return primaryRowId;
  }

  public void setPrimaryRowId(String primaryRowId) {
    this.primaryRowId = primaryRowId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  @Override
  public List<BeanInterface> getJoinTemplates() {
    return null;
  }
}
