package com.bcx.plat.core.validate;

import com.bcx.plat.core.entity.FrontFunc;

/**
 * 校验工具类，主要负责对功能块的条件进行校验
 * <p>
 * Create By HCL at 2017/9/25
 */
public abstract class ValidateUtils {

  /**
   * 获取功能块信息,如果查询不到是会返回 null
   *
   * @param rowId 功能块的 rowId
   * @return 返回功能块
   */
  public static FrontFunc getFunctionBlock(String rowId) {
    return new FrontFunc().selectOneById(rowId);
  }

}
