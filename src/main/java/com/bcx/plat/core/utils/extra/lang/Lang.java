package com.bcx.plat.core.utils.extra.lang;

/**
 * 提供了一些帮助，让 java 使用更加简单
 *
 * Create By HCL at 2017/8/10
 */
public abstract class Lang {

  /**
   * 抛出运行时异常
   *
   * @param format 格式化
   * @param args 参数
   * @return 返回运行时异常
   */
  public static RuntimeException makeThrow(String format, Object... args) {
    return new RuntimeException(String.format(format, args));
  }
}