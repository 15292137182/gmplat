DROP TABLE IF EXISTS t_sys_config;

CREATE TABLE t_sys_config (
  row_id           VARCHAR(70)  NOT NULL,
  conf_key         VARCHAR(64)  NULL,
  conf_value       VARCHAR(128) NULL,
  desp             TEXT         NULL,
  status           VARCHAR(8)   NULL,
  "version"        VARCHAR(16)  NULL,
  create_time      VARCHAR(32)  NULL,
  create_user      VARCHAR(16)  NULL,
  create_user_name VARCHAR(16)  NULL,
  modify_time      VARCHAR(32)  NULL,
  modify_user      VARCHAR(16)  NULL,
  modify_user_name VARCHAR(16)  NULL,
  delete_time      VARCHAR(32)  NULL,
  delete_user      VARCHAR(16)  NULL,
  delete_user_name VARCHAR(16)  NULL,
  delete_flag      VARCHAR(4)   NULL,
  CONSTRAINT t_sys_config_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_sys_config IS '系统资源配置';