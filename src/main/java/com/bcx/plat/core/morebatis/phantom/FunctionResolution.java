package com.bcx.plat.core.morebatis.phantom;

import com.bcx.plat.core.morebatis.component.function.Functions;
import java.util.LinkedList;

public interface FunctionResolution<T> {
  LinkedList resolve(Functions.SqlFunction function,LinkedList linkedList);
}
