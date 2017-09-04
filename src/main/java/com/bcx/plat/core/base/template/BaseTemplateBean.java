package com.bcx.plat.core.base.template;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.manager.SpApplicationManager;

import java.util.List;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;
import static com.bcx.plat.core.utils.UtilsTool.getDateTimeNow;

/**
 * 基础公共字段，定义了平台一些基础的字段和基本方法
 * <p>
 * Create By HCL at 2017/8/30
 */
public class BaseTemplateBean implements BeanInterface<BaseTemplateBean> {

  private String status;  // 状态
  private String version; // 版本
  private String createUser;  // 创建人
  private String createUserName;  // 创建名称
  private String createTime;  // 创建时间
  private String modifyUser;  // 修改人
  private String modifyUserName;  // 修改名称
  private String modifyTime;  // 修改时间
  private String deleteUser;  // 删除人
  private String deleteUserName;  // 删除名称
  private String deleteTime;  // 删除时间
  private String deleteFlag = BaseConstants.NOT_DELETE_FLAG;  // 删除标记

  /**
   * @return 返回加入的模版
   */
  @Override
  public List<BeanInterface> getJoinTemplates() {
    return null;
  }

  /**
   * 创建 - 创建信息
   *
   * @return 返回自身
   */
  public BaseTemplateBean buildCreateInfo() {
    this.createTime = getDateTimeNow();
    User user = SpApplicationManager.getInstance().getLoginUser();
    this.createUser = user.getId();
    this.createUserName = user.getName();
    return this;
  }

  /**
   * 创建 - 修改信息
   *
   * @return 返回自身
   */
  public BaseTemplateBean buildModifyInfo() {
    this.modifyTime = getDateTimeNow();
    this.modifyUser = SpApplicationManager.getInstance().getLoginUserId();
    this.modifyUserName = SpApplicationManager.getInstance().getLoginUserName();
    return this;
  }

  /**
   * 创建 - 修改信息
   *
   * @return 返回自身
   */
  public BaseTemplateBean buildDeleteInfo() {
    this.deleteFlag = DELETE_FLAG;
    this.deleteTime = getDateTimeNow();
    this.deleteUser = SpApplicationManager.getInstance().getLoginUserId();
    this.deleteUserName = SpApplicationManager.getInstance().getLoginUserName();
    return this;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getModifyUser() {
    return modifyUser;
  }

  public void setModifyUser(String modifyUser) {
    this.modifyUser = modifyUser;
  }

  public String getModifyUserName() {
    return modifyUserName;
  }

  public void setModifyUserName(String modifyUserName) {
    this.modifyUserName = modifyUserName;
  }

  public String getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(String modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getDeleteUser() {
    return deleteUser;
  }

  public void setDeleteUser(String deleteUser) {
    this.deleteUser = deleteUser;
  }

  public String getDeleteUserName() {
    return deleteUserName;
  }

  public void setDeleteUserName(String deleteUserName) {
    this.deleteUserName = deleteUserName;
  }

  public String getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(String deleteTime) {
    this.deleteTime = deleteTime;
  }

  public String getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(String deleteFlag) {
    this.deleteFlag = deleteFlag;
  }
}
