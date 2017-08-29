DROP TABLE  IF EXISTS t_dataset_config;

CREATE TABLE public.t_dataset_config (
  row_id           VARCHAR(70)  NOT NULL,
  dataset_code     VARCHAR(64)  NULL,
  dataset_name     VARCHAR(128) NULL,
  dataset_type     VARCHAR(255) NULL,
  dataset_content  TEXT         NULL,
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
  belong_module    VARCHAR(32)  NULL,
  belong_system    VARCHAR(32)  NULL,
  CONSTRAINT t_dataset_config_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_dataset_config IS '数据集配置';