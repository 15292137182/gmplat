package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.constants.CodeMessage;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;

import java.io.Serializable;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * 业务对象实体类 Created by Went on 2017/8/1.
 */
@Table(TableInfo.T_BUSINESS_OBJECT)
public class BusinessObject extends BaseEntity<BusinessObject> implements Serializable {

  @TablePK
  private String rowId;//唯一标识
  private String objectCode;//对象代码
  private String objectName;//对象名称
  private String relateTableRowId;//关联表
  private String changeOperat;//执行变更操作
  private String belongModule;//所属模块
  private String belongSystem;//所属系统

  /**
   * 构建 - 创建信息
   *
   * @return 返回自身
   */
  @Override
  public BusinessObject buildCreateInfo() {
    this.objectCode = SequenceManager.getInstance().buildSequenceNo(CodeMessage.BUSIN_OBJECT, null);
    setChangeOperat(BaseConstants.CHANGE_OPERAT_FAIL);
    setRowId(lengthUUID(32));
    // getBaseTemplateBean().setVersion(BaseConstants.VERSION);
    setVersion(BaseConstants.VERSION);
    // getBaseTemplateBean().setStatus(BaseConstants.UNUSED);
    setStatus(BaseConstants.UNUSED);
    return super.buildCreateInfo();
  }

  public String getBelongModule() {
    return belongModule;
  }

  public void setBelongModule(String belongModule) {
    this.belongModule = belongModule;
  }

  public String getBelongSystem() {
    return belongSystem;
  }

  public void setBelongSystem(String belongSystem) {
    this.belongSystem = belongSystem;
  }

  public String getChangeOperat() {
    return changeOperat;
  }

  public void setChangeOperat(String changeOperat) {
    this.changeOperat = changeOperat;
  }

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getObjectCode() {
    return objectCode;
  }

  public void setObjectCode(String objectCode) {
    this.objectCode = objectCode;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public String getRelateTableRowId() {
    return relateTableRowId;
  }

  public void setRelateTableRowId(String relateTableRowId) {
    this.relateTableRowId = relateTableRowId;
  }


}
