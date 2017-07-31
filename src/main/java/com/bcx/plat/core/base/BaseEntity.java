package com.bcx.plat.core.base;

import java.util.HashMap;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 基础 entity 类，建议所有实体类均继承此类
 * Create By HCL at 2017/7/31
 */
public class BaseEntity<T extends BaseEntity> {

    private String status;
    private String version;
    private String createUser;
    private String createName;
    private String createDate;
    private String modifyUser;
    private String modifyName;
    private String modifyDate;
    private String deleteUser;
    private String deleteName;
    private String deleteDate;
    private String deleteFlag;
    private String rowId;

    private Map etc = new HashMap();

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @SuppressWarnings("unchecked")
    public T buildCreateInfo() {
        setCreateDate(getDateTimeNow());
        setCreateUser("admin");
        setCreateName("系统管理员");
        setRowId(lengthUUID(32));
        return (T) this;
    }

    /**
     * 构建 - 修改信息
     *
     * @return 返回自身
     */
    @SuppressWarnings("unchecked")
    public T buildModifyInfo() {
        setModifyDate(getDateTimeNow());
        setModifyUser("admin");
        setModifyName("系统管理员");
        return (T) this;
    }

    /**
     * 构建 - 删除信息
     *
     * @return 自身
     */
    @SuppressWarnings("unchecked")
    public T buildDeleteInfo() {
        setDeleteDate(getDateTimeNow());
        setDeleteUser("admin");
        setDeleteName("系统管理员");
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
        if (isValid(map.get("etc"))) {
            if (!(map.get("etc") instanceof Map)) {
                map.put("etc", jsonToObj(map.get("etc").toString(), HashMap.class));
            }
        } else {
            map.put("etc", new HashMap<>());
        }
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

    public String getDeleteName() {
        return deleteName;
    }

    public void setDeleteName(String deleteName) {
        this.deleteName = deleteName;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public Map getEtc() {
        return etc;
    }

    public void setEtc(Map etc) {
        this.etc = etc;
    }
}
