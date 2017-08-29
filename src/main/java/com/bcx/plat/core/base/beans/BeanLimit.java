package com.bcx.plat.core.base.beans;

import java.util.Map;

/**
 * 定义 bean 接口
 * <p>
 * Create By HCL at 2017/8/29
 */
public interface BeanLimit<T extends BeanLimit> {

  Map toMap();

  T fromMap(Map map);
}
