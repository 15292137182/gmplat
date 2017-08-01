package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

import java.io.Serializable;

/**
 * 业务对象实体类
 * Created by Went on 2017/8/1.
 */
public class BusinessObject extends BaseEntity<BusinessObject> implements Serializable{

    private String rowId;//唯一标识
    private String objectCode;//对象代码
    private String objectName;//对象名称
    private String relateTableRowId;//关联表

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
