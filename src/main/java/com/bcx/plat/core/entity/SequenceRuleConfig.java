package com.bcx.plat.core.entity;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;

/**
 * 序列号规则配置 pojo 类
 *
 * Create By HCL at 2017/8/6
 */
@Table(TableInfo.T_SEQUENCE_RULE_CONFG)
public class SequenceRuleConfig extends BaseEntity<SequenceRuleConfig> {

  @TablePK
  private String rowId;
  private String seqCode;
  private String seqName;
  private String seqContent;
  private String desp;

  @Override
  public SequenceRuleConfig buildCreateInfo() {
    setRowId(lengthUUID(32));
    return super.buildCreateInfo();
  }

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
