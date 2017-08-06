package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;

import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * Created by Went on 2017/8/4.
 */
@Table(TableInfo.T_FRONT_FUNC_PRO)
public class FrontFuncPro extends BaseEntity{
    @TablePK
    private String rowId;//唯一标识
    private String funcRowId;//功能块唯一标识
    private String relateBusiPro;//关联业务对象属性
    private String displayTitle;//显示标题
    private String wetherDisplay;//是否显示
    private String displayWidget;//显示控件
    private String wetherReadonly;//只读
    private String allowEmpty;//允许为空
    private String lengthInterval;//长度区间
    private String validateFunc;//验证函数
    private String displayFunc;//显示函数
    private String sort;//排序

    /**
     * 构建 - 创建信息
     *
     * @return 返回自身
     */
    @Override
    public BaseEntity buildCreateInfo() {
        setRowId(lengthUUID(32));
        return super.buildCreateInfo();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getFuncRowId() {
        return funcRowId;
    }

    public void setFuncRowId(String funcRowId) {
        this.funcRowId = funcRowId;
    }

    public String getRelateBusiPro() {
        return relateBusiPro;
    }

    public void setRelateBusiPro(String relateBusiPro) {
        this.relateBusiPro = relateBusiPro;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getWetherDisplay() {
        return wetherDisplay;
    }

    public void setWetherDisplay(String wetherDisplay) {
        this.wetherDisplay = wetherDisplay;
    }

    public String getDisplayWidget() {
        return displayWidget;
    }

    public void setDisplayWidget(String displayWidget) {
        this.displayWidget = displayWidget;
    }

    public String getWetherReadonly() {
        return wetherReadonly;
    }

    public void setWetherReadonly(String wetherReadonly) {
        this.wetherReadonly = wetherReadonly;
    }

    public String getAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(String allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public String getLengthInterval() {
        return lengthInterval;
    }

    public void setLengthInterval(String lengthInterval) {
        this.lengthInterval = lengthInterval;
    }

    public String getValidateFunc() {
        return validateFunc;
    }

    public void setValidateFunc(String validateFunc) {
        this.validateFunc = validateFunc;
    }

    public String getDisplayFunc() {
        return displayFunc;
    }

    public void setDisplayFunc(String displayFunc) {
        this.displayFunc = displayFunc;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
