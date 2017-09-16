package com.bcx.plat.core.morebatis.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.SpringContextHolder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ConditionBuilderContext {
  private TableSource tableSource;
  private Class<? extends BeanInterface> clz;
  private static final MoreBatis moreBatis=SpringContextHolder.getBean("moreBatis");
  private String defaultMapField;

  public ConditionBuilderContext(Class<? extends BeanInterface> clz,String defaultMapField) {
    this.clz = clz;
    tableSource = moreBatis.getTable(clz);
    this.defaultMapField=defaultMapField;
  }

  public String getDefaultMapField() {
    return defaultMapField;
  }

  public void setDefaultMapField(String defaultMapField) {
    this.defaultMapField = defaultMapField;
  }

  public TableSource getTableSource() {
    return tableSource;
  }

  public Class<? extends BeanInterface> getClz() {
    return clz;
  }

}