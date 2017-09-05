package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.constants.CodeMessage;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * 业务对象实体类 Created by Went on 2017/8/1.
 * <p>
 * 基础业务对象
 */
public class BusinessObject extends BaseEntity<BusinessObject> {

  private String objectCode;  //对象代码
  private String objectName;  //对象名称
  private String relateTableRowId;  //关联表
  private String changeOperat;  //执行变更操作
  private String belongModule;  //所属模块
  private String belongSystem;  //所属系统
  private String relateTemplateObject;  //关联模板对象

  private String rowId;
  /**
   * 构建 - 创建信息
   *
   * @return 返回自身
   */
  @Override
  public BusinessObject buildCreateInfo() {
    super.buildCreateInfo();
    this.objectCode = SequenceManager.getInstance().buildSequenceNo(CodeMessage.BUSIN_OBJECT, null);
    setChangeOperat(BaseConstants.CHANGE_OPERAT_FAIL);
    // getBaseTemplateBean().setVersion(BaseConstants.VERSION);
    setVersion(BaseConstants.VERSION);
    // getBaseTemplateBean().setStatus(BaseConstants.UNUSED);
    setStatus(BaseConstants.UNUSED);
    this.rowId = UtilsTool.lengthUUID(32);
    return this;
  }

  public String getRowId() {
    return rowId;
  }

  public void setRowId(String rowId) {
    this.rowId = rowId;
  }

  public String getRelateTemplateObject() {
    return relateTemplateObject;
  }

  public void setRelateTemplateObject(String relateTemplateObject) {
    this.relateTemplateObject = relateTemplateObject;
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
