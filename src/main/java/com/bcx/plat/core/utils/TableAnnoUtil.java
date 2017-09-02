package com.bcx.plat.core.utils;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.annotations.Table;
import com.bcx.plat.core.morebatis.annotations.TablePK;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public class TableAnnoUtil {

  private static final HashMap<Class, List<String>> pkRegister = new HashMap<>();
  private static final HashMap<Class, TableSource> tableSourceRegister = new HashMap<>();
  private static final HashMap<Class, Map<String,AliasedColumn>> fieldInTableRegister = new HashMap<>();

  /**
   * 禁止以 new 的方式构造该函数
   */
  private TableAnnoUtil() {

  }

  /**
   * 获取带有 TablePk 注解的字段
   *
   * @param clazz clazz
   * @return 返回集合
   */
  public static List<String> getPkAnnoField(Class<? extends BaseEntity> clazz) {
    List<String> result = pkRegister.get(clazz);
    if (result == null) {
      result = UtilsTool.getAnnoFieldName(clazz, TablePK.class);
      pkRegister.put(clazz, result);
      return result;
    }
    return result;
  }

  public static TableSource getTableSource(Class<? extends BaseEntity> clazz) {
    TableSource result = tableSourceRegister.get(clazz);
    if (result == null) {
      Table annotation = clazz.getAnnotation(Table.class);
      if (annotation==null) throw new NullPointerException("检查一下实体类是不是没有加@Table注解");
      result = annotation.value();
      tableSourceRegister.put(clazz, result);
    }
    return result;
  }
}
