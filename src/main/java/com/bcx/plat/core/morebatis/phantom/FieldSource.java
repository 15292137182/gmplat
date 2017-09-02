package com.bcx.plat.core.morebatis.phantom;

import com.bcx.plat.core.morebatis.translator.Translator;

import java.util.LinkedList;

public interface FieldSource<T> {
  String getFieldSource();
  String getColumnSqlFragment(SqlComponentTranslator translator);
  default LinkedList getFieldSource(Translator translator){
    return null;
  };
}
