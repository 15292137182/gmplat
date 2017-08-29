package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.utils.UtilsTool;

/**
 * <p>Title: TemplateObject</p>
 * <p>Description: 模板对象实体类</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/28  Wen TieHu Create
 *          </pre>
 */
@Table(TableInfo.T_TEMPLATE_OBJECT)
public class TemplateObject extends BaseEntity<TemplateObject>{

    @TablePK
    private String rowId;//唯一标示
    private String templateCode;//模板代码
    private String templateName;//模板名称
    private String desp;//说明
    private String belongModule;//所属模块
    private String belongSystem;//所属系统

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public TemplateObject buildCreateInfo() {
        this.rowId = UtilsTool.lengthUUID(32);
        this.templateCode = UtilsTool.lengthUUID(5).toUpperCase();
        return super.buildCreateInfo();
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
