package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.constants.CodeMessage;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * Title: TemplateObject</p>
 * Description: 模板对象实体类
 * Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 * <p>
 * 2017/8/28  Wen TieHu Create
 */
public class TemplateObject extends BaseEntity<TemplateObject> {

  private String templateCode;//模板代码
  private String templateName;//模板名称
  private String desp;//说明
  private String belongModule;//所属模块
  private String belongSystem;//所属系统

  /**
   * 构建 - 创建信息
   *
   * @return 返回自身
   */
  @Override
  public TemplateObject buildCreateInfo() {
    this.templateCode = SequenceManager.getInstance().buildSequenceNo(CodeMessage.TEMPLATE, null);
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


  public String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getDesp() {
    return desp;
  }

  public void setDesp(String desp) {
    this.desp = desp;
  }
}
