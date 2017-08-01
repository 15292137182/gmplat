package com.bcx.plat.core.base;

import com.bcx.plat.core.database.action.annotations.TablePK;

import java.util.HashMap;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 基础 entity 类，建议所有实体类均继承此类
 * Create By HCL at 2017/7/31
 */
public class BaseEntity<T extends BaseEntity> {

    private String status;//状态
    private String version;//版本
    private String createUser;//创建人
    private String createUserName;//创建名称
    private String createTime;//创建时间
    private String modifyUser;//修改人
    private String modifyUserName;//修改名称
    private String modifyTime;//修改时间
    private String deleteUser;//删除人
    private String deleteUserName;//删除名称
    private String deleteTime;//删除时间
    private String deleteFlag;//删除标记

    private Map etc;

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @SuppressWarnings("unchecked")
    public T buildCreateInfo() {
        setCreateTime(getDateTimeNow());
        setCreateUser("admin");
        setCreateUserName("系统管理员");
        return (T) this;
    }

    /**
     * 构建 - 修改信息
     *
     * @return 返回自身
     */
    @SuppressWarnings("unchecked")
    public T buildModifyInfo() {
        setModifyTime(getDateTimeNow());
        setModifyUser("admin");
        setModifyUserName("系统管理员");
        return (T) this;
    }

    /**
     * 构建 - 删除信息
     *
     * @return 自身
     */
    @SuppressWarnings("unchecked")
    public T buildDeleteInfo() {
        setDeleteTime(getDateTimeNow());
        setDeleteUser("admin");
        setDeleteUserName("系统管理员");
        setDeleteFlag(DELETE_FLAG);
        return (T) this;
    }

    /**
     * 将 entity 实体类转换为 map
     *
     * @return map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> toMap() {
        return jsonToObj(objToJson(this), HashMap.class);
    }

    /**
     * 尝试从 map 中读取 entity 类
     *
     * @param map map数据
     * @return 返回实体类
     */
    @SuppressWarnings("unchecked")
    public T fromMap(Map<String, Object> map) {
        return (T) jsonToObj(objToJson(map), getClass());
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

    public Map getEtc() {
        return etc;
    }

    public void setEtc(Map etc) {
        this.etc = etc;
    }
}
