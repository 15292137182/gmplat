package com.bcx.plat.core.database.info;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.cctv1.ImmuteFieldInTable;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.FieldInTable;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.TableAnnoUtil;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Fields {

  private static final HashMap<TableSource, Map<String, Column>> tableFieldMap = new HashMap<>();
  private static final HashMap<Class<? extends BaseEntity>, Map<String, Column>> entityFieldMap = new HashMap<>();

  private static void putField(TableSource tableSource, Column column, String alies) {
    Map<String, Column> map = tableFieldMap.get(tableSource);
    if (map == null) {
      map = new HashMap<>();
      tableFieldMap.put(tableSource, map);
    }
    map.put(alies, column);
  }

  public static Column getField(TableSource tableSource, String alies) {
    return tableFieldMap.get(tableSource).get(alies);
  }

  public static Column getField(Class<? extends BaseEntity> entityClass, String alies) {
    return getFieldMap(entityClass).get(alies);
  }

  public static Map<String, Column> getFieldMap(Class<? extends BaseEntity> entityClass) {
    final HashMap<Class<? extends BaseEntity>, Map<String, Column>> fieldMap = getFieldMap();
    Map<String, Column> columnMap = fieldMap.get(entityClass);
    if (columnMap == null) {
      final TableSource tableSource = TableAnnoUtil.getTableSource(entityClass);
      columnMap = fieldMap.get(tableSource);
      fieldMap.put(entityClass, columnMap);
    }
    return columnMap;
  }

  private static HashMap<Class<? extends BaseEntity>, Map<String, Column>> getFieldMap(){
    if (entityFieldMap.size()==0) {
      for (Class<?> aClass : Fields.class.getDeclaredClasses()) {
        for (java.lang.reflect.Field field : aClass.getDeclaredFields()) {
          final int modifiers = field.getModifiers();
          if (Modifier.isStatic(modifiers)&&Modifier.isPublic(modifiers)) {
            Object value = null;
            try {
              value = field.get(aClass);
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
            value.hashCode();
          }
        }
      }
    }
    return entityFieldMap;
  }

  public static void initRegister() throws IllegalAccessException {
    for (Class<?> aClass : Fields.class.getDeclaredClasses()) {
      for (java.lang.reflect.Field field : aClass.getDeclaredFields()) {
        final int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers)&&Modifier.isPublic(modifiers)) {
          FieldInTable value = (FieldInTable)field.get(aClass);
//          value.
        }
      }
    }

  }

  public static Collection<Column> getFields(Class<? extends BaseEntity> entityClass) {
    return getFieldMap(entityClass).values();
  }

  public enum T_BUSINESS_OBJECT implements FieldInTable {
    ROW_ID(TableInfo.T_BUSINESS_OBJECT, "row_id", "rowId"),
    OBJECT_CODE(TableInfo.T_BUSINESS_OBJECT, "object_code", "objectCode"),
    OBJECT_NAME(TableInfo.T_BUSINESS_OBJECT, "object_name", "objectName"),
    RELATE_TABLE_ROW_ID(TableInfo.T_BUSINESS_OBJECT, "relate_table_row_id", "relateTableRowId"),
    CHANGE_OPERAT(TableInfo.T_BUSINESS_OBJECT, "change_operat", "changeOperat"),
    STATUS(TableInfo.T_BUSINESS_OBJECT, "status", "status"),
    VERSION(TableInfo.T_BUSINESS_OBJECT, "version", "version"),
    CREATE_TIME(TableInfo.T_BUSINESS_OBJECT, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_BUSINESS_OBJECT, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_BUSINESS_OBJECT, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_BUSINESS_OBJECT, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_BUSINESS_OBJECT, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_BUSINESS_OBJECT, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_BUSINESS_OBJECT, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_BUSINESS_OBJECT, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_BUSINESS_OBJECT, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_BUSINESS_OBJECT, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_BUSINESS_OBJECT(TableInfo tableSource, String fieldName, String alies) {
      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }

  public enum T_SYS_CONFIG implements FieldInTable {
    ROW_ID(TableInfo.T_SYS_CONFIG, "row_id", "rowId"),
    CONF_KEY(TableInfo.T_SYS_CONFIG, "conf_key", "confKey"),
    CONF_VALUE(TableInfo.T_SYS_CONFIG, "conf_value", "confValue"),
    DESP(TableInfo.T_SYS_CONFIG, "desp", "desp"),
    STATUS(TableInfo.T_SYS_CONFIG, "status", "status"),
    VERSION(TableInfo.T_SYS_CONFIG, "version", "version"),
    CREATE_TIME(TableInfo.T_SYS_CONFIG, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_SYS_CONFIG, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_SYS_CONFIG, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_SYS_CONFIG, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_SYS_CONFIG, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_SYS_CONFIG, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_SYS_CONFIG, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_SYS_CONFIG, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_SYS_CONFIG, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_SYS_CONFIG, "delete_flag", "deleteFlag");
    public final ImmuteFieldInTable field;

    T_SYS_CONFIG(TableInfo tableSource, String fieldName, String alies) {
      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }


    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return field.getTableSource(translator);
    }
  }


  public enum T_SEQUENCE_RULE_CONFIG implements FieldInTable {
    ROW_ID(TableInfo.T_SEQUENCE_RULE_CONFIG, "row_id", "rowId"),
    SEQ_CODE(TableInfo.T_SEQUENCE_RULE_CONFIG, "seq_code", "seqCode"),
    SEQ_NAME(TableInfo.T_SEQUENCE_RULE_CONFIG, "seq_name", "seqName"),
    SEQ_CONTENT(TableInfo.T_SEQUENCE_RULE_CONFIG, "seq_content", "seqContent"),
    DESP(TableInfo.T_SEQUENCE_RULE_CONFIG, "desp", "desp"),
    STATUS(TableInfo.T_SEQUENCE_RULE_CONFIG, "status", "status"),
    VERSION(TableInfo.T_SEQUENCE_RULE_CONFIG, "version", "version"),
    CREATE_TIME(TableInfo.T_SEQUENCE_RULE_CONFIG, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_SEQUENCE_RULE_CONFIG, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_SEQUENCE_RULE_CONFIG, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_SEQUENCE_RULE_CONFIG, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_SEQUENCE_RULE_CONFIG, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_SEQUENCE_RULE_CONFIG, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_SEQUENCE_RULE_CONFIG, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_SEQUENCE_RULE_CONFIG, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_SEQUENCE_RULE_CONFIG, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_SEQUENCE_RULE_CONFIG, "delete_flag", "deleteFlag");
    public final ImmuteFieldInTable field;

    T_SEQUENCE_RULE_CONFIG(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_SEQUENCE_GENERATE implements FieldInTable {
    ROW_ID(TableInfo.T_SEQUENCE_GENERATE, "row_id", "rowId"),
    SEQ_ROW_ID(TableInfo.T_SEQUENCE_GENERATE, "seq_row_id", "seqRowId"),
    VARIABLE_KEY(TableInfo.T_SEQUENCE_GENERATE, "variable_key", "variableKey"),
    CURRENT_VALUE(TableInfo.T_SEQUENCE_GENERATE, "current_value", "currentValue"),
    STATUS(TableInfo.T_SEQUENCE_GENERATE, "status", "status"),
    VERSION(TableInfo.T_SEQUENCE_GENERATE, "version", "version"),
    CREATE_TIME(TableInfo.T_SEQUENCE_GENERATE, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_SEQUENCE_GENERATE, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_SEQUENCE_GENERATE, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_SEQUENCE_GENERATE, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_SEQUENCE_GENERATE, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_SEQUENCE_GENERATE, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_SEQUENCE_GENERATE, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_SEQUENCE_GENERATE, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_SEQUENCE_GENERATE, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_SEQUENCE_GENERATE, "delete_flag", "deleteFlag"),
    BRANCH_SIGN(TableInfo.T_SEQUENCE_GENERATE, "branch_sign", "branchSign");

    public final ImmuteFieldInTable field;

    T_SEQUENCE_GENERATE(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }

//没有使用
  public enum T_SEQ_BUSI_GENERATE implements FieldInTable {
    ROW_ID(TableInfo.T_SEQ_BUSI_GENERATE, "row_id", "rowId"),
    SEQ_ROW_ID(TableInfo.T_SEQ_BUSI_GENERATE, "seq_row_id", "seqRowId"),
    BUSI_VARIABLE_KEY(TableInfo.T_SEQ_BUSI_GENERATE, "busi_variable_key", "busiVariableKey"),
    CURRENT_VALUE(TableInfo.T_SEQ_BUSI_GENERATE, "current_value", "currentValue"),
    STATUS(TableInfo.T_SEQ_BUSI_GENERATE, "status", "status"),
    VERSION(TableInfo.T_SEQ_BUSI_GENERATE, "version", "version"),
    CREATE_TIME(TableInfo.T_SEQ_BUSI_GENERATE, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_SEQ_BUSI_GENERATE, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_SEQ_BUSI_GENERATE, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_SEQ_BUSI_GENERATE, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_SEQ_BUSI_GENERATE, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_SEQ_BUSI_GENERATE, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_SEQ_BUSI_GENERATE, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_SEQ_BUSI_GENERATE, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_SEQ_BUSI_GENERATE, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_SEQ_BUSI_GENERATE, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_SEQ_BUSI_GENERATE(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_KEYSET implements FieldInTable {
    ROW_ID(TableInfo.T_KEYSET, "row_id", "rowId"),
    NUMBER(TableInfo.T_KEYSET, "number", "number"),
    KEYSET_CODE(TableInfo.T_KEYSET, "keyset_code", "keysetCode"),
    KEYSET_NAME(TableInfo.T_KEYSET, "keyset_name", "keysetName"),
    CONF_KEY(TableInfo.T_KEYSET, "conf_key", "confKey"),
    CONF_VALUE(TableInfo.T_KEYSET, "conf_value", "confValue"),
    DESP(TableInfo.T_KEYSET, "desp", "desp"),
    STATUS(TableInfo.T_KEYSET, "status", "status"),
    VERSION(TableInfo.T_KEYSET, "version", "version"),
    CREATE_TIME(TableInfo.T_KEYSET, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_KEYSET, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_KEYSET, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_KEYSET, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_KEYSET, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_KEYSET, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_KEYSET, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_KEYSET, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_KEYSET, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_KEYSET, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_KEYSET(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_FRONT_FUNC_PRO implements FieldInTable {
    ROW_ID(TableInfo.T_FRONT_FUNC_PRO, "row_id", "rowId"),
    FUNC_ROW_ID(TableInfo.T_FRONT_FUNC_PRO, "func_row_id", "funcRowId"),
    RELATE_BUSI_PRO(TableInfo.T_FRONT_FUNC_PRO, "relate_busi_pro", "relateBusiPro"),
    DISPLAY_TITLE(TableInfo.T_FRONT_FUNC_PRO, "display_title", "displayTitle"),
    WETHER_DISPLAY(TableInfo.T_FRONT_FUNC_PRO, "wether_display", "wetherDisplay"),
    DISPLAY_WIDGET(TableInfo.T_FRONT_FUNC_PRO, "display_widget", "displayWidget"),
    WETHER_READONLY(TableInfo.T_FRONT_FUNC_PRO, "wether_readonly", "wetherReadonly"),
    ALLOW_EMPTY(TableInfo.T_FRONT_FUNC_PRO, "allow_empty", "allowEmpty"),
    LENGTH_INTERVAL(TableInfo.T_FRONT_FUNC_PRO, "length_interval", "lengthInterval"),
    VALIDATE_FUNC(TableInfo.T_FRONT_FUNC_PRO, "validate_func", "validateFunc"),
    DISPLAY_FUNC(TableInfo.T_FRONT_FUNC_PRO, "display_func", "displayFunc"),
    SORT(TableInfo.T_FRONT_FUNC_PRO, "sort", "sort"),
    STATUS(TableInfo.T_FRONT_FUNC_PRO, "status", "status"),
    VERSION(TableInfo.T_FRONT_FUNC_PRO, "version", "version"),
    CREATE_TIME(TableInfo.T_FRONT_FUNC_PRO, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_FRONT_FUNC_PRO, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_FRONT_FUNC_PRO, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_FRONT_FUNC_PRO, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_FRONT_FUNC_PRO, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_FRONT_FUNC_PRO, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_FRONT_FUNC_PRO, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_FRONT_FUNC_PRO, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_FRONT_FUNC_PRO, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_FRONT_FUNC_PRO, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_FRONT_FUNC_PRO(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_FRONT_FUNC implements FieldInTable {
    ROW_ID(TableInfo.T_FRONT_FUNC, "row_id", "rowId"),
    FUNC_CODE(TableInfo.T_FRONT_FUNC, "func_code", "funcCode"),
    FUNC_NAME(TableInfo.T_FRONT_FUNC, "func_name", "funcName"),
    FUNC_TYPE(TableInfo.T_FRONT_FUNC, "func_type", "funcType"),
    RELATE_BUSI_OBJ(TableInfo.T_FRONT_FUNC, "relate_busi_obj", "relateBusiObj"),
    WETHER_AVAILABLE(TableInfo.T_FRONT_FUNC, "wether_available", "wetherAvailable"),
    DESP(TableInfo.T_FRONT_FUNC, "desp", "desp"),
    STATUS(TableInfo.T_FRONT_FUNC, "status", "status"),
    VERSION(TableInfo.T_FRONT_FUNC, "version", "version"),
    CREATE_TIME(TableInfo.T_FRONT_FUNC, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_FRONT_FUNC, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_FRONT_FUNC, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_FRONT_FUNC, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_FRONT_FUNC, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_FRONT_FUNC, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_FRONT_FUNC, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_FRONT_FUNC, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_FRONT_FUNC, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_FRONT_FUNC, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_FRONT_FUNC(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_DB_TABLES implements FieldInTable {
    ROW_ID(TableInfo.T_DB_TABLES, "row_id", "rowId"),
    TABLE_SCHEMA(TableInfo.T_DB_TABLES, "table_schema", "tableSchema"),
    TABLE_CNAME(TableInfo.T_DB_TABLES, "table_cname", "tableCname"),
    TABLE_ENAME(TableInfo.T_DB_TABLES, "table_ename", "tableEname"),
    DESP(TableInfo.T_DB_TABLES, "desp", "desp"),
    STATUS(TableInfo.T_DB_TABLES, "status", "status"),
    VERSION(TableInfo.T_DB_TABLES, "version", "version"),
    CREATE_TIME(TableInfo.T_DB_TABLES, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_DB_TABLES, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_DB_TABLES, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_DB_TABLES, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_DB_TABLES, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_DB_TABLES, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_DB_TABLES, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_DB_TABLES, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_DB_TABLES, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_DB_TABLES, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_DB_TABLES(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_DB_TABLE_COLUMN implements FieldInTable {
    ROW_ID(TableInfo.T_DB_TABLE_COLUMN, "row_id", "rowId"),
    RELATE_TABLE_ROW_ID(TableInfo.T_DB_TABLE_COLUMN, "relate_table_row_id", "relateTableRowId"),
    COLUMN_ENAME(TableInfo.T_DB_TABLE_COLUMN, "column_ename", "columnEname"),
    COLUMN_CNAME(TableInfo.T_DB_TABLE_COLUMN, "column_cname", "columnCname"),
    DESP(TableInfo.T_DB_TABLE_COLUMN, "desp", "desp"),
    STATUS(TableInfo.T_DB_TABLE_COLUMN, "status", "status"),
    VERSION(TableInfo.T_DB_TABLE_COLUMN, "version", "version"),
    CREATE_TIME(TableInfo.T_DB_TABLE_COLUMN, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_DB_TABLE_COLUMN, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_DB_TABLE_COLUMN, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_DB_TABLE_COLUMN, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_DB_TABLE_COLUMN, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_DB_TABLE_COLUMN, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_DB_TABLE_COLUMN, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_DB_TABLE_COLUMN, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_DB_TABLE_COLUMN, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_DB_TABLE_COLUMN, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_DB_TABLE_COLUMN(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_DATASET_CONFIG implements FieldInTable {
    ROW_ID(TableInfo.T_DATASET_CONFIG, "row_id", "rowId"),
    DATASET_CODE(TableInfo.T_DATASET_CONFIG, "dataset_code", "datasetCode"),
    DATASET_NAME(TableInfo.T_DATASET_CONFIG, "dataset_name", "datasetName"),
    DATASET_TYPE(TableInfo.T_DATASET_CONFIG, "dataset_type", "datasetType"),
    DATASET_CONTENT(TableInfo.T_DATASET_CONFIG, "dataset_content", "datasetContent"),
    DESP(TableInfo.T_DATASET_CONFIG, "desp", "desp"),
    STATUS(TableInfo.T_DATASET_CONFIG, "status", "status"),
    VERSION(TableInfo.T_DATASET_CONFIG, "version", "version"),
    CREATE_TIME(TableInfo.T_DATASET_CONFIG, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_DATASET_CONFIG, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_DATASET_CONFIG, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_DATASET_CONFIG, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_DATASET_CONFIG, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_DATASET_CONFIG, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_DATASET_CONFIG, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_DATASET_CONFIG, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_DATASET_CONFIG, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_DATASET_CONFIG, "delete_flag", "deleteFlag");

    public final ImmuteFieldInTable field;

    T_DATASET_CONFIG(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }


  public enum T_BUSINESS_OBJECT_PRO implements FieldInTable {
    ROW_ID(TableInfo.T_BUSINESS_OBJECT_PRO, "row_id", "rowId"),
    PROPERTY_CODE(TableInfo.T_BUSINESS_OBJECT_PRO, "property_code", "propertyCode"),
    PROPERTY_NAME(TableInfo.T_BUSINESS_OBJECT_PRO, "property_name", "propertyName"),
    RELATE_TABLE_COLUMN(TableInfo.T_BUSINESS_OBJECT_PRO, "relate_table_column","relateTableColumn"),
    VALUE_TYPE(TableInfo.T_BUSINESS_OBJECT_PRO, "value_type", "valueType"),
    VALUE_RESOURCE_TYPE(TableInfo.T_BUSINESS_OBJECT_PRO, "value_resource_type","valueResourceType"),
    VALUE_RESOURCE_CONTENT(TableInfo.T_BUSINESS_OBJECT_PRO, "value_resource_content","valueResourceContent"),
    WETHER_EXPAND_PRO(TableInfo.T_BUSINESS_OBJECT_PRO, "wether_expand_pro", "wetherExpandPro"),
    STATUS(TableInfo.T_BUSINESS_OBJECT_PRO, "status", "status"),
    VERSION(TableInfo.T_BUSINESS_OBJECT_PRO, "version", "version"),
    CREATE_TIME(TableInfo.T_BUSINESS_OBJECT_PRO, "create_time", "createTime"),
    CREATE_USER(TableInfo.T_BUSINESS_OBJECT_PRO, "create_user", "createUser"),
    CREATE_USER_NAME(TableInfo.T_BUSINESS_OBJECT_PRO, "create_user_name", "createUserName"),
    MODIFY_TIME(TableInfo.T_BUSINESS_OBJECT_PRO, "modify_time", "modifyTime"),
    MODIFY_USER(TableInfo.T_BUSINESS_OBJECT_PRO, "modify_user", "modifyUser"),
    MODIFY_USER_NAME(TableInfo.T_BUSINESS_OBJECT_PRO, "modify_user_name", "modifyUserName"),
    DELETE_TIME(TableInfo.T_BUSINESS_OBJECT_PRO, "delete_time", "deleteTime"),
    DELETE_USER(TableInfo.T_BUSINESS_OBJECT_PRO, "delete_user", "deleteUser"),
    DELETE_USER_NAME(TableInfo.T_BUSINESS_OBJECT_PRO, "delete_user_name", "deleteUserName"),
    DELETE_FLAG(TableInfo.T_BUSINESS_OBJECT_PRO, "delete_flag", "deleteFlag"),
    OBJ_ROW_ID(TableInfo.T_BUSINESS_OBJECT_PRO, "obj_row_id", "objRowId");

    public final ImmuteFieldInTable field;

    T_BUSINESS_OBJECT_PRO(TableInfo tableSource, String fieldName, String alies) {

      this.field = new ImmuteFieldInTable(new Field(fieldName, alies), tableSource);
      putField(tableSource, this.field, alies);
    }

    @Override
    public LinkedList<Object> getTableSource(SqlComponentTranslator translator) {
      return null;
    }

    @Override
    public String getColumnSqlFragment(SqlComponentTranslator translator) {
      return field.getColumnSqlFragment(translator);
    }

    @Override
    public String getAlies() {
      return field.getAlies();
    }

    @Override
    public String getFieldSource() {
      return field.getFieldSource();
    }
  }
}