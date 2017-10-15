package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;

public class ConditionBuilderContext {

  private TableSource tableSource;
  private Class<? extends BeanInterface> clz;
  private static MoreBatis moreBatis;

  public ConditionBuilderContext(Class<? extends BeanInterface> clz) {
    this.clz = clz;
    tableSource = getMoreBatis().getTable(clz);
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  private MoreBatis getMoreBatis(){
    if (moreBatis==null) {
      moreBatis = SpringContextHolder.getBean("moreBatis");
    }
    return moreBatis;
  }

  public Class<? extends BeanInterface> getClz() {
    return clz;
  }

}