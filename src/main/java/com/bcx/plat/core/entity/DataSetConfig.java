package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * 数据集配置实体类
 * Created by Wen Tiehu on 2017/8/8.
 */
@Table(TableInfo.T_DATASET_CONFIG)
public class DataSetConfig extends BaseEntity<DataSetConfig> {
    @TablePK
    private String rowId;//唯一标示
    private String dataSetCode;//数据集代码
    private String dataSetName;//数据集名称
    private String dataSetTypee;//数据集类型
    private String dataSetContent;//数据集内容
    private String desp;//说明

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public DataSetConfig buildCreateInfo() {
        this.rowId = UtilsTool.lengthUUID(32);
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getDataSetCode() {
        return dataSetCode;
    }

    public void setDataSetCode(String dataSetCode) {
        this.dataSetCode = dataSetCode;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getDataSetTypee() {
        return dataSetTypee;
    }

    public void setDataSetTypee(String dataSetTypee) {
        this.dataSetTypee = dataSetTypee;
    }

    public String getDataSetContent() {
        return dataSetContent;
    }

    public void setDataSetContent(String dataSetContent) {
        this.dataSetContent = dataSetContent;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
