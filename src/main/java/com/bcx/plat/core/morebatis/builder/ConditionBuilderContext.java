package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;

public class ConditionBuilderContext {

  private TableSource tableSource;
  private Class<? extends BeanInterface> clz;
  private static final MoreBatis moreBatis = SpringContextHolder.getBean("moreBatis");

  public ConditionBuilderContext(Class<? extends BeanInterface> clz) {
    this.clz = clz;
    tableSource = moreBatis.getTable(clz);
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public Class<? extends BeanInterface> getClz() {
    return clz;
  }

}