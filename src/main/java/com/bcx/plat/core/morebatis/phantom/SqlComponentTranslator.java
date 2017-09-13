package com.bcx.plat.core.morebatis.phantom;

import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;

import java.util.LinkedList;

public interface SqlComponentTranslator {
  public LinkedList translateQueryAction(QueryAction queryAction, LinkedList linkedList);

  public LinkedList translateInsertAction(InsertAction insertAction, LinkedList linkedList);

  public LinkedList translateDeleteAction(DeleteAction deleteAction, LinkedList linkedList);

  public LinkedList translateUpdateAction(UpdateAction updateAction, LinkedList linkedList);

  LinkedList translateTableSource(TableSource tableSource,LinkedList list);

  LinkedList translateFieldSource(FieldSource fieldSource, LinkedList list);
}