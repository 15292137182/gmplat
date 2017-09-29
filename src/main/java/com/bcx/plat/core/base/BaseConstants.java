package com.bcx.plat.core.base;

/**
 * 平台常量类 Create By HCL at 2017/7/31.
 */
public class BaseConstants {

  public static final String TRUE_FLAG = "1";
  public static final String DELETE_FLAG = TRUE_FLAG;
  public static final String NOT_DELETE_FLAG = "0";
  public static final boolean LOGIC_DELETE = true;

  public static final int STATUS_SUCCESS = 1;
  public static final int STATUS_FAIL = -1;
  public static final int STATUS_WARNING = 2;

  public static final String VERSION = "1.0";//版本号
  public static final String TAKE_EFFECT = "20";    //生效状态
  public static final String INVALID = "40";    //失效状态
  public static final String UNUSED = "0";//新增数据未使用

  public static final String CHANGE_OPERAT_FAIL = "50";   //执行变更(默认状态为50 不可用)
  public static final String CHANGE_OPERAT_SUCCESS = "20";    //执行变更(状态可用)

  public static final String PAGE_NUM = "1";  //分页默认选择第一页
  public static final String PAGE_SIZE = "10"; //分页信息默认一页显示十条

  public static final String ATTRIBUTE_SOURCE_BASE = "base";//基本属性
  public static final String ATTRIBUTE_SOURCE_EXTEND = "extend";//扩展属性
  public static final String ATTRIBUTE_SOURCE_MODULE = "module";//模块属性


  public static final String SEQUENCE_RULE = "sequenceRule";//序列查询
  public static final String DATA_SET = "dataSet";//数据集
  public static final String KEY_SET = "keySet";//键值集合


}
