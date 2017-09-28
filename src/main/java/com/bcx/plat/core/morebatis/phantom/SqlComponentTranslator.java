package com.bcx.plat.core.morebatis.phantom;

import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;

import java.util.LinkedList;

public interface SqlComponentTranslator {

  LinkedList translateQueryAction(QueryAction queryAction, LinkedList linkedList);

  LinkedList translateInsertAction(InsertAction insertAction, LinkedList linkedList);

  LinkedList translateDeleteAction(DeleteAction deleteAction, LinkedList linkedList);

  LinkedList translateUpdateAction(UpdateAction updateAction, LinkedList linkedList);

  LinkedList translateTableSource(TableSource tableSource, LinkedList list);

  LinkedList translateFieldSource(FieldSource fieldSource, LinkedList list);
}