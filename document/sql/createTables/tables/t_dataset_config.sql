DROP TABLE  IF EXISTS t_dataset_config;

CREATE TABLE t_dataset_config (
  row_id varchar(70) NOT NULL,
  dataset_code varchar(64) NULL,
  dataset_name varchar(128) NULL,
  dataset_type varchar(255) NULL,
  dataset_content text NULL,
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
  CONSTRAINT t_dataset_config_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_dataset_config IS '数据集配置';