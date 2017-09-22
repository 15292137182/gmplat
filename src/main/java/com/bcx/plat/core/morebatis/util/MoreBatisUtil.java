package com.bcx.plat.core.morebatis.util;

import com.bcx.plat.core.morebatis.cctv1.SqlSegment;
import java.util.LinkedList;

/**
 * MoreBatis 内部工具类
 */
public class MoreBatisUtil {

  /**
   * 向list中添加sql片段
   */
  public static LinkedList<Object> addSqlSegmentToList(String sql, LinkedList<Object> list) {
    list.add(new SqlSegment(sql));
    return list;
  }

  /**
   * 向list中添加参数
   */
  public static LinkedList<Object> addSqlParameterToList(Object parameter,
      LinkedList<Object> list) {
    list.add(parameter);
    return list;
  }
}
