package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 序列号规则配置 pojo 类
 * Create By HCL at 2017/8/6
 */
public class SequenceRuleConfig extends BaseEntity<SequenceRuleConfig>{

  private String rowId;
  private String seqCode;
  private String seqName;
  private String seqContent;
  private String desp;

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getSeqCode() {
    return seqCode;
  }

  public void setSeqCode(String seqCode) {
    this.seqCode = seqCode;
  }

  public String getSeqName() {
    return seqName;
  }

  public void setSeqName(String seqName) {
    this.seqName = seqName;
  }

  public String getSeqContent() {
    return seqContent;
  }

  public void setSeqContent(String seqContent) {
    this.seqContent = seqContent;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }
}
