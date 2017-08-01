package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.action.annotations.Table;
import com.bcx.plat.core.database.action.annotations.TablePK;
import com.bcx.plat.core.database.info.TableInfo;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * 数据库表信息
 * Create By HCL at 2017/7/31
 */
@Table(TableInfo.T_DB_TABLE_COLUMN)
public class DBTableColumn extends BaseEntity<DBTableColumn> {
    private String relateTableRowId;
    private String columnEname;
    private String columnCname;
    private String desp;
    @TablePK
    private String rowId;

    /**
     * 重载构建 - 创建信息方法，加入rowId
     *
     * @return
     */
    @Override
    public DBTableColumn buildCreateInfo() {
        setRowId(lengthUUID(32));
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRelateTableRowId() {
        return relateTableRowId;
    }

    public void setRelateTableRowId(String relateTableRowId) {
        this.relateTableRowId = relateTableRowId;
    }

    public String getColumnEname() {
        return columnEname;
    }

    public void setColumnEname(String columnEname) {
        this.columnEname = columnEname;
    }

    public String getColumnCname() {
        return columnCname;
    }

    public void setColumnCname(String columnCname) {
        this.columnCname = columnCname;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}