package com.bcx.plat.core.morebatis.configuration;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.List;

public class EntityEntry {
  private Class<? extends BeanInterface> entityClass;
  private List<Field> pks;
  private List<Field> fields;
  private TableSource table;

  public EntityEntry(Class<? extends BeanInterface> entityClass, TableSource table, List<Field> fields, List<Field> pks) {
    this.entityClass = entityClass;
    this.fields = fields;
    this.table = table;
    this.pks=pks;
  }

  public Class<? extends BeanInterface> getEntityClass() {
    return entityClass;
  }

  public List<Field> getFields() {
    return fields;
  }

  public TableSource getTable() {
    return table;
  }

  public List<Field> getPks() {
    return pks;
  }
}
