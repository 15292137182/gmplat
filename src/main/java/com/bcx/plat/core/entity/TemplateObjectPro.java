package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * <p>Title: TemplateObjectPro</p>
 * <p>Description: 模板对象属性实体类</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/28  Wen TieHu Create
 *          </pre>
 */
@Table(TableInfo.T_TEMPLATE_OBJECT_PRO)
public class TemplateObjectPro extends BaseEntity<TemplateObjectPro>{

    @TablePK
    private String proRowId;//唯一标示
    private String templateObjRowId;//关联模板对象rowId
    private String code;//代码
    private String cname ;//中文名称
    private String ename ;//英文名称
    private String valueType;//值类型
    private String defaultValue;//默认值
    private String desp;//说明

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public TemplateObjectPro buildCreateInfo() {
        this.proRowId = UtilsTool.lengthUUID(32);
        this.code = UtilsTool.lengthUUID(5).toUpperCase();
        return super.buildCreateInfo();
    }

    public String getProRowId() {
        return proRowId;
    }

    public void setProRowId(String proRowId) {
        this.proRowId = proRowId;
    }

    public String getTemplateObjRowId() {
        return templateObjRowId;
    }

    public void setTemplateObjRowId(String templateObjRowId) {
        this.templateObjRowId = templateObjRowId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
