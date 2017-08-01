DROP TABLE IF EXISTS t_sys_config;

CREATE TABLE t_sys_config (
  row_id varchar(70) NOT NULL,
  conf_key varchar(64) NULL,
  conf_value varchar(128) NULL,
  desp text NULL,
  status varchar(8) NULL,
  "version" varchar(16) NULL,
  create_time varchar(32) NULL,
  create_user varchar(16) NULL,
  create_user_name varchar(16) NULL,
  modify_time varchar(32) NULL,
  modify_user varchar(16) NULL,
  modify_user_name varchar(16) NULL,
  delete_time varchar(32) NULL,
  delete_user varchar(16) NULL,
  delete_user_name varchar(16) NULL,
  delete_flag varchar(4) NULL,
  CONSTRAINT t_sys_config_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_sys_config IS '系统资源配置';