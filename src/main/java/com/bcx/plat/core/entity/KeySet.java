package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.manager.SequenceManager;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.Random;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * Created by Went on 2017/8/3.
 */
@Table(TableInfo.T_KEYSET)
public class KeySet extends BaseEntity<KeySet>{
    @TablePK
    private String rowId;//唯一标示
    private String number;//编号
    private String keysetCode;//键值代码
    private String keysetName;//键值名称
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
        try {
            setKeysetCode(SequenceManager.getInstance().buildSequenceNo(UtilsTool.loadProperties("keySet"), null));
        } catch (Exception e) {
            this.keysetCode = "KS" + UtilsTool.getDateTimeNow("yyyyMMdd").toUpperCase();
        }
        setVersion(BaseConstants.VERSION);
        setRowId(lengthUUID(32));
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
