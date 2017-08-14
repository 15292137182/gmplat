package com.bcx.plat.core.morebatis.phantom;

import java.util.LinkedList;

/**
 * 接口里面总得有个方法吧
 * 我不能让你implement你就implement 首先你要override一下
 */
public interface TableSource {

  /**
   * 如果方法里面写了translator.translate(this)
   * 意思是解析程序交给translator去处理
   * @param translator
   * @return
   */
  LinkedList<Object> getTableSource(SqlComponentTranslator translator);
}