package com.bcx.plat.core.morebatis.cctv1;

import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.LinkedList;

/**
 * 供单例模式使用的不可变表
 */
public class ImmuteTable implements TableSource {

  TableSource tableSource;

  public ImmuteTable(TableSource tableSource) {
    this.tableSource = tableSource;
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return tableSource.getTableSource(translator);
  }
}