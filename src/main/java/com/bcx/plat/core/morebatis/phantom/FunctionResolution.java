package com.bcx.plat.core.morebatis.phantom;
import com.bcx.plat.core.morebatis.component.function.SqlFunction;
import java.util.LinkedList;

public interface FunctionResolution<T> {
  LinkedList resolve(SqlFunction function,LinkedList linkedList);
}
