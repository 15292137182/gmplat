package com.bcx.plat.core.morebatis.singleton;

import com.bcx.plat.core.morebatis.phantom.TableSource;

/**
 * 供单例模式使用的不可变表
 */
public class ImmuteTable implements TableSource {

  TableSource tableSource;

  @Override
  public String getTableSourceSqlFragment() {
    return tableSource.getTableSourceSqlFragment();
  }
}