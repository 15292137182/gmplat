package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.manager.SequenceManager;

import static com.bcx.plat.core.constants.CodeMessage.USER_GROUP_NUMBER;

/**
 * <p>Title: UserGroup Class</p>
 * <p>Description: 用户组实体类</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/11  Wen TieHu Create </pre>
 */
public class UserGroup extends BaseEntity<UserGroup> {

  private String groupNumber;//组编号

  private String groupName;//组名称

  private String belongSector;//所属部门

  private String groupCategory;//组类别

  private String desc;//描述

  private String remarks;//描述

  /**
   * 构建删除信息
   *
   * @return 返回自身
   */
  @Override
  public UserGroup buildCreateInfo() {
   this.groupNumber =  SequenceManager.getInstance().buildSequenceNo(USER_GROUP_NUMBER,null);
    return super.buildCreateInfo();
  }

  public String getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(String groupNumber) {
    this.groupNumber = groupNumber;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getBelongSector() {
    return belongSector;
  }

  public void setBelongSector(String belongSector) {
    this.belongSector = belongSector;
  }

  public String getGroupCategory() {
    return groupCategory;
  }

  public void setGroupCategory(String groupCategory) {
    this.groupCategory = groupCategory;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }
}
