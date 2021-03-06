package com.bcx.plat.core.database.info;

import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.HashMap;
import java.util.LinkedList;

public enum TableInfo implements TableSource<TableInfo> {
  T_SYS_CONFIG("t_sys_config"),
  T_SEQUENCE_RULE_CONFG("t_sequence_rule_config"),
  T_SEQUENCE_GENERATE("t_sequence_generate"),
  T_SEQ_BUSI_GENERATE("t_seq_busi_generate"),
  T_KEYSET("t_keyset"),
  T_KEYSET_PRO("t_keyset_pro"),
  T_FRONT_FUNC_PRO("t_front_func_pro"),
  T_FRONT_FUNC("t_front_func"),
  T_DB_TABLES("t_db_tables"),
  T_DB_TABLE_COLUMN("t_db_table_column"),
  T_DATASET_CONFIG("t_dataset_config"),
  T_BUSINESS_OBJECT_PRO("t_business_object_pro"),
  T_BUSINESS_OBJECT("t_business_object"),
  T_BUSINESS_RELATE_TEMPLATE("t_business_relate_template"),
  TEST("test_only.test_table1"),
  T_SEQUENCE_RULE_CONFIG("t_sequence_rule_config"),
  T_TEMPLATE_OBJECT("t_template_object"),
  T_TEMPLATE_OBJECT_PRO("t_template_object_pro"),
  T_SYS_USER("t_user");
  public final Table table;
  private final HashMap<String, TableSource> register = new HashMap<>();

  TableInfo(String tableName) {
    this.table = new Table(tableName);
    register.put(tableName, table);
  }

  public TableSource getTableSourceByName(String tableName) {
    return register.get(tableName);
  }

  TableInfo(Table table) {
    this.table = table;
  }

  @Override
  public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
    return table.getTableSource(translator);
  }
}