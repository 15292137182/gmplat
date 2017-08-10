package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * 系统资源配置实体类
 * Created by Wen Tiehu on 2017/8/7.
 */
@Table(TableInfo.T_SYS_CONFIG)
public class SysConfig extends BaseEntity<SysConfig>{

    @TablePK
    private String rowId;//唯一标示
    private String confKey;//键
    private String confValue;//值
    private String desp;//说明


    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public SysConfig buildCreateInfo() {
        setRowId(UtilsTool.lengthUUID(32));
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
