package com.bcx.plat.core.morebatis.phantom;

public interface Column extends FieldAlies, FieldSource {

  String getColumnSqlFragment();
}