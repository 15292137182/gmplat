package com.bcx.plat.core.morebatis.phantom;

import java.util.LinkedList;

public interface TableSource<T extends TableSource<T>> {

  /**
   * 如果方法里面写了translator.translateCondition(this)
   * 意思是解析程序交给translator去处理
   * @param translator
   * @return
   */
  default LinkedList<Object> getTableSource(SqlComponentTranslator translator){
    return translator.translate((T)this);
  };
}