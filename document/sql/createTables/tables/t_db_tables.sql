DROP TABLE IF EXISTS t_db_tables;

/*==============================================================*/
/* Table: t_db_tables                                           */
/*==============================================================*/
CREATE TABLE t_db_tables (
  row_id           VARCHAR(70) NULL,
  table_schema     VARCHAR(32) NULL,
  table_ename      VARCHAR(64) NULL,
  table_cname      VARCHAR(64) NULL,
  desp             TEXT        NULL,

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

COMMENT ON TABLE t_db_tables IS
'数据库表信息';