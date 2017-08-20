package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.Random;

/**
 * 数据集配置实体类
 * Created by Wen Tiehu on 2017/8/8.
 */
@Table(TableInfo.T_DATASET_CONFIG)
public class DataSetConfig extends BaseEntity<DataSetConfig> {
    @TablePK
    private String rowId;//唯一标示
    private String datasetCode;//数据集代码
    private String datasetName;//数据集名称
    private String datasetType;//数据集类型
    private String datasetContent;//数据集内容
    private String desp;//说明

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public DataSetConfig buildCreateInfo() {
        try {
            this.setDatasetCode(SequenceManager.getInstance().buildSequenceNo(UtilsTool.loadProperties("dataSet"), null));
        } catch (Exception e) {
            this.setDatasetCode("DATAS" +UtilsTool.getDateTimeNow("yyyyMMdd")+ UtilsTool.lengthUUID(5).toUpperCase());
        }
        this.setVersion("1.0");
        this.rowId = UtilsTool.lengthUUID(32);
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getDatasetCode() {
        return datasetCode;
    }

    public void setDatasetCode(String datasetCode) {
        this.datasetCode = datasetCode;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public String getDatasetContent() {
        return datasetContent;
    }

    public void setDatasetContent(String datasetContent) {
        this.datasetContent = datasetContent;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
