DROP TABLE IF EXISTS t_sequence_rule_confg;

CREATE TABLE public.t_sequence_rule_config (
  row_id           VARCHAR(70)  NOT NULL,
  seq_code         VARCHAR(64)  NULL,
  seq_name         VARCHAR(128) NULL,
  seq_content      TEXT         NULL,
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
  CONSTRAINT t_sequence_rule_confg_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_sequence_rule_confg IS '序列规则配置';