package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * 键值集合实体类
 * Created by Went on 2017/8/3.
 */
public class KeySet extends BaseEntity<KeySet>{

    private String keysetCode;//键值代码
    private String keysetName;//键值名称
    private String belongModule;//所属模块
    private String belongSystem;//所属系统
    private String desp;//说明

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public KeySet buildCreateInfo() {
        setRowId(lengthUUID(32));
        return this;
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

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getKeysetCode() {
        return keysetCode;
    }

    public void setKeysetCode(String keysetCode) {
        this.keysetCode = keysetCode;
    }

    public String getKeysetName() {
        return keysetName;
    }

    public void setKeysetName(String keysetName) {
        this.keysetName = keysetName;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
