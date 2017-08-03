package com.bcx.plat.core.database;

/**
 * 使用本地线程控制数据库类型
 */
public class DBContextHolder {

  private static final ThreadLocal contextHolder = new ThreadLocal<>();

  /**
   * 设置数据源
   *
   * @param dbTypeEnum 设置数据源类型
   */
  public static void setDbType(DBTypeEnum dbTypeEnum) {
    contextHolder.set(dbTypeEnum.getValue());
  }

  /**
   * 取得当前数据源
   *
   * @return 返回数据源类型
   */
  public static Object getDbType() {
    return contextHolder.get();
  }

  /**
   * 清除上下文数据
   */
  public static void clearDbType() {
    contextHolder.remove();
  }
}
