package com.bcx.plat.core.entity;

import com.bcx.plat.core.base.BaseEntity;

/**
 * 功能块明细实体类
 * Created by Went on 2017/8/4.
 */
public class FrontFuncPro extends BaseEntity<FrontFuncPro> {

  private String attrSource = "";//添加属性来源
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
  private Integer sort;//排序
  private String widthSetting;//宽度设置
  private String align;//对齐方式
  private String exactQuery;//是否精确查询
  private String supportSort;//支持排序
  private String keywordOne;//关键字一
  private String keywordTwo;//关键字二
  private String keywordThree;//关键字三
  private String customField;//自定义字段

  /**
   * 构建 - 创建信息
   *
   * @return 返回自身
   */
  @Override
  public FrontFuncPro buildCreateInfo() {
    return super.buildCreateInfo();
  }

  public String getAttrSource() {
    return attrSource;
  }

  public void setAttrSource(String attrSource) {
    this.attrSource = attrSource;
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

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getWidthSetting() {
    return widthSetting;
  }

  public void setWidthSetting(String widthSetting) {
    this.widthSetting = widthSetting;
  }

  public String getAlign() {
    return align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public String getExactQuery() {
    return exactQuery;
  }

  public void setExactQuery(String exactQuery) {
    this.exactQuery = exactQuery;
  }

  public String getSupportSort() {
    return supportSort;
  }

  public void setSupportSort(String supportSort) {
    this.supportSort = supportSort;
  }

  public String getKeywordOne() {
    return keywordOne;
  }

  public void setKeywordOne(String keywordOne) {
    this.keywordOne = keywordOne;
  }

  public String getKeywordTwo() {
    return keywordTwo;
  }

  public void setKeywordTwo(String keywordTwo) {
    this.keywordTwo = keywordTwo;
  }

  public String getKeywordThree() {
    return keywordThree;
  }

  public void setKeywordThree(String keywordThree) {
    this.keywordThree = keywordThree;
  }

  public String getCustomField() {
    return customField;
  }

  public void setCustomField(String customField) {
    this.customField = customField;
  }
}
