package com.bcx.plat.core.base;

/**
 * 平台常量类 Create By HCL at 2017/7/31.
 */
public class BaseConstants {

  public static final String DELETE_FLAG = "1";
  public static final String NOT_DELETE_FLAG = "0";
  public static final boolean LOGIC_DELETE = true;

  public static final int STATUS_SUCCESS = 1;
  public static final int STATUS_FAIL = -1;
  public static final int STATUS_WARNING = 2;

  /**
   * 业务对象状态
   **/
  //生效状态
  public static final String TAKE_EFFECT = "20";
  //失效状态
  public static final String INVALID = "40";
  //执行变更(默认状态为50 不可用)
  public static final String CHANGE_OPERAT_FAIL = "50";
  //执行变更(状态可用)
  public static final String CHANGE_OPERAT_SUCCESS = "20";


}
