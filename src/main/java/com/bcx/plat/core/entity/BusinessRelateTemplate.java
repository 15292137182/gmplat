package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/30  Wen TieHu Create
 *          </pre>
 */
@Table(TableInfo.T_BUSINESS_RELATE_TEMPLATE)
public class BusinessRelateTemplate extends BaseEntity<BusinessRelateTemplate>{
    @TablePK
    private String rowId;//唯一标示
    private String businessRowId;//关联业务对象
    private String templateRowId;//关联模板对象

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public BusinessRelateTemplate buildCreateInfo() {
        this.rowId = UtilsTool.lengthUUID(32);
        return this;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getBusinessRowId() {
        return businessRowId;
    }

    public void setBusinessRowId(String businessRowId) {
        this.businessRowId = businessRowId;
    }

    public String getTemplateRowId() {
        return templateRowId;
    }

    public void setTemplateRowId(String templateRowId) {
        this.templateRowId = templateRowId;
    }
}
