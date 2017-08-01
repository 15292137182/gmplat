DROP TABLE IF EXISTS t_sequence_rule_confg;

CREATE TABLE t_sequence_rule_confg (
  row_id varchar(70) NOT NULL,
  seq_code varchar(64) NULL,
  seq_name varchar(128) NULL,
  seq_content text NULL,
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
  CONSTRAINT t_sequence_rule_confg_pkey PRIMARY KEY (row_id)
);

COMMENT ON TABLE t_sequence_rule_confg IS '序列规则配置';