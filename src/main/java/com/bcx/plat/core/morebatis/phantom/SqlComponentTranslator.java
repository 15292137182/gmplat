package com.bcx.plat.core.morebatis.phantom;

import java.util.LinkedList;

public interface SqlComponentTranslator {
  public LinkedList<Object> translate(Condition condition);
  public LinkedList<Object> translate(Condition condition,LinkedList<Object> list);
  public LinkedList<Object> translate(TableSource tableSource);
  public LinkedList<Object> translate(TableSource tableSource,LinkedList<Object> list);
}