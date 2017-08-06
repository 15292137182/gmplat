package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * Created by Went on 2017/8/3.
 */
@Table(TableInfo.T_KEYSET)
public class KeySet extends BaseEntity<KeySet>{
    @TablePK
    private String rowId;//唯一标示
    private String keySetCode;//键值代码
    private String keySetName;//键值名称
    private String confKey;//键
    private String confValue;//值
    private String desp;//说明

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public KeySet buildCreateInfo() {
        setRowId(lengthUUID(32));
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getKeySetCode() {
        return keySetCode;
    }

    public void setKeySetCode(String keySetCode) {
        this.keySetCode = keySetCode;
    }

    public String getKeySetName() {
        return keySetName;
    }

    public void setKeySetName(String keySetName) {
        this.keySetName = keySetName;
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
