package com.bcx.plat.core.morebatis.phantom;

public interface AliasedColumn extends Alias, FieldSource {
  String getColumnSqlFragment(SqlComponentTranslator translator);
}