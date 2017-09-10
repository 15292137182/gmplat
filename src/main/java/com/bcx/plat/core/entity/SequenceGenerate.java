package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * 序列号生成 Entity，准确的来说，更像是序列号的历史表
 *
 * Create By HCL at 2017/8/8
 */
public class SequenceGenerate extends BaseEntity<SequenceGenerate> {
  private String seqRowId;
  private String variableKey;
  private String currentValue;
  private String branchSign;  // 分支标志
  private String objectSigns;

  @Override
  public SequenceGenerate buildCreateInfo() {
    setRowId(lengthUUID(64));
    return super.buildCreateInfo();
  }

  public String getObjectSigns() {
    return objectSigns;
  }

  public void setObjectSigns(String objectSigns) {
    this.objectSigns = objectSigns;
  }

  public String getSeqRowId() {
    return seqRowId;
  }

  public void setSeqRowId(String seqRowId) {
    this.seqRowId = seqRowId;
  }

  public String getVariableKey() {
    return variableKey;
  }

  public void setVariableKey(String variableKey) {
    this.variableKey = variableKey;
  }

  public String getCurrentValue() {
    return currentValue;
  }

  public void setCurrentValue(String currentValue) {
    this.currentValue = currentValue;
  }

  public String getBranchSign() {
    return branchSign;
  }

  public void setBranchSign(String branchSign) {
    this.branchSign = branchSign;
  }
}
