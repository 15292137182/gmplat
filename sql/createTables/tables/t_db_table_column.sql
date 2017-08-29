DROP TABLE IF EXISTS t_db_table_column;

/*==============================================================*/
/* Table: t_db_table_column                                     */
/*==============================================================*/
CREATE TABLE t_db_table_column (
  row_id              VARCHAR(70) NULL,
  relate_table_row_id VARCHAR(70) NULL,
  column_ename        VARCHAR(64) NULL,
  column_cname        VARCHAR(64) NULL,
  desp                TEXT        NULL,

  status           VARCHAR(8)  NULL,
  version          VARCHAR(16) NULL,
  create_time      VARCHAR(32) NULL,
  create_user      VARCHAR(16) NULL,
  create_user_name VARCHAR(16) NULL,
  modify_time      VARCHAR(32) NULL,
  modify_user      VARCHAR(16) NULL,
  modify_user_name VARCHAR(16) NULL,
  delete_time      VARCHAR(32) NULL,
  delete_user      VARCHAR(16) NULL,
  delete_user_name VARCHAR(16) NULL,
  delete_flag      VARCHAR(4)  NULL
);

COMMENT ON TABLE t_db_table_column IS
'数据库表字段信息';
