package com.bcx.plat.core.morebatis.configuration;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.List;

public class EntityEntry {
  private Class<? extends BaseEntity> entityClass;
  private List<Column> pks;
  private List<Column> fields;
  private TableSource table;

  public EntityEntry(Class<? extends BaseEntity> entityClass,TableSource table,List<Column> fields,List<Column> pks) {
    this.entityClass = entityClass;
    this.fields = fields;
    this.table = table;
    this.pks=pks;
  }

  public Class<? extends BaseEntity> getEntityClass() {
    return entityClass;
  }

  public List<Column> getFields() {
    return fields;
  }

  public TableSource getTable() {
    return table;
  }

  public List<Column> getPks() {
    return pks;
  }
}
