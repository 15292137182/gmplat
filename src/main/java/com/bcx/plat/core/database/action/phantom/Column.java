package com.bcx.plat.core.database.action.phantom;

public interface Column extends FieldAlies, FieldSource {

  String getColumnSqlFragment();
}